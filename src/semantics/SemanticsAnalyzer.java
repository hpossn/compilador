package semantics;

import java.util.ArrayList;
import java.util.List;


public class SemanticsAnalyzer {

	private StringBuilder genCode = new StringBuilder();
	private TokenStack tokenStack = new TokenStack();
	private List<Variable> variableList = new ArrayList<>();
	private String currentContext = "gbl";

	
	public void performOperation(TransitionInfo info) {
//		if (semanticOperation.equals("subVardecl")) {
//			createVariable();
//		}
//		
//		if(semanticOperation.equals("begin")) {
//			writeVariables();
//		}
		
		if(info.goingToMachine.equals("subProgram") &&
				info.goingToState.equals("7") &&
				info.comingFromMachine.equals("subVardecl")) {
			createVariable();
			
		} else if(info.goingToMachine.equals("subProgram") &&
				info.goingToState.equals("11")) {
			writeGlobalVariables();
			currentContext = "bgn";
			
		} 

	}

	public void push(String token) {
		tokenStack.push(token);
	}

	public void flush() {
		tokenStack.clear();
	}

	public void variableDeclaration(List<Variable> variables) {
		StringBuilder builder = new StringBuilder();

		for (Variable each : variables) {
			if (each.getType().equals("int")) {

				String decString = "=" + each.getValue();

				builder.append(each.getFullName() + "\t" + "K" + "\t" + decString + "\t; nova variavel\n");
			} else if (each.getType().contains("vector")) {

				int size = getVectorSize(each.getType());
				builder.append(each.getFullName() + "\t" + "$" + "\t" + "=" + size + "\t; novo vetor\n");
			}

		}
		
		genCode.append(builder.toString());

		//System.out.println(builder.toString());

	}

	private int getVectorSize(String type) {

		String sub = type.substring(7);
		String[] array = sub.split(":");
		array[1] = array[1].replaceFirst("]", "");
		int int1 = Integer.parseInt(array[0]);
		int int2 = Integer.parseInt(array[1]);

		return (int1 + 1) * (int2 + 1);
	}

	

	private void createVariable() {
		String stackTop = tokenStack.pop();

		String t = tokenStack.pop();

		String varType;
		String varName;
		String num1;
		String num2;

		if (!t.equals(";"))
			return;

		t = tokenStack.pop();

		if (t.equals("INT") || t.equals("STRING") || t.equals("BOOLEAN")) {
			varType = t;
		} else if(t.equals("]")){
			num2 = tokenStack.pop();
			tokenStack.pop();
			num1 = tokenStack.pop();
			tokenStack.pop();
			varType = tokenStack.pop() + "[" + num1 + ":" + num2 + "]";
		} else {
			return;
		}

		tokenStack.pop(); //pops :

		varName = tokenStack.pop();

		tokenStack.pop();

		Variable v = new Variable(varName, "0", varType.toLowerCase());
		v.setFullName(currentContext + "_" + v.getInitialName());
		v.setContext(currentContext);
		
		if(variableList.contains(v))
			throw new AssertionError("Ja contem variavel" + v.getFullName());
			
		
		variableList.add(v);

		tokenStack.push(stackTop);
		// SymbolTable currentSymbolTable = symbolStack.pop();

	}
	
	private void writeGlobalVariables() {
		List<Variable> vars = new ArrayList<>();
		
		for(Variable each : variableList) {
			if(each.getContext().equals("gbl")) {
				vars.add(each);
			}
		}
		
		variableDeclaration(vars);
		
	}

	public String getGenCode() {
		return genCode.toString();
	}

	/*
	 * public String getHexString(String value) { String initial =
	 * Integer.toHexString(Integer.parseInt(value));
	 * 
	 * while(initial.length() < 4) { initial = "0" + initial; }
	 * 
	 * return ("/" + initial).toUpperCase(); }
	 */
}
