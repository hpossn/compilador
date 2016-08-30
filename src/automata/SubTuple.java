package automata;

import java.util.Objects;

public class SubTuple {
	
	private final String nextSubMachine;
	private final int returnState;
	
	public SubTuple(String nextSubMachine, int returnState) {
		this.nextSubMachine = nextSubMachine;
		this.returnState = returnState;
	}
	
	public int getReturnState() {
		return returnState;
	}

	public String getNextSubMachine() {
		return nextSubMachine;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof SubTuple)) return false;
		
		SubTuple tupla = (SubTuple) o;
		
		return this.nextSubMachine.equals(tupla.nextSubMachine) && this.returnState == tupla.returnState;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nextSubMachine, returnState);
	}

}
