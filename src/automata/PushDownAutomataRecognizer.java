package automata;

import java.util.List;

import lexicalAnalyzer.LexicalAnalyzer;

public class PushDownAutomataRecognizer {

	private List<PushDownAutomata> subMachines;
	private LexicalAnalyzer lexicalAnalyzer;
	
	private boolean trace = true;

	public PushDownAutomataRecognizer(List<PushDownAutomata> subMachines, LexicalAnalyzer lexicalAnalyzer) {
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

		while (acceptable && keepGoing) {
			PushDownAutomata currentMachine = subMachines.get(machineIndex);
			String token = lexicalAnalyzer.getNextToken().getValue();

			String stackTop = "empty";
			if (!stack.isEmpty())
				stackTop = stack.top();

			if (trace) {
				System.out.println("Current sub-machine: " + currentMachine.getSubMachineName());
				System.out.println("Current State: " + currentMachine.getCurrentState());
				System.out.println("Stack top: " + stackTop);
				System.out.println("Current token: " + token);
			}

			String transition = currentMachine.findTransition(token);

			boolean getNewToken = false;

			if (transition.equals("NO_SUBMACHINE")) {
				if (stack.isEmpty()) {
					acceptable = false;
				} else {
					String returnSubMachine = stack.top().split(":")[0];
					String returnState = stack.top().split(":")[1];
					stack.pop();

					PushDownAutomata nextAut = null;
					for (PushDownAutomata aut : subMachines) {
						if (aut.getSubMachineName().equals(returnSubMachine)) {
							nextAut = aut;
						}
					}

					machineIndex = subMachines.indexOf(nextAut);
					currentMachine = subMachines.get(machineIndex);
					currentMachine.setCurrentState(Integer.parseInt(returnState));

					if (trace)
						System.out.println("SubMachine return " + returnSubMachine + " state " + returnState);

				}
			} else {
				if (getNewToken) {
					if (trace)
						System.out.println("Transition: " + currentMachine.getCurrentState() + ", " + token
								+ " -> " + transition);
					
					currentMachine.setCurrentState(Integer.parseInt(transition));
					token = lexicalAnalyzer.getNextToken().getValue();
					
				} else {
					String[] subCallArray = transition.split(":");
					
					String nextMachine = subCallArray[0];
					
					if (trace)
						System.out.println("Sub-machine call: " + currentMachine.getSubMachineName() + " -> " + nextMachine
								+ ". Return state: " + subCallArray[1]);

					PushDownAutomata nextAut = null;
					
					for (PushDownAutomata aut : subMachines) {
						if (aut.getSubMachineName().equals(nextMachine)) {
							nextAut = aut;
						}
					}

					machineIndex = subMachines.indexOf(nextAut);
					stack.push(currentMachine.getSubMachineName() + ":" + currentMachine.getCurrentState());
					currentMachine = subMachines.get(machineIndex);
					currentMachine.resetState();
				}
			}

			// System.out.println("Configuracao da fita: " +
			// fitaEntrada.configuracao());

			if (token.equals("EOF")) {
				if (finalCounter == 1) {
					if (!stack.isEmpty())
						acceptable = false;
					else
						keepGoing = false;
				} else if (stack.isEmpty())
					keepGoing = false;

				finalCounter++;
			}
		}

		if (acceptable)
			System.out.println("\n\nAccepted");
		else
			System.out.println("\n\nAccepted");

	}

}
