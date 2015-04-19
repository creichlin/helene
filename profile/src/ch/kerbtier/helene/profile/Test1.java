package ch.kerbtier.helene.profile;

import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.ModifiableNode;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.Types;

class Test1 implements Task {
  
  private StoreProvider storeProvider;
  private Store store;
  
  public Test1(StoreProvider storeProvider) {
    this.storeProvider = storeProvider;
  }
  
  @Override
  public void setup() {
    store = storeProvider.create();
  }
  
  @Override
  public void run() {
    for(int cnt = 0; cnt < 1000; cnt++) {
      ModifiableNode node = store.getList("object_list", Types.OBJECT).add();
      node.set("title", "lalala");
      node.set("content", "ldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsaldasdsf dsadsa");
      node.set("slug", new HSlug("slug" + (int)(Math.random() * 1000000000)));
      node.commit();
    }
  }

  @Override
  public String getName() {
    return "T1 create 1000 Objects (" + storeProvider.getName() + ")";
  }
}