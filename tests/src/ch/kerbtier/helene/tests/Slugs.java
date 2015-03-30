package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.DuplicateSlugException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class Slugs extends StorImpls {
  
  public Slugs(StoreFactory sf) {
    super(sf);
  }
  
  @Test
  public void getSlug() {
    ModifiableNode mn = store.getObject("post").getObjects("comments").add();
    mn.set("name", "lazarus");
    mn.set("slug", new HSlug("terror"));
    HObject d = mn.commit();
    
    assertEquals(d, store.getObject("#terror"));
  }

  @Test(expected = DuplicateSlugException.class)
  public void saveDuplicateSlug() {
    ModifiableNode mn = store.getObject("post").getObjects("comments").add();
    mn.set("name", "lazarus");
    mn.set("slug", new HSlug("terror"));
    mn.commit();
    
    mn = store.getObject("post").getObjects("comments").add();
    mn.set("name", "lazarus2");
    mn.set("slug", new HSlug("terror"));
    mn.commit();
  }
}
