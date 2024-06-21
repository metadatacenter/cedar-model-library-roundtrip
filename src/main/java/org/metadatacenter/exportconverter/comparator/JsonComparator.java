package org.metadatacenter.exportconverter.comparator;

import com.fasterxml.jackson.databind.JsonNode;
import org.metadatacenter.exportconverter.model.ComparisonResult;

public interface JsonComparator {
  ComparisonResult compare(JsonNode original, JsonNode reSerialized);
}

