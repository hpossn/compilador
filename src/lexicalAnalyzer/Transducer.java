package lexicalAnalyzer;

import automata.FiniteAutomata;
import lexicalAnalyzer.TokenPair.TokenType;

public class Transducer {
	
	private String fileContent;
	
	private int currentIndex = 0;
	private int maxIndex;
	
	private FiniteAutomata automata;
	
	private TokenClassifier classifier;
	
	public Transducer() {
		automata = new FiniteAutomata();
		classifier = new TokenClassifier();
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
		maxIndex = fileContent.length() - 1;
	}
	
	public boolean isEOF() {
		return currentIndex >= maxIndex;
	}
	
	public TokenPair getNextToken() {
		if(isEOF())
			return makePair("EOF");
		
		StringBuilder buffer = new StringBuilder();
		
		boolean continua = true;
		
		while(continua) {
			char ch = getNextChar();
			
			if(ch == FileParser.END_OF_FILE)
				return makePair(buffer.toString());
				
			int nextState = automata.makeTransition(ch);
			
			switch(nextState) {
			
			case FiniteAutomata.NO_TRANSITION:
				moveBackOne();
				return makePair("invalid token");
				
			case FiniteAutomata.END_OF_TOKEN:
				moveBackOne();
				continua = false;
				automata.reset();
				break;
				
			default:
				if(ch != ' ' && ch != '\n' && ch != '\t')
					buffer.append(ch);
			}

		}
		
		return makePair(buffer.toString());
	}
	

	
	private TokenPair makePair(String token) {
		TokenType tokenType = classifier.classify(token);
		return new TokenPair(tokenType, token);
	}


	private void moveBackOne() {
		if(currentIndex > 0)
			currentIndex--;
	}
	
	private char getNextChar() {
		if(isEOF())
			return FileParser.END_OF_FILE;
		
		return fileContent.charAt(currentIndex++);
	}

}
