package ch.kerbtier.helene.tests;

import static org.junit.Assert.*;

import java.util.Date;

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
    store.getObject("post").onChange("content", new SetTrue());
    ModifiableNode mn = store.getObject("post").update();
    mn.set("content", "hanswurst");
    assertFalse(eventRun);
    mn.commit();

    assertTrue(eventRun);
  }

  @Test
  public void addElementToList() {
    store.getObject("post").getObjects("comments").onChange(new SetTrue());
    
    ModifiableNode mn = store.getObject("post").getObjects("comments").add();
    assertFalse(eventRun);
    mn.commit();
    assertTrue(eventRun);
  }

  @Test
  public void removeElementFromList() {
    
    store.getObject("post").getObjects("comments").add().commit();
    store.getObject("post").getObjects("comments").add().commit();

    store.getObject("post").getObjects("comments").onChange(new SetTrue());
    
    assertFalse(eventRun);
    store.getObject("post").getObjects("comments").get(0).delete();
    assertTrue(eventRun);
  }

  @Test
  public void swapElementInList() {
    
    store.getObject("post").getObjects("comments").add().commit();
    store.getObject("post").getObjects("comments").add().commit();

    store.getObject("post").getObjects("comments").onChange(new SetTrue());
    
    assertFalse(eventRun);
    store.getObject("post").getObjects("comments").swap(0, 1);
    assertTrue(eventRun);
  }

  @Test
  public void addElementToPList() {
    store.getObject("post").getDates("hits").onChange(new SetTrue());
    
    assertFalse(eventRun);
    store.getObject("post").getDates("hits").add(new Date());
    assertTrue(eventRun);
  }

  @Test
  public void removeElementFromPList() {
    store.getObject("post").getDates("hits").add(new Date());
    store.getObject("post").getDates("hits").add(new Date());

    store.getObject("post").getDates("hits").onChange(new SetTrue());

    assertFalse(eventRun);
    store.getObject("post").getDates("hits").delete(0);
    assertTrue(eventRun);
  }

  @Test
  public void swapElementFromPList() {
    store.getObject("post").getDates("hits").add(new Date());
    store.getObject("post").getDates("hits").add(new Date());

    store.getObject("post").getDates("hits").onChange(new SetTrue());

    assertFalse(eventRun);
    store.getObject("post").getDates("hits").swap(0, 1);
    assertTrue(eventRun);
  }

  class SetTrue implements Runnable {
    @Override
    public void run() {
      eventRun = true;
    }
  }

}
