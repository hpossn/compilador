package semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPNConverter {
	private Map<String, Integer> symbols;
	
	public RPNConverter() {
		symbols = new HashMap<String, Integer>();
		
		populateSymbols();
	}

	private void populateSymbols() {
		symbols.put("+", 2);
		symbols.put("-", 2);
		symbols.put("*", 3);
		symbols.put("/", 3);
	}
	
	public List<String> convert(List<String>list) {		
		List<String> outputStack = new ArrayList<>();
		List<String>operatorStack = new ArrayList<>();
		
		while(list.size() > 0) {
			String token = list.remove(0);
			if(token == null) break;
			
			if(isNumber(token)) {
				outputStack.add(token);
			} else if (isOperator(token)){
				boolean flag = true;
				
				if(operatorStack.size() == 0)
					flag = false;
				
				while(flag) {
					int index = operatorStack.size() - 1;
					String topOp = operatorStack.get(index);
					
					if(!isOperator(topOp))
						break;
					
					if(symbols.get(token) <= symbols.get(topOp)) {
						operatorStack.remove(index);
						outputStack.add(topOp);
					} else {
						flag = false;
					}
					
				}
				
				operatorStack.add(token);
				
			} else if(token.equals("(")) {
				operatorStack.add("(");
			} else if(token.equals(")")) {
				int index = operatorStack.size() - 1;
				
				if(index < 0)
					throw new AssertionError("Nao encontrado parenteses");
				
				while(!operatorStack.get(index).equals("(")) {
					outputStack.add(operatorStack.remove(index));
					index = operatorStack.size() - 1;
					
					if(index < 0)
						throw new AssertionError("Nao encontrado parenteses");
				}
				
				operatorStack.remove(index);
				
			}
			
		}
		
		while(operatorStack.size() > 0) {
			int index = operatorStack.size() - 1;
			
			if(operatorStack.get(index).equals("(")) {
				throw new AssertionError("Nao encontrado parenteses");
			} else {
				outputStack.add(operatorStack.remove(index));
			}
		}
		
		return outputStack;
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

	private boolean isNumber(String token) {
		
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

}
