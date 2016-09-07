package structure;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import automata.PushDownTransitions;
import automata.PushDownAutomata;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.TokenPair;
import lexicalAnalyzer.TokenPair.TokenType;
import maker.MakeAutomata;
import syntaticalAnalyzer.SyntaticalAnalyzer;
import wirth.LexicalAnalyzerWirth;
import wirth.SyntaticalAnalyzerWirth;

public class Compiler {
	
	public void initializeCompiler() {
		printInitialMessage();
		
		/*Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		input.trim();*/
		
		//int option = Integer.parseInt(input);
		int option = 3;
		
		System.out.print("Digite o nome do arquivo de entrada: ");
		
		//String fileName = scanner.nextLine().trim();
		String fileName = "makeAutomata.txt";
		
		System.out.print("Ativar Trace (y/n): ");
		
		//input = scanner.next().trim();
		String input = "y";
		
		boolean trace = false;
		
		switch(input) {
		case "y":
			trace = true;
			break;
		}
		
		System.out.println();
		
		//scanner.close();
		
		switch(option) {
		case 1:
			startCompilation(fileName, trace);
			break;
		case 2:
			wirthAnalyzer(fileName, trace);
		case 3:
			makeAutomata(fileName, trace);

		}
		
	}


	private void printInitialMessage() {
		System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("Compilador - Escola Politecnica da USP");
        System.out.println("------------------------------------------------------------------------------------------------------------\n");
        System.out.print("Options\n");
        System.out.println("------------------------------------------------------------------------------------------------------------\n");
        System.out.println("1: Arquivo de entrada - Linguagem HPOSS");
        System.out.println("2: Analise de gramaticas em notacao de Wirth");
        System.out.println("3: Construir automato a partir de Wirth");
        System.out.println();
        
        System.out.print("Digite o numero da opcao: ");
	}

	private void startCompilation(String fileName, boolean trace) {
		LexicalAnalyzer lexicalAnalyzer = null;
		
		try {
			lexicalAnalyzer = new LexicalAnalyzer(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo fonte nao encontrado.");
			return;
		}
		
		lexicalAnalyzer.setTrackSwitch(trace);
		lexicalAnalyzer.readFile();
		String fileString = lexicalAnalyzer.getNumberedLinesFile();
		
		System.out.println("\nArquivo lido:\n\n" + fileString);
		
		if(trace) {
			System.out.print("------------------------------------------------------------------------------------------------------------");
			System.out.println("\nTokenizer");
			System.out.println("------------------------------------------------------------------------------------------------------------\n");	
		}
		
		boolean keepReading = true;
		
		while(keepReading) {
			 TokenPair token = lexicalAnalyzer.getNextToken();
			 
			 
			 if(token.getTokenType() == TokenType.INVALID ||
					 token.getTokenType() == TokenType.EOF)
				 keepReading = false;
			 
			 if(trace)
				 System.out.println("Token: " + token.toString());
		}
		
		SyntaticalAnalyzer syntacticAnalyzer = new SyntaticalAnalyzer(lexicalAnalyzer, trace);
		syntacticAnalyzer.recognize();
	
	}

	private void wirthAnalyzer(String fileName, boolean trace) {
		LexicalAnalyzerWirth lexicalAnalyzerWirth;
		
		try {
			lexicalAnalyzerWirth = new LexicalAnalyzerWirth(fileName);
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
		
		lexicalAnalyzerWirth.resetAnalyzer();
		
		SyntaticalAnalyzerWirth syntacticAnalyzerWirth = new SyntaticalAnalyzerWirth(lexicalAnalyzerWirth);
		syntacticAnalyzerWirth.recognize();

	}
	
	private void makeAutomata(String fileName, boolean trace) {
		LexicalAnalyzerWirth lexicalAnalyzerWirth;
		
		try {
			lexicalAnalyzerWirth = new LexicalAnalyzerWirth(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo fonte nao encontrado.");
			System.exit(0);
			return;
		}
		
		
		lexicalAnalyzerWirth.setTrackSwitch(false);
		lexicalAnalyzerWirth.readFile();
		String fileString = lexicalAnalyzerWirth.getNumberedLinesFile();
		
		System.out.println("\nArquivo lido:\n\n" + fileString);
		
		System.out.print("------------------------------------------------------------------------------------------------------------");
		System.out.println("\nTokenizer");
		System.out.println("------------------------------------------------------------------------------------------------------------\n");
		
		boolean keepReading = true;
		
		if(false) {
			while(keepReading) {
				 TokenPair token = lexicalAnalyzerWirth.getNextToken();
				 
				 if(token.getTokenType() == TokenType.INVALID ||
						 token.getTokenType() == TokenType.EOF)
					 keepReading = false;
				 
				 System.out.println("Token: " + token.toString());
			}
		}
		
		System.out.println();
		
		lexicalAnalyzerWirth.resetAnalyzer();
		
		MakeAutomata automataMaker = new MakeAutomata(trace, lexicalAnalyzerWirth);
		automataMaker.make();
		
	}
}
