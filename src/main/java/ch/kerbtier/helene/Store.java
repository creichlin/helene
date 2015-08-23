package ch.kerbtier.helene;

public interface Store extends HObject {
  public HObject get(HSlug slug);
  public boolean isAvailable(HSlug slug);
}
