package ch.kerbtier.helene;

import ch.kerbtier.helene.events.ListenerReference;

public interface HList<T> extends HNode, Iterable<T> {
  int size();
  T get(int cnt);
  
  ModifiableNode add();
  void add(T value);
  void set(int i, T value);
  void delete(int i);
  void swap(int i1, int i2);
  
  ListenerReference onChange(Runnable runnable);
}
