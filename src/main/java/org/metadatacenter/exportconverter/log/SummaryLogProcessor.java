package org.metadatacenter.exportconverter.log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SummaryLogProcessor {
  private String folderPrefix;

  public SummaryLogProcessor(String folderPrefix) {
    this.folderPrefix = folderPrefix;
  }

  public void saveLogObject(List<SummaryLog> logObject) {
    String filePath = Paths.get(folderPrefix, "summary.json").toString();
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), logObject);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<ErrorStat> transformAndSortErrorStats(Map<String, Integer> errorStats) {
    List<ErrorStat> errorStatsArray = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : errorStats.entrySet()) {
      String[] parts = entry.getKey().split(":");
      String errorType = parts[0];
      String errorPath = parts.length > 1 ? parts[1] : "";
      errorStatsArray.add(new ErrorStat(errorType, errorPath, entry.getValue()));
    }
    errorStatsArray.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
    return errorStatsArray;
  }

  public void saveErrorStats(Map<String, Integer> errorStats, String fileName) {
    List<ErrorStat> transformedAndSortedErrorStats = transformAndSortErrorStats(errorStats);
    String filePath = Paths.get(folderPrefix, fileName).toString();
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), transformedAndSortedErrorStats);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class ErrorStat {
    private String errorType;
    private String errorPath;
    private int count;

    public ErrorStat(String errorType, String errorPath, int count) {
      this.errorType = errorType;
      this.errorPath = errorPath;
      this.count = count;
    }

    public String getErrorType() {
      return errorType;
    }

    public void setErrorType(String errorType) {
      this.errorType = errorType;
    }

    public String getErrorPath() {
      return errorPath;
    }

    public void setErrorPath(String errorPath) {
      this.errorPath = errorPath;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }
}

