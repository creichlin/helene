package ch.kerbtier.helene.tests;

import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.tests.util.StorImpls;
import ch.kerbtier.helene.tests.util.StoreFactory;

public class BasicStorePrimitiveArrays extends StorImpls {
  
  public BasicStorePrimitiveArrays(StoreFactory sf) {
    super(sf);
  }

  @Test(expected = WrongFieldTypeException.class)
  public void checkProperListType() {
    store.getObject("post").getStrings("hits");
  }
  
  @Test
  public void checkSizeOfEmptyList() {
    HList<Date> hits = store.getObject("post").getDates("hits");
    assertEquals(0, hits.size());
  }

  
  @Test
  public void adNewObjectToList() {
    HList<Date> hits = store.getObject("post").getDates("hits");
    Date date = new Date(10000000);
    hits.add(date);
    assertEquals(1, store.getObject("post").getDates("hits").size());
  }

  @Test
  public void add2NewObjectToList() {
    HList<Date> hits = store.getObject("post").getDates("hits");
    Date date = new Date(10000000);
    hits.add(date);
    assertEquals(new Date(10000000), store.getObject("post").getDates("hits").get(0));
  }
  
  @Test
  public void checkIterator() {
    HList<Date> dates = store.getObject("post").getDates("hits");
    
    Date d1 = new Date(12100000);
    Date d2 = new Date(12200000);
    
    dates.add(d1);
    dates.add(d2);
    
    int cnt = 0;
    for(Date d: store.getObject("post").getDates("hits")) {
      if(cnt == 0) {
        assertEquals(d1, d);
      } else {
        assertEquals(d2, d);
      }
      cnt++;
    }
  }

  @Test
  public void swapTwoObjs() {
    HList<Date> hits = store.getObject("post").getDates("hits");
    hits.add(new Date(10000));
    hits.add(new Date(20000));
    
    store.getObject("post").getDates("hits").swap(0, 1);
    
    assertEquals(new Date(20000), store.getObject("post").getDates("hits").get(0));
    assertEquals(new Date(10000), store.getObject("post").getDates("hits").get(1));
  }

  @Test
  public void addAndModNewObjectToList() {
    HList<Date> hits = store.getObject("post").getDates("hits");
    hits.add(new Date(10000000));
    
    hits.set(0, new Date(10000001));
    
    assertEquals(new Date(10000001), store.getObject("post").getDates("hits").get(0));
  }

  @Test
  public void deleteFromArray() {
    HList<Date> hits = store.getObject("post").getDates("hits");

    hits.add(new Date(10000000));
    hits.add(new Date(10000033));

    assertEquals(2, store.getObject("post").getDates("hits").size());
    
    store.getObject("post").getDates("hits").delete(1);
    assertEquals(1, store.getObject("post").getDates("hits").size());
    
    store.getObject("post").getDates("hits").delete(0);
    assertEquals(0, store.getObject("post").getDates("hits").size());
  }


}
