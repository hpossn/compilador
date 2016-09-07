package maker;

import java.util.Objects;

public class Pair {
    private int first;
    private int second;

    public Pair(int first, int second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    public int hashCode() {
    	return Objects.hash(first, second);
    }

    public boolean equals(Object o) {
    	if(this == o) return true;
		if(!(o instanceof Pair)) return false;
		
		Pair pair = (Pair) o;
		
		return this.first == pair.first && this.second == pair.second;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public int getFirst() {
    	return first;
    }

    public void setFirst(int first) {
    	this.first = first;
    }

    public int getSecond() {
    	return second;
    }

    public void setSecond(int second) {
    	this.second = second;
    }
}