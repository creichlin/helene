package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.store.mod.ValueConverters;
import ch.kerbtier.struwwel.Observable;

public class MemoryHList<X> extends MemoryHBaseList<X> implements HList<X> {

  @SuppressWarnings("unused")
  private MemoryStore store;
  private Entity entity;

  public MemoryHList(MemoryStore store, MemoryHNode parent, Entity entity) {
    super(parent);
    this.store = store;
    this.entity = entity;
  }

  @Override
  public void reconstruct(MemoryHNode parent, MemoryStore storep, Entity ent) {
    setParent(parent);
    this.store = storep;
    this.entity = ((EntityList) entity).get();
    this.onChange = new Observable();
  }

  @Override
  public ModifiableNode add() {
    throw new UnsupportedOperationException("Call add(instance) for primitive lists.");
  }

  @Override
  public void add(Object value) {
    value = ValueConverters.convert(value);
    list.add((X)value);
    onChange.inform();
  }

  @Override
  public void set(int i, Object value) {
    value = ValueConverters.convert(value);
    list.set(i, (X)value);
  }

  @Override
  public String getName() {
    return entity.getName();
  }
}
