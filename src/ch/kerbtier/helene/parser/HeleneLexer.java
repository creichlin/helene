// Generated from src/ch/kerbtier/helene/parser/Helene.g4 by ANTLR 4.3
package ch.kerbtier.helene.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HeleneLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, STRING=6, SLUG=7, USER=8, DATE=9, 
		INTEGER=10, IDENTIFIER=11, BLOCK_COMMENT=12, EOL_COMMENT=13, WS=14;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'"
	};
	public static final String[] ruleNames = {
		"T__4", "T__3", "T__2", "T__1", "T__0", "STRING", "SLUG", "USER", "DATE", 
		"INTEGER", "IDENTIFIER", "BLOCK_COMMENT", "EOL_COMMENT", "WS"
	};


	public HeleneLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Helene.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\20m\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\6\fJ\n\f\r\f\16\fK\3\r\3\r\3\r\3\r\7\rR\n\r\f\r\16\rU\13\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\7\16`\n\16\f\16\16\16c\13\16\3\16"+
		"\3\16\3\17\6\17h\n\17\r\17\16\17i\3\17\3\17\3S\2\20\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\6\4\2C\\c|\6\2"+
		"\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\17\17\"\"p\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7#\3\2\2\2\t%\3\2\2\2\13\'\3\2"+
		"\2\2\r)\3\2\2\2\17\60\3\2\2\2\21\65\3\2\2\2\23:\3\2\2\2\25?\3\2\2\2\27"+
		"G\3\2\2\2\31M\3\2\2\2\33[\3\2\2\2\35g\3\2\2\2\37 \7<\2\2 \4\3\2\2\2!\""+
		"\7}\2\2\"\6\3\2\2\2#$\7]\2\2$\b\3\2\2\2%&\7\177\2\2&\n\3\2\2\2\'(\7_\2"+
		"\2(\f\3\2\2\2)*\7u\2\2*+\7v\2\2+,\7t\2\2,-\7k\2\2-.\7p\2\2./\7i\2\2/\16"+
		"\3\2\2\2\60\61\7u\2\2\61\62\7n\2\2\62\63\7w\2\2\63\64\7i\2\2\64\20\3\2"+
		"\2\2\65\66\7w\2\2\66\67\7u\2\2\678\7g\2\289\7t\2\29\22\3\2\2\2:;\7f\2"+
		"\2;<\7c\2\2<=\7v\2\2=>\7g\2\2>\24\3\2\2\2?@\7k\2\2@A\7p\2\2AB\7v\2\2B"+
		"C\7g\2\2CD\7i\2\2DE\7g\2\2EF\7t\2\2F\26\3\2\2\2GI\t\2\2\2HJ\t\3\2\2IH"+
		"\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\30\3\2\2\2MN\7\61\2\2NO\7,\2\2"+
		"OS\3\2\2\2PR\13\2\2\2QP\3\2\2\2RU\3\2\2\2ST\3\2\2\2SQ\3\2\2\2TV\3\2\2"+
		"\2US\3\2\2\2VW\7,\2\2WX\7\61\2\2XY\3\2\2\2YZ\b\r\2\2Z\32\3\2\2\2[\\\7"+
		"\61\2\2\\]\7\61\2\2]a\3\2\2\2^`\n\4\2\2_^\3\2\2\2`c\3\2\2\2a_\3\2\2\2"+
		"ab\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\b\16\2\2e\34\3\2\2\2fh\t\5\2\2gf\3\2"+
		"\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2jk\3\2\2\2kl\b\17\2\2l\36\3\2\2\2\7"+
		"\2KSai\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}