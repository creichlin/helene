package ch.kerbtier.helene;

import java.util.Set;

public interface EntityMap extends Entity, Iterable<String> {
  DateEntity getDate(String name);
  IntegerEntity getInteger(String name);
  StringEntity getString(String name);
  UserEntity getUser(String name);
  EntityList getList(String name);
  EntityMap getMap(String name);
  Entity get(String name);
  Set<String> getProperties();
}
