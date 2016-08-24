package lexicalAnalyzer;

import java.util.HashMap;
import java.util.Map;

import lexicalAnalyzer.TokenPair.TokenType;

public class TokenClassifier {
	
	private Map<String, TokenType> tokenMap = new HashMap<>();
	
	public TokenClassifier() {
		addValuesToHashMap();
	}
	
	private void addValuesToHashMap() {
		tokenMap.put("PROGRAM", TokenType.PROGRAM);
		tokenMap.put("VAR", TokenType.VARIABLE);
		tokenMap.put("INTEGER", TokenType.VAR_TYPE);
		tokenMap.put("STRING", TokenType.VAR_TYPE);
		tokenMap.put("BOOLEAN", TokenType.VAR_TYPE);
		tokenMap.put("VECTOR", TokenType.VAR_TYPE);
		tokenMap.put("FUNCTION", TokenType.FUNC_DEC);
		tokenMap.put("->", TokenType.FUNC_DEC);
		tokenMap.put("(", TokenType.FUNC_DEC);
		tokenMap.put(")", TokenType.FUNC_DEC);
		tokenMap.put("RETURN", TokenType.FUNC_DEC);
		tokenMap.put("ENDFUNC", TokenType.FUNC_DEC);
		tokenMap.put("BEGIN", TokenType.BLOCK);
		tokenMap.put("END", TokenType.BLOCK);
		tokenMap.put("GOTO", TokenType.GOTO);
		tokenMap.put("PRINT", TokenType.IO);
		tokenMap.put("READ", TokenType.IO);
		tokenMap.put("IF", TokenType.CONDITIONAL);
		tokenMap.put("THEN:", TokenType.CONDITIONAL);
		tokenMap.put("ELSE:", TokenType.CONDITIONAL);
		tokenMap.put("ENDIF", TokenType.CONDITIONAL);
		tokenMap.put("WHILE", TokenType.LOOP);
		tokenMap.put("DO:", TokenType.LOOP);
		tokenMap.put("ENDWHILE", TokenType.LOOP);
		tokenMap.put("TRUE", TokenType.BOOL);
		tokenMap.put("FALSE", TokenType.BOOL);
		tokenMap.put(";", TokenType.SEPARATOR);
		tokenMap.put(",", TokenType.SEPARATOR);
		tokenMap.put(":", TokenType.TYPE_INDICATOR);
		tokenMap.put("[", TokenType.VECT_ARG);
		tokenMap.put("]", TokenType.VECT_ARG);
		tokenMap.put("..", TokenType.VECT_ARG);
		tokenMap.put(":=", TokenType.COMPARATOR);
		tokenMap.put("<", TokenType.COMPARATOR);
		tokenMap.put(">", TokenType.COMPARATOR);
		tokenMap.put("<=", TokenType.COMPARATOR);
		tokenMap.put(">=", TokenType.COMPARATOR);
		tokenMap.put("==", TokenType.COMPARATOR);
		tokenMap.put("!=", TokenType.COMPARATOR);
		tokenMap.put("+", TokenType.OPERATION);
		tokenMap.put("-", TokenType.OPERATION);
		tokenMap.put("/", TokenType.OPERATION);
		tokenMap.put("*", TokenType.OPERATION);
		tokenMap.put("#", TokenType.COMMENT);
		tokenMap.put("EOF", TokenType.EOF);
	}

	public TokenType classify(String token) {
		if(token.equals("invalid token"))
			return TokenType.INVALID;
		
		if(tokenMap.containsKey(token))
			return tokenMap.get(token);
		
		if(token.contains("\""))
			return  TokenType.STRING;
		
		if(isNumber(token))
			return TokenType.NUMBER;
		
		else return TokenType.IDENTIFIER;
	}

	private boolean isNumber(String token) {
		try{
			Integer.parseInt(token);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
