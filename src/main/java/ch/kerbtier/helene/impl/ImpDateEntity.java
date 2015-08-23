package ch.kerbtier.helene.impl;

import java.util.Date;

import ch.kerbtier.helene.entities.DateEntity;
import ch.kerbtier.helene.entities.Entity;

public class ImpDateEntity extends ImpEntity implements DateEntity {

  public ImpDateEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public String getType() {
    return "date";
  }

  @Override
  public Class<?> isOf() {
    return Date.class;
  }

}
