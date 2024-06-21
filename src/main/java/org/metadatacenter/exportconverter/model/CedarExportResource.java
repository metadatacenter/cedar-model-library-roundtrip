package org.metadatacenter.exportconverter.model;

public class CedarExportResource {
  private String type;
  private String id;
  private String name;
  private String computedPath;
  private String physicalPath;
  private int orderNumber;

  public CedarExportResource(String type, String id, String name, String computedPath, String physicalPath, int orderNumber) {
    this.type = type;
    this.id = id;
    this.name = name;
    this.computedPath = computedPath;
    this.physicalPath = physicalPath;
    this.orderNumber = orderNumber;
  }

  // Getter for orderNumber
  public int getOrderNumber() {
    return orderNumber;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getComputedPath() {
    return computedPath;
  }

  public String getPhysicalPath() {
    return physicalPath;
  }
}

