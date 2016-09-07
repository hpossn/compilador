package maker;

import java.util.ArrayList;
import java.util.List;

import lexicalAnalyzer.TokenPair;
import wirth.LexicalAnalyzerWirth;

public class MakeAutomata {

	public static final int POP_TRANSITION = -1;

	private LexicalAnalyzerWirth analyzer;
	private boolean trace;

	private boolean printStatusFlag;

	private List<String> subMachine;
	private List<Pair> stack;

	public MakeAutomata(boolean trace, LexicalAnalyzerWirth lexicalAnalyzerWirth) {
		this.trace = trace;
		this.analyzer = lexicalAnalyzerWirth;
		this.printStatusFlag = true;

		subMachine = new ArrayList<>();
		stack = new ArrayList<>();
	}

	public void make() {

		boolean containSubMachine = true;

		while (containSubMachine) {
			
			printStatusFlag = true;
			
			TokenPair token;

			int finalState = 1;
			int state = 0;
			int counter = 0;

			String transition;
			
			token = analyzer.getNextToken();
			
			if(token.getValue().equals("EOF")){
				System.out.println("\n");
				printSubMachine();
				return;
			}

			counter++;
			writeHeader(token.getValue());
			transition = addTransition(finalState, token.getValue(), POP_TRANSITION);
			printStatus(token.getValue(), state, counter, transition);

			counter++;
			token = analyzer.getNextToken();
			push(state, finalState);
			printStatus(token.getValue(), state, counter, "nenhuma");

			boolean toDo = true;

			int previous = 0;
			int offset = 0;
			boolean beginPar = true;
			boolean beginPipe = false; // Pipe
			boolean beginSB = false; // Square bracket
			boolean beginCB = false; // Curly bracket
			char mostRecent = '(';

			while (toDo) {
				token = analyzer.getNextToken();

				switch (token.getValue()) {
				case "(":
					counter++;
					push(state, state + 1);
					transition = "nenhuma";
					printStatus(token.getValue(), state, counter, transition);
					beginPar = true;
					mostRecent = '(';
					break;
				case ")":
					state = getSecondFromStack();
					pop();
					transition = addTransition(previous, "BLANK", state);
					printStatus(token.getValue(), state, counter, transition);
					break;
				case "|":
					state = getFirstFromStack();
					transition = addTransition(previous, "BLANK", getSecondFromStack());
					beginPipe = true;
					printStatus(token.getValue(), state, counter, transition);
					mostRecent = '|';
					break;
				case "[":
					beginSB = true;
					transition = addTransition(state, "BLANK", counter);
					push(state, counter);
					counter++;
					printStatus(token.getValue(), state, counter, transition);
					mostRecent = '[';
					break;
				case "]":
					state = getSecondFromStack();
					pop();
					transition = addTransition(previous, "BLANK", state);
					printStatus(token.getValue(), state, counter, transition);
					break;
				case "{":
					state = counter;
					counter++;
					beginCB = true;
					push(state, state);
					transition = addTransition(previous, "BLANK", state);
					printStatus(token.getValue(), state, counter, transition);
					mostRecent = '{';
					break;
				case "}":
					state = getSecondFromStack();
					pop();
					transition = addTransition(previous, "BLANK", state);
					printStatus(token.getValue(), state, counter, transition);
					break;
				case ".":
					state = getSecondFromStack();
					transition = addTransition(previous, "BLANK", state);
					printStatus(token.getValue(), state, counter, transition);
					break;
				default:
					counter++;
					state++;

					if (stack.size() > 0)
						offset = getSecondFromStack();

					if (beginPar && mostRecent == '(') {
						state = offset + 1;
						beginPar = false;
					}

					if (beginPipe && mostRecent == '|') {
						state = counter - 1;
						beginPipe = false;
					}

					if (beginSB && mostRecent == '[') {
						state = counter - 1;
						beginSB = false;
					}

					if (beginCB && mostRecent == '{') {
						state = counter - 1;
						beginCB = false;
					}

					transition = addTransition(previous, token.getValue(), state);
					printStatus(token.getValue(), state, counter, transition);
					break;

				}

				previous = state;
				
				if (token.getValue().equals(".")) {subMachine.add("\n"); break;}
			}

		}
		
	}

	private void writeHeader(String name) {
		subMachine.add("subMachine:" + name);
		subMachine.add(0 + ""); // Initial state 0
		subMachine.add(1 + ""); // Final state 1
	}

	private boolean push(int first, int second) {
		return stack.add(new Pair(first, second));
	}

	private Pair pop() {
		if (stack.size() > 0)
			return stack.remove(stack.size() - 1);
		else
			return null;
	}

	private void printStatus(String token, int state, int counter, String transition) {
		if (printStatusFlag) {
			System.out.println(
					String.format("\n%15s%10s%10s%65s%30s", "Token", "Estado", "Contador", "Pilha", "Transicoes"));

			printStatusFlag = false;
		}

		System.out.println(String.format("%15s%10d%10d%65s%30s", token, state, counter, getStack(), transition));

	}

	private String getStack() {
		StringBuilder b = new StringBuilder();

		stack.forEach(p -> b.append(p + " "));

		if (b.length() == 0)
			return "vazia";
		
		if(b.length() > 60) {
			int start = b.length() - 56;
			String sub = b.substring(start, b.length());
			return "..." + sub;
		}

		return b.toString().trim();
	}

	private String addTransition(int origin, String value, int destination) {
		String dest;
		String v;

		if (destination == POP_TRANSITION) {
			dest = "pop()";
			v = "BLANK";
		} else {
			dest = destination + "";
			v = value;
		}

		String transition = String.format("%d,%s,%s", origin, v, dest);

		subMachine.add(transition);

		return transition;
	}

	private int getFirstFromStack() {
		return getStackHead().getFirst();
	}

	private int getSecondFromStack() {
		return getStackHead().getSecond();
	}

	private Pair getStackHead() {
		if (stack.size() > 0)
			return stack.get(stack.size() - 1);
		else
			return null;
	}

	private void printSubMachine() {
		System.out.println("-------------------");
		subMachine.forEach(System.out::println);
		System.out.println("-------------------");
	}

	private void printStack() {
		System.out.println("\nStack");
		stack.forEach(System.out::println);
	}

}
