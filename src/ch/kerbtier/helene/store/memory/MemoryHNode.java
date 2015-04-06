package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.HList;
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
  
  @Override
  public void up() {
    if(parent instanceof HList) {
      MemoryHBaseList l = (MemoryHBaseList)parent;
      int i = l.indexOf(this);
      if(i > 0) {
        l.swap(i, i - 1);
      }
    }
  }
  
  @Override
  public void down() {
    if(parent instanceof HList) {
      MemoryHBaseList l = (MemoryHBaseList)parent;
      int i = l.indexOf(this);
      if(i + 1 < l.size()) {
        l.swap(i, i + 1);
      }
    }
  }
  
  abstract void reconstruct(MemoryHNode parentp, MemoryStore store, Entity ent);
}
