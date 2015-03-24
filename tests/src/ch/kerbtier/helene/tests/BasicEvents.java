package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicEvents extends StorImpls {
  
  boolean eventRun = false;
  
  public BasicEvents(StoreFactory sf) {
    super(sf);
  }

  @Test
  public void changeBasicProperty() {
    
    Runnable r = new Runnable() {
      @Override
      public void run() {
        eventRun = true;
      }
    };
    
    store.getObject("post").onChange("content", r);
    
    ModifiableNode mn = store.getObject("post").update();
    mn.set("content", "hanswurst");
    assertFalse(eventRun);
    mn.commit();
    assertTrue(eventRun);
  }

}
