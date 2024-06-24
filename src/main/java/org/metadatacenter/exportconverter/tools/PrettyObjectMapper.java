package org.metadatacenter.exportconverter.tools;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.metadatacenter.artifacts.model.tools.CustomPrettyPrinter;

public class PrettyObjectMapper {

  public static ObjectWriter PRETTY_OBJECT_WRITER;
  public static ObjectMapper PRETTY_OBJECT_MAPPER;

  static {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JavaTimeModule());

    SimpleModule module = new SimpleModule();
    module.addSerializer(Number.class, new CustomNumberSerializer());
    module.addSerializer(JsonNode.class, new CustomJsonNodeSerializer());
    mapper.registerModule(module);

    DefaultPrettyPrinter prettyPrinter = new CustomPrettyPrinter();
    PRETTY_OBJECT_MAPPER = mapper;
    PRETTY_OBJECT_WRITER = mapper.writer(prettyPrinter);
  }
}
