package ch.kerbtier.helene.store.sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import ch.kerbtier.helene.HObject;
import ch.kerbtier.helene.HSlug;
import ch.kerbtier.helene.Store;
import ch.kerbtier.helene.entities.Entity;
import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.store.sql.dao.DaoAttslug;
import ch.kerbtier.helene.store.sql.dao.DaoObject;
import ch.kerbtier.webb.db.Db;
import ch.kerbtier.webb.db.DbPs;
import ch.kerbtier.webb.db.exceptions.NoMatchFound;

public class SqlStore extends SqlHObject implements Store {

  private Db db;
  private Path binaryFolder;

  public SqlStore(EntityMap def, String driver, String url, Path binaryFolder) {
    super(def);
    this.binaryFolder = binaryFolder;

    try {
      Class.forName(driver);
      db = new Db(url);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      if (!db.hasTables("object")) {
        try {
          InputStream is = SqlStore.class.getResourceAsStream("tables.sql");
          String sql = IOUtils.toString(is);
          is.close();

          db.prepareStatement(sql).executeUpdate();
          db.commit();
        } catch (IOException e) {
          assert (false);
        }
      }

      try {
        setDao(db.select(DaoObject.class, 0));
        db.commit();
      } catch (NoMatchFound e) {
        DbPs ps = db.prepareStatement("insert into object (id, type) values(0, '');");
        ps.executeUpdate();

        db.commit();

        setDao(db.select(DaoObject.class, 0));
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }
  }

  @Override
  protected SqlStore getStore() {
    return this;
  }

  public Path getBinaryFolder() {
    return binaryFolder;
  }

  @Override
  public HObject get(HSlug slug) {

    try {
      try {
        DaoAttslug das = db.selectFirst(DaoAttslug.class, "value = ?", slug.getValue());
        try {
          DaoObject obj = db.select(DaoObject.class, das.getParent());
          return new SqlHObject(this, getDef().getObject(obj.getType()), obj);
        } catch (NoMatchFound e) {
          throw new RuntimeException("slug must always have an object as parent");
        }

      } catch (NoMatchFound e) {
        return null;
      } finally {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
    }

    return null;
  }

  @Override
  public boolean isAvailable(HSlug slug) {
    if (slug == null) {
      return true;
    }

    try {
      try {
        DaoAttslug das = db.selectFirst(DaoAttslug.class, "value = ?", slug.getValue());
        db.commit();
        return false;
      } catch (NoMatchFound e) {
        db.commit();
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
      return false;
    }
  }

  public Db getDb() {
    return db;
  }

  public Path writeBackupTo(Path path) {
    DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmm");
    path = path.toAbsolutePath().resolve("h2-backup-" + df.format(new Date()) + ".zip");
    try {
      getDb().prepareStatement("BACKUP TO '" + path + "'").executeUpdate();
      getDb().commit();
    } catch (SQLException e) {
      getDb().rollback();
      e.printStackTrace();
    }
    return path;
  }

  public Entity getEntity(String type) {
    return getDef().get(type);
  }
}
