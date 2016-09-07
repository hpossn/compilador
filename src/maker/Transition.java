package maker;

public class Transition {
	
	private int origin;
	private String token;
	private int destination;
	
	public static final int POP_TRANSITION = -1;
	
	public Transition(int origin, String token, int destination) {
		super();
		this.origin = origin;
		this.token = token;
		this.destination = destination;
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

	public void setToken(String token) {
		this.token = token;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		String dest; 
		
		if(destination == POP_TRANSITION) dest = "pop()";
		else dest = destination + "";
		
		return "(" + origin + ", " + token + ") -> " + dest + "";
	}
}
