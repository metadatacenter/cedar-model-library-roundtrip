package org.metadatacenter.exportconverter.log;


public class SummaryLogBuilder {
  int orderNumber;
  String type;
  String uuid = "";
  String id;
  String name;
  String computedPath;
  String physicalPath;
  int parsingErrorCount = 0;
  int compareErrorCount = 0;
  int compareWarningCount = 0;
  boolean hasException;
  String createdOn;
  String lastUpdatedOn;
  String createdBy;
  boolean isCSV2CEDAR;

  public SummaryLogBuilder withOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  public SummaryLogBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public SummaryLogBuilder withUUID(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public SummaryLogBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public SummaryLogBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public SummaryLogBuilder withComputedPath(String computedPath) {
    this.computedPath = computedPath;
    return this;
  }

  public SummaryLogBuilder withPhysicalPath(String physicalPath) {
    this.physicalPath = physicalPath;
    return this;
  }

  public SummaryLogBuilder withParsingErrorCount(int parsingErrorCount) {
    this.parsingErrorCount = parsingErrorCount;
    return this;
  }

  public SummaryLogBuilder withCompareErrorCount(int compareErrorCount) {
    this.compareErrorCount = compareErrorCount;
    return this;
  }

  public SummaryLogBuilder withCompareWarningCount(int compareWarningCount) {
    this.compareWarningCount = compareWarningCount;
    return this;
  }

  public SummaryLogBuilder withHasException(boolean hasException) {
    this.hasException = hasException;
    return this;
  }

  public SummaryLogBuilder withCreatedOn(String createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  public SummaryLogBuilder withLastUpdatedOn(String lastUpdatedOn) {
    this.lastUpdatedOn = lastUpdatedOn;
    return this;
  }

  public SummaryLogBuilder withCreatedBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  public SummaryLogBuilder withCSV2CEDAR(boolean isCSV2CEDAR) {
    this.isCSV2CEDAR = isCSV2CEDAR;
    return this;
  }

  public SummaryLog build() {
    return new SummaryLog(this);
  }
}
