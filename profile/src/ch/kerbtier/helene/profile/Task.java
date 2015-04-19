package ch.kerbtier.helene.profile;

public interface Task extends Runnable {
  
  void setup();
  
  String getName();
}
