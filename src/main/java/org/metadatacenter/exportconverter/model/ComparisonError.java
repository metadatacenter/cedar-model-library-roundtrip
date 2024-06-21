package org.metadatacenter.exportconverter.model;

import org.metadatacenter.exportconverter.wrapper.JsonPath;
import org.metadatacenter.exportconverter.wrapper.Primitive;
import org.metadatacenter.exportconverter.model.ComparisonErrorType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComparisonError {
  private final String errorLocation;
  private final ComparisonErrorType errorType;
  private final JsonPath errorPath;
  private final Primitive expectedValue;
  private final Primitive encounteredValue;
  private List<String> stackTopLines;

  public ComparisonError(
      String errorLocation,
      ComparisonErrorType errorType,
      JsonPath errorPath,
      Primitive expectedValue,
      Primitive encounteredValue
  ) {
    this.errorLocation = errorLocation;
    this.errorType = errorType;
    this.errorPath = errorPath;
    this.expectedValue = expectedValue;
    this.encounteredValue = encounteredValue;
    if (false) {
      parseStackTrace();
    }
  }

  private void parseStackTrace() {
    Exception logError = new Exception();
    StackTraceElement[] stackTrace = logError.getStackTrace();
    stackTopLines = Arrays.stream(stackTrace)
        .skip(3)
        .limit(9)
        .map(StackTraceElement::toString)
        .collect(Collectors.toList());
  }

  // Getters for all fields
  public String getErrorLocation() {
    return errorLocation;
  }

  public ComparisonErrorType getErrorType() {
    return errorType;
  }

  public JsonPath getErrorPath() {
    return errorPath;
  }

  public Primitive getExpectedValue() {
    return expectedValue;
  }

  public Primitive getEncounteredValue() {
    return encounteredValue;
  }

  public List<String> getStackTopLines() {
    return stackTopLines;
  }
}

