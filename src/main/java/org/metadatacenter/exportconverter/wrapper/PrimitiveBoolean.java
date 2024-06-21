package org.metadatacenter.exportconverter.wrapper;

public class PrimitiveBoolean extends Primitive {
  private final Boolean value;

  public PrimitiveBoolean(Boolean value) {
    this.value = value;
  }

  public Boolean getValue() {
    return value;
  }
}
