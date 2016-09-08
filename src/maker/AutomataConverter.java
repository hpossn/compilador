package maker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import automata.StringTuple;
import automata.SubTuple;
import wirth.PushDownAutomataWirth;

public class AutomataConverter {

	private String fileName;
	private boolean trace;

	private TransitionsMaker loadedAutomata;
	private List<PushDownAutomataWirth> machines;
	private Map<Integer, List<StringTuple>> transitions;

	public AutomataConverter(String fileName, boolean trace) {
		this.fileName = fileName;
		this.trace = trace;

		loadedAutomata = new TransitionsMaker(fileName, false);
		machines = loadedAutomata.getSubMachines();
	}

	public void convert() {
		
		
		/*while (true) {
		PushDownAutomataWirth currentMachine = machines.get(count);
		currentMachineTrans = currentMachine.getStateTransitions();
		currentMachineEmptyTrans = getCurrentMachineEmptyTransitions(currentMachineTrans);
		
		for(StringTupleComplete each : currentMachineEmptyTrans) {
			int origin = each.getOrigin();
			int destination = each.getNextState();
			
			System.out.println("------------------");
			System.out.println("Estado " + origin);
			
			List<StringTupleComplete> leavingTrans = getLeavingTrans(origin, currentMachineTrans);
			List<StringTupleComplete> arrivingTrans = getArrivingTrans(origin, currentMachineTrans);
		
			System.out.println("------------------");
			
		}

		count++;

		if (count > 0)
			break;
	}*/
		
		/*for(StringTupleComplete each : emptyTransitions) {
		int origin = each.getOrigin();
		int destination = each.getNextState();
		
		System.out.println("------------------");
		System.out.println("Estado de destino " + destination + "\n");
		
		System.out.println("Inicialmente: ");
		List<StringTupleComplete> leavingTransitions = getLeavingTrans(destination, currentMachineTrans);
		List<StringTupleComplete> arrivingTransitions = getArrivingTrans(destination, currentMachineTrans);
		
		
		//System.out.println("\n Aplica algoritmo:");
		
		for(StringTupleComplete eachLeaving : leavingTransitions) {
			currentMachine.modifyTransition(eachLeaving, new StringTupleComplete(origin, eachLeaving.getToken(), eachLeaving.getNextState()));
		}
		
		currentMachineTrans = currentMachine.getStateTransitions();
		leavingTransitions = getLeavingTrans(destination, currentMachineTrans);
		
		//leavingTransitions.forEach(System.out::println);
		
		for(StringTupleComplete eachArriving : arrivingTransitions) {
			currentMachine.modifyTransition(eachArriving, new StringTupleComplete(eachArriving.getOrigin(), eachArriving.getToken(), origin));
		}
		
		currentMachineTrans = currentMachine.getStateTransitions();
		arrivingTransitions = getArrivingTrans(destination, currentMachineTrans);
		
		//arrivingTransitions.forEach(System.out::println);
		
		currentMachine.changeState(destination, origin);
		
		if(currentMachineTrans.containsKey(destination)) {
			System.out.println("FODEEEEUUUU");
		}
		
		System.out.println("------------------");
		
		//emptyTransitions.remove(each);
	}*/
		
		Map<Integer, List<StringTuple>> currentMachineTrans;
		List<StringTupleComplete> emptyTransitions;
		List<StringTupleComplete> allTransistions;

		transitions = new HashMap<>();

		int count = 0;
		
		while(true) {
			
			PushDownAutomataWirth currentMachine = machines.get(count);
			currentMachineTrans = currentMachine.getStateTransitions();
			
			allTransistions = getAllTransitions(currentMachine);
			emptyTransitions = getAllEmptyTransitions(allTransistions);
			
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
					
					System.out.println("------------------");
					System.out.println("Estado de destino " + destination + "\n");
					
					System.out.println("Inicialmente: ");
					List<StringTupleComplete> leavingTransitions = getLeavingTrans(destination, allTransistions);
					List<StringTupleComplete> arrivingTransitions = getArrivingTrans(destination, allTransistions);
					
					
					List<StringTupleComplete> temp = new ArrayList<>(leavingTransitions);
					for(StringTupleComplete eachLeaving : temp) {
						leavingTransitions.remove(eachLeaving);
						eachLeaving.setOrigin(origin);
						leavingTransitions.add(eachLeaving);
					}					
					System.out.println("Apos algoritmo:");
					leavingTransitions.forEach(System.out::println);
					
					temp = new ArrayList<>(arrivingTransitions);
					for(StringTupleComplete eachArriving : temp) {
						arrivingTransitions.remove(eachArriving);
						eachArriving.setNextState(origin);
						arrivingTransitions.add(eachArriving);
					}		
					
					arrivingTransitions.forEach(System.out::println);
		
					allTransistions.remove(eachBlank);
					
					insertTransitions(allTransistions, leavingTransitions);
					insertTransitions(allTransistions, arrivingTransitions);
					
					removeAllDestination(destination, allTransistions);
					
					emptyTransitions = getAllEmptyTransitions(allTransistions);
					
					//System.out.println("\nAll transitions");
					//allTransistions.forEach(System.out::println);
					
					
					if(emptyTransitions.isEmpty())
						break;
				}
				
				System.out.println("\nTodas as transicoes");
				allTransistions.forEach(System.out::println);
			}
			
			count++;
			
			if(count > 0)
				break;
		}

	}
	
	private void removeAllDestination(int state, List<StringTupleComplete> allTransistions) {
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
			System.out.println("Todas as transicoes: ");
			transitions.forEach(System.out::println);
			System.out.println();
		}
		
		return transitions;
		
	}
	
	private void printAllTransitions(Map<Integer, List<StringTuple>> currentMachineTrans) {
		for(int entry : currentMachineTrans.keySet()) {
			List<StringTuple> temp = currentMachineTrans.get(entry);
			for(StringTuple each : temp) {
				System.out.println(String.format("Transicao: %d,%s,%d", entry, each.getToken(), each.getNextState()));
			}
		}
		
		System.out.println();
	}


	private List<StringTupleComplete> getAllEmptyTransitions(List<StringTupleComplete> allTransitions) {	
		/*List<StringTupleComplete> list = new ArrayList<>();

		for (int entry : currentMachineTrans.keySet()) {
			List<StringTuple> tempList = currentMachineTrans.get(entry);

			if (tempList == null) continue;

			for (StringTuple each : tempList) {
				if (each.getToken().equals("BLANK")) {
					if(trace)
						System.out.println("Transicao em vazio no estado " + entry  + ": " + each);
					boolean clean = true;
					for(StringTupleComplete listEntry : list) {
						if(listEntry.getNextState() == each.getNextState())
							clean = false;
					}
					if(clean)
						list.add(new StringTupleComplete(entry, each));
				}
			}
		}

		System.out.println();
		
		if (trace)		
			System.out.println();

		return list;*/
		
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
