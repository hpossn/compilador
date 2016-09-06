package wirth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import automata.StringTuple;
import automata.SubTuple;

import java.util.List;

public class PushDownTransitionsWirth {
	
	private List<PushDownAutomataWirth> subMachines;
	List<String> inputConfiguration;
	private boolean trace = true;
	
	public PushDownTransitionsWirth(){
		subMachines = new ArrayList<>(); 
		
		inputConfiguration = new ArrayList<String>();
		
		BufferedReader bufferedReader;

		try {
			FileReader fileReader = new FileReader("wirthTransitions.txt");
			bufferedReader = new BufferedReader(fileReader);

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if(!line.equals(""))
					inputConfiguration.add(line);
			}

			bufferedReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Nao encontrado o arquivo de transicoes");
			return;
		} catch (IOException e) {
			System.out.println("Problema inesperado com o arquivo de transicoes.");
			return;
		}
		
	}
	
	public List<PushDownAutomataWirth> getSubMachines() {
		int currentLine = 0;
		boolean keepSearching = true;
		
		while(keepSearching) {
			//FindName
			String subMachineName = inputConfiguration.get(currentLine).split(":")[1];
			currentLine++;
			
			//Busca alfabeto de entrada
			List<String> alphabet = Arrays.asList(inputConfiguration.get(currentLine).split(","));
			currentLine++;
			
			//Busca por estado inicial
			int initialState = Integer.parseInt(inputConfiguration.get(currentLine));
			currentLine++;
			
			//Busca por estados finais
			List<String> finalStates = Arrays.asList(inputConfiguration.get(currentLine).split(","));
			currentLine++;
			
			if(trace) {
				System.out.println("\nCurrent Machine: " + subMachineName);
				System.out.println("Alphabet: " + alphabet.toString());
				System.out.println("Initial states: " + initialState);
				System.out.println("Final States: " + finalStates.toString());
			}
			
			//Busca por transicoes
			String possibleTransitions = inputConfiguration.get(currentLine);
			Map<Integer, List<StringTuple>> transitionsMap = new HashMap<>();
			Map<Integer, SubTuple> subMachineTransitions = new HashMap<>();
			
			if(currentLine == inputConfiguration.size() - 1) {
				keepSearching = false;
			}else {
				boolean hasTransition;
				if(!possibleTransitions.contains("subMachine:")) {
					hasTransition = true;
				} else {
					hasTransition = false;
				}
				while(hasTransition && !possibleTransitions.contains("subMachine:")) {
					List<String> transitions = Arrays.asList(inputConfiguration.get(currentLine).split(","));
					
					String token = transitions.get(1);
					
					if(alphabet.contains(token)) {
						if(token.equals("^")) 
							token = ",";
						
						
						int currentState = Integer.parseInt(transitions.get(0));
						List<StringTuple> temp;
						
						if(transitionsMap.containsKey(currentState)) 
							temp = transitionsMap.get(currentState);
							
						else
							temp = new ArrayList<>();
						
						
						temp.add(new StringTuple(token, Integer.parseInt(transitions.get(2))));
						transitionsMap.put(currentState, temp);
					} else {
						subMachineTransitions.put(Integer.parseInt(transitions.get(0)), new SubTuple(transitions.get(1), Integer.parseInt(transitions.get(2))));
					}
					
					if(trace) {
						if(alphabet.contains(transitions.get(1))) {
							System.out.println("Transition: " + transitions.get(0) + ", " + token + " -> " + transitions.get(2));
						} else {
							System.out.println("SubMachine Transition: " + transitions.get(0) + ", call to  " + transitions.get(1) + ", stores " + transitions.get(2));
						}
					}
						
					currentLine++;
					if(currentLine < inputConfiguration.size()) {
						possibleTransitions = inputConfiguration.get(currentLine);
					} else {
						keepSearching = false;
						hasTransition = false;
					}
				}	
			}
			
			subMachines.add(new PushDownAutomataWirth(subMachineName, transitionsMap, subMachineTransitions, finalStates, initialState));
		}
		
		return subMachines;

	}
}
