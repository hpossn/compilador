package wirth;

import lexicalAnalyzer.FileParser;
import lexicalAnalyzer.TokenPair;
import lexicalAnalyzer.TokenPair.TokenType;

public class TransducerWirth {
	
	private String fileContent;
	
	private int currentIndex = 0;
	private int maxIndex;
	
	private FiniteAutomataWirth automata;
	
	
	public TransducerWirth() {
		automata = new FiniteAutomataWirth();
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
		maxIndex = fileContent.length() - 1;
	}
	
	public boolean isEOF() {
		return currentIndex >= maxIndex;
	}
	
	public void reset() {
		automata.reset();
		currentIndex = 0;
	}
	
	public TokenPair getNextToken() {
		if (isEOF())
			return makePair("EOF");

		StringBuilder buffer = new StringBuilder();

		char ch = getCleanChar();

		while(ch == ' ')
			ch = getCleanChar();
		
		if (ch == FileParser.END_OF_FILE)
			return makePair("EOF");

		boolean flag = false;

		while (true) {
			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
				buffer.append(ch);
				ch = getCleanChar();
				flag = true;
			} else {
				break;
			}

		}
			
			
		if(flag) {
			currentIndex--;
			return makePair(buffer.toString());
		}
		
		if(ch != '"')
			return makePair(ch + "");

		buffer.append("\"");
		
		if(fileContent.charAt(currentIndex) == '"') {
			currentIndex += 2;
			return makePair(buffer.toString());
		}
		

		while ((ch = getNextChar()) != '"') {
			if (ch == FileParser.END_OF_FILE)
				return makePair(buffer.toString());

			buffer.append(ch);
		}

		buffer.append("\"");
		

		return makePair(buffer.toString());
	}

	private char getCleanChar() {
		char ch = getNextChar();
		
		while(ch == '\n' || ch == '\t' || ch == '\r')
			ch = getNextChar();
		return ch;
	}

	
	private TokenPair makePair(String token) {
		if(token.equals("EOF"))
			return new TokenPair(TokenType.EOF, token);
		
		if(token.contains("\""))
			return new TokenPair(TokenType.TERMINAL, token);
		
		if(token.length() == 1)
			return new TokenPair(TokenType.SYMBOL, token);
		
		return new TokenPair(TokenType.NON_TERMINAL, token);
	}
	
	private char getNextChar() {
		if(isEOF())
			return FileParser.END_OF_FILE;
		
		return fileContent.charAt(currentIndex++);
	}

}
