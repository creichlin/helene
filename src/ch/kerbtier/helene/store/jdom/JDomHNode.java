package ch.kerbtier.helene.store.jdom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ch.kerbtier.helene.HNode;

public abstract class JDomHNode implements HNode {
  private Document document;
  private JDomHNode parent;
  private Element element;

  JDomHNode(Document document, JDomHNode parent, Element element) {
    this.document = document;
    this.parent = parent;
    this.element = element;
  }

  void init(Document documentp, JDomHNode parentp, Element elementp) {
    this.document = documentp;
    this.parent = parentp;
    this.element = elementp;
  }

  HNode getParent() {
    return parent;
  }

  Document getDocument() {
    return document;
  }

  Element getElement() {
    return element;
  }
  
  
  @Override
  public final void delete() {
    getParent().delete(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof JDomHNode) {
      JDomHNode o = (JDomHNode) obj;
      return o.element.equals(element);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return element.hashCode();
  }
  
  
}
