package automata;

import java.util.Objects;

public class SubTuple {
	
	private final String nextSubMachine;
	private final int startState;
	
	public SubTuple(String nextSubMachine, int storeState) {
		this.nextSubMachine = nextSubMachine;
		this.startState = storeState;
	}
	
	public int getStartState() {
		return startState;
	}

	public String getNextSubMachine() {
		return nextSubMachine;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof SubTuple)) return false;
		
		SubTuple tupla = (SubTuple) o;
		
		return this.nextSubMachine.equals(tupla.nextSubMachine) && this.startState == tupla.startState;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nextSubMachine, startState);
	}

}
