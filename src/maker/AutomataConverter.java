package maker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import automata.StringTuple;
import automata.SubTuple;
import wirth.PushDownAutomataWirth;

public class AutomataConverter {
	private boolean trace = false;

	private TransitionsMaker loadedAutomata;
	private List<PushDownAutomataWirth> machines;
	private List<String> cache;
	private String outputFilename;

	public AutomataConverter(String fileName, boolean trace, String ofile) {
		this.trace = trace;
		this.outputFilename = ofile;
		//outputFilename = "automatoProntoSemVazio.txt";
		cache = new ArrayList<>();

		loadedAutomata = new TransitionsMaker(fileName, false);
		machines = loadedAutomata.getSubMachines();
	}

	public void convert() {		
		List<StringTupleComplete> emptyTransitions;
		List<StringTupleComplete> allTransistions;

		int count = 0;
		
		while(true) {
			
			PushDownAutomataWirth currentMachine = machines.get(count);
			
			allTransistions = getAllTransitions(currentMachine);
			emptyTransitions = getAllEmptyTransitions(allTransistions);
			
			int initialState = currentMachine.getInitialState();
			List<String> finalStates = currentMachine.getFinalStates();
			
			//printAllTransitions(currentMachineTrans);
			
			if(!emptyTransitions.isEmpty()) {
				while(true) {
					
					StringTupleComplete eachBlank = emptyTransitions.get(0);
					
					int origin = eachBlank.getOrigin();
					int destination = eachBlank.getNextState();
					
					if(origin == destination) {
						allTransistions.remove(eachBlank);
						
						emptyTransitions = getAllEmptyTransitions(allTransistions);						
						
						if(emptyTransitions.isEmpty())
							break;
						else
							continue;
					}
					
					if(trace) {
						System.out.println("------------------");
						System.out.println("Estado de destino " + destination + "\n");
						
						System.out.println("Inicialmente: ");
					}
					List<StringTupleComplete> leavingTransitions = getLeavingTrans(destination, allTransistions);
					List<StringTupleComplete> arrivingTransitions;
					
					
					List<StringTupleComplete> temp = new ArrayList<>(leavingTransitions);
					for(StringTupleComplete eachLeaving : temp) {
						leavingTransitions.remove(eachLeaving);
						eachLeaving.setOrigin(origin);
						leavingTransitions.add(eachLeaving);
					}					
					
					insertTransitions(allTransistions, leavingTransitions);
					
					if(trace) {
						System.out.println("Apos algoritmo:");
						leavingTransitions.forEach(System.out::println);
					}
					
					arrivingTransitions = getArrivingTrans(destination, allTransistions);
					
					temp = new ArrayList<>(arrivingTransitions);
					for(StringTupleComplete eachArriving : temp) {
						arrivingTransitions.remove(eachArriving);
						eachArriving.setNextState(origin);
						arrivingTransitions.add(eachArriving);
					}		
					
					if(trace) {
						arrivingTransitions.forEach(System.out::println);
					}
		
					allTransistions.remove(eachBlank);
					
					
					insertTransitions(allTransistions, arrivingTransitions);
					
					removeAllWithState(destination, allTransistions);
					
					if(destination == initialState)
						initialState = origin;
					
					if(finalStates.contains(destination + "")) {
						int index = finalStates.indexOf(destination + "");
						finalStates.set(index, origin + "");
					}
						
					
					emptyTransitions = getAllEmptyTransitions(allTransistions);
					
					//System.out.println("\nAll transitions");
					//allTransistions.forEach(System.out::println);
					
					
					if(emptyTransitions.isEmpty())
						break;
				}
				
				System.out.println("\n\nSub-Maquina: " + currentMachine.getSubMachineName());
				
				System.out.println("Todas as transicoes");
				allTransistions.forEach(System.out::println);
				
				System.out.println("\nEstado inicial: " + initialState);
				System.out.println("Estados finais: " + finalStates.toString());
				
				cacheMachine(allTransistions, currentMachine, initialState, finalStates);
			}
			
			count++;
			
			if(count >= machines.size()) {
				break;
			}
		}
		
		writeToFile();

	}
	
	private void writeToFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputFilename, "UTF-8");
			cache.forEach(each -> writer.write(each + "\n"));
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Problema na escrita do arquivo");
			System.exit(0);
		}
		
	}

	private void cacheMachine(List<StringTupleComplete> allTransistions, PushDownAutomataWirth currentMachine, int initialState, List<String> finalStates) {
		cache.add("subMachine:" + currentMachine.getSubMachineName());
		
		StringBuilder sb = new StringBuilder();
		
		List<String> temp = new ArrayList<>();
		
		for(StringTupleComplete each : allTransistions) {
			if(!each.getToken().startsWith("sub")) {
				if(!temp.contains(each.getToken())) {
					temp.add(each.getToken());
					sb.append(each.getToken().replace(',', '^') + ",");
				}
			}
		}
		
		if(sb.length() > 0) {
			cache.add(sb.substring(0, sb.length() - 1));
		} else {
			cache.add("^");
		}
		
		cache.add(initialState + "");
		
		
		sb = new StringBuilder();
		for(String each : finalStates) {
			sb.append(each + ",");
		}
		
		if(sb.length() > 0)
			cache.add(sb.substring(0, sb.length() - 1));
		
		for(StringTupleComplete each : allTransistions) {
			cache.add(each.getOrigin() + "," + each.getToken().replace(',', '^') + "," + each.getNextState());
		}
		
		cache.add("\n");
	}

	private void removeAllWithState(int state, List<StringTupleComplete> allTransistions) {
		List<StringTupleComplete> temp;
		
		temp = new ArrayList<>(allTransistions);
		for(StringTupleComplete each : temp) {
			if(each.getOrigin() == state)
				allTransistions.remove(each);
		}
		
		temp = new ArrayList<>(allTransistions);
		for(StringTupleComplete each : temp) {
			if(each.getNextState() == state)
				allTransistions.remove(each);
		}
	}

	private List<StringTupleComplete> insertTransitions(List<StringTupleComplete> allTransitions, List<StringTupleComplete> newTransitions) {
		for(StringTupleComplete each : newTransitions) {
			if(allTransitions.contains(each)) continue;
			allTransitions.add(each);
		}
		return allTransitions;
	}

	private List<StringTupleComplete> getAllTransitions(PushDownAutomataWirth currentMachine) {
		List<StringTupleComplete> transitions = new ArrayList<>();
		
		
		Map<Integer, List<StringTuple>> temp = currentMachine.getStateTransitions();
		for(int entry : temp.keySet()) {
			for(StringTuple each : temp.get(entry)) {
				transitions.add(new StringTupleComplete(entry, each));
			}
		}
		
		Map<Integer, SubTuple> subMachineTransitions = currentMachine.getSubMachineTransitions();
		for(int entry : subMachineTransitions.keySet()) {
			SubTuple st = subMachineTransitions.get(entry);
			transitions.add(new StringTupleComplete(entry, st.getNextSubMachine(), st.getReturnState()));
		}
		
		
		if(trace) {
			System.out.println("\nTodas as transicoes: ");
			transitions.forEach(System.out::println);
			System.out.println();
		}
		
		return transitions;
		
	}

	private List<StringTupleComplete> getAllEmptyTransitions(List<StringTupleComplete> allTransitions) {			
		List<StringTupleComplete> emptyTransitions = new ArrayList<>();
		
		for (StringTupleComplete each : allTransitions) {
			if (each.getToken().equals("BLANK")) {
				emptyTransitions.add(each);
			}
		}
		
		if(trace) {
			System.out.println("Transicao em vazio: ");
			emptyTransitions.forEach(System.out::println);
		}
		
		return emptyTransitions;
	}

	private List<StringTupleComplete> getArrivingTrans(int destination,
			List<StringTupleComplete> allTransitions) {
		 
		List<StringTupleComplete> temp = new ArrayList<>();
		
		for(StringTupleComplete each : allTransitions) {
			if(each.getNextState() == destination)	
				temp.add(new StringTupleComplete(each.getOrigin(), each.getToken(), destination));
		}
		
		
		if(trace) {
			
			if(temp.isEmpty()){
				System.out.println("Nenhuma transicao chegando");
				return temp;
			}
			
			System.out.println("Transicoes chegando: \n");
			temp.forEach(System.out::println);
			System.out.println();
		}
		
		return temp;
	}

	private List<StringTupleComplete> getLeavingTrans(int destination,
			List<StringTupleComplete> allTransitions) {
		
		List<StringTupleComplete> temp = new ArrayList<>();
		
		for(StringTupleComplete each : allTransitions) {
			if(each.getOrigin() == destination)	
				temp.add(new StringTupleComplete(each.getOrigin(), each.getToken(), each.getNextState()));
		}
		
		
		if(trace) {
			
			if(temp.isEmpty()){
				System.out.println("Nenhuma transicao saindo");
				return temp;
			}
			
			System.out.println("Transicoes saindo: \n");
			temp.forEach(System.out::println);
			System.out.println();
		}
		
		return temp;
		
	}

}
