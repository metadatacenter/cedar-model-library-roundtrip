package org.metadatacenter.exportconverter.model;

public enum OutputFormat {
  NULL_AUTH(null),
  JSON("json"),
  YAML("yaml");

  private final String value;

  OutputFormat(String value) {
    this.value = value;
  }
}
