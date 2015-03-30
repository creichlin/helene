package ch.kerbtier.helene;

import static ch.kerbtier.helene.Types.DATE;
import static ch.kerbtier.helene.Types.INTEGER;
import static ch.kerbtier.helene.Types.OBJECT;
import static ch.kerbtier.helene.Types.STRING;

import java.math.BigInteger;
import java.util.Date;

public abstract class HNodeDefault implements HNode {

  @Override
  public Object get(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T get(String name, Class<T> type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> HList<T> getList(String name, Class<T> type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Date getDate(String name) {
    return get(name, DATE);
  }

  @Override
  public String getString(String name) {
    return get(name, STRING);
  }

  @Override
  public HObject getObject(String name) {
    return get(name, OBJECT);
  }

  @Override
  public BigInteger getInteger(String name) {
    return get(name, INTEGER);
  }

  @Override
  public HList<Date> getDates(String name) {
    return getList(name, DATE);
  }

  @Override
  public HList<String> getStrings(String name) {
    return getList(name, STRING);
  }

  @Override
  public HList<BigInteger> getIntegers(String name) {
    return getList(name, INTEGER);
  }

  @Override
  public HList<HObject> getObjects(String name) {
    return getList(name, OBJECT);
  }

}
