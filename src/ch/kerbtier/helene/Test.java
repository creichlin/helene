package ch.kerbtier.helene;

import java.nio.file.Paths;

import ch.kerbtier.helene.impl.ImpEntityMap;

public class Test {
  
  public static void main(String[] args) {
    
    System.out.println("xxx");
    
    ImpEntityMap root = new ImpEntityMap();
    
    Parse.extend(root, Paths.get("examples", "post", "post.model"));
    
  }
  
}
