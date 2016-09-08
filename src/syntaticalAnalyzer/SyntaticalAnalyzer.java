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
	
	public SyntaticalAnalyzer(LexicalAnalyzer lexicalAnalyzer, boolean trace, String transitionsFile) {
		transitions = new PushDownTransitions(trace, transitionsFile);
		machines = transitions.getSubMachines();
		
		lexicalAnalyzer.resetAnalyzer();
		
		recognizer = new PushDownAutomataRecognizer(machines, lexicalAnalyzer, trace);
	}
	
	public void recognize() {
		recognizer.transitions();
	}
	
	
	
}
