package automata;

import java.util.List;

import lexicalAnalyzer.LexicalAnalyzer;
import semantics.SemanticsAnalyzer;
import semantics.TransitionInfo;

public class PushDownAutomataRecognizer {

	private List<PushDownAutomata> subMachines;
	private LexicalAnalyzer lexicalAnalyzer;
	private SemanticsAnalyzer semantics = new SemanticsAnalyzer();
	
	private boolean trace;

	public PushDownAutomataRecognizer(List<PushDownAutomata> subMachines, LexicalAnalyzer lexicalAnalyzer, boolean trace) {
		//this.trace = trace;
		this.trace = false;
		this.subMachines = subMachines;
		this.lexicalAnalyzer = lexicalAnalyzer;
	}

	public void transitions() {
		PushDownStack stack = new PushDownStack();

		System.out.println();

		boolean acceptable = true;
		boolean keepGoing = true;
		int machineIndex = 0;

		int finalCounter = 0;
		
		String token = getNextToken();

		while (acceptable && keepGoing) {
			PushDownAutomata currentMachine = subMachines.get(machineIndex);

			String stackTop = "empty";
			if (!stack.isEmpty())
				stackTop = stack.top();

			printCurrentInfo(token, currentMachine, stackTop);

			String transition = currentMachine.findTransition(token);

			boolean getNewToken = (!transition.contains(":") ? true : false);

			if (transition.equals("NO_SUBMACHINE")) {
				if (stack.isEmpty()) {
					acceptable = false;
				} else if(!currentMachine.isInFinalState() && !isDifferentTreatment(currentMachine)) {
					acceptable = false;
					keepGoing = false;
				} else {
					
					String semanticOperation = currentMachine.getSubMachineName();
					
					TransitionInfo info = new TransitionInfo(currentMachine.getSubMachineName(),
							"",
							currentMachine.getCurrentState() + "",
							"");
					

					String returnSubMachine = stack.top().split(":")[0];
					String returnState = stack.top().split(":")[1];
					stack.pop();

					PushDownAutomata nextAut = null;
					nextAut = getNextSubMachine(returnSubMachine, nextAut);

					if (isDifferentTreatment(currentMachine))
						token = getNextToken();

					machineIndex = subMachines.indexOf(nextAut);
					currentMachine = subMachines.get(machineIndex);
					currentMachine.setCurrentState(Integer.parseInt(returnState));

					if (trace)
						System.out.println("SubMachine return " + returnSubMachine + " state " + returnState);
					
					info.setGoingToMachine(returnSubMachine);
					info.setGoingToState(returnState);
					
					semantics.performOperation(info);
				}
			} else if (transition.equals("ERROR")) {
				acceptable = false;
				keepGoing = false;
			} else {
				if (getNewToken) {
					if (trace)
						System.out.println("Transition: " + currentMachine.getCurrentState() + ", " + token
								+ " -> " + transition);
					
					String temp1 = currentMachine.getCurrentState() + "";
					
					currentMachine.setCurrentState(Integer.parseInt(transition));
					token = getNextToken();
					
					semantics.performOperation(new TransitionInfo(
							currentMachine.getSubMachineName(),
							currentMachine.getSubMachineName(),
							temp1,
							transition));
					
				} else {
					String[] subCallArray = transition.split(":");
					
					String nextMachine = subCallArray[0];
					
					if (trace)
						System.out.println("Sub-machine call: " + currentMachine.getSubMachineName() + " -> " + nextMachine
								+ ". Return state: " + subCallArray[1]);
					
					String tempNameCurrent = currentMachine.getSubMachineName();
					String tempComingFromState = currentMachine.getCurrentState() + "";

					PushDownAutomata nextAut = null;
					
					nextAut = getNextSubMachine(nextMachine, nextAut);

					machineIndex = subMachines.indexOf(nextAut);
					stack.push(currentMachine.getSubMachineName() + ":" + subCallArray[1]);
					currentMachine = subMachines.get(machineIndex);
					currentMachine.resetState();
					
					semantics.performOperation(new TransitionInfo(
							tempNameCurrent,
							nextMachine,
							tempComingFromState,
							currentMachine.getCurrentState() + ""));

				}
			}

			// System.out.println("Configuracao da fita: " +
			// fitaEntrada.configuracao());

			if (token.equals("EOF")) {
				//System.out.println("\n finalCounter " + finalCounter + " acceptable " + acceptable + " stack " + stack.top() + " keepGoing: " + keepGoing);
				if (finalCounter == 1) {
					if (!stack.isEmpty())
						acceptable = false;
					else {
						keepGoing = false;
						acceptable = true;
					}
				} else if (stack.isEmpty())
					keepGoing = false;
					

				finalCounter++;
			}
		}

		if (acceptable)
			System.out.println("\n\nAccepted");
		else
			System.out.println("\n\nNot Accepted");
		
		//System.out.println("\n\n" + semantics.getGenCode());
		semantics.printCode();

	}

	private boolean isDifferentTreatment(PushDownAutomata currentMachine) {
		boolean b = currentMachine.getSubMachineName().equals("subID") ||
				currentMachine.getSubMachineName().equals("subNum");
		
		return b;
	}

	private String getNextToken() {
		String token = lexicalAnalyzer.getNextToken().getValue();
		
		while(token.contains("#"))
			token = lexicalAnalyzer.getNextToken().getValue();
		
		semantics.push(token);
		
		return token;
	}

	private void printCurrentInfo(String token, PushDownAutomata currentMachine, String stackTop) {
		if (trace) {
			System.out.println("\n");
			System.out.println("Current sub-machine: " + currentMachine.getSubMachineName());
			System.out.println("Current State: " + currentMachine.getCurrentState());
			System.out.println("Stack top: " + stackTop);
			System.out.println("Current token: " + token);
		}
	}

	private PushDownAutomata getNextSubMachine(String nextMachine, PushDownAutomata nextAut) {
		for (PushDownAutomata aut : subMachines) {
			if (aut.getSubMachineName().equals(nextMachine)) {
				nextAut = aut;
				break;
			}
		}
		return nextAut;
	}

}
