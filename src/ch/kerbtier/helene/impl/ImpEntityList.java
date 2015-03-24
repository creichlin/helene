package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.DateEntity;
import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.EntityList;
import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.IntegerEntity;
import ch.kerbtier.helene.StringEntity;
import ch.kerbtier.helene.UserEntity;

public class ImpEntityList extends ImpEntity implements EntityList {

  private Entity type;
  
  @Override
  public DateEntity getDate() {
    return (DateEntity)type;
  }

  @Override
  public IntegerEntity getInteger() {
    return (IntegerEntity)type;
  }

  @Override
  public StringEntity getString() {
    return (StringEntity)type;
  }

  @Override
  public UserEntity getUser() {
    return (UserEntity)type;
  }

  @Override
  public EntityList getList() {
    return (EntityList)type;
  }

  @Override
  public EntityMap getMap() {
    return (EntityMap)type;
  }

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

}
