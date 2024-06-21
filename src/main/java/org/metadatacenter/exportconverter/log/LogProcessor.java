package org.metadatacenter.exportconverter.log;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.metadatacenter.artifacts.model.tools.CustomPrettyPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogProcessor {
  private String folderPrefix;

  private static ObjectWriter PRETTY_OBJECT_WRITER;
  static {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JavaTimeModule());
    DefaultPrettyPrinter prettyPrinter = new CustomPrettyPrinter();
    PRETTY_OBJECT_WRITER = mapper.writer(prettyPrinter);
  }


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
    PRETTY_OBJECT_WRITER.writeValue(filePath.toFile(), logObject);
  }
}

