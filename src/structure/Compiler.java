package structure;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import automata.PushDownTransitions;
import automata.PushDownAutomata;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.TokenPair;
import lexicalAnalyzer.TokenPair.TokenType;
import syntaticalAnalyzer.SyntaticalAnalyzer;
import wirth.LexicalAnalyzerWirth;
import wirth.SyntaticalAnalyzerWirth;

public class Compiler {
	
	public void initializeCompiler() {
		//printInitialMessage();
		
		// CHANGE TO DEFAULT
		//String fileName = getFileNameFromUser();
		//System.out.println();
		String fileName = "test1.hposs";
		
		startCompilation(fileName);
		
		
	}

	private String getFileNameFromUser() {
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
		
		scanner.close();
		
		return fileName;
	}

	private void printInitialMessage() {
		System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("Compilador - Escola Politecnica da USP");
        System.out.println("------------------------------------------------------------------------------------------------------------\n");
        System.out.print("Arquivo fonte: ");
	}

	private void startCompilation(String fileName) {
		/*LexicalAnalyzer lexicalAnalyzer = null;
		
		try {
			lexicalAnalyzer = new LexicalAnalyzer(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo fonte nao encontrado.");
			return;
		}
		
		lexicalAnalyzer.setTrackSwitch(true);
		lexicalAnalyzer.readFile();
		String fileString = lexicalAnalyzer.getNumberedLinesFile();
		
		System.out.println("\nArquivo lido:\n\n" + fileString);
		
		System.out.print("------------------------------------------------------------------------------------------------------------");
		System.out.println("\nTokenizer");
		System.out.println("------------------------------------------------------------------------------------------------------------\n");
		
		boolean keepReading = true;
		
		while(keepReading) {
			 TokenPair token = lexicalAnalyzer.getNextToken();
			 
			 if(token.getTokenType() == TokenType.INVALID ||
					 token.getTokenType() == TokenType.EOF)
				 keepReading = false;
			 
			 System.out.println("Token: " + token.toString());
		}
		
		##################
		
		SyntaticalAnalyzer syntacticAnalyzer = new SyntaticalAnalyzer(lexicalAnalyzer);
		syntacticAnalyzer.recognize();
		

		###################*/
		
		
		wirthAnalyzer();
	
	}

	private void wirthAnalyzer() {
		LexicalAnalyzerWirth lexicalAnalyzerWirth;
		
		try {
			lexicalAnalyzerWirth = new LexicalAnalyzerWirth("grammar.txt");
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo fonte nao encontrado.");
			System.exit(0);
			return;
		}
		
		lexicalAnalyzerWirth.setTrackSwitch(true);
		lexicalAnalyzerWirth.readFile();
		String fileString = lexicalAnalyzerWirth.getNumberedLinesFile();
		
		System.out.println("\nArquivo lido:\n\n" + fileString);
		
		System.out.print("------------------------------------------------------------------------------------------------------------");
		System.out.println("\nTokenizer");
		System.out.println("------------------------------------------------------------------------------------------------------------\n");
		
		boolean keepReading = true;
		
		while(keepReading) {
			 TokenPair token = lexicalAnalyzerWirth.getNextToken();
			 
			 if(token.getTokenType() == TokenType.INVALID ||
					 token.getTokenType() == TokenType.EOF)
				 keepReading = false;
			 
			 System.out.println("Token: " + token.toString());
		}
		
		SyntaticalAnalyzerWirth syntacticAnalyzerWirth = new SyntaticalAnalyzerWirth(lexicalAnalyzerWirth);
		syntacticAnalyzerWirth.recognize();

	}
}
