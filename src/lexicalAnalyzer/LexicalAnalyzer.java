package lexicalAnalyzer;

import java.io.FileNotFoundException;
import structure.Event;
import structure.Event.EventType;
import structure.EventQueue;

public class LexicalAnalyzer {
	
	private final FileParser fileParser;
	private final EventQueue eventQueue;
	private final StringBuilder readFile;
	
	private final Transducer transducer;
	
	private boolean trackSwitch;

	public LexicalAnalyzer(String fileName) throws FileNotFoundException {
		fileParser = new FileParser(fileName);
		eventQueue = new EventQueue();
		readFile = new StringBuilder();
		
		transducer = new Transducer();
		
		trackSwitch = false;
	}

	private void createEvent(EventType eventType) {
		eventQueue.addEvent(new Event(eventType));
	}
	
	public String getNumberedLinesFile() {
		
		StringBuilder lineNumberedFile = new StringBuilder();
		
		int lineNumber = 1;
		
		for(String eachLine : readFile.toString().split("\\n")) {
			lineNumberedFile.append(lineNumber + " " + eachLine + "\n");
			lineNumber++;
		}
		
		return lineNumberedFile.toString();
	}

	public void readFile() {
		createEvent(EventType.READ_FILE);
		execute();
		
		startTransducer();
	}
	
	private void startTransducer() {
		transducer.setFileContent(readFile.toString());
	}
	
	public TokenPair getNextToken() {
		return transducer.getNextToken();
	}
	
	public void setTrackSwitch(boolean trackSwitch) {
		this.trackSwitch = trackSwitch;
	}

	private void execute() {
		Event currentEvent;
		boolean keepExecutingRound = true;
		
		while(keepExecutingRound) {
			currentEvent = eventQueue.getHead();
			
			switch(currentEvent.getEventType()) {
			case READ_FILE:
				processReadFileEvent();
				break;
			case READ_LINE:
				processReadLineEvent();
				break;
			case READ_CHARACTER:
				processReadCharacterEvent();
				break;
			default:
				processDefaultEvent();
				break;
				
			}
			
			if(eventQueue.isEmpty())
				keepExecutingRound = false;
		}
	}

	private void processReadFileEvent() {
		eventQueue.removeHead();
		
		createEvent(EventType.READ_LINE);
		
		if(trackSwitch)
			System.out.println("Read File");
		
	}
	
	private void processReadLineEvent() {
		eventQueue.removeHead();
		
		createEvent(EventType.READ_CHARACTER);
		
		if(trackSwitch)
			System.out.println("Read Line");
		
		/*if(addLineNumbers)
			readFile.append(fileParser.getCurrentLineNumber() + " ");*/
	}

	private void processReadCharacterEvent() {
		eventQueue.removeHead();
		
		if(trackSwitch)
			System.out.print("Read Char: ");
		
		char ch = fileParser.getNextChar();	
		
		switch(ch) {
		case FileParser.END_OF_FILE:
			return;
		case FileParser.END_OF_LINE:
		case FileParser.END_OF_LINE_WINDOWS:
			createEvent(EventType.READ_LINE);
			break;
		default:
			createEvent(EventType.READ_CHARACTER);	
		}
		
		readFile.append(ch);
		
		if(trackSwitch) {
			String charInfo = getCharInfo(ch);				
			System.out.println(charInfo);
		}
		
	}
	
	private String getCharInfo(char ch) {
		String info;
		
		switch(ch) {
		case FileParser.END_OF_LINE:
			info = "\\n";
			break;
		case FileParser.END_OF_LINE_WINDOWS:
			info = "\\r";
			break;
		case FileParser.TAB:
			info = "\\t";
			break;
		case FileParser.SPACE:
			info = "\\space";
			break;
		default:
			info = String.valueOf(ch);
		}
		
		info = String.format("%8s --- ASCII (Hex): %2x", info, (int) ch);
		
		return info;
	}
	
	private void processDefaultEvent() {
		if(trackSwitch)		
			System.out.println("Process Default");
		
	}
	
}
