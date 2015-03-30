package ch.kerbtier.helene;

public class HSlug {
  private String value;

  public HSlug(String value) {
    if (value.startsWith("#")) {
      this.value = value.substring(1);
    } else {
      this.value = value;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof HSlug) {
      return value.equals(((HSlug) o).value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "Slug[" + value.toString() + "]";
  }
  
  public String getValue() {
    return value;
  }
}
