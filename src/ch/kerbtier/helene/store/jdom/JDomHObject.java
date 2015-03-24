package ch.kerbtier.helene.store.jdom;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.EntityList;
import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HeleneUser;
import ch.kerbtier.helene.ListenerReference;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class JDomHObject extends JDomHNode implements HObject, EntitySubject {

  private static Map<Element, Map<String, List<WeakReference<Runnable>>>> events = new WeakHashMap<>();

  private EntityMap def;

  public JDomHObject() {
    super(null, null, null);
  }

  public JDomHObject(Document document, JDomHNode parent, Element element, EntityMap def) {
    super(document, parent, element);
    this.def = def;
  }

  public void init(Document document, Element element, EntityMap defp) {
    super.init(document, null, element);
    this.def = defp;
  }

  @SuppressWarnings("unchecked")
  private <T> T getAs(String name, Class<T> expected) {
    Entity ent = def.get(name);
    if (!ent.is(expected)) {
      throw new WrongFieldTypeException("field '" + name + "' is of type '" + ent + "' instead of '"
          + expected.getClass() + "'");
    }
    Object value = get(name);
    return (T) value;
  }

  @Override
  public Object get(String name) {
    Entity ent = def.get(name);

    Element sub = Util.get(getElement(), name);

    if (ent.is(HObject.class)) {
      return new JDomHObject(getDocument(), this, Util.getCreate(getElement(), name), (EntityMap) ent);

    } else if (ent.is(HList.class)) {
      EntityList el = (EntityList) ent;
      if (el.get().is(HObject.class)) {
        return getObjects(name);
      } else {
        return getList(name, el.get().isOf());
      }

    } else { // primitive types
      if (sub == null) {
        return null;
      } else {
        return Formater.parse(sub.getTextContent(), ent.isOf());
      }
    }
  }

  @Override
  public Date getDate(String name) {
    return getAs(name, Date.class);
  }

  @Override
  public String getString(String name) {
    return getAs(name, String.class);
  }

  @Override
  public BigInteger getBigInteger(String name) {
    return getAs(name, BigInteger.class);
  }

  @Override
  public HeleneUser getUser(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HObject getObject(String name) {
    return getAs(name, HObject.class);
  }

  @Override
  public HList<HObject> getObjects(String name) {
    Entity ent = def.get(name);
    if (ent.is(HList.class)) {
      if (((EntityList) ent).get().is(HObject.class)) {
        Element list = Util.getCreate(getElement(), name);
        return new JDomHobjectList(getDocument(), this, list, (EntityList) ent);
      }
    }
    throw new WrongFieldTypeException("expected " + HObject.class + " but found " + ent);
  }

  private <T> HList<T> getList(String name, Class<T> expected) {
    Entity ent = def.get(name);
    if (ent.is(HList.class)) {
      if (((EntityList) ent).get().is(expected)) {
        Element list = Util.getCreate(getElement(), name);
        return new JDomHList<>(getDocument(), this, list, (EntityList) ent);
      }
    }
    throw new WrongFieldTypeException("expected " + expected + " but found " + ent);
  }

  @Override
  public HList<Date> getDates(String name) {
    return getList(name, Date.class);
  }

  @Override
  public HList<String> getStrings(String name) {
    return getList(name, String.class);
  }

  @Override
  public HList<BigInteger> getIntegers(String name) {
    return getList(name, BigInteger.class);
  }

  @Override
  public HList<HeleneUser> getUsers(String name) {
    return getList(name, HeleneUser.class);
  }

  @Override
  public <X> HList<HList<X>> getLists(Class<X> type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListenerReference onChange(String name, Runnable run) {
    getChangeListeners(name).add(new WeakReference<>(run));
    return new ListenerReference(run);
  }

  @Override
  public ModifiableNode update() {
    return new HObjectModifiableNode(this, def);
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
      Element e = Util.get(getElement(), name);
      if (e != null) {
        getElement().removeChild(e);
      }
    } else if (entity.is(value.getClass())) {
      Element e = Util.getCreate(getElement(), name);
      e.setTextContent(Formater.format(value));
    } else {
      throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '"
          + value.getClass() + "'");
    }

    trigger(name);
  }

  private void trigger(String name) {
    if (hasChangeListeners(name)) {
      for (WeakReference<Runnable> r : getChangeListeners(name)) {
        Runnable r2 = r.get();
        if(r2 != null) {
         r2.run();
        }
      }
    }
  }

  @Override
  public HObject create(Map<String, Object> data) {
    for (String key : data.keySet()) {
      setData(key, data.get(key));
    }
    return this;
  }

  private List<WeakReference<Runnable>> getChangeListeners(String name) {
    if (!events.containsKey(getElement())) {
      synchronized (events) {
        if (!events.containsKey(getElement())) {

          events.put(getElement(), new HashMap<String, List<WeakReference<Runnable>>>());
        }
      }
    }
    
    Map<String, List<WeakReference<Runnable>>> map = events.get(getElement());
    
    if (!map.containsKey(name)) {
      synchronized (events) {
        if (!map.containsKey(name)) {

          map.put(name, new ArrayList<WeakReference<Runnable>>());
        }
      }
    }
    return map.get(name);
  }

  private boolean hasChangeListeners(String name) {
    return events.containsKey(getElement()) && events.get(getElement()).containsKey(name);
  }
  
  @Override
  public String toString() {
    return getElement().toString();
  }
}
