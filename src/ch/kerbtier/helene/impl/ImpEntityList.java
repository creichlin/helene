package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;

public class ImpEntityList extends ImpEntity implements EntityList {

  private Entity type;
  
  @Override
  public Entity get() {
    return type;
  }

  public void setType(ImpEntity type) {
    this.type = type;
  }

  @Override
  public String getType() {
    return "[" + type.getType() + "]";
  }

  @Override
  public Class<?> isOf() {
    return HList.class;
  }

  @Override
  public EntityMap getObject() {
    return (EntityMap)type;
  }

  @Override
  public EntityList getList() {
    return (EntityList)type;
  }

}
