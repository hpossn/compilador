package syntaticalAnalyzer;

import java.util.List;

import automata.PushDownAutomata;
import automata.PushDownAutomataRecognizer;
import automata.PushDownTransitions;
import lexicalAnalyzer.LexicalAnalyzer;

public class SyntaticalAnalyzer {

	private PushDownTransitions transitions;
	private List<PushDownAutomata> machines;
	private PushDownAutomataRecognizer recognizer;
	
	public SyntaticalAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
		transitions = new PushDownTransitions();
		machines = transitions.getSubMachines();
		
		lexicalAnalyzer.resetAnalyzer();
		
		recognizer = new PushDownAutomataRecognizer(machines, lexicalAnalyzer);
	}
	
	public void recognize() {
		recognizer.transitions();
	}
	
	
	
}
