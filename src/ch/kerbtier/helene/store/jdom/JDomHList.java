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
import ch.kerbtier.helene.events.ListenerReference;
import ch.kerbtier.helene.events.MappedListeners;

public class JDomHList<T> extends JDomHNode implements HList<T> {
  private static final String ITEM = "item";
  
  private Entity childDef;
  
  private static MappedListeners<Element> onChange = new MappedListeners<>();
  
  public JDomHList(Document document, JDomHNode parent, Element element, EntityList ent) {
    super(document, parent, element);
    this.childDef = ent.get();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Iterator<T> iterator() {
    List<T> objs = new ArrayList<>();
    NodeList nl = getElement().getElementsByTagName(ITEM);
    for(int ni = 0; ni < nl.getLength(); ni++) {
      objs.add((T) Formater.parse(nl.item(ni).getTextContent(), childDef.isOf()));
    }
    return objs.iterator();
  }

  @Override
  public int size() {
    return getElement().getElementsByTagName(ITEM).getLength();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get(int cnt) {
    NodeList nl = getElement().getElementsByTagName(ITEM);
    
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
    Element e = getDocument().createElement(ITEM);
    getElement().appendChild(e);
    e.setTextContent(Formater.format(value));
    onChange.trigger(getElement());
  }

  @Override
  public void set(int i, T value) {
    NodeList nl = getElement().getElementsByTagName(ITEM);
    
    if(nl.getLength() > i) {
      nl.item(i).setTextContent(Formater.format(value));
    } else {
      throw new RuntimeException("out of bounds");
    }
  }

  @Override
  public void delete(int i) {
    NodeList nl = getElement().getElementsByTagName(ITEM);
    
    if(i < nl.getLength()) {
      getElement().removeChild(nl.item(i));
    }
    onChange.trigger(getElement());
  }
  
  @Override
  public final void delete(HNode node) {
    Element e = ((JDomHNode)node).getElement();
    getElement().removeChild(e);
    onChange.trigger(getElement());
  }

  @Override
  public ListenerReference onChange(Runnable runnable) {
    return onChange.on(getElement(), runnable);
  }
  
  @Override
  public String getName(HNode node) {
    return getParent().getName(this);
  }
}
