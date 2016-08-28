package automata;

import java.util.List;
import java.util.Map;

public class PushDownAutomata {
	
	public static int NO_TRANSITIONS = -1;
	
	private String subMachineName;
	private Map<Integer, List<StringTuple>> transitions;
	private Map<Integer, SubTuple> subMachineTransitions;
	private List<String> finalStates;
	
	private int initialState;
	private int currentState;

	public PushDownAutomata(String subMachineName, Map<Integer, List<StringTuple>> transitions,
			Map<Integer, SubTuple> subMachineTransitions,
			List<String> finalStates, int initialState) {

		this.subMachineName = subMachineName;
		this.transitions = transitions;
		this.subMachineTransitions = subMachineTransitions;
		this.finalStates = finalStates;
		this.initialState = initialState;
		this.currentState = this.initialState;
		
	}
	
	public String getSubMachineName() {
		return subMachineName;
	}
	
	public boolean isInFinalState() {
		return finalStates.contains(currentState);
	}
	
	public int getCurrentState() {
		return currentState;
	}
	
	
	public void resetState() {
		currentState = initialState;
	}
	
	public String findTransition(String token) {
		int next = getTransition(token);
		
		if(next == NO_TRANSITIONS)
			return getSubMachineTransition(token);
		else {
			return String.format("%d", next);
		}
	}
	
	public void setCurrentState(int state) {
		this.currentState = state;
	}
	
	public void makeTransition(int nextState) {
		this.currentState = nextState;
	}

	private String getSubMachineTransition(String token) {
		
		if(subMachineTransitions.containsKey(currentState)) {
			SubTuple nextMachine = subMachineTransitions.get(currentState);
			return String.format("%s:%d", nextMachine.getNextSubMachine(), nextMachine.getStartState());
		}
		
		else
			return "NO_SUBMACHINE";		
	}

	private int getTransition(String token) {
		List<StringTuple> possibleTransitions = transitions.get(currentState);
		
		for(int i = 0; i < possibleTransitions.size(); i++) {
			StringTuple each = possibleTransitions.get(i);
			if(each.getToken().equals(token.toUpperCase())) {
				return each.getNextState();
			}
		}
		
		return NO_TRANSITIONS;

	}


}
