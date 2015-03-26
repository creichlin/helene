package ch.kerbtier.helene.store.memory;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.EntityList;
import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HeleneUser;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.events.ListenerReference;
import ch.kerbtier.helene.events.MappedListeners;
import ch.kerbtier.helene.exceptions.WrongFieldDataException;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class MemoryHObject extends MemoryHNode implements HObject, EntitySubject {

  final MemoryStore store;
  final EntityMap def;

  private final Map<String, Object> data = new HashMap<>();
  
  private MappedListeners<String> listeners = new MappedListeners<>();

  public MemoryHObject(MemoryStore store, MemoryHNode parent, EntityMap def) {
    super(parent);
    this.store = store;
    this.def = def;
  }

  @Override
  public ListenerReference onChange(String attrib, Runnable run) {
    return listeners.on(attrib, run);
  }

  @Override
  public Object get(String name) {
    Entity entity = def.get(name);
    if (entity.is(HList.class)) {
      Entity subType = ((EntityList) entity).get();
      if (subType.is(HObject.class)) {
        return getObjects(name);
      }
    }
    return getObjData(name);
  }

  @Override
  public ModifiableNode update() {
    return new HObjectModifiableNode(this, def);
  }

  public EntityMap getDef() {
    return def;
  }

  @Override
  public Date getDate(String name) {
    return getData(name, Date.class);
  }

  @Override
  public String getString(String name) {
    return getData(name, String.class);
  }

  @Override
  public BigInteger getBigInteger(String name) {
    return getData(name, BigInteger.class);
  }

  @Override
  public HeleneUser getUser(String name) {
    return getData(name, HeleneUser.class);
  }

  @Override
  public HObject getObject(String name) {
    return getData(name, HObject.class);
  }

  /**
   * for commiting data
   * 
   * @param key
   * @param value
   */
  private void setData(String name, Object value) {
    Entity entity = def.get(name);

    if (value == null) {
      data.remove(name);
      return;
    }
    if (entity.is(value.getClass())) {
      data.put(name, value);
    } else {
      throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '"
          + value.getClass() + "'");
    }

    trigger(name);
  }

  @Override
  public HObject create(Map<String, Object> newData) {
    for(String key: newData.keySet()) {
      setData(key, newData.get(key));
    }
    return this;
  }

  private void trigger(String name) {
    listeners.trigger(name);
  }

  @SuppressWarnings("unchecked")
  private <X> X getData(String name, Class<X> expected) {
    Entity entity = def.get(name);
    if (entity.is(expected)) {
      Object value = data.get(name);
      if (value != null) {
        if (entity.is(value.getClass())) {
          return (X) value;
        } else {
          throw new WrongFieldDataException("invalid datatype in field '" + name + "' found '" + value.getClass()
              + "' instead of '" + expected.getName() + "'");
        }
      } else {

        if (entity.is(HObject.class)) { // create empty hobject
          data.put(name, new MemoryHObject(store, this, (EntityMap) entity));
          return (X) data.get(name);
        } else {
          return null; // TODO default
        }
      }
    }
    throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '"
        + expected.getName() + "'");
  }

  private Object getObjData(String name) {
    Entity entity = def.get(name);

    Object value = data.get(name);
    if (value != null) {
      if (entity.is(value.getClass())) {
        return value;
      } else {
        throw new WrongFieldDataException("invalid datatype in field '" + name + "' found '" + value.getClass()
            + "' instead of '" + entity.getType() + "'");
      }
    } else {

      if (entity.is(HObject.class)) { // create empty hobject
        data.put(name, new MemoryHObject(store, this, (EntityMap) entity));
        return data.get(name);
      } else {
        return null; // TODO default
      }
    }
  }

  @SuppressWarnings("unchecked")
  private <X, Y> HList<X> checkDataList(String name, Class<X> expected, Creator<Y> creator) {
    Entity entity = def.get(name);

    if (entity.is(HList.class)) {
      Entity subType = ((EntityList) entity).get();
      if (subType.is(expected)) {
        Object value = data.get(name);
        if (value != null) {
          if (entity.is(value.getClass())) {
            return (HList<X>) value;
          } else {
            throw new WrongFieldDataException("invalid datatype in field '" + name + "' found '" + value.getClass()
                + "' instead of '" + expected.getName() + "'");
          }
        } else {
          data.put(name, creator.create());
          return (HList<X>) data.get(name);
        }
      } else {
        throw new WrongFieldTypeException("field '" + name + "' is of type '[" + subType + "]' instead of '["
            + expected.getName() + "]'");
      }
    } else {
      throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '["
          + expected.getName() + "]'");
    }
  }

  @Override
  public HList<HObject> getObjects(final String name) {
    return checkDataList(name, HObject.class, new Creator<HObject>() {
      @Override
      public HList<HObject> create() {
        return new MemoryHObjectList(store, MemoryHObject.this, (EntityMap) ((EntityList) def.get(name)).get());
      }
    });
  }

  @Override
  public HList<Date> getDates(final String name) {
    return checkDataList(name, Date.class, new Creator<Date>() {

      @Override
      public HList<Date> create() {
        return new MemoryHList<>(store, MemoryHObject.this, ((EntityList) def.get(name)).get());
      }

    });
  }

  @Override
  public HList<String> getStrings(final String name) {
    return checkDataList(name, String.class, new Creator<Date>() {
      @Override
      public HList<Date> create() {
        return new MemoryHList<>(store, MemoryHObject.this, ((EntityList) def.get(name)).get());
      }
    });
  }

  @Override
  public HList<BigInteger> getIntegers(final String name) {
    return checkDataList(name, BigInteger.class, new Creator<BigInteger>() {
      @Override
      public HList<BigInteger> create() {
        return new MemoryHList<>(store, MemoryHObject.this, ((EntityList) def.get(name)).get());
      }
    });
  }

  @Override
  public HList<HeleneUser> getUsers(final String name) {
    return checkDataList(name, HeleneUser.class, new Creator<HeleneUser>() {
      @Override
      public HList<HeleneUser> create() {
        return new MemoryHList<>(store, MemoryHObject.this, ((EntityList) def.get(name)).get());
      }
    });
  }

  @Override
  public <X> HList<HList<X>> getLists(Class<X> type) {
    // TODO Auto-generated method stub
    return null;
  }

  interface Creator<X> {
    HList<X> create();
  }

  @Override
  public void delete() {
    getParent().delete(this);
  }

  @Override
  public void delete(HNode node) {
    for(Entry<String, Object> e: data.entrySet()) {
      if(e.getValue().equals(node)) {
        data.remove(e.getKey());
        return;
      }
    }
  }
}
