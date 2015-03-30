package ch.kerbtier.helene.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.UndefinedFieldException;

public class ImpEntityMap extends ImpEntity implements EntityMap {

  private Map<String, Entity> map = new HashMap<>();

  @Override
  public Iterator<String> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public Entity get(String name) {
    Entity e = map.get(name);
    if (e == null) {
      throw new UndefinedFieldException("field " + name + " does not exist in " + this);
    }
    return e;
  }

  public void put(String key, Entity type) {
    map.put(key, type);
  }

  @Override
  public String getType() {
    return "{}";
  }

  @Override
  public Class<?> isOf() {
    return HObject.class;
  }

  @Override
  public Set<String> getProperties() {
    return map.keySet();
  }

  @Override
  public EntityMap getObject(String name) {
    return (EntityMap)map.get(name);
  }

  @Override
  public EntityList getList(String name) {
    return (EntityList)map.get(name);
  }
}
