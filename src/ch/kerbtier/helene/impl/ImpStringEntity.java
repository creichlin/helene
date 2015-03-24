package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.StringEntity;

public class ImpStringEntity extends ImpEntity implements StringEntity {

  @Override
  public String getType() {
    return "str";
  }

  @Override
  public Class<?> isOf() {
    return String.class;
  }

}
