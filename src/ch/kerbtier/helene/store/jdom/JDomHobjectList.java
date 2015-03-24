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
import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.store.mod.EntitySubject;
import ch.kerbtier.helene.store.mod.HObjectModifiableNode;

public class JDomHobjectList extends JDomHNode implements HList<HObject>, EntitySubject {
  
  private static final String ITEM = "item";
  
  private EntityMap childDef;
  
  public JDomHobjectList(Document document, JDomHNode parent, Element element, EntityList ent) {
    super(document, parent, element);
    this.childDef = (EntityMap)ent.get();
  }

  @Override
  public Iterator<HObject> iterator() {
    List<HObject> objs = new ArrayList<>();
    NodeList nl = getElement().getElementsByTagName(ITEM);
    for(int ni = 0; ni < nl.getLength(); ni++) {
      objs.add(new JDomHObject(getDocument(), this, (Element)nl.item(ni), childDef));
    }
    return objs.iterator();
  }

  @Override
  public int size() {
    return getElement().getElementsByTagName(ITEM).getLength();
  }

  @Override
  public HObject get(int cnt) {
    NodeList nl = getElement().getElementsByTagName(ITEM);
    
    if(nl.getLength() > cnt) {
      return new JDomHObject(getDocument(), this, (Element)nl.item(cnt), childDef);
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
    Element ne = getDocument().createElement(ITEM);
    getElement().appendChild(ne);
    JDomHObject jdh = new JDomHObject(getDocument(), this, ne, childDef);
    jdh.create(data);
    return jdh;
  }

  @Override
  public void delete(int i) {
    NodeList nl = getElement().getElementsByTagName(ITEM);
    
    if(i < nl.getLength()) {
      getElement().removeChild(nl.item(i));
    }
  }
}
