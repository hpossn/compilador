package wirth;

import java.util.List;

import automata.PushDownAutomata;
import automata.PushDownStack;
import lexicalAnalyzer.TokenPair;
import lexicalAnalyzer.TokenPair.TokenType;

public class PushDownAutomataRecognizerWirth {

	private List<PushDownAutomataWirth> subMachines;
	private LexicalAnalyzerWirth lexicalAnalyzer;
	
	private boolean trace = false;

	public PushDownAutomataRecognizerWirth(List<PushDownAutomataWirth> subMachines, LexicalAnalyzerWirth lexicalAnalyzerWirth) {
		this.subMachines = subMachines;
		this.lexicalAnalyzer = lexicalAnalyzerWirth;
	}

	public void transitions() {
		PushDownStack stack = new PushDownStack();

		System.out.println();

		boolean acceptable = true;
		boolean keepGoing = true;
		int machineIndex = 0;

		int finalCounter = 0;
		
		TokenPair t = getNextToken();
		
		String token = t.getValue();

		while (acceptable && keepGoing) {
			PushDownAutomataWirth currentMachine = subMachines.get(machineIndex);

			String stackTop = "empty";
			if (!stack.isEmpty())
				stackTop = stack.top();

			printCurrentInfo(token, currentMachine, stackTop);

			String transition = currentMachine.findTransition(t);

			boolean getNewToken = (!transition.contains(":") ? true : false);

			if (transition.equals("NO_SUBMACHINE")) {
				if (stack.isEmpty()) {
					acceptable = false;
				} else if(!currentMachine.isInFinalState() && !isDifferentTreatment(currentMachine)) {
					acceptable = false;
					keepGoing = false;
				} else {

					String returnSubMachine = stack.top().split(":")[0];
					String returnState = stack.top().split(":")[1];
					stack.pop();

					PushDownAutomataWirth nextAut = null;
					nextAut = getNextSubMachine(returnSubMachine, nextAut);

					if (isDifferentTreatment(currentMachine)) {
						
						//if(t.getTokenType() == TokenType.SYMBOL)
						
						t = getNextToken();
						token = t.getValue();
					}
					
			

					machineIndex = subMachines.indexOf(nextAut);
					currentMachine = subMachines.get(machineIndex);
					currentMachine.setCurrentState(Integer.parseInt(returnState));

					if (trace)
						System.out.println("SubMachine return " + returnSubMachine + " state " + returnState);
				}
			} else if (transition.equals("ERROR")) {
				acceptable = false;
				keepGoing = false;
			} else {
				if (getNewToken) {
					if (trace)
						System.out.println("Transition: " + currentMachine.getCurrentState() + ", " + token
								+ " -> " + transition);
					
					currentMachine.setCurrentState(Integer.parseInt(transition));
					t = getNextToken();
					token = t.getValue();
					
				} else {
					String[] subCallArray = transition.split(":");
					
					String nextMachine = subCallArray[0];
					
					if (trace)
						System.out.println("Sub-machine call: " + currentMachine.getSubMachineName() + " -> " + nextMachine
								+ ". Return state: " + subCallArray[1]);

					PushDownAutomataWirth nextAut = null;
					
					nextAut = getNextSubMachine(nextMachine, nextAut);

					machineIndex = subMachines.indexOf(nextAut);
					stack.push(currentMachine.getSubMachineName() + ":" + subCallArray[1]);
					currentMachine = subMachines.get(machineIndex);
					currentMachine.resetState();

				}
			}

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

	}

	private boolean isDifferentTreatment(PushDownAutomataWirth currentMachine) {
		return currentMachine.getSubMachineName().equals("subNT");
	}

	private TokenPair getNextToken() {
		TokenPair token = lexicalAnalyzer.getNextToken();
		
		/*while(token.contains("#"))
			token = lexicalAnalyzer.getNextToken().getValue();*/
		
		return token;
	}

	private void printCurrentInfo(String token, PushDownAutomataWirth currentMachine, String stackTop) {
		if (trace) {
			System.out.println("\n");
			System.out.println("Current sub-machine: " + currentMachine.getSubMachineName());
			System.out.println("Current State: " + currentMachine.getCurrentState());
			System.out.println("Stack top: " + stackTop);
			System.out.println("Current token: " + token);
		}
	}

	private PushDownAutomataWirth getNextSubMachine(String nextMachine, PushDownAutomataWirth nextAut) {
		for (PushDownAutomataWirth aut : subMachines) {
			if (aut.getSubMachineName().equals(nextMachine)) {
				nextAut = aut;
				break;
			}
		}
		return nextAut;
	}

}
