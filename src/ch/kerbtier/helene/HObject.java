package ch.kerbtier.helene;

import java.math.BigInteger;
import java.util.Date;

import ch.kerbtier.helene.events.ListenerReference;

public interface HObject extends HNode {
  Object get(String name);
  
  Date getDate(String name);
  String getString(String name);
  BigInteger getBigInteger(String name);
  HeleneUser getUser(String name);
  HObject getObject(String name);

  HList<HObject> getObjects(String name);
  HList<Date> getDates(String names);
  HList<String> getStrings(String names);
  HList<BigInteger> getIntegers(String names);
  HList<HeleneUser> getUsers(String names);
  <X> HList<HList<X>> getLists(Class<X> type);
  
  ListenerReference onChange(String attrib, Runnable run);
  
  ModifiableNode update();
}
