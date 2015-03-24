package ch.kerbtier.helene.store.jdom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ch.kerbtier.helene.EntityList;
import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class JDomHobjectList extends JDomHNode implements HList<HObject>, EntitySubject {
  
  private static final String ITEM = "item";
  
  private Document document;
  private Element element;
  private EntityMap childDef;
  
  public JDomHobjectList(Document document, JDomHNode parent, Element element, EntityList ent) {
    super(parent);
    this.document = document;
    this.element = element;
    this.childDef = (EntityMap)ent.get();
  }

  @Override
  public Iterator<HObject> iterator() {
    List<HObject> objs = new ArrayList<>();
    NodeList nl = element.getElementsByTagName(ITEM);
    for(int ni = 0; ni < nl.getLength(); ni++) {
      objs.add(new JDomHObject(document, this, (Element)nl.item(ni), childDef));
    }
    return objs.iterator();
  }

  @Override
  public int size() {
    return element.getElementsByTagName(ITEM).getLength();
  }

  @Override
  public HObject get(int cnt) {
    NodeList nl = element.getElementsByTagName(ITEM);
    
    if(nl.getLength() > cnt) {
      return new JDomHObject(document, this, (Element)nl.item(cnt), childDef);
    }
    
    return null;
  }

  @Override
  public ModifiableNode add() {
    return new HObjectModifiableNode(this, childDef);
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
  public HObject create(Map<String, Object> data) {
    Element ne = document.createElement(ITEM);
    element.appendChild(ne);
    JDomHObject jdh = new JDomHObject(document, this, ne, childDef);
    jdh.create(data);
    return jdh;
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
