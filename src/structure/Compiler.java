package structure;

import java.io.FileNotFoundException;
import java.util.Scanner;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.TokenPair;
import lexicalAnalyzer.TokenPair.TokenType;
import maker.AutomataConverter;
import maker.MakeAutomata;
import syntaticalAnalyzer.SyntaticalAnalyzer;
import wirth.LexicalAnalyzerWirth;
import wirth.SyntaticalAnalyzerWirth;

public class Compiler {
	
	private Scanner scanner = new Scanner(System.in);
	
	public void initializeCompiler() {
		printInitialMessage();
		
		String input;
		//String input = scanner.nextLine();
		//input.trim();
		
		int option = 1;
		
//		try {
//			option = Integer.parseInt(input);
//		} catch (Exception e) {
//			System.out.println("Valor incorreto");
//			System.exit(0);
//		}
		
		
		//int option = 4;
		
		System.out.print("Digite o nome do arquivo de entrada: ");
		
		//String fileName = scanner.nextLine().trim();
		String fileName = "program.hposs";

		System.out.print("Ativar Trace (y/n): ");
		
		//input = scanner.nextLine().trim();
		//String input = "y";
		boolean trace = true;
		//boolean trace = false;
		
		input = "y";
		
		switch(input) {
		case "y":
			trace = true;
			break;
		}
		
		//System.out.println();
		
		switch(option) {
		case 1:
			startCompilation(fileName, trace);
			break;
		case 2:
			wirthAnalyzer(fileName, trace);
			break;
		case 3:
			makeAutomata(fileName, trace);
			break;
		case 4:
			eliminaVazio(fileName, trace);
			break;
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
        System.out.println("4: Eliminar transicoes em vazio de automato");
        System.out.println();
        
        System.out.print("Digite o numero da opcao: ");
	}

	private void startCompilation(String fileName, boolean trace) {
		LexicalAnalyzer lexicalAnalyzer = null;
		trace = false;
//		System.out.print("Arquivo com transicoes (\"Enter\" para padrao): ");
//		
//		String ofile = scanner.nextLine().trim();
//		
//		if(ofile.equals(""))
//			ofile = "automata";
		
		String ofile = "automata";
		
		
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
		
		SyntaticalAnalyzer syntacticAnalyzer = new SyntaticalAnalyzer(lexicalAnalyzer, trace, ofile);
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
		System.out.print("Arquivo de saida: ");
		
		String ofile = scanner.nextLine().trim();
		
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
		
		if(trace) {
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
		automataMaker.make(ofile);
	}
	
	private void eliminaVazio(String fileName, boolean trace) {
		System.out.print("Arquivo de saida: ");
		
		String ofile = scanner.nextLine().trim();
		//String ofile = "automatoProntoSemVazio.txt";
		
		AutomataConverter converter = new AutomataConverter(fileName, trace, ofile);
		converter.convert();
	}
}
