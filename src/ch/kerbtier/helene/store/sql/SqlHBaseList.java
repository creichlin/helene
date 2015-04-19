package ch.kerbtier.helene.store.sql;

import ch.kerbtier.helene.HList;
import ch.kerbtier.helene.events.ListenerReference;
import ch.kerbtier.helene.events.MappedListeners;
import ch.kerbtier.helene.store.sql.dao.DaoList;

public abstract class SqlHBaseList<T> extends SqlHNode implements HList<T> {

  protected static MappedListeners<DaoList> listeners = new MappedListeners<>();
  protected DaoList dao;
  
  public SqlHBaseList(SqlStore store, DaoList dao) {
    super(store);
    this.dao = dao;
  }
  
  @Override
  public final ListenerReference onChange(Runnable runnable) {
    return listeners.on(dao, runnable);
  }
}
