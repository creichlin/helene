package ch.kerbtier.helene.entities;

public interface EntityList extends Entity {
  Entity get();
  EntityMap getObject();
  EntityList getList();
}
