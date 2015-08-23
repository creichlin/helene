package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.IntegerEntity;

public class ImpIntegerEntity extends ImpEntity implements IntegerEntity {

  public ImpIntegerEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public String getType() {
    return "int";
  }

  @Override
  public Class<?> isOf() {
    return Integer.class;
  }
}
