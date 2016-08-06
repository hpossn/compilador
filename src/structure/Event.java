package structure;

public class Event {
	
	public enum EventType {
		START, FINISH,	// Simulator generic events
		
		READ_FILE, READ_LINE, READ_CHARACTER	// Lexical Analyzer events
		
	}
	
	private final EventType eventType;
	
	public Event(EventType eventType) {
		this.eventType = eventType;
	}

	public EventType getEventType() {
		return eventType;
	}

}
