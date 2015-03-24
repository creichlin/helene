package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.Store;

public class MemoryStore extends MemoryHObject implements Store {

  private EntityMap def;
  
  public MemoryStore(EntityMap def) {
    super(null, null, def);
  }
  
}
