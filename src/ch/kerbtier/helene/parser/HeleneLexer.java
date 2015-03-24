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
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, STRING=6, USER=7, DATE=8, INTEGER=9, 
		IDENTIFIER=10, BLOCK_COMMENT=11, EOL_COMMENT=12, WS=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'"
	};
	public static final String[] ruleNames = {
		"T__4", "T__3", "T__2", "T__1", "T__0", "STRING", "USER", "DATE", "INTEGER", 
		"IDENTIFIER", "BLOCK_COMMENT", "EOL_COMMENT", "WS"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17f\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\6\13C\n\13\r\13\16\13D\3\f\3"+
		"\f\3\f\3\f\7\fK\n\f\f\f\16\fN\13\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\7\rY\n\r\f\r\16\r\\\13\r\3\r\3\r\3\16\6\16a\n\16\r\16\16\16b\3\16\3\16"+
		"\3L\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\3\2\6\4\2C\\c|\6\2\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\17\17\"\"i\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t#\3\2\2\2\13"+
		"%\3\2\2\2\r\'\3\2\2\2\17.\3\2\2\2\21\63\3\2\2\2\238\3\2\2\2\25@\3\2\2"+
		"\2\27F\3\2\2\2\31T\3\2\2\2\33`\3\2\2\2\35\36\7<\2\2\36\4\3\2\2\2\37 \7"+
		"}\2\2 \6\3\2\2\2!\"\7]\2\2\"\b\3\2\2\2#$\7\177\2\2$\n\3\2\2\2%&\7_\2\2"+
		"&\f\3\2\2\2\'(\7u\2\2()\7v\2\2)*\7t\2\2*+\7k\2\2+,\7p\2\2,-\7i\2\2-\16"+
		"\3\2\2\2./\7w\2\2/\60\7u\2\2\60\61\7g\2\2\61\62\7t\2\2\62\20\3\2\2\2\63"+
		"\64\7f\2\2\64\65\7c\2\2\65\66\7v\2\2\66\67\7g\2\2\67\22\3\2\2\289\7k\2"+
		"\29:\7p\2\2:;\7v\2\2;<\7g\2\2<=\7i\2\2=>\7g\2\2>?\7t\2\2?\24\3\2\2\2@"+
		"B\t\2\2\2AC\t\3\2\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\26\3\2\2"+
		"\2FG\7\61\2\2GH\7,\2\2HL\3\2\2\2IK\13\2\2\2JI\3\2\2\2KN\3\2\2\2LM\3\2"+
		"\2\2LJ\3\2\2\2MO\3\2\2\2NL\3\2\2\2OP\7,\2\2PQ\7\61\2\2QR\3\2\2\2RS\b\f"+
		"\2\2S\30\3\2\2\2TU\7\61\2\2UV\7\61\2\2VZ\3\2\2\2WY\n\4\2\2XW\3\2\2\2Y"+
		"\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\Z\3\2\2\2]^\b\r\2\2^\32\3\2"+
		"\2\2_a\t\5\2\2`_\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\b\16"+
		"\2\2e\34\3\2\2\2\7\2DLZb\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}