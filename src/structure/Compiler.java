package structure;

import java.io.FileNotFoundException;
import java.util.Scanner;

import lexicalAnalyzer.LexicalAnalyzer;

public class Compiler {
	
	public void initializeCompiler() {
		printInitialMessage();
		
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
		LexicalAnalyzer lexicalAnalyzer = null;
		
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
		
		String token = "";
		
		while(!token.equals("EOF")) {
			 token = lexicalAnalyzer.getNextToken();
			 System.out.println("Token: " + token);
		}
		
		/*for(int i = 0; i < 50; i++) {
			token = lexicalAnalyzer.getNextToken();
			System.out.println("Token: " + token);
		}*/
		
	}
}