package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class Delete extends StorImpls {
  
  public Delete(StoreFactory sf) {
    super(sf);
  }
  
  @Test
  public void deletObjectWithAttributes() {
    
    ModifiableNode mn = store.getObject("post").update();
    mn.set("title", "lalala");
    mn.set("slug", new HSlug("lolo"));
    mn.set("date", new Date());
    mn.commit();

    assertEquals("lalala", store.getObject("post").get("title"));

    store.getObject("post").delete();
    
    assertNull(store.getObject("post").get("title"));
    assertNull(store.getObject("post").get("slug"));
    assertNull(store.getObject("post").get("date"));
  }
}
