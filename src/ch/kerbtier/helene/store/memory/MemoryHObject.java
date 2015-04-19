package ch.kerbtier.helene.store.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.ModifiableNode;
import static ch.kerbtier.helene.Types.*;
import ch.kerbtier.helene.def.GetOperation;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.events.ListenerReference;
import ch.kerbtier.helene.events.MappedListeners;
import ch.kerbtier.helene.exceptions.WrongFieldTypeException;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;
import ch.kerbtier.helene.store.mod.ValueConverters;

public class MemoryHObject extends MemoryHNode implements HObject, EntitySubject {

  private MemoryStore store;
  EntityMap def;

  Map<String, Object> data = new HashMap<>();
  private MappedListeners<String> listeners = new MappedListeners<>();;

  public MemoryHObject(MemoryStore store, MemoryHNode parent, EntityMap def) {
    super(parent);
    this.store = store;
    this.def = def;
  }

  @Override
  protected void reconstruct(MemoryHNode parent, MemoryStore storep, Entity defp) {
    setParent(parent);
    this.store = storep;
    this.def = (EntityMap) defp;
    listeners = new MappedListeners<>();

    for (String key : data.keySet()) {
      Entity ent = this.def.get(key);
      if (ent.is(OBJECT) || ent.is(LIST)) {
        ((MemoryHNode) data.get(key)).reconstruct(this, store, ent);
      }
    }
  }

  public MemoryHObject(EntityMap def) {
    super(null);
    this.store = (MemoryStore) this;
    this.def = def;
  }

  protected MemoryStore getStore() {
    return store;
  }

  @Override
  public ListenerReference onChange(String attrib, Runnable run) {
    return listeners.on(attrib, run);
  }

  @Override
  public ModifiableNode update() {
    return new HObjectModifiableNode(this, def);
  }

  public EntityMap getDef() {
    return def;
  }

  /**
   * for commiting data
   * 
   * @param key
   * @param value
   */
  private void setData(String name, Object value) {
    value = ValueConverters.convert(value);

    Entity entity = def.get(name);

    Object oldValue = data.get(name);

    if (value == null) {
      if (oldValue != null) {
        if (entity.is(SLUG)) {
          store.release((HSlug) oldValue);
        }
      }
      data.remove(name);
      return;
    }
    if (entity.is(value.getClass())) {
      if (entity.is(SLUG)) {
        if (oldValue != null) {
          if (!oldValue.equals(value)) {
            store.release((HSlug) oldValue);
            // might throw a duplicate slug exception
            store.register((HSlug) value, this);
          }
        } else { // old value is null
          // might throw a duplicate slug exception
          store.register((HSlug) value, this);
        }
      }
      data.put(name, value);
    } else {
      throw new WrongFieldTypeException("field '" + name + "' is of type '" + entity + "' instead of '"
          + value.getClass() + "'");
    }

    trigger(name);
  }

  @Override
  public HObject create(Map<String, Object> newData) {
    // TODO: slug processing must go here...
    for (String key : newData.keySet()) {
      setData(key, newData.get(key));
    }
    return this;
  }

  private void trigger(String name) {
    listeners.trigger(name);
  }

  @Override
  public Object get(String name) {
    return get(name, null);
  }

  @Override
  public <X> X get(String name, Class<X> expected) {
    return get(name, expected, null);
  }

  public <X> X get(String name, Class<X> expected, Class<?> subExpected) {
    return GetOperation.get(store, name, expected, subExpected, def, new MemoryGetWorker(this));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <X> HList<X> getList(String name, Class<X> expected) {
    return get(name, LIST, expected);
  }

  @Override
  public void delete() {
    getParent().delete(this);
  }

  @Override
  public void delete(HNode node) {
    for (Entry<String, Object> e : data.entrySet()) {
      if (e.getValue().equals(node)) {
        data.remove(e.getKey());
        return;
      }
    }
  }

  @Override
  public void delete(String field) {
    def.get(field); // throws exception when apropriate
    data.remove(field);
  }

  @Override
  public Set<String> getProperties() {
    return def.getProperties();
  }

  @Override
  public String getName() {
    return def.getName();
  }

}
