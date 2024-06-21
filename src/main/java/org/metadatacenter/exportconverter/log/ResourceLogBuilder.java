package org.metadatacenter.exportconverter.log;

import com.fasterxml.jackson.databind.JsonNode;
import org.metadatacenter.exportconverter.model.ComparisonError;

import java.util.List;

public class ResourceLogBuilder {
  int orderNumber = -1;
  String type = "";
  String id = "";
  String name = "";
  String computedPath = "";
  String physicalPath = "";
  List<ComparisonError> parsingErrors;
  List<ComparisonError> compareResultErrors;
  List<ComparisonError> compareResultWarnings;
  Exception exception = null;
  JsonNode sourceJSON;
  JsonNode targetJSON;

  public ResourceLogBuilder() {
  }

  public ResourceLogBuilder withOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
    return this;
  }

  public ResourceLogBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public ResourceLogBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public ResourceLogBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ResourceLogBuilder withComputedPath(String computedPath) {
    this.computedPath = computedPath;
    return this;
  }

  public ResourceLogBuilder withPhysicalPath(String physicalPath) {
    this.physicalPath = physicalPath;
    return this;
  }

  public ResourceLogBuilder withParsingErrors(List<ComparisonError> parsingErrors) {
    this.parsingErrors = parsingErrors;
    return this;
  }

  public ResourceLogBuilder withCompareResultErrors(List<ComparisonError> compareResultErrors) {
    this.compareResultErrors = compareResultErrors;
    return this;
  }

  public ResourceLogBuilder withCompareResultWarnings(List<ComparisonError> compareResultWarnings) {
    this.compareResultWarnings = compareResultWarnings;
    return this;
  }

  public ResourceLogBuilder withException(Exception exception) {
    this.exception = exception;
    return this;
  }

  public ResourceLogBuilder withSourceJSON(JsonNode sourceJSON) {
    this.sourceJSON = sourceJSON;
    return this;
  }

  public ResourceLogBuilder withTargetJSON(JsonNode targetJSON) {
    this.targetJSON = targetJSON;
    return this;
  }

  public ResourceLog build() {
    return new ResourceLog(this);
  }

  // Getters for internal use (optional)
  public int getOrderNumber() {
    return orderNumber;
  }

  public String getType() {
    return type;
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

  public List<ComparisonError> getParsingErrors() {
    return parsingErrors;
  }

  public List<ComparisonError> getCompareResultErrors() {
    return compareResultErrors;
  }

  public List<ComparisonError> getCompareResultWarnings() {
    return compareResultWarnings;
  }

  public Exception getException() {
    return exception;
  }

  public JsonNode getSourceJSON() {
    return sourceJSON;
  }

  public JsonNode getTargetJSON() {
    return targetJSON;
  }
}

