package ch.kerbtier.helene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import ch.kerbtier.helene.entities.EntityMap;
import ch.kerbtier.helene.exceptions.ModelParseException;
import ch.kerbtier.helene.impl.ImpEntityMap;
import ch.kerbtier.helene.parser.HeleneLexer;
import ch.kerbtier.helene.parser.HeleneParser;
import ch.kerbtier.helene.parser.HeleneParser.RootContext;
import ch.kerbtier.helene.parser.ImpVisitor;

public class Parse {
  
  public static void extend(EntityMap root, Path path) {
    try {
      extend(root, new FileInputStream(path.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static void extend(EntityMap root, InputStream in) {
    extend(root, in, null);
  }

  public static void extend(EntityMap root, InputStream in, Charset charset) {
    Reader reader = new InputStreamReader(in, charset == null ? Charset.defaultCharset() : charset);
    extend(root, reader);
  }
  
  public static void extend(EntityMap root, Reader reader) {
    try {
      ANTLRInputStream input = new ANTLRInputStream(reader);

      HeleneLexer lexer = new HeleneLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      HeleneParser parser = new HeleneParser(tokens);
      parser.setErrorHandler(new BailErrorStrategy());

      RootContext tree = parser.root();

      reader.close();

      ImpVisitor iv = new ImpVisitor((ImpEntityMap)root);
      tree.accept(iv);
    } catch (ParseCancellationException e) {
      throw new ModelParseException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
