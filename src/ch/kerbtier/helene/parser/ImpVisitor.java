package ch.kerbtier.helene.parser;

import java.util.List;

import ch.kerbtier.helene.impl.ImpBlobEntity;
import ch.kerbtier.helene.impl.ImpDateEntity;
import ch.kerbtier.helene.impl.ImpEntity;
import ch.kerbtier.helene.impl.ImpEntityList;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.impl.ImpIntegerEntity;
import ch.kerbtier.helene.impl.ImpSlugEntity;
import ch.kerbtier.helene.impl.ImpStringEntity;
import ch.kerbtier.helene.impl.ImpUserEntity;
import ch.kerbtier.helene.parser.HeleneParser.BlobContext;
import ch.kerbtier.helene.parser.HeleneParser.DateContext;
import ch.kerbtier.helene.parser.HeleneParser.EntityContext;
import ch.kerbtier.helene.parser.HeleneParser.IntegerContext;
import ch.kerbtier.helene.parser.HeleneParser.ListContext;
import ch.kerbtier.helene.parser.HeleneParser.MapContext;
import ch.kerbtier.helene.parser.HeleneParser.RootContext;
import ch.kerbtier.helene.parser.HeleneParser.SlugContext;
import ch.kerbtier.helene.parser.HeleneParser.StringContext;
import ch.kerbtier.helene.parser.HeleneParser.UserContext;

public class ImpVisitor extends HeleneBaseVisitor<ImpEntity> {
  
  private ImpEntityMap root;
  
  public ImpVisitor(ImpEntityMap root) {
    this.root = root;
  }

  @Override
  public ImpEntity visitRoot(RootContext ctx) {
    populateEntity(ctx.entity(), root);
    
    return root;
  }


  @Override
  public ImpEntity visitDate(DateContext ctx) {
    return new ImpDateEntity();
  }

  @Override
  public ImpEntity visitString(StringContext ctx) {
    return new ImpStringEntity();
  }

  @Override
  public ImpEntity visitInteger(IntegerContext ctx) {
    return new ImpIntegerEntity();
  }

  @Override
  public ImpEntity visitBlob(BlobContext ctx) {
    return new ImpBlobEntity();
  }

  @Override
  public ImpEntity visitList(ListContext ctx) {
    ImpEntityList list = new ImpEntityList();
    
    ImpEntity type = ctx.type().accept(this);
    
    list.setType(type);
    
    return list;
  }

  @Override
  public ImpEntity visitUser(UserContext ctx) {
    return new ImpUserEntity();
  }

  @Override
  public ImpEntity visitSlug(SlugContext ctx) {
    return new ImpSlugEntity();
  }

  @Override
  public ImpEntity visitMap(MapContext ctx) {
    ImpEntityMap map = new ImpEntityMap();

    populateEntity(ctx.entity(), map);
    
    return map;
  }

  private void populateEntity(List<EntityContext> list, ImpEntityMap map) {
    for(EntityContext ec: list) {
      String key = ec.identifier().getText();
      ImpEntity type = ec.type().accept(this);
      map.put(key, type);
    }
  }
}
