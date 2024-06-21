package org.metadatacenter.exportconverter.model;

import java.util.ArrayList;
import java.util.List;

public class ComparisonResult {
  private final List<ComparisonError> comparisonErrors = new ArrayList<>();
  private final List<ComparisonError> comparisonWarnings = new ArrayList<>();

  public void addComparisonError(ComparisonError error) {
    comparisonErrors.add(error);
  }

  public List<ComparisonError> getComparisonErrors() {
    return new ArrayList<>(comparisonErrors); // Return a copy to avoid external modification
  }

  public int getComparisonErrorCount() {
    return comparisonErrors.size();
  }

  public void addComparisonWarning(ComparisonError warning) {
    comparisonWarnings.add(warning);
  }

  public List<ComparisonError> getComparisonWarnings() {
    return new ArrayList<>(comparisonWarnings); // Return a copy to avoid external modification
  }

  public int getComparisonWarningCount() {
    return comparisonWarnings.size();
  }

  public boolean areEqual() {
    return comparisonErrors.isEmpty() && comparisonWarnings.isEmpty();
  }

  public void merge(ComparisonResult otherResult) {
    comparisonErrors.addAll(otherResult.comparisonErrors);
    comparisonWarnings.addAll(otherResult.comparisonWarnings);
  }
}

