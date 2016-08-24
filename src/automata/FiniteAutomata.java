package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiniteAutomata {
	
	public static final int NO_TRANSITION = -1;
	public static final int END_OF_TOKEN = -2;
	
	private int currentState;
	private int initialState;
	private List<Integer> finalStates;
	private Map<Integer, List<Tuple>> transitions;
	
	public FiniteAutomata() {
		finalStates = new ArrayList<>();
		transitions = new HashMap<>();
		
		configStates();
		
		reset();
	}
	
	private void configStates() {
		initialState = 1;
		
		finalStates.add(2);
		finalStates.add(3);
		finalStates.add(4);
		finalStates.add(5);
		finalStates.add(6);
		finalStates.add(8);
		finalStates.add(10);
		finalStates.add(11);
		finalStates.add(12);
		
		createTransitions();
		
	}
	
	public int getCurrentState() {
		return currentState;
	}
	
	public int makeTransition(char symbol) {
		List<Tuple> possibleTransitions = transitions.get(currentState);
		
		for(int i = 0; i < possibleTransitions.size(); i++) {
			Tuple each = possibleTransitions.get(i);
			if(each.getSymbol() == symbol) {
				currentState = each.getNextState();
				return currentState;
			}
		}
		
		reset();
		
		return END_OF_TOKEN;
		
	}
	
	public boolean isFinalState() {
		return finalStates.contains(currentState);
	}
	
	public void reset() {
		currentState = initialState;
	}
	
	private void createTransitions() {
		ArrayList<Tuple> stateTransitions;
		
		//State 1
		stateTransitions = new ArrayList<>();
		
		stateTransitions.add(new Tuple(' ', 1));
		stateTransitions.add(new Tuple('\n', 1));
		stateTransitions.add(new Tuple('\t', 1));
		
		stateTransitions.add(new Tuple('0', 2));
		stateTransitions.add(new Tuple('1', 2));
		stateTransitions.add(new Tuple('2', 2));
		stateTransitions.add(new Tuple('3', 2));
		stateTransitions.add(new Tuple('4', 2));
		stateTransitions.add(new Tuple('5', 2));
		stateTransitions.add(new Tuple('6', 2));
		stateTransitions.add(new Tuple('7', 2));
		stateTransitions.add(new Tuple('8', 2));
		stateTransitions.add(new Tuple('9', 2));
		
		stateTransitions.add(new Tuple('a', 3));
		stateTransitions.add(new Tuple('b', 3));
		stateTransitions.add(new Tuple('c', 3));
		stateTransitions.add(new Tuple('d', 3));
		stateTransitions.add(new Tuple('e', 3));
		stateTransitions.add(new Tuple('f', 3));
		stateTransitions.add(new Tuple('g', 3));
		stateTransitions.add(new Tuple('h', 3));
		stateTransitions.add(new Tuple('i', 3));
		stateTransitions.add(new Tuple('j', 3));
		stateTransitions.add(new Tuple('k', 3));
		stateTransitions.add(new Tuple('l', 3));
		stateTransitions.add(new Tuple('m', 3));
		stateTransitions.add(new Tuple('n', 3));
		stateTransitions.add(new Tuple('o', 3));
		stateTransitions.add(new Tuple('p', 3));
		stateTransitions.add(new Tuple('q', 3));
		stateTransitions.add(new Tuple('r', 3));
		stateTransitions.add(new Tuple('s', 3));
		stateTransitions.add(new Tuple('t', 3));
		stateTransitions.add(new Tuple('u', 3));
		stateTransitions.add(new Tuple('v', 3));
		stateTransitions.add(new Tuple('w', 3));
		stateTransitions.add(new Tuple('x', 3));
		stateTransitions.add(new Tuple('y', 3));
		stateTransitions.add(new Tuple('z', 3));
		stateTransitions.add(new Tuple('A', 3));
		stateTransitions.add(new Tuple('B', 3));
		stateTransitions.add(new Tuple('C', 3));
		stateTransitions.add(new Tuple('D', 3));
		stateTransitions.add(new Tuple('E', 3));
		stateTransitions.add(new Tuple('F', 3));
		stateTransitions.add(new Tuple('G', 3));
		stateTransitions.add(new Tuple('H', 3));
		stateTransitions.add(new Tuple('I', 3));
		stateTransitions.add(new Tuple('J', 3));
		stateTransitions.add(new Tuple('K', 3));
		stateTransitions.add(new Tuple('L', 3));
		stateTransitions.add(new Tuple('M', 3));
		stateTransitions.add(new Tuple('N', 3));
		stateTransitions.add(new Tuple('O', 3));
		stateTransitions.add(new Tuple('P', 3));
		stateTransitions.add(new Tuple('Q', 3));
		stateTransitions.add(new Tuple('R', 3));
		stateTransitions.add(new Tuple('S', 3));
		stateTransitions.add(new Tuple('T', 3));
		stateTransitions.add(new Tuple('U', 3));
		stateTransitions.add(new Tuple('V', 3));
		stateTransitions.add(new Tuple('W', 3));
		stateTransitions.add(new Tuple('X', 3));
		stateTransitions.add(new Tuple('Y', 3));
		stateTransitions.add(new Tuple('Z', 3));
		
		stateTransitions.add(new Tuple(';', 4));
		stateTransitions.add(new Tuple(',', 4));
		stateTransitions.add(new Tuple('[', 4));
		stateTransitions.add(new Tuple(']', 4));
		stateTransitions.add(new Tuple('(', 4));
		stateTransitions.add(new Tuple(')', 4));
		stateTransitions.add(new Tuple('+', 4));
		stateTransitions.add(new Tuple('/', 4));
		stateTransitions.add(new Tuple('*', 4));
		
		stateTransitions.add(new Tuple(':', 5));
		stateTransitions.add(new Tuple('<', 5));
		stateTransitions.add(new Tuple('>', 5));
		stateTransitions.add(new Tuple('=', 5));
		stateTransitions.add(new Tuple('!', 5));
		
		stateTransitions.add(new Tuple('#', 7));
		
		stateTransitions.add(new Tuple('"', 9));
		
		stateTransitions.add(new Tuple('-', 11));
		
		stateTransitions.add(new Tuple('.', 13));

		transitions.put(1, stateTransitions);
		
		//State 2
		stateTransitions = new ArrayList<>();
		
		stateTransitions.add(new Tuple('0', 2));
		stateTransitions.add(new Tuple('1', 2));
		stateTransitions.add(new Tuple('2', 2));
		stateTransitions.add(new Tuple('3', 2));
		stateTransitions.add(new Tuple('4', 2));
		stateTransitions.add(new Tuple('5', 2));
		stateTransitions.add(new Tuple('6', 2));
		stateTransitions.add(new Tuple('7', 2));
		stateTransitions.add(new Tuple('8', 2));
		stateTransitions.add(new Tuple('9', 2));
		
		transitions.put(2, stateTransitions);
		
		//State 3
		stateTransitions = new ArrayList<>();

		stateTransitions.add(new Tuple('0', 3));
		stateTransitions.add(new Tuple('1', 3));
		stateTransitions.add(new Tuple('2', 3));
		stateTransitions.add(new Tuple('3', 3));
		stateTransitions.add(new Tuple('4', 3));
		stateTransitions.add(new Tuple('5', 3));
		stateTransitions.add(new Tuple('6', 3));
		stateTransitions.add(new Tuple('7', 3));
		stateTransitions.add(new Tuple('8', 3));
		stateTransitions.add(new Tuple('9', 3));
		
		stateTransitions.add(new Tuple('a', 3));
		stateTransitions.add(new Tuple('b', 3));
		stateTransitions.add(new Tuple('c', 3));
		stateTransitions.add(new Tuple('d', 3));
		stateTransitions.add(new Tuple('e', 3));
		stateTransitions.add(new Tuple('f', 3));
		stateTransitions.add(new Tuple('g', 3));
		stateTransitions.add(new Tuple('h', 3));
		stateTransitions.add(new Tuple('i', 3));
		stateTransitions.add(new Tuple('j', 3));
		stateTransitions.add(new Tuple('k', 3));
		stateTransitions.add(new Tuple('l', 3));
		stateTransitions.add(new Tuple('m', 3));
		stateTransitions.add(new Tuple('n', 3));
		stateTransitions.add(new Tuple('o', 3));
		stateTransitions.add(new Tuple('p', 3));
		stateTransitions.add(new Tuple('q', 3));
		stateTransitions.add(new Tuple('r', 3));
		stateTransitions.add(new Tuple('s', 3));
		stateTransitions.add(new Tuple('t', 3));
		stateTransitions.add(new Tuple('u', 3));
		stateTransitions.add(new Tuple('v', 3));
		stateTransitions.add(new Tuple('w', 3));
		stateTransitions.add(new Tuple('x', 3));
		stateTransitions.add(new Tuple('y', 3));
		stateTransitions.add(new Tuple('z', 3));
		stateTransitions.add(new Tuple('A', 3));
		stateTransitions.add(new Tuple('B', 3));
		stateTransitions.add(new Tuple('C', 3));
		stateTransitions.add(new Tuple('D', 3));
		stateTransitions.add(new Tuple('E', 3));
		stateTransitions.add(new Tuple('F', 3));
		stateTransitions.add(new Tuple('G', 3));
		stateTransitions.add(new Tuple('H', 3));
		stateTransitions.add(new Tuple('I', 3));
		stateTransitions.add(new Tuple('J', 3));
		stateTransitions.add(new Tuple('K', 3));
		stateTransitions.add(new Tuple('L', 3));
		stateTransitions.add(new Tuple('M', 3));
		stateTransitions.add(new Tuple('N', 3));
		stateTransitions.add(new Tuple('O', 3));
		stateTransitions.add(new Tuple('P', 3));
		stateTransitions.add(new Tuple('Q', 3));
		stateTransitions.add(new Tuple('R', 3));
		stateTransitions.add(new Tuple('S', 3));
		stateTransitions.add(new Tuple('T', 3));
		stateTransitions.add(new Tuple('U', 3));
		stateTransitions.add(new Tuple('V', 3));
		stateTransitions.add(new Tuple('W', 3));
		stateTransitions.add(new Tuple('X', 3));
		stateTransitions.add(new Tuple('Y', 3));
		stateTransitions.add(new Tuple('Z', 3));

		transitions.put(3, stateTransitions);
		
		//State 4
		stateTransitions = new ArrayList<>();

		transitions.put(4, stateTransitions);

		//State 5
		stateTransitions = new ArrayList<>();

		stateTransitions.add(new Tuple('=', 6));
		
		transitions.put(5, stateTransitions);
		
		//State 6
		stateTransitions = new ArrayList<>();

		transitions.put(6, stateTransitions);
		
		//State 7
		stateTransitions = new ArrayList<>();

		stateTransitions.add(new Tuple('#', 8));
		
		stateTransitions.add(new Tuple(' ', 7));
		
		stateTransitions.add(new Tuple('0', 7));
		stateTransitions.add(new Tuple('1', 7));
		stateTransitions.add(new Tuple('2', 7));
		stateTransitions.add(new Tuple('3', 7));
		stateTransitions.add(new Tuple('4', 7));
		stateTransitions.add(new Tuple('5', 7));
		stateTransitions.add(new Tuple('6', 7));
		stateTransitions.add(new Tuple('7', 7));
		stateTransitions.add(new Tuple('8', 7));
		stateTransitions.add(new Tuple('9', 7));
		
		stateTransitions.add(new Tuple('a', 7));
		stateTransitions.add(new Tuple('b', 7));
		stateTransitions.add(new Tuple('c', 7));
		stateTransitions.add(new Tuple('d', 7));
		stateTransitions.add(new Tuple('e', 7));
		stateTransitions.add(new Tuple('f', 7));
		stateTransitions.add(new Tuple('g', 7));
		stateTransitions.add(new Tuple('h', 7));
		stateTransitions.add(new Tuple('i', 7));
		stateTransitions.add(new Tuple('j', 7));
		stateTransitions.add(new Tuple('k', 7));
		stateTransitions.add(new Tuple('l', 7));
		stateTransitions.add(new Tuple('m', 7));
		stateTransitions.add(new Tuple('n', 7));
		stateTransitions.add(new Tuple('o', 7));
		stateTransitions.add(new Tuple('p', 7));
		stateTransitions.add(new Tuple('q', 7));
		stateTransitions.add(new Tuple('r', 7));
		stateTransitions.add(new Tuple('s', 7));
		stateTransitions.add(new Tuple('t', 7));
		stateTransitions.add(new Tuple('u', 7));
		stateTransitions.add(new Tuple('v', 7));
		stateTransitions.add(new Tuple('w', 7));
		stateTransitions.add(new Tuple('x', 7));
		stateTransitions.add(new Tuple('y', 7));
		stateTransitions.add(new Tuple('z', 7));
		stateTransitions.add(new Tuple('A', 7));
		stateTransitions.add(new Tuple('B', 7));
		stateTransitions.add(new Tuple('C', 7));
		stateTransitions.add(new Tuple('D', 7));
		stateTransitions.add(new Tuple('E', 7));
		stateTransitions.add(new Tuple('F', 7));
		stateTransitions.add(new Tuple('G', 7));
		stateTransitions.add(new Tuple('H', 7));
		stateTransitions.add(new Tuple('I', 7));
		stateTransitions.add(new Tuple('J', 7));
		stateTransitions.add(new Tuple('K', 7));
		stateTransitions.add(new Tuple('L', 7));
		stateTransitions.add(new Tuple('M', 7));
		stateTransitions.add(new Tuple('N', 7));
		stateTransitions.add(new Tuple('O', 7));
		stateTransitions.add(new Tuple('P', 7));
		stateTransitions.add(new Tuple('Q', 7));
		stateTransitions.add(new Tuple('R', 7));
		stateTransitions.add(new Tuple('S', 7));
		stateTransitions.add(new Tuple('T', 7));
		stateTransitions.add(new Tuple('U', 7));
		stateTransitions.add(new Tuple('V', 7));
		stateTransitions.add(new Tuple('W', 7));
		stateTransitions.add(new Tuple('X', 7));
		stateTransitions.add(new Tuple('Y', 7));
		stateTransitions.add(new Tuple('Z', 7));

		transitions.put(7, stateTransitions);

		//State 8
		stateTransitions = new ArrayList<>();

		transitions.put(8, stateTransitions);
		
		//State 9
		stateTransitions = new ArrayList<>();

		stateTransitions.add(new Tuple('"', 10));
		
		stateTransitions.add(new Tuple(' ', 9));
		
		stateTransitions.add(new Tuple(':', 9));

		stateTransitions.add(new Tuple('0', 9));
		stateTransitions.add(new Tuple('1', 9));
		stateTransitions.add(new Tuple('2', 9));
		stateTransitions.add(new Tuple('3', 9));
		stateTransitions.add(new Tuple('4', 9));
		stateTransitions.add(new Tuple('5', 9));
		stateTransitions.add(new Tuple('6', 9));
		stateTransitions.add(new Tuple('7', 9));
		stateTransitions.add(new Tuple('8', 9));
		stateTransitions.add(new Tuple('9', 9));

		stateTransitions.add(new Tuple('a', 9));
		stateTransitions.add(new Tuple('b', 9));
		stateTransitions.add(new Tuple('c', 9));
		stateTransitions.add(new Tuple('d', 9));
		stateTransitions.add(new Tuple('e', 9));
		stateTransitions.add(new Tuple('f', 9));
		stateTransitions.add(new Tuple('g', 9));
		stateTransitions.add(new Tuple('h', 9));
		stateTransitions.add(new Tuple('i', 9));
		stateTransitions.add(new Tuple('j', 9));
		stateTransitions.add(new Tuple('k', 9));
		stateTransitions.add(new Tuple('l', 9));
		stateTransitions.add(new Tuple('m', 9));
		stateTransitions.add(new Tuple('n', 9));
		stateTransitions.add(new Tuple('o', 9));
		stateTransitions.add(new Tuple('p', 9));
		stateTransitions.add(new Tuple('q', 9));
		stateTransitions.add(new Tuple('r', 9));
		stateTransitions.add(new Tuple('s', 9));
		stateTransitions.add(new Tuple('t', 9));
		stateTransitions.add(new Tuple('u', 9));
		stateTransitions.add(new Tuple('v', 9));
		stateTransitions.add(new Tuple('w', 9));
		stateTransitions.add(new Tuple('x', 9));
		stateTransitions.add(new Tuple('y', 9));
		stateTransitions.add(new Tuple('z', 9));
		stateTransitions.add(new Tuple('A', 9));
		stateTransitions.add(new Tuple('B', 9));
		stateTransitions.add(new Tuple('C', 9));
		stateTransitions.add(new Tuple('D', 9));
		stateTransitions.add(new Tuple('E', 9));
		stateTransitions.add(new Tuple('F', 9));
		stateTransitions.add(new Tuple('G', 9));
		stateTransitions.add(new Tuple('H', 9));
		stateTransitions.add(new Tuple('I', 9));
		stateTransitions.add(new Tuple('J', 9));
		stateTransitions.add(new Tuple('K', 9));
		stateTransitions.add(new Tuple('L', 9));
		stateTransitions.add(new Tuple('M', 9));
		stateTransitions.add(new Tuple('N', 9));
		stateTransitions.add(new Tuple('O', 9));
		stateTransitions.add(new Tuple('P', 9));
		stateTransitions.add(new Tuple('Q', 9));
		stateTransitions.add(new Tuple('R', 9));
		stateTransitions.add(new Tuple('S', 9));
		stateTransitions.add(new Tuple('T', 9));
		stateTransitions.add(new Tuple('U', 9));
		stateTransitions.add(new Tuple('V', 9));
		stateTransitions.add(new Tuple('W', 9));
		stateTransitions.add(new Tuple('X', 9));
		stateTransitions.add(new Tuple('Y', 9));
		stateTransitions.add(new Tuple('Z', 9));

		transitions.put(9, stateTransitions);
		
		//State 10
		stateTransitions = new ArrayList<>();

		transitions.put(10, stateTransitions);
		
		//State 11
		stateTransitions = new ArrayList<>();
		
		stateTransitions.add(new Tuple('>', 12));

		transitions.put(11, stateTransitions);
		
		//State 12
		stateTransitions = new ArrayList<>();

		transitions.put(12, stateTransitions);
		
		//State 13
		stateTransitions = new ArrayList<>();
		
		stateTransitions.add(new Tuple('.', 12));

		transitions.put(13, stateTransitions);
		
		//State 14
		stateTransitions = new ArrayList<>();

		transitions.put(14, stateTransitions);
		
	}

}
