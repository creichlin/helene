package ch.kerbtier.helene.store.sql;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.store.sql.dao.DaoList;
import ch.kerbtier.struwwel.MappedObservable;
import ch.kerbtier.struwwel.ObserverReference;

public abstract class SqlHBaseList<T> extends SqlHNode implements HList<T> {

  protected static MappedObservable<DaoList> listeners = new MappedObservable<>();
  protected DaoList dao;
  
  public SqlHBaseList(SqlStore store, DaoList dao) {
    super(store);
    this.dao = dao;
  }
  
  @Override
  public final ObserverReference onChange(Runnable runnable) {
    return listeners.register(dao, runnable);
  }
}
