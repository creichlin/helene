package ch.kerbtier.helene.store.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.HNode;
import ch.kerbtier.struwwel.Observable;
import ch.kerbtier.struwwel.ObserverReference;

public abstract class MemoryHBaseList<X> extends MemoryHNode implements HList<X> {

  List<X> list = new ArrayList<>();
  Observable onChange = new Observable();

  public MemoryHBaseList(MemoryHNode parent) {
    super(parent);
  }

  public int indexOf(Object node) {
    return list.indexOf(node);
  }
  
  @Override
  public final void swap(int i1, int i2) {
    if(i1 < list.size() && i2 < list.size()) {
      synchronized(list) {
        X tmp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, tmp);
      }
      onChange.inform();
    }
  }

  @Override
  public final Iterator<X> iterator() {
    return list.iterator();
  }

  @Override
  public final int size() {
    return list.size();
  }

  @Override
  public final X get(int cnt) {
    return list.get(cnt);
  }

  @Override
  public final void delete() {
    getParent().delete(this);
  }

  @Override
  public final void delete(int i) {
    list.remove(i);
    onChange.inform();
  }

  @Override
  public final void delete(HNode node) {
    int index = list.indexOf(node);
    if (index > -1) {
      delete(index);
    }
  }

  @Override
  public final ObserverReference onChange(Runnable runnable) {
    return onChange.register(runnable);
  }

}
