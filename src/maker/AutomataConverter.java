package maker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import automata.StringTuple;
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
		Map<Integer, List<StringTuple>> currentMachineTrans;
		List<StringTupleComplete> currentMachineEmptyTrans;

		transitions = new HashMap<>();

		int count = 0;

		while (true) {
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
		}

	}


	private List<StringTupleComplete> getArrivingTrans(int origin,
			Map<Integer, List<StringTuple>> currentMachineTrans) {
		 
		List<StringTupleComplete> temp = new ArrayList<>();
		
		for(int entry : currentMachineTrans.keySet()) {
			for(StringTuple each : currentMachineTrans.get(entry)) {
				if(each.getNextState() == origin)	
					temp.add(new StringTupleComplete(entry, each.getToken(), origin));
			}
		}
		
		if(trace) {
			System.out.println("Transicoes chegando: \n");
			temp.forEach(System.out::println);
			System.out.println();
		}
		
		return temp;
	}

	private List<StringTupleComplete> getLeavingTrans(int origin,
			Map<Integer, List<StringTuple>> currentMachineTrans) {
		
		List<StringTupleComplete> temp = new ArrayList<>();
		
		for(StringTuple each : currentMachineTrans.get(origin)) {
			temp.add(new StringTupleComplete(origin, each));
		}
		
		if(trace) {
			System.out.println("Transicoes Saindo: \n");
			temp.forEach(System.out::println);
			System.out.println();
		}
		
		return temp;
	}

	private List<StringTupleComplete> getCurrentMachineEmptyTransitions(
		Map<Integer, List<StringTuple>> currentMachineTransitions) {
		List<StringTupleComplete> list = new ArrayList<>();

		for (int entry : currentMachineTransitions.keySet()) {
			List<StringTuple> tempList = currentMachineTransitions.get(entry);

			if (tempList == null) continue;

			for (StringTuple each : tempList) {
				if (each.getToken().equals("BLANK"))
					list.add(new StringTupleComplete(entry, each));
			}
		}

		System.out.println();
		
		if (trace)
			list.forEach(each -> System.out.println("Transicao em vazio no estado: " + each));
		
		System.out.println();

		return list;
	}

}
