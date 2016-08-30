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
		if(subMachineName.equals("subID"))
			return goSubID(token);
		if(subMachineName.equals("subNum"))
				return goSubNum(token);
		
		
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
			return String.format("%s:%d", nextMachine.getNextSubMachine(), nextMachine.getReturnState());
		}
		
		else
			return "NO_SUBMACHINE";		
	}

	private String goSubNum(String token) {
		try {
			Integer.parseInt(token);
			return "NO_SUBMACHINE";
		} catch (Exception e) {
			return "ERROR";
		}
	}
	
	private String goSubID(String token) {
		char ch = token.charAt(0);
		
		if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '"')
			return "NO_SUBMACHINE";
		else
			return "ERROR";
	}

	private int getTransition(String token) {	
		List<StringTuple> possibleTransitions = transitions.get(currentState);
		
		if(possibleTransitions != null)
			for(int i = 0; i < possibleTransitions.size(); i++) {
				StringTuple each = possibleTransitions.get(i);
				if(each.getToken().equals(token.toUpperCase())) {
					return each.getNextState();
				}
			}
		
		return NO_TRANSITIONS;

	}
	
	public String toString() {
		StringBuilder info = new StringBuilder();
		
		info.append("Machine name: " + subMachineName + '\n');
		info.append("Final states: " + finalStates.toString() + '\n');
		info.append("Transitions: " + '\n');
		
		for(int each : transitions.keySet()) {
			info.append("State: " + each + '\n');
			
			for(StringTuple eachTuple : transitions.get(each)) {
				info.append("\ttoken: " + eachTuple.getToken() + " next state: " + eachTuple.getNextState() + '\n');
			}
		}
		
		info.append("Calls: " + '\n');
		
		for(int each : subMachineTransitions.keySet()) {
			SubTuple sub = subMachineTransitions.get(each);
			info.append(("Current state: " + each + " Return State: " + sub.getReturnState() + " next submachine: " + sub.getNextSubMachine() + '\n'));
		}
		
		
		return info.toString();
		
	}


}
