package org.metadatacenter.exportconverter.wrapper;

import org.metadatacenter.exportconverter.model.ComparisonError;

public class ErrorKey {
  private String errorType;
  private String errorPath;

  public ErrorKey(String errorType, String errorPath) {
    this.errorType = errorType;
    this.errorPath = errorPath;
  }

  @Override
  public String toString() {
    return errorType + ":" + errorPath;
  }

  public static ErrorKey fromComparisonError(ComparisonError error) {
    return new ErrorKey(
        error.getErrorType() != null ? error.getErrorType().getValue() : "",
        error.getErrorPath() != null ? error.getErrorPath().toString() : ""
    );
  }

  public static ErrorKey fromComparisonErrorPartial(ComparisonError error, int lastN) {
    return new ErrorKey(
        error.getErrorType() != null ? error.getErrorType().getValue() : "",
        error.getErrorPath() != null ? error.getErrorPath().getLastNComponents(lastN).toString() : ""
    );
  }
}

