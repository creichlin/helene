package ch.kerbtier.helene.store.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.events.Listeners;

public class MemoryHList <X> extends MemoryHNode implements HList<X> {
  
  private MemoryStore store;
  private Entity entity;
  private List<X> list = new ArrayList<>();
  private Listeners onChange = new Listeners();
  
  public MemoryHList(MemoryStore store, MemoryHNode parent, Entity entity) {
    super(parent);
    this.store = store;
    this.entity = entity;
  }

  @Override
  public Iterator<X> iterator() {
    return list.iterator();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public X get(int cnt) {
    return list.get(cnt);
  }

  @Override
  public ModifiableNode add() {
    throw new UnsupportedOperationException("Call add(instance) for primitive lists.");
  }

  @Override
  public void add(X value) {
    list.add(value);
    onChange.trigger();
  }

  @Override
  public void set(int i, X value) {
    list.set(i, value);
  }

  @Override
  public void delete() {
    getParent().delete(this);
  }

  @Override
  public void delete(int i) {
    list.remove(i);
    onChange.trigger();
  }

  @Override
  public void delete(HNode node) {
    int index = list.indexOf(node);
    if(index > -1) {
      delete(index);
    }
  }

  @Override
  public void onChange(Runnable runnable) {
    onChange.onEvent(runnable);
  }
}
