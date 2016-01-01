package ch.kerbtier.helene.store.sql;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.kerbtier.helene.HBlob;
import ch.kerbtier.helene.HSlug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mchange.util.Base64Encoder;

public class JsonWriter implements Visitor<Object> {

  private SqlStore store = null;

  public JsonWriter(SqlStore store) {

    this.store = store;

  }

  public String write() {

    Map<String, Object> result = (Map<String, Object>) visit((SqlHObject) store);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    return gson.toJson(result);

  }

  @Override
  public Object visit(SqlHList list) {
    List<Object> results = new ArrayList<>();
    
    for(Object value: list) {
      Object result = fetchValue(value);
      results.add(result);
    }
    
    return results;
  }

  @Override
  public Object visit(SqlHObjectList list) {
    List<Object> results = new ArrayList<>();
    
    for(Object value: list) {
      Object result = fetchValue(value);
      results.add(result);
    }
    
    return results;
  }

  @Override
  public Object visit(SqlHObject object) {
    Map<String, Object> results = new HashMap<>();
    for (String name : object.getProperties()) {
      Object value = object.get(name);
      Object result = fetchValue(value);
      
      results.put(name, result);
    }

    return results;
  }

  private Object fetchValue(Object value) {
    Object result = null;
    
    if (value instanceof SqlHObject) {
      result = visit((SqlHObject) value);
    } else if (value instanceof SqlHObjectList) {
      result = visit((SqlHObjectList) value);
    } else if (value instanceof SqlHList) {
      result = visit((SqlHList) value);
    } else if (value instanceof String) {
      result = value;
    } else if (value instanceof HSlug) {
      result = "#" + ((HSlug)value).getValue();
    } else if (value instanceof HBlob) {
      byte[] data = ((HBlob)value).asArray();
      data = Base64.getEncoder().encode(data);
      result = "BASE64::" + new String(data, StandardCharsets.ISO_8859_1);
    } else if(value == null){
      // skip null values
    } else {
      throw new RuntimeException("write to json: unknown value " + value);
    }
    return result;
  }

}
