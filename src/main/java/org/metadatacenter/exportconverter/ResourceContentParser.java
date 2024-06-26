package org.metadatacenter.exportconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class ResourceContentParser {

  static ObjectMapper mapper = new ObjectMapper();

  public static ObjectNode parseContentJson(String content) throws IOException {
    ObjectNode jsonObj = (ObjectNode) mapper.readTree(content);

    if (jsonObj.has("_id")) {
      jsonObj.remove("_id"); // Remove the top-level _id node
    }

    return jsonObj;
  }
}

