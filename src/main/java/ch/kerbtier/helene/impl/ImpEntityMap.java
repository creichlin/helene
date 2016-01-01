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

  public ImpEntityMap(Entity parent, String name) {
    super(parent, name);
  }

  public ImpEntityMap() {
    super(null, "");
  }

  @Override
  public Iterator<String> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public Entity get(String name) {
    
    if(name.length() == 0) {
      return this;
    }
    
    if(name.indexOf(".") != -1) {
      return getByPath(name);
    }
    
    
    Entity e = map.get(name);
    if (e == null) {
      throw new UndefinedFieldException("field " + name + " does not exist in " + this.getName() + " for entity " + getName());
    }
    return e;
  }

  private Entity getByPath(String name) {
    Entity current = this;
    
    for(String part: name.split("\\.")) {
      if(part.equals("_")) {
        current = ((EntityList)current).get();
      } else {
        current = ((EntityMap)current).get(part);
      }
    }

    return current;
  }

  public void put(String key, Entity type) {
    map.put(key, type);
  }

  @Override
  public String getType() {
    return "object";
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
    return (EntityMap)get(name);
  }

  @Override
  public EntityList getList(String name) {
    return (EntityList)get(name);
  }
}
