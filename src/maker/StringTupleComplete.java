package maker;

import java.util.Objects;

import automata.StringTuple;

public class StringTupleComplete extends StringTuple {
	
	private int origin;

	public StringTupleComplete(int origin, String token, int nextState) {
		super(token, nextState);
		
		this.origin = origin;
	}
	
	public StringTupleComplete(int origin, StringTuple st) {
		super(st.getToken(), st.getNextState());
		
		this.origin = origin;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}
	
	public String getToken() {
		return token;
	}
	
	public int nextState() {
		return nextState;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof StringTupleComplete)) return false;
		
		StringTupleComplete tupla = (StringTupleComplete) o;
		
		return this.token.equals(tupla.token) && this.nextState == tupla.nextState && this.origin == tupla.origin;
	}
	@Override
	public String toString() {
		return "[origin=" + origin +  ", token=" + token + ", nextState=" + nextState + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, nextState, origin);
	}

}
