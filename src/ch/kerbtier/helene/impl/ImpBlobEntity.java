package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.helene.entities.BlobEntity;
import ch.kerbtier.helene.entities.Entity;

public class ImpBlobEntity extends ImpEntity implements BlobEntity {

  public ImpBlobEntity(Entity parent, String name) {
    super(parent, name);
  }

  @Override
  public String getType() {
    return "blob";
  }

  @Override
  public Class<?> isOf() {
    return HBlob.class;
  }

}
