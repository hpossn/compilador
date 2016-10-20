package semantics;

import java.util.ArrayList;
import java.util.List;

public class AssignmentStack {
	private int index = 0;

	private List<String> variableStack = new ArrayList<>();

	public void push(String token) {
		variableStack.add(token);
	}

	public String pop() {
		if (variableStack.size() > 0)
			return variableStack.remove(variableStack.size() - 1);

		return null;
	}

	public String getHead() {
		if (variableStack.size() > 0)
			return variableStack.get(variableStack.size() - 1);

		return null;
	}

	public void clear() {
		variableStack.clear();
	}

	public void printStack() {
		for (String each : variableStack) {
			System.out.println(each);
		}
	}

	public void backToBottom() {
		index = 0;
	}

	public String next() {
		if (index < variableStack.size())
			return variableStack.get(index++);

		return null;
	}

	public boolean isEmpty() {
		return variableStack.isEmpty();
	}

	public List<String> getFullStack() {
		return new ArrayList<String>(variableStack);
	}
}
