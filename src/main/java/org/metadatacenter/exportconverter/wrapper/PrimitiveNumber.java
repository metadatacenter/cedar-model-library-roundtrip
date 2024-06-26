package org.metadatacenter.exportconverter.wrapper;

public class PrimitiveNumber extends Primitive {
  private final Number value;

  public PrimitiveNumber(Number value) {
    this.value = value;
  }

  public Number getValue() {
    return value;
  }
}
