package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.Parse;
import ch.kerbtier.helene.impl.ImpEntityMap;

public class BasicEntity {
  
  private EntityMap root;
  
  @Before
  public void init() {
    root = new ImpEntityMap();
    Parse.extend(root, Paths.get("tests", "post.model"));
  }
  

  @Test()
  public void checkExistingMapType() {
    Entity entity = root.get("post");
    assertTrue(entity instanceof EntityMap);
  }

  @Test()
  public void checkExistingMapTypeWithIs() {
    Entity entity = root.get("post");
    assertTrue(entity.is(HObject.class));
  }
}
