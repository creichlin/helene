package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.helene.entities.DateEntity;

public class ImpBlobEntity extends ImpEntity implements DateEntity {

  @Override
  public String getType() {
    return "Blob";
  }

  @Override
  public Class<?> isOf() {
    return HBlob.class;
  }

}
