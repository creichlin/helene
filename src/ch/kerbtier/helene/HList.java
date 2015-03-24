package ch.kerbtier.helene;

public interface HList<T> extends HNode, Iterable<T> {
  int size();
  T get(int cnt);
  
  ModifiableNode add();
  void add(T value);
  void set(int i, T value);
  void delete(int i);
}
