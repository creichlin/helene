package ch.kerbtier.helene.impl;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.entities.SlugEntity;

public class ImpSlugEntity extends ImpEntity implements SlugEntity {

  @Override
  public Class<?> isOf() {
    return HSlug.class;
  }

  @Override
  public String getType() {
    return "slug";
  }

}
