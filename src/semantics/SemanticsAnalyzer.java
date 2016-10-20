package semantics;

import java.util.ArrayList;
import java.util.List;

public class SemanticsAnalyzer {

	private StringBuilder genCode = new StringBuilder();
	private StringBuilder tempBuilder = new StringBuilder();
	private StringBuilder funcParamBuilder = new StringBuilder();
	private StringBuilder variablesBuilder = new StringBuilder();
	private StringBuilder constantBuilder = new StringBuilder();
	private TokenStack tokenStack = new TokenStack();
	private TokenStack tempTokenStack = new TokenStack();
	private List<Variable> variableList = new ArrayList<>();
	private List<String> constantList = new ArrayList<>();
	private String currentContext = "gbl";
	
	private AssignmentStack assignmentStack = new AssignmentStack();
	
	private int counter = 0;
	
	private int expressionCounter = 0;

	public void performOperation(TransitionInfo info) {
		// if (semanticOperation.equals("subVardecl")) {
		// createVariable();
		// }
		//
		// if(semanticOperation.equals("begin")) {
		// writeVariables();
		// }

		// Declaracao de nova variavel
		// if(info.goingToMachine.equals("subProgram") &&
		// info.goingToState.equals("7") &&
		// info.comingFromMachine.equals("subVardecl")) {

		// Declaracao de nova variavel
		if (info.goingToMachine.equals("subVardecl") && info.goingToState.equals("5")
				&& info.comingFromMachine.equals("subVardecl")) {
			System.out.println("subVarDecl");
			createVariable();

			// Inicio da rotina principal
		} else if (info.goingToMachine.equals("subProgram") && info.goingToState.equals("11")) {
			System.out.println("subProgram");
			writeGlobalVariables();
			//writeCode();

			currentContext = "bgn";
			
		//Declaracao de funcao
		} else if (info.goingToMachine.equals("subFuncdecl") && info.goingToState.equals("11")
				&& info.comingFromMachine.equals("subFuncdecl")) {
			System.out.println("subFuncDecl");

			createFuncDecl();
		/*} else if(info.comingFromMachine.equals("subExpression") && info.comingFromState.equals("12") &&
				!info.goingToMachine.equals("subExpression")) {
			solveExpression();*/
			
		//Expressao inicio
		} else if(!info.comingFromMachine.equals("subExpression") && info.goingToState.equals("4") &&
				info.goingToMachine.equals("subExpression")) {
			System.out.println("subExp start");
			treatExpressionStart();
			
		//Expressao fim
		} else if(info.comingFromMachine.equals("subExpression") && info.comingFromState.equals("12") &&
				!info.goingToMachine.equals("subExpression")) {
			System.out.println("subExp end");
			treatExpressionFinal();
			
			/*
		} else if(info.comingFromMachine.equals("subCommand") && info.comingFromState.equals("2")) {
			
			//assignmentFunction();
			
		}*/
			
		//Inicio assignment
		} else if(info.comingFromMachine.equals("subAssignment") && info.goingToState.equals("3")) {
			assignmentFunctionStart();
			System.out.println("subAssignment Start");
			
		//Fim assignment
		} else if(info.comingFromMachine.equals("subAssignment") && info.comingFromState.equals("9")) {
			assignmentFunctionEnd();
			System.out.println("subAssignment End");
			
		}
		
		//System.out.println(info.toString());

	}


	private void assignmentFunctionEnd() {
		String var = assignmentStack.pop();
		
		genCode.append("\tMM\t" + var + "\t; Salva assignment to variable\n\n");
	}


	private void treatExpressionFinal() {
		if(--expressionCounter == 0) {
			tokenStack.pop();
			treatExpression(tokenStack.getFullStack());
			tokenStack = tempTokenStack;
			tempTokenStack = new TokenStack();
			
		}
		
	}


	private void treatExpressionStart() {
		if(expressionCounter == 0) {
			tempTokenStack.addStack(tokenStack);
			tokenStack = new TokenStack();
			
			tokenStack.push(tempTokenStack.getHead());
			
			tempTokenStack.pop();
		}
		
		expressionCounter++;
		
	}
	
	private void treatExpression(List<String> fullStack) {
		RPNConverter rpnConverter = new RPNConverter();
		List<String> rpnList = rpnConverter.convert(fullStack);
		
//		System.out.println("\n\nRPN");
//		for(String each : rpnList) {
//			System.out.println(each);
//		}
		
		System.out.println();
		writeExpressionASM(rpnList);
		
	}
	
	private void writeExpressionASM(List<String> rpnList) {
		StringBuilder expTempSB = new StringBuilder();
		
		while(rpnList.size() > 0) {
			String token = rpnList.remove(0);
			
			if(isNumero(token)) {
				saveConstant(token);
				expTempSB.append("\tLD\tconst_" + token + "\t\t; Carrega constante\n");
				expTempSB.append("\tSC\tPUSH\t\t; Envia para pilha\n");
			} else if(isOperator(token)) {
				expTempSB.append("\tSC\tPOP\t\t; Retira argumento\n");
				expTempSB.append("\tMM\tARG\t\t; Salva argumento\n");
				expTempSB.append("\tSC\tPOP\t\t; Retira argumento\n");
				expTempSB.append("\t"+ token +"\tARG\t\t; Efetua operacao\n");
				expTempSB.append("\tSC\tPUSH\t\t; Guarda resultado na pilha\n");
			} else if(isVariable(token)) {
				String fullName = existsInVariable(token);
				if(fullName != null) {
					expTempSB.append("\tLD\t" + fullName + "\t\t; Carrega variavel\n");
					expTempSB.append("\tSC\tPUSH\t\t; Envia variavel para pilha\n");
				} else {
					throw new AssertionError("Variavel nao existe");
				}
			}
			
		}
		
		tokenStack.clear();
		
		genCode.append(expTempSB.toString());
	}


	private String existsInVariable(String token) {
		token = token.substring(1);
		for(Variable each : variableList) {
			if(each.getInitialName().equals(token)) {
				if(each.getContext().equals(currentContext))
					return each.getFullName();
			}
		}
		
		for(Variable each : variableList) {
			if(each.getInitialName().equals(token)) {
				if(each.getContext().equals("gbl"))
					return each.getFullName();
			}
		}
		
		return null;
	}


	private boolean isVariable(String token) {
		if(token.startsWith("_")) return true;
		return false;
	}


	private boolean isOperator(String token) {
		switch(token) {
		case "+":
		case "-":
		case "/":
		case "*":
			return true;
		}
		
		return false;
	}


	private boolean isNumero(String token) {
		switch(token) {
		case "+":
		case "-":
		case "/":
		case "*":
		case "(":
		case ")":
			return false;
		}
		
		try {
			Integer.parseInt(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}


	private void saveConstant(String value) {
		if(constantList.contains("const_" + value)) return;
		
		constantList.add("const_" + value);
		constantBuilder.append("const_" + value + "\tK\t=" + value + "\t; Adds constant " + value + "\n");
		
	}



	private void writeCode() {
		//System.out.println(funcParamBuilder.toString());
		//System.out.println(genCode.toString());
		
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
		

		variablesBuilder.append(builder.toString());

		// System.out.println(builder.toString());

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
		} else if (t.equals("]")) {
			num2 = tokenStack.pop();
			tokenStack.pop();
			num1 = tokenStack.pop();
			tokenStack.pop();
			varType = tokenStack.pop() + "[" + num1 + ":" + num2 + "]";
		} else {
			return;
		}

		tokenStack.pop(); // pops :

		varName = tokenStack.pop();

		tokenStack.pop();

		Variable v = new Variable(varName, "0", varType.toLowerCase());
		v.setFullName(currentContext + "_" + v.getInitialName());
		v.setContext(currentContext);

		if (variableList.contains(v))
			throw new AssertionError("Ja contem variavel" + v.getFullName());

		variableList.add(v);

		// tokenStack.push(stackTop);
		tokenStack.clear();
		tokenStack.push(stackTop);
		// SymbolTable currentSymbolTable = symbolStack.pop();

	}

	private void writeGlobalVariables() {
		List<Variable> vars = new ArrayList<>();

		for (Variable each : variableList) {
			if (each.getContext().equals("gbl")) {
				vars.add(each);
			}
		}

		variableDeclaration(vars);

	}

	public String getGenCode() {
		return genCode.toString();
	}

	public void createFuncDecl() {
		// String previousContext = currentContext;

		String stackTop = tokenStack.pop();
		//tokenStack.printStack();

		tempBuilder.setLength(0);
		
		tokenStack.next();

		String subName = tokenStack.next();

		currentContext = subName;

		tempBuilder.append("sub_" + subName + "\tJP\t=0000\t; Endereco de retorno\n");

		tokenStack.next();
		String t = tokenStack.next();

		while (!t.equals(")")) {
			String paramName;
			String varType;

			if (t.equals(","))
				t = tokenStack.next();

			if (t.toUpperCase().equals("VECTOR")) {
				tokenStack.next();
				String num1 =tokenStack.next();
				tokenStack.next();
				String num2 = tokenStack.next();
				tokenStack.next();
				varType = "vector[" + num1 + ":" + num2 + "]";
			} else {
				varType = t.toLowerCase();
			}

			paramName = currentContext + "_" + tokenStack.next();
			tempBuilder.append("\tSC\tPOP\t; Retira parametro da pilha\n");
			tempBuilder.append("\tMM\t" + paramName + "\t; Retira parametro da pilha\n");

			t = tokenStack.next();

			Variable each = new Variable(paramName, "0", varType.toLowerCase());
			each.setFullName(paramName);
			each.setContext(currentContext);
			buildFuncDataParam(each);
			
		}
		
		genCode.append(tempBuilder.toString());

//		System.out.println("\n\n");
//		System.out.println(tempBuilder.toString());
//		System.out.println(funcParamBuilder.toString());

		tokenStack.clear();
		tokenStack.push(stackTop);
		
		tempBuilder.setLength(0);

	}

	public void buildFuncDataParam(Variable each) {
		StringBuilder builder = new StringBuilder();

		if (each.getType().equals("int")) {

			String decString = "=" + each.getValue();

			builder.append(each.getFullName() + "\t" + "K" + "\t" + decString + "\t; nova variavel\n");
		} else if (each.getType().contains("vector")) {

			int size = getVectorSize(each.getType());
			builder.append(each.getFullName() + "\t" + "$" + "\t" + "=" + size + "\t; novo vetor\n");
		}

		funcParamBuilder.append(builder.toString());
		
		if (variableList.contains(each))
			throw new AssertionError("Ja contem variavel" + each.getFullName());

		variableList.add(each);

		// System.out.println(builder.toString());

	}

	public void printCode() {
		
		System.out.println("\n\n");
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
		System.out.println(";;;;;;;;;;;;;;Area de dados;;;;;;;;;;;;;;");
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;\n\n");
		System.out.println("; Variaveis\n");
		System.out.println(variablesBuilder.toString());
		System.out.println("\n; Constantes\n");
		System.out.println(constantBuilder.toString());
		System.out.println("\n; Funcoes\n");
		System.out.println(funcParamBuilder.toString());
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
		System.out.println(";;;;;;;;;;;;;;;;;Programa;;;;;;;;;;;;;;;;");
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;\n\n");
		System.out.println(genCode.toString());
		
	}
	
	private void solveExpression() {
		System.out.println("\n\nEXPRESSION!!!");
		tokenStack.printStack();
		tokenStack.clear();
		
	}
	
	private void assignmentFunctionStart() {
		String stackTop = tokenStack.pop();
		//tokenStack.printStack();
		
		tokenStack.pop();
		
		String var = existsInVariable("_" + tokenStack.pop());
		
		if(var == null)
			throw new AssertionError("Variavel nao existe");
		
		assignmentStack.push(var);
		
		
		tokenStack.clear();
		
		tokenStack.push(stackTop);
		
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
