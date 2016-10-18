package semantics;

public class TransitionInfo {
	public String comingFromMachine;
	public String goingToMachine;
	public String comingFromState;
	public String goingToState;
	
	public TransitionInfo(String comingFromMachine, String goingToMachine, String comingFromState,
			String goingToState) {
		super();
		this.comingFromMachine = comingFromMachine;
		this.goingToMachine = goingToMachine;
		this.comingFromState = comingFromState;
		this.goingToState = goingToState;
	}
	
	public String getComingFromMachine() {
		return comingFromMachine;
	}
	public void setComingFromMachine(String comingFromMachine) {
		this.comingFromMachine = comingFromMachine;
	}
	public String getGoingToMachine() {
		return goingToMachine;
	}
	public void setGoingToMachine(String goingToMachine) {
		this.goingToMachine = goingToMachine;
	}
	public String getComingFromState() {
		return comingFromState;
	}
	public void setComingFromState(String comingFromState) {
		this.comingFromState = comingFromState;
	}
	public String getGoingToState() {
		return goingToState;
	}
	public void setGoingToState(String goingToState) {
		this.goingToState = goingToState;
	}

	@Override
	public String toString() {
		return "TransitionInfo [comingFromMachine=" + comingFromMachine + ", goingToMachine=" + goingToMachine
				+ ", comingFromState=" + comingFromState + ", goingToState=" + goingToState + "]";
	}
	
	
	
}
