package ch.kerbtier.helene;

import java.util.Set;

import ch.kerbtier.struwwel.ObserverReference;

public interface HObject extends HNode {
  ObserverReference onChange(String attrib, Runnable run);
  
  Set<String> getProperties();
  
  ModifiableNode update();
  
  void delete(String field);
}
