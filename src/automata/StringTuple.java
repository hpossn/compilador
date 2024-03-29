package automata;

import java.util.Objects;

public class StringTuple {
	
	protected String token;
	protected int nextState;
	
	public StringTuple(String token, int nextState) {
		this.token = token;
		this.nextState = nextState;
	}
	
	public int getNextState() {
		return nextState;
	}

	public String getToken() {
		return token;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof StringTuple)) return false;
		
		StringTuple tupla = (StringTuple) o;
		
		return this.token.equals(tupla.token) && this.nextState == tupla.nextState;
	}
	
	
	
	@Override
	public String toString() {
		return "[token=" + token + ", nextState=" + nextState + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, nextState);
	}

}
