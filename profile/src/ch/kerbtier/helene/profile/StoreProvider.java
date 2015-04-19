package ch.kerbtier.helene.profile;

import ch.kerbtier.helene.Store;

interface StoreProvider {
  Store create();

  String getName();
}