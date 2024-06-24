package org.metadatacenter.exportconverter.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomJsonNodeSerializer extends StdSerializer<JsonNode> {

  public CustomJsonNodeSerializer() {
    super(JsonNode.class);
  }

  @Override
  public void serialize(JsonNode value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    if (value instanceof DoubleNode) {
      double doubleValue = value.doubleValue();
      if (doubleValue % 1.0 == 0.0) {
        gen.writeNumber((long) doubleValue);
      } else {
        gen.writeNumber(doubleValue);
      }
    } else {
      switch (value.getNodeType()) {
        case NUMBER:
          gen.writeNumber(value.numberValue().toString());
          break;
        case STRING:
          gen.writeString(value.textValue());
          break;
        case BOOLEAN:
          gen.writeBoolean(value.booleanValue());
          break;
        case NULL:
          gen.writeNull();
          break;
        case OBJECT:
          gen.writeStartObject();
          value.fields().forEachRemaining(entry -> {
            try {
              gen.writeFieldName(entry.getKey());
              serialize(entry.getValue(), gen, provider);
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
          gen.writeEndObject();
          break;
        case ARRAY:
          gen.writeStartArray();
          for (JsonNode item : value) {
            serialize(item, gen, provider);
          }
          gen.writeEndArray();
          break;
        default:
          gen.writeObject(value);
          break;
      }
    }
  }
}
