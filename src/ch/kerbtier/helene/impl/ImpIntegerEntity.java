package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.IntegerEntity;

public class ImpIntegerEntity extends ImpEntity implements IntegerEntity {

  @Override
  public String getType() {
    return "int";
  }

  @Override
  public Class<?> isOf() {
    return Integer.class;
  }
}
