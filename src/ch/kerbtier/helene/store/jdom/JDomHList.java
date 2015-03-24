package ch.kerbtier.helene.store.jdom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ch.kerbtier.helene.Entity;
import ch.kerbtier.helene.EntityList;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.ModifiableNode;

public class JDomHList<T> extends JDomHNode implements HList<T> {

  
  private static final String ITEM = "item";
  
  private Document document;
  private Element element;
  private Entity childDef;
  
  public JDomHList(Document document, JDomHNode parent, Element element, EntityList ent) {
    super(parent);
    this.document = document;
    this.element = element;
    this.childDef = ent.get();
  }

  @Override
  public Iterator<T> iterator() {
    List<T> objs = new ArrayList<T>();
    NodeList nl = element.getElementsByTagName(ITEM);
    for(int ni = 0; ni < nl.getLength(); ni++) {
      objs.add((T) Formater.parse(nl.item(ni).getTextContent(), childDef.isOf()));
    }
    return objs.iterator();
  }

  @Override
  public int size() {
    return element.getElementsByTagName(ITEM).getLength();
  }

  @Override
  public T get(int cnt) {
    NodeList nl = element.getElementsByTagName(ITEM);
    
    if(nl.getLength() > cnt) {
      return (T) Formater.parse(nl.item(cnt).getTextContent(), childDef.isOf());
    }
    
    return null;
  }

  @Override
  public ModifiableNode add() {
    throw new UnsupportedOperationException("Can only call add(Item) for primitive List");
  }

  @Override
  public void add(T value) {
    Element e = document.createElement(ITEM);
    element.appendChild(e);
    e.setTextContent(Formater.format(value));
  }

  @Override
  public void set(int i, T value) {
    NodeList nl = element.getElementsByTagName(ITEM);
    
    if(nl.getLength() > i) {
      nl.item(i).setTextContent(Formater.format(value));
    } else {
      throw new RuntimeException("out of bounds");
    }
  }

  @Override
  public void delete() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(HNode node) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(int i) {
    // TODO Auto-generated method stub
    
  }
}
