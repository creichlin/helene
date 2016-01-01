package ch.kerbtier.helene.store.sql;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonWriter implements Visitor<Object> {
  
  private SqlStore store = null;
  
  public JsonWriter(SqlStore store) {
    
    this.store = store;
    
    
  }
  
  public String write() {
    
    Map<String, Object> result = (Map<String, Object>) visit((SqlHObject)store);
    
    Gson gson = new Gson();
    
    return gson.toJson(result);
    
  }

  @Override
  public Object visit(SqlHList list) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visit(SqlHObjectList list) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visit(SqlHObject object) {
    Map<String, Object> result = new HashMap<>();
    for(String name: store.getProperties()) {
      Object value = object.get(name);
      if(value instanceof SqlHObject) {
        result.put(name, visit((SqlHObject)object));
      }
    }
    
    return result;
  }
  
  
}
