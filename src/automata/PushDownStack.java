package automata;

import java.util.ArrayList;
import java.util.List;

public class PushDownStack {
	
	private List <String> pilha;
	
	public PushDownStack() {
		pilha = new ArrayList<String>();
	}
	
	public String top() {
		return !pilha.isEmpty() ? pilha.get(pilha.size() - 1) : "";
	}
	
	public String pop() {
		return pilha.remove(pilha.size() - 1);
	}
	
	public void push(String valor) {
		pilha.add(valor);
	}
	
	public boolean isEmpty() {
		return pilha.isEmpty();
	}
	
	public String configuration() {
		return "(" + pilha.toString() + ")";
	}

}
