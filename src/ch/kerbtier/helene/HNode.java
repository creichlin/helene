package ch.kerbtier.helene;

public interface HNode {
  void delete();
  void delete(HNode node);
  String getName(HNode node);
}
