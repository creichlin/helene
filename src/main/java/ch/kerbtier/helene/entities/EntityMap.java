package ch.kerbtier.helene.entities;

import java.util.Set;

public interface EntityMap extends Entity, Iterable<String> {
  Entity get(String name);
  Set<String> getProperties();
  EntityMap getObject(String name);
  EntityList getList(String name);
}
