package automata;

import java.util.Objects;

public class Tuple {
	
	private final char symbol;
	private final int nextState;
	
	public Tuple(char symbol, int nextState) {
		this.symbol = symbol;
		this.nextState = nextState;
	}
	
	public int getNextState() {
		return nextState;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Tuple)) return false;
		
		Tuple tupla = (Tuple) o;
		
		return this.symbol == tupla.symbol && this.nextState == tupla.nextState;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(symbol, nextState);
	}

}
