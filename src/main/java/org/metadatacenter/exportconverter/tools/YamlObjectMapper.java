package org.metadatacenter.exportconverter.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;

public class YamlObjectMapper {

  public static ObjectMapper YAML_OBJECT_MAPPER;

  static {
    YAMLFactory yamlFactory = new YAMLFactory()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .disable(YAMLGenerator.Feature.MINIMIZE_QUOTES) //enable this
        .enable(YAMLGenerator.Feature.USE_PLATFORM_LINE_BREAKS)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
        .disable(YAMLGenerator.Feature.SPLIT_LINES) //enable this
        .disable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE);

    YAML_OBJECT_MAPPER = new ObjectMapper(yamlFactory);
    YAML_OBJECT_MAPPER.registerModule(new Jdk8Module());
    YAML_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);

    // Register custom serializer module
    SimpleModule module = new SimpleModule();
    module.addSerializer(Double.class, new CustomDoubleSerializer());
    module.addSerializer(Float.class, new CustomFloatSerializer());
    YAML_OBJECT_MAPPER.registerModule(module);
  }

  // Custom serializer for Double
  public static class CustomDoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (value == value.intValue()) {
        gen.writeNumber(value.intValue());
      } else {
        gen.writeNumber(value);
      }
    }
  }

  // Custom serializer for Float
  public static class CustomFloatSerializer extends JsonSerializer<Float> {
    @Override
    public void serialize(Float value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      if (value == value.intValue()) {
        gen.writeNumber(value.intValue());
      } else {
        gen.writeNumber(value);
      }
    }
  }
}
