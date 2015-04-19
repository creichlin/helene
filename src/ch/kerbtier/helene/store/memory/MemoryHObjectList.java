package ch.kerbtier.helene.store.memory;

import java.util.Map;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityList;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.events.Listeners;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class MemoryHObjectList extends MemoryHBaseList<HObject> implements EntitySubject {

  private MemoryStore store;
  private EntityMap entity;

  public MemoryHObjectList(MemoryStore store, MemoryHNode parent, EntityMap entity) {
    super(parent);
    this.store = store;
    this.entity = entity;
  }

  @SuppressWarnings("hiding")
  @Override
  public void reconstruct(MemoryHNode parent, MemoryStore store, Entity entity) {
    setParent(parent);
    this.store = store;
    this.entity = (EntityMap)((EntityList) entity).get();
    this.onChange = new Listeners();
    
    for(HObject ho: list) {
      ((MemoryHObject)ho).reconstruct(this, store, this.entity);
    }
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
    // TODO: slug processing must go here
    MemoryHObject mho = new MemoryHObject(store, this, entity);
    mho.create(data);
    list.add(mho);
    onChange.trigger();
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
  public String getName() {
    return entity.getName();
  }
}
