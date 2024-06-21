package org.metadatacenter.exportconverter.wrapper;

public class PrimitiveString extends Primitive {
  private final String value;

  public PrimitiveString(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
