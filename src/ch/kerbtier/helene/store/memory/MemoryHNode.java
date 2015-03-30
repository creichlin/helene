package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.HNodeDefault;
import ch.kerbtier.helene.entities.Entity;

public abstract class MemoryHNode extends HNodeDefault {
  private MemoryHNode parent;

  public MemoryHNode(MemoryHNode parent) {
    this.parent = parent;
  }

  public MemoryHNode getParent() {
    return parent;
  }
  
  void setParent(MemoryHNode parent) {
    this.parent = parent;
  }
  
  abstract void reconstruct(MemoryHNode parentp, MemoryStore store, Entity ent);
}
