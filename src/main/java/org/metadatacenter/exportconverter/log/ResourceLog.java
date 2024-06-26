package org.metadatacenter.exportconverter.log;

import com.fasterxml.jackson.databind.JsonNode;
import org.metadatacenter.exportconverter.model.ComparisonError;

import java.util.List;

public class ResourceLog {
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
  private List<ComparisonError> parsingErrors;
  private List<ComparisonError> compareResultErrors;
  private List<ComparisonError> compareResultWarnings;
  private Exception exception;
  private JsonNode sourceJSON;
  private JsonNode targetJSON;

  ResourceLog(ResourceLogBuilder builder) {
    this.orderNumber = builder.orderNumber;
    this.type = builder.type;
    this.uuid = "";
    this.id = builder.id;
    this.name = builder.name;
    this.computedPath = builder.computedPath;
    this.physicalPath = builder.physicalPath;
    this.parsingErrors = builder.parsingErrors;
    this.compareResultErrors = builder.compareResultErrors;
    this.compareResultWarnings = builder.compareResultWarnings;
    this.exception = builder.exception;
    this.sourceJSON = builder.sourceJSON;
    this.targetJSON = builder.targetJSON;
  }

  // Getters and Setters (optional based on usage)
  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComputedPath() {
    return computedPath;
  }

  public void setComputedPath(String computedPath) {
    this.computedPath = computedPath;
  }

  public String getPhysicalPath() {
    return physicalPath;
  }

  public void setPhysicalPath(String physicalPath) {
    this.physicalPath = physicalPath;
  }

  public int getParsingErrorCount() {
    return parsingErrorCount;
  }

  public void setParsingErrorCount(int parsingErrorCount) {
    this.parsingErrorCount = parsingErrorCount;
  }

  public int getCompareErrorCount() {
    return compareErrorCount;
  }

  public void setCompareErrorCount(int compareErrorCount) {
    this.compareErrorCount = compareErrorCount;
  }

  public int getCompareWarningCount() {
    return compareWarningCount;
  }

  public void setCompareWarningCount(int compareWarningCount) {
    this.compareWarningCount = compareWarningCount;
  }

  public List<ComparisonError> getParsingErrors() {
    return parsingErrors;
  }

  public void setParsingErrors(List<ComparisonError> parsingErrors) {
    this.parsingErrors = parsingErrors;
  }

  public List<ComparisonError> getCompareResultErrors() {
    return compareResultErrors;
  }

  public void setCompareResultErrors(List<ComparisonError> compareResultErrors) {
    this.compareResultErrors = compareResultErrors;
  }

  public List<ComparisonError> getCompareResultWarnings() {
    return compareResultWarnings;
  }

  public void setCompareResultWarnings(List<ComparisonError> compareResultWarnings) {
    this.compareResultWarnings = compareResultWarnings;
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public JsonNode getSourceJSON() {
    return sourceJSON;
  }

  public void setSourceJSON(JsonNode sourceJSON) {
    this.sourceJSON = sourceJSON;
  }

  public JsonNode getTargetJSON() {
    return targetJSON;
  }

  public void setTargetJSON(JsonNode targetJSON) {
    this.targetJSON = targetJSON;
  }

}
