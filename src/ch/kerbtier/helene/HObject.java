package ch.kerbtier.helene;

import java.util.Set;

import ch.kerbtier.helene.events.ListenerReference;

public interface HObject extends HNode {
  ListenerReference onChange(String attrib, Runnable run);
  
  Set<String> getProperties();
  
  ModifiableNode update();
  
  void delete(String field);
}
