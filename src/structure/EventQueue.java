package structure;

import java.util.ArrayList;
import java.util.List;

public class EventQueue {
	
	private final List<Event> eventList;

	public EventQueue() {
		eventList = new ArrayList<>();		
	}
	
	public boolean addEvent(Event event) {
		return eventList.add(event);
	}
	
	public Event removeHead() {
		return eventList.remove(0);
	}
	
	public Event getHead() {
		return eventList.get(0);
	}
	
	public boolean isEmpty() {
		return eventList.isEmpty();
	}
	
	public int getSize() {
		return eventList.size();
	}
	
}