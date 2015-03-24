package ch.kerbtier.helene.store.jdom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formater {

  private static DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSSS");
  
  public static String format(Object value) {
    if(value instanceof String) {
      return (String)value;
    } else if(value instanceof Date) {
      return format.format((Date)value);
    }
    return "" + value;
  }
  
  public static <T> T parse(String data, Class<T> type) {
    if(String.class.isAssignableFrom(type)) {
      return (T)data;
    } else if(Date.class.isAssignableFrom(type)) {
      try {
        return (T)format.parse(data);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    
    throw new RuntimeException();
  }
  
}
