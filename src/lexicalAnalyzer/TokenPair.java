package lexicalAnalyzer;

import java.util.Objects;

public class TokenPair {
	
	public enum TokenType {
		PROGRAM, VARIABLE, VAR_TYPE, FUNC_DEC, BLOCK, GOTO,
		IO, CONDITIONAL, LOOP, BOOL, SEPARATOR, TYPE_INDICATOR,
		VECT_ARG, ATRIBUTION, COMPARATOR, OPERATION, STRING,
		IDENTIFIER, NUMBER, COMMENT, EOF, INVALID, TERMINAL, NON_TERMINAL, SYMBOL, BLANK
	}
	
	private TokenType tokenType;
	private String value;

	public TokenPair(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}
	
	public TokenPair(TokenType tokenType) {
		this(tokenType, null);
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof TokenPair)) return false;
		
		TokenPair token = (TokenPair) o;
		
		return this.tokenType == token.tokenType && this.value.equals(token.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(tokenType, value);
	}
	
	@Override
	public String toString() {
		return String.format("(%s, \"%s\")", this.tokenType, this.value);
	}
	
}
