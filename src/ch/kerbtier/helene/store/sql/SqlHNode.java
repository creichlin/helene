package ch.kerbtier.helene.store.sql;

import ch.kerbtier.helene.def.HNodeDefault;

public abstract class SqlHNode extends HNodeDefault implements Visitable{

  private SqlStore store;

  public SqlHNode(SqlStore store) {
    this.store = store;
  }

  protected SqlStore getStore() {
    return store;
  }
}
