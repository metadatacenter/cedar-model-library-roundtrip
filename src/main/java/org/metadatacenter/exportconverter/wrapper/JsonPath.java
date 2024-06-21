package org.metadatacenter.exportconverter.wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonPath {
  private final List<Object> path;
  public static final String ANY = "*";

  public JsonPath(Object... pathComponents) {
    this.path = Arrays.asList(pathComponents);
  }

  public boolean equal(JsonPath another) {
    if (this.path.size() != another.path.size()) {
      return false;
    }
    for (int i = 0; i < this.path.size(); i++) {
      if (!Objects.equals(this.path.get(i), another.path.get(i))) {
        return false;
      }
    }
    return true;
  }

  public JsonPath add(Object... pathComponents) {
    List<Object> newPath = new ArrayList<>(this.path);
    newPath.addAll(Arrays.asList(pathComponents));
    return new JsonPath(newPath.toArray());
  }

  public JsonPath join(JsonPath another) {
    List<Object> newPath = new ArrayList<>(this.path);
    newPath.addAll(another.path);
    return new JsonPath(newPath.toArray());
  }

  @Override
  public String toString() {
    return this.path.stream()
        .map(component -> component instanceof Integer ? "/[" + component + "]" : "/" + component)
        .collect(Collectors.joining()) + "/";
  }

  public String toJSON() {
    return this.toString();
  }

  public Object getLastComponent() {
    if (this.path.isEmpty()) {
      return null;
    }
    return this.path.get(this.path.size() - 1);
  }

  public boolean endsIn(Object... pathComponents) {
    for (int i = 1; i <= pathComponents.length; i++) {
      if (i > this.path.size()) {
        return false;
      }
      Object pc = pathComponents[pathComponents.length - i];
      if (!Objects.equals(this.path.get(this.path.size() - i), pc) && !pc.equals(JsonPath.ANY)) {
        return false;
      }
    }
    return true;
  }

  public JsonPath getLastNComponents(int n) {
    if (n <= 0 || this.path.isEmpty()) {
      return new JsonPath();
    }
    int startIdx = Math.max(this.path.size() - n, 0);
    List<Object> lastNComponents = this.path.subList(startIdx, this.path.size());
    return new JsonPath(lastNComponents.toArray());
  }
}
