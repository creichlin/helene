package ch.kerbtier.helene;

public interface EntityList extends Entity {
  DateEntity getDate();
  IntegerEntity getInteger();
  StringEntity getString();
  UserEntity getUser();
  EntityList getList();
  EntityMap getMap();
  Entity get();
}
