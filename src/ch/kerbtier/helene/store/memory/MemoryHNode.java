package ch.kerbtier.helene.store.memory;

import ch.kerbtier.helene.HNode;

public abstract class MemoryHNode implements HNode {
  private MemoryHNode parent;

  public MemoryHNode(MemoryHNode parent) {
    this.parent = parent;
  }

  public MemoryHNode getParent() {
    return parent;
  }
}
