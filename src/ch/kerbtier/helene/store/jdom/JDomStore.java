package ch.kerbtier.helene.store.jdom;

import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ch.kerbtier.helene.EntityMap;
import ch.kerbtier.helene.Store;

public class JDomStore extends JDomHObject implements Store {

  private Document document;
  private EntityMap def;

  public JDomStore(EntityMap def) {
    this.def = def;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = dbf.newDocumentBuilder();
      document = builder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }

    Element e = document.createElement("helene");
    document.appendChild(e);

    init(document, e, def);
  }

  public void write(Path p) {
    TransformerFactory tFactory = TransformerFactory.newInstance();
    try {
      Transformer transformer = tFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      
      
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(p.toFile());
      transformer.transform(source, result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * discards old data and reads from the file
   * 
   * @param p
   */
  public void read(Path p) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = dbf.newDocumentBuilder();
      document = builder.parse(p.toFile());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    init(document, document.getDocumentElement(), def);
  }
}
