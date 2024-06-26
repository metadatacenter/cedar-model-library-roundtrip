package org.metadatacenter.exportconverter.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomNumberSerializer extends StdSerializer<Number> {

  public CustomNumberSerializer() {
    super(Number.class);
  }

  @Override
  public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value instanceof Double || value instanceof Float) {
      if (value.doubleValue() % 1.0 == 0.0) {
        gen.writeNumber(value.longValue());
      } else {
        gen.writeNumber(value.doubleValue());
      }
    } else if (value instanceof Long) {
      gen.writeNumber(value.longValue());
    } else if (value instanceof Integer) {
      gen.writeNumber(value.intValue());
    } else {
      gen.writeNumber(value.toString());
    }
  }
}


