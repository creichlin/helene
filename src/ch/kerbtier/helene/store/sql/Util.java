package ch.kerbtier.helene.store.sql;

import java.util.HashMap;
import java.util.Map;

import ch.kerbtier.helene.store.sql.dao.DaoAtt;
import ch.kerbtier.helene.store.sql.dao.DaoAttblob;
import ch.kerbtier.helene.store.sql.dao.DaoAttdate;
import ch.kerbtier.helene.store.sql.dao.DaoAttint;
import ch.kerbtier.helene.store.sql.dao.DaoAttlist;
import ch.kerbtier.helene.store.sql.dao.DaoAttobj;
import ch.kerbtier.helene.store.sql.dao.DaoAttslug;
import ch.kerbtier.helene.store.sql.dao.DaoAttstr;
import ch.kerbtier.helene.store.sql.dao.DaoLs;
import ch.kerbtier.helene.store.sql.dao.DaoLsdate;
import ch.kerbtier.helene.store.sql.dao.DaoLsobj;
import ch.kerbtier.helene.store.sql.dao.DaoLsstr;

public class Util {
  public static Map<String, Class<? extends DaoLs>> LIST_TYPES = new HashMap<>();
  public static Map<String, Class<? extends DaoAtt>> ATT_TYPES = new HashMap<>();
  
  static {
    LIST_TYPES.put("object", DaoLsobj.class);
    LIST_TYPES.put("str", DaoLsstr.class);
    LIST_TYPES.put("date", DaoLsdate.class);
    
    ATT_TYPES.put("object", DaoAttobj.class);
    ATT_TYPES.put("str", DaoAttstr.class);
    ATT_TYPES.put("date", DaoAttdate.class);
    ATT_TYPES.put("int", DaoAttint.class);
    ATT_TYPES.put("list", DaoAttlist.class);
    ATT_TYPES.put("slug", DaoAttslug.class);
    ATT_TYPES.put("blob", DaoAttblob.class);
  }


}
