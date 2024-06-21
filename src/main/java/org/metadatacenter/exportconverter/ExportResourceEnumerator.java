package org.metadatacenter.exportconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.metadatacenter.artifacts.model.core.ElementSchemaArtifact;
import org.metadatacenter.artifacts.model.core.FieldSchemaArtifact;
import org.metadatacenter.artifacts.model.core.TemplateSchemaArtifact;
import org.metadatacenter.artifacts.model.reader.JsonSchemaArtifactReader;
import org.metadatacenter.artifacts.model.renderer.JsonSchemaArtifactRenderer;
import org.metadatacenter.exportconverter.comparator.ElementContentComparator;
import org.metadatacenter.exportconverter.comparator.FieldContentComparator;
import org.metadatacenter.exportconverter.comparator.InstanceContentComparator;
import org.metadatacenter.exportconverter.comparator.TemplateContentComparator;
import org.metadatacenter.exportconverter.log.*;
import org.metadatacenter.exportconverter.model.CedarExportResource;
import org.metadatacenter.exportconverter.model.ComparisonError;
import org.metadatacenter.exportconverter.model.ComparisonResult;
import org.metadatacenter.exportconverter.wrapper.ErrorKey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExportResourceEnumerator {
  private final String sourceDir;
  private final String targetDir;

  private final ObjectMapper mapper;
  private int orderCounter = 0;
  private final LogProcessor logProcessor;
  private final SummaryLogProcessor summaryLogProcessor;
  private int counter = 0;
  private final Map<String, Integer> errorStats = new HashMap<>();
  private final Map<String, Integer> errorStatsLast2 = new HashMap<>();
  private final List<SummaryLog> logSummary = new ArrayList<>();
  private static TemplateContentComparator templateContentComparator;
  private static ElementContentComparator elementContentComparator;
  private static FieldContentComparator fieldContentComparator;
  private static InstanceContentComparator instanceContentComparator;
  private static JsonSchemaArtifactReader artifactReader;
  private static JsonSchemaArtifactRenderer jsonSchemaArtifactRenderer;


  public ExportResourceEnumerator(String sourceDir, String targetDir) {
    this.sourceDir = Path.of(sourceDir, "resources").toString();
    this.targetDir = targetDir;
    this.logProcessor = new LogProcessor(this.targetDir);
    this.summaryLogProcessor = new SummaryLogProcessor(this.targetDir);
    this.mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    this.templateContentComparator = new TemplateContentComparator();
    this.elementContentComparator = new ElementContentComparator();
    this.fieldContentComparator = new FieldContentComparator();
    this.instanceContentComparator = new InstanceContentComparator();
    this.artifactReader = new JsonSchemaArtifactReader();
    this.jsonSchemaArtifactRenderer = new JsonSchemaArtifactRenderer();
  }

  public void parse() {
    this.counter = 0;
    parseDirectory(this.sourceDir, "");
    summaryLogProcessor.saveLogObject(logSummary);
    summaryLogProcessor.saveErrorStats(errorStats, "errors-all.json");
    summaryLogProcessor.saveErrorStats(errorStatsLast2, "errors-last-2.json");
    System.out.println("Total logged: " + logSummary.size());
  }

  private void parseDirectory(String directoryPath, String virtualPath) {
    File directory = new File(directoryPath);
    File[] shardDirectories = directory.listFiles(File::isDirectory);
    if (shardDirectories == null) {
      return;
    }
    Arrays.sort(shardDirectories, Comparator.comparing(File::getName));

    for (File shardDirectory : shardDirectories) {
      File[] zipFiles = shardDirectory.listFiles(file -> file.isFile() && file.getName().endsWith(".zip"));
      if (zipFiles == null) {
        continue;
      }
      Arrays.sort(zipFiles, Comparator.comparing(File::getName));

      for (File zipFile : zipFiles) {
        try {
          processZipFile(zipFile.getAbsolutePath(), virtualPath);
        } catch (Exception e) {
          System.err.println("Error processing file " + zipFile.getAbsolutePath() + ": " + e.getMessage());
        }
      }
    }
  }

  private void processZipFile(String zipFilePath, String virtualPath) throws IOException {
    try (ZipFile zipFile = new ZipFile(zipFilePath)) {
      ZipEntry resourceEntry = zipFile.getEntry("resource.json");
      if (resourceEntry == null) {
        return;
      }
      try (InputStream stream = zipFile.getInputStream(resourceEntry);
           Scanner scanner = new Scanner(stream).useDelimiter("\\A")) {
        String resourceJson = scanner.hasNext() ? scanner.next() : "";
        JsonNode resourceObject = mapper.readTree(resourceJson);
        CedarExportResource cedarResource = new CedarExportResource(
            resourceObject.get("resourceType").textValue(),
            resourceObject.get("@id").textValue(),
            resourceObject.get("schema:name").textValue(),
            Path.of(virtualPath, resourceObject.get("schema:name").textValue()).toString(),
            zipFilePath.replace(this.sourceDir, ""),
            ++orderCounter
        );

        if ("folder".equals(cedarResource.getType())) {
          String contentFolderPath = zipFilePath.replace(".zip", "");
          if (Files.exists(Paths.get(contentFolderPath))) {
            parseDirectory(contentFolderPath, cedarResource.getComputedPath());
          }
        } else {
          ZipEntry contentEntry = zipFile.getEntry("content.json");
          if (contentEntry != null) {
            try (InputStream contentStream = zipFile.getInputStream(contentEntry);
                 Scanner contentScanner = new Scanner(contentStream).useDelimiter("\\A")) {
              String contentJson = contentScanner.hasNext() ? contentScanner.next() : "";
              handleContentJson(cedarResource, contentJson);
            }
          }
        }
      }
    }
  }

  private void handleContentJson(CedarExportResource cedarResource, String contentJson) {
    List<ComparisonError> parsingResultErrors = new ArrayList<>();
    List<ComparisonError> compareResultErrors = new ArrayList<>();
    List<ComparisonError> compareResultWarnings = new ArrayList<>();
    ObjectNode parsedContent = null;
    ObjectNode reSerialized = null;

    Exception exception = null;

    boolean doSave = true;

    try {
      parsedContent = ResourceContentParser.parseContentJson(contentJson);
      switch (cedarResource.getType()) {
        case "template":
          TemplateSchemaArtifact template = artifactReader.readTemplateSchemaArtifact(parsedContent);
          reSerialized = jsonSchemaArtifactRenderer.renderTemplateSchemaArtifact(template);
          compareResults(templateContentComparator.compare(parsedContent, reSerialized));
          break;
        case "element":
          ElementSchemaArtifact element = artifactReader.readElementSchemaArtifact(parsedContent);
          reSerialized = jsonSchemaArtifactRenderer.renderElementSchemaArtifact(element);
          compareResults(elementContentComparator.compare(parsedContent, reSerialized));
          break;
        case "field":
          FieldSchemaArtifact field = artifactReader.readFieldSchemaArtifact(parsedContent);
          reSerialized = jsonSchemaArtifactRenderer.renderFieldSchemaArtifact(field);
          compareResults(fieldContentComparator.compare(parsedContent, reSerialized));
          break;
        case "instance":
          compareResults(instanceContentComparator.compare(parsedContent, reSerialized));
          doSave = false;
          break;
      }
    } catch (Exception e) {
      exception = e;
    }

    if (doSave) {
      logErrors(parsingResultErrors, compareResultErrors, compareResultWarnings);
      logResource(cedarResource, parsedContent, reSerialized, parsingResultErrors, compareResultErrors, compareResultWarnings, exception);
    }
  }

//  private void compareResults(
//      ComparisonResult result,
//      List<ComparisonError> parsingResultErrors,
//      List<ComparisonError> compareResultErrors,
//      List<ComparisonError> compareResultWarnings,
//      ObjectNode reSerialized) {
//    compareResultErrors.addAll(result.getComparisonErrors());
//    compareResultWarnings.addAll(result.getComparisonWarnings());
//    reSerialized.put("result", mapper.valueToTree(result));
//  }

  private void compareResults(
      ComparisonResult result
  ) {
    //TODO nothing
  }

  private void logErrors(List<ComparisonError> parsingResultErrors, List<ComparisonError> compareResultErrors, List<ComparisonError> compareResultWarnings) {
    for (ComparisonError error : parsingResultErrors) {
      updateErrorStats(error);
    }
    for (ComparisonError error : compareResultErrors) {
      updateErrorStats(error);
    }
    for (ComparisonError error : compareResultWarnings) {
      updateErrorStats(error);
    }
  }

  private void updateErrorStats(ComparisonError error) {
    String key = ErrorKey.fromComparisonError(error).toString();
    errorStats.put(key, errorStats.getOrDefault(key, 0) + 1);
    String key2 = ErrorKey.fromComparisonErrorPartial(error, 2).toString();
    errorStatsLast2.put(key2, errorStatsLast2.getOrDefault(key2, 0) + 1);
  }

  private void logResource(CedarExportResource cedarResource, JsonNode parsedContent, JsonNode reSerialized,
                           List<ComparisonError> parsingResultErrors, List<ComparisonError> compareResultErrors,
                           List<ComparisonError> compareResultWarnings, Exception exception) {
    ResourceLog logObject = new ResourceLogBuilder()
        .withOrderNumber(cedarResource.getOrderNumber())
        .withId(cedarResource.getId())
        .withName(cedarResource.getName())
        .withComputedPath(cedarResource.getComputedPath())
        .withType(cedarResource.getType())
        .withPhysicalPath(cedarResource.getPhysicalPath())
        .withParsingErrors(parsingResultErrors)
        .withCompareResultErrors(compareResultErrors)
        .withCompareResultWarnings(compareResultWarnings)
        .withSourceJSON(parsedContent)
        .withTargetJSON(reSerialized)
        .withException(exception)
        .build();
    logProcessor.processLog(logObject);

    SummaryLogBuilder builder = new SummaryLogBuilder()
        .withOrderNumber(logObject.getOrderNumber())
        .withType(logObject.getType())
        .withUUID(logObject.getUuid())
        .withId(logObject.getId())
        .withName(logObject.getName())
        .withComputedPath(logObject.getComputedPath())
        .withPhysicalPath(logObject.getPhysicalPath())
        .withParsingErrorCount(logObject.getParsingErrorCount())
        .withCompareErrorCount(logObject.getCompareErrorCount())
        .withCompareWarningCount(logObject.getCompareWarningCount())
        .withHasException(logObject.getException() != null)
        .withCreatedOn(parsedContent.get("pav:createdOn").textValue())
        .withLastUpdatedOn(parsedContent.get("pav:lastUpdatedOn").textValue())
        .withCreatedBy(parsedContent.get("pav:createdBy").textValue());

    if (parsedContent.has("description") && parsedContent.get("description").toString() != null) {
      builder.withCSV2CEDAR(parsedContent.get("description").toString().contains("CSV2CEDAR"));
    }

    logSummary.add(builder.build());
    counter++;
    if (counter % 1000 == 0) {
      System.out.println(counter);
    }
  }

}
