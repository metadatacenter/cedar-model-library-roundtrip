package org.metadatacenter.exportconverter.log;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.metadatacenter.artifacts.model.core.Artifact;
import org.metadatacenter.artifacts.model.tools.YamlSerializer;
import org.metadatacenter.exportconverter.model.CedarExportResource;
import org.metadatacenter.exportconverter.tools.PrettyObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogProcessor {
  private String folderPrefix;


  public LogProcessor(String folderPrefix) {
    this.folderPrefix = folderPrefix;
  }

  public void processLog(ResourceLog logObject) {
    String uuid = extractUUID(logObject.getId());
    if (uuid == null) {
      System.err.println("Invalid ID format, UUID not found");
      return;
    }
    logObject.setUuid(uuid);
    logObject.setParsingErrorCount(logObject.getParsingErrors().size());
    logObject.setCompareErrorCount(logObject.getCompareResultErrors().size());
    logObject.setCompareWarningCount(logObject.getCompareResultWarnings().size());
    String shardFolder = getShardFolder(uuid);
    try {
      saveLogObject(logObject, uuid, shardFolder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String extractUUID(String id) {
    String[] parts = id.split("/");
    return parts.length > 0 ? parts[parts.length - 1] : null;
  }

  private String getShardFolder(String uuid) {
    String shardPrefix = uuid.substring(0, 2);
    return Paths.get(folderPrefix, shardPrefix).toString();
  }

  private void saveLogObject(ResourceLog logObject, String uuid, String shardFolder) throws IOException {
    Path filePath = Paths.get(shardFolder, uuid + ".json");
    Files.createDirectories(filePath.getParent());
    ObjectWriter prettyObjectWriter = PrettyObjectMapper.PRETTY_OBJECT_WRITER;
    prettyObjectWriter.writeValue(filePath.toFile(), logObject);
  }

  public void saveYAML(CedarExportResource cedarResource, ObjectNode parsedContent, Artifact artifact) {
    String uuid = extractUUID(cedarResource.getId());
    if (uuid == null) {
      System.err.println("Invalid ID format, UUID not found");
      return;
    }
    String shardFolder = getShardFolder(uuid);
    Path filePath = Paths.get(shardFolder, uuid + ".yaml");
    try {
      Files.createDirectories(filePath.getParent());
      YamlSerializer.saveYAML(artifact, false, true, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

