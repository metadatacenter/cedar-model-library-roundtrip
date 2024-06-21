package org.metadatacenter.exportconverter.model;

import java.util.Arrays;
import java.util.List;

public class ComparisonErrorType {
  private final String value;

  private ComparisonErrorType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static final ComparisonErrorType MISSING_KEY_IN_REAL_OBJECT = new ComparisonErrorType("missingKeyInRealObject");
  public static final ComparisonErrorType UNEXPECTED_KEY_IN_REAL_OBJECT = new ComparisonErrorType("unexpectedKeyInRealObject");
  public static final ComparisonErrorType VALUE_MISMATCH = new ComparisonErrorType("valueMismatch");
  public static final ComparisonErrorType MISSING_INDEX_IN_REAL_OBJECT = new ComparisonErrorType("missingIndexInRealObject");
  public static final ComparisonErrorType UNEXPECTED_INDEX_IN_REAL_OBJECT = new ComparisonErrorType("unexpectedIndexInRealObject");
  public static final ComparisonErrorType MISSING_VALUE_IN_REAL_OBJECT = new ComparisonErrorType("missingValueInRealObject");
  public static final ComparisonErrorType UNEXPECTED_VALUE_IN_REAL_OBJECT = new ComparisonErrorType("unexpectedValueInRealObject");
  public static final ComparisonErrorType NULL_MISMATCH = new ComparisonErrorType("nullMismatch");
  public static final ComparisonErrorType TYPE_MISMATCH = new ComparisonErrorType("typeMismatch");

  public static final ComparisonErrorType NULL = new ComparisonErrorType(null);

  public static List<ComparisonErrorType> values() {
    return Arrays.asList(MISSING_KEY_IN_REAL_OBJECT, UNEXPECTED_KEY_IN_REAL_OBJECT, VALUE_MISMATCH, MISSING_INDEX_IN_REAL_OBJECT, UNEXPECTED_INDEX_IN_REAL_OBJECT, MISSING_VALUE_IN_REAL_OBJECT,
        UNEXPECTED_VALUE_IN_REAL_OBJECT);
  }

  public static ComparisonErrorType forValue(String value) {
    for (ComparisonErrorType type : values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    return NULL;
  }

  @Override
  public String toString() {
    return value;
  }
}

