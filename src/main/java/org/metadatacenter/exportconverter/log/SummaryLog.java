package org.metadatacenter.exportconverter.log;

public class SummaryLog {
  private int orderNumber;
  private String type;
  private String uuid = "";
  private String id;
  private String name;
  private String computedPath;
  private String physicalPath;
  private int parsingErrorCount = 0;
  private int compareErrorCount = 0;
  private int compareWarningCount = 0;
  private boolean hasException;
  private String createdOn;
  private String lastUpdatedOn;
  private String createdBy;
  private boolean isCSV2CEDAR;

  SummaryLog(SummaryLogBuilder builder) {
    this.orderNumber = builder.orderNumber;
    this.type = builder.type;
    this.uuid = builder.uuid;
    this.id = builder.id;
    this.name = builder.name;
    this.computedPath = builder.computedPath;
    this.physicalPath = builder.physicalPath;
    this.parsingErrorCount = builder.parsingErrorCount;
    this.compareErrorCount = builder.compareErrorCount;
    this.compareWarningCount = builder.compareWarningCount;
    this.hasException = builder.hasException;
    this.createdOn = builder.createdOn;
    this.lastUpdatedOn = builder.lastUpdatedOn;
    this.createdBy = builder.createdBy;
    this.isCSV2CEDAR = builder.isCSV2CEDAR;
  }

  // Getters (optional, depending on usage)
  public int getOrderNumber() {
    return orderNumber;
  }

  public String getType() {
    return type;
  }

  public String getUuid() {
    return uuid;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getComputedPath() {
    return computedPath;
  }

  public String getPhysicalPath() {
    return physicalPath;
  }

  public int getParsingErrorCount() {
    return parsingErrorCount;
  }

  public int getCompareErrorCount() {
    return compareErrorCount;
  }

  public int getCompareWarningCount() {
    return compareWarningCount;
  }

  public boolean hasException() {
    return hasException;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public String getLastUpdatedOn() {
    return lastUpdatedOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public boolean isCSV2CEDAR() {
    return isCSV2CEDAR;
  }

}

