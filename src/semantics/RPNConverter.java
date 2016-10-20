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
	
	public List<String> convert(List<String>initialList) {		
		List<String> outputStack = new ArrayList<>();
		List<String>operatorStack = new ArrayList<>();
		
		List<String> list = fixList(initialList);
		
		while(list.size() > 0) {
			String token = list.remove(0);
			if(token == null) break;
			
			if(isNumber(token) || isVariable(token)) {
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
						if(operatorStack.size() == 0)
							flag = false;
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

	private List<String> fixList(List<String> initialList) {
		List<String> finalList = new ArrayList<>();
		
		for(int i = 0; i < initialList.size(); i++) {
			String token = initialList.get(i);
			if(token.equals("_")) {
				String temp = "_" + initialList.get(++i);
				
				if(initialList.get(i + 1).equals("[")) {
					i++;
					temp = temp + "[";
					token = initialList.get(++i);
					while(!token.equals("]")) {
						temp = temp + token;
						token = initialList.get(++i);
						
					}
					
					temp = temp + "]";
					
				}
				
				finalList.add(temp);
			} else {
				finalList.add(initialList.get(i));
			}
		}
		
		
		
		
		return finalList;
		
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
