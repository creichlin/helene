package ch.kerbtier.helene.parser;

import java.util.List;
import java.util.Stack;

import com.google.common.base.Joiner;

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
  
  private Stack<ImpEntity> parents = new Stack<>();
  private Stack<String> names = new Stack<>();
  
  
  private ImpEntityMap root;
  
  public ImpVisitor(ImpEntityMap root) {
    this.root = root;
    parents.push(root);
  }
  
  private String getName() {
    return Joiner.on(".").join(names);
  }

  @Override
  public ImpEntity visitRoot(RootContext ctx) {
    populateEntity(ctx.entity(), root);
    
    return root;
  }


  @Override
  public ImpEntity visitDate(DateContext ctx) {
    return new ImpDateEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitString(StringContext ctx) {
    return new ImpStringEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitInteger(IntegerContext ctx) {
    return new ImpIntegerEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitBlob(BlobContext ctx) {
    return new ImpBlobEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitList(ListContext ctx) {
    ImpEntityList list = new ImpEntityList(parents.peek(), getName());
    
    names.push("_");
    parents.push(list);
    list.setType(ctx.type().accept(this));
    parents.pop();
    names.pop();
    
    return list;
  }

  @Override
  public ImpEntity visitUser(UserContext ctx) {
    return new ImpUserEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitSlug(SlugContext ctx) {
    return new ImpSlugEntity(parents.peek(), getName());
  }

  @Override
  public ImpEntity visitMap(MapContext ctx) {
    ImpEntityMap map = new ImpEntityMap(parents.peek(), getName());

    parents.push(map);
    populateEntity(ctx.entity(), map);
    parents.pop();
    
    return map;
  }

  private void populateEntity(List<EntityContext> list, ImpEntityMap map) {
    for(EntityContext ec: list) {
      String key = ec.identifier().getText();
      names.push(key);
      ImpEntity type = ec.type().accept(this);
      names.pop();
      map.put(key, type);
    }
  }
}
