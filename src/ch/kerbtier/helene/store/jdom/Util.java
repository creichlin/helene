package ch.kerbtier.helene.store.jdom;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Util {
  public static Element get(Element parent, String name) {
    NodeList subs = parent.getElementsByTagName(name);
    if (subs.getLength() > 0) {
      return (Element) subs.item(0);
    }
    return null;
  }

  public static Element getCreate(Element element, String name) {
    Element e = get(element, name);
    
    if(e == null) {
      e = element.getOwnerDocument().createElement(name);
      element.appendChild(e);
    }
    return e;
  }
}
