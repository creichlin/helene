package ch.kerbtier.helene.profile;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.Types;

class Test3Delete implements Task {
  
  private StoreProvider storeProvider;
  private Store store;
  
  public Test3Delete(StoreProvider storeProvider) {
    this.storeProvider = storeProvider;
  }
  
  @Override
  public void setup() {
    store = storeProvider.create();
    for(int cnt = 0; cnt < 1000; cnt++) {
      ModifiableNode node = store.getList("object_list", Types.OBJECT).add();
      node.set("title", "lalala");
      node.set("content", "ldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsa");
      node.set("slug", new HSlug("slug" + cnt));
      node.commit();
    }
  }
  
  @Override
  public void run() {
    for(int cnt = 0; cnt < 1000; cnt++) {
      HObject obj = store.getList("object_list", Types.OBJECT).get(0);
      obj.delete();
    }
  }

  @Override
  public String getName() {
    return "T2 read from 1000 Objects (" + storeProvider.getName() + ")";
  }
}