package ch.kerbtier.helene.impl;

import java.nio.ByteBuffer;

import ch.kerbtier.helene.entities.DateEntity;

public class ImpBlobEntity extends ImpEntity implements DateEntity {

  @Override
  public String getType() {
    return "Blob";
  }

  @Override
  public Class<?> isOf() {
    return ByteBuffer.class;
  }

}
