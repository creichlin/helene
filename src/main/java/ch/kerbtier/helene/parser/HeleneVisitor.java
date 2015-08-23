// Generated from src/ch/kerbtier/helene/parser/Helene.g4 by ANTLR 4.3
package ch.kerbtier.helene.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HeleneParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HeleneVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HeleneParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(@NotNull HeleneParser.DateContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(@NotNull HeleneParser.IdentifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#blob}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlob(@NotNull HeleneParser.BlobContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(@NotNull HeleneParser.StringContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(@NotNull HeleneParser.RootContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(@NotNull HeleneParser.IntegerContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull HeleneParser.TypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(@NotNull HeleneParser.ListContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#user}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUser(@NotNull HeleneParser.UserContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#map}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(@NotNull HeleneParser.MapContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#entity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntity(@NotNull HeleneParser.EntityContext ctx);

	/**
	 * Visit a parse tree produced by {@link HeleneParser#slug}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlug(@NotNull HeleneParser.SlugContext ctx);
}