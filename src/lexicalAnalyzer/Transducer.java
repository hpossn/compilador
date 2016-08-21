package lexicalAnalyzer;

import automata.FiniteAutomata;

public class Transducer {
	
	private String fileContent;
	
	private int currentIndex = 0;
	private int maxIndex;
	
	FiniteAutomata automata;
	
	
	public Transducer() {
		
		automata = new FiniteAutomata();
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
		maxIndex = fileContent.length() - 1;
	}
	
	public boolean isEOF() {
		return currentIndex >= maxIndex;
	}
	
	public String getNextToken() {
		if(isEOF())
			return "EOF";
		
		StringBuilder buffer = new StringBuilder();
		boolean invalid = false;
		boolean continua = true;
		
		while(continua) {
			char ch = getNextChar();
			
			if(ch == FileParser.END_OF_FILE) {
				return buffer.toString();
			}
				
			int nextState = automata.makeTransition(ch);
			
			switch(nextState) {
			
			case FiniteAutomata.NO_TRANSITION:
				invalid = true;
				
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
		
		if(invalid) 
			return "invalid token";
		
		return buffer.toString();
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
