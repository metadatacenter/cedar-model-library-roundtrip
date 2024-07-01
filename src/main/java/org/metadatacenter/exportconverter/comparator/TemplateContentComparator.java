package org.metadatacenter.exportconverter.comparator;

import com.fasterxml.jackson.databind.JsonNode;
import org.metadatacenter.exportconverter.model.ComparisonError;
import org.metadatacenter.exportconverter.model.ComparisonErrorType;
import org.metadatacenter.exportconverter.model.ComparisonResult;
import org.metadatacenter.exportconverter.wrapper.JsonPath;

public class TemplateContentComparator implements JsonComparator {
  @Override
  public ComparisonResult compare(JsonNode original, JsonNode reSerialized) {
    ComparisonResult result = new ComparisonResult();
    compareJsonNodes("loc", new JsonPath(), original, reSerialized, result);
    return result;
  }

  private void compareJsonNodes(String location, JsonPath path, JsonNode original, JsonNode reSerialized, ComparisonResult result) {
    if (original == null || reSerialized == null) {
      if (original != reSerialized) {
        result.addComparisonError(new ComparisonError(location, ComparisonErrorType.NULL_MISMATCH, path, null, null));
      }
      return;
    }

    if (!original.getNodeType().equals(reSerialized.getNodeType())) {
      result.addComparisonError(new ComparisonError(location, ComparisonErrorType.TYPE_MISMATCH, path, null, null));
      return;
    }

    if (original.isObject()) {
      original.fieldNames().forEachRemaining(fieldName -> {
        if (!reSerialized.has(fieldName)) {
          result.addComparisonError(new ComparisonError(location, ComparisonErrorType.MISSING_KEY_IN_REAL_OBJECT, path, null, null));
        } else {
          compareJsonNodes(location, path.add(fieldName), original.get(fieldName), reSerialized.get(fieldName), result);
        }
      });

      reSerialized.fieldNames().forEachRemaining(fieldName -> {
        if (!original.has(fieldName)) {
          result.addComparisonError(new ComparisonError(location, ComparisonErrorType.UNEXPECTED_KEY_IN_REAL_OBJECT, path.add(fieldName), null, null));
        }
      });
    } else if (original.isArray()) {
      for (int i = 0; i < original.size(); i++) {
        compareJsonNodes(location, path.add(i), original.get(i), reSerialized.get(i), result);
      }
    } else if (!original.equals(reSerialized)) {
      result.addComparisonError(new ComparisonError(location, ComparisonErrorType.VALUE_MISMATCH, path, null, null));
    }
  }
}
