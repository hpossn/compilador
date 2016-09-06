package wirth;

import java.util.List;

public class SyntaticalAnalyzerWirth {

	private PushDownTransitionsWirth transitions;
	private List<PushDownAutomataWirth> machines;
	private PushDownAutomataRecognizerWirth recognizer;
	
	public SyntaticalAnalyzerWirth(LexicalAnalyzerWirth lexicalAnalyzerWirth) {
		transitions = new PushDownTransitionsWirth();
		machines = transitions.getSubMachines();
		
		lexicalAnalyzerWirth.resetAnalyzer();
		
		recognizer = new PushDownAutomataRecognizerWirth(machines, lexicalAnalyzerWirth);
	}
	
	public void recognize() {
		recognizer.transitions();
	}
	
	
	
}
