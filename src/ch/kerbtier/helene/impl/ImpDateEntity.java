package ch.kerbtier.helene.impl;

import java.util.Date;

import ch.kerbtier.helene.entities.DateEntity;

public class ImpDateEntity extends ImpEntity implements DateEntity {

  @Override
  public String getType() {
    return "Date";
  }

  @Override
  public Class<?> isOf() {
    return Date.class;
  }

}
