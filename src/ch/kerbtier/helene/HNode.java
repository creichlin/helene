package ch.kerbtier.helene;

import java.math.BigInteger;
import java.util.Date;

public interface HNode {
  void delete();
  void delete(HNode node);
  String getName(HNode node);
  
  // generic getters
  Object get(String name);

  <T> T get(String name, Class<T> type);
  
  <T> HList<T> getList(String name, Class<T> type);
  
  // getters
  Date getDate(String name);
  String getString(String name);
  HObject getObject(String name);
  BigInteger getInteger(String name);
  
  HList<Date> getDates(String name);
  HList<String> getStrings(String name);
  HList<HObject> getObjects(String name);
  HList<BigInteger> getIntegers(String name);

}
