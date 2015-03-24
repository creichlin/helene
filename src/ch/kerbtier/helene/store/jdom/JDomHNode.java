package ch.kerbtier.helene.store.jdom;

import ch.kerbtier.helene.HNode;

public abstract class JDomHNode implements HNode {
  private JDomHNode parent;

  public JDomHNode(JDomHNode parent) {
    this.parent = parent;
  }

  public HNode getParent() {
    return parent;
  }

}
