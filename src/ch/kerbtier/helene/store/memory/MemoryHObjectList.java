package ch.kerbtier.helene.store.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class MemoryHObjectList extends MemoryHNode implements HList<HObject>, EntitySubject {
  
  private MemoryStore store;
  private EntityMap entity;
  private List<HObject> list = new ArrayList<HObject>();
  
  public MemoryHObjectList(MemoryStore store, MemoryHNode parent, EntityMap entity) {
    super(parent);
    this.store = store;
    this.entity = entity;
  }

  @Override
  public Iterator<HObject> iterator() {
    return list.iterator();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public HObject get(int cnt) {
    return list.get(cnt);
  }

  @Override
  public ModifiableNode add() {
    return new HObjectModifiableNode(this, entity);
  }

  public EntityMap getDef() {
    return entity;
  }

  @Override
  public HObject create(Map<String, Object> data) {
    MemoryHObject mho = new MemoryHObject(store, this, entity);
    mho.create(data);
    list.add(mho);
    return mho;
  }

  @Override
  public void add(HObject value) {
    throw new UnsupportedOperationException("Can only call add() for ObjectList");
  }

  @Override
  public void set(int i, HObject value) {
    throw new UnsupportedOperationException("Can not set new HObject. Use get(index) and modify instance.");
  }

  @Override
  public void delete() {
    getParent().delete(this);
  }

  @Override
  public void delete(int i) {
    list.remove(i);
  }

  @Override
  public void delete(HNode node) {
    int index = list.indexOf(node);
    if(index > -1) {
      delete(index);
    }
  }
}
