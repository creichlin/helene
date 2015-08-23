package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.StringEntity;

public class ImpStringEntity extends ImpEntity implements StringEntity {

  public ImpStringEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public String getType() {
    return "str";
  }

  @Override
  public Class<?> isOf() {
    return String.class;
  }

}
