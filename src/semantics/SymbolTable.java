package semantics;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	
	private String contextName;
	private int counter;
	
	private List<Variable> variables;
	
	
	public SymbolTable(String contextName) {
		super();
		this.contextName = contextName;
		counter = 0;
		variables = new ArrayList<>();
	}

	public String addVariable(Variable v) {
		for(Variable each : variables) {
			if(each.getInitialName().equals(v.getInitialName()))
				return null;
		}
		
		v.setFullName(contextName + counter++ + "_" + v.getInitialName());
		
		if(variables.add(v)) {
			return v.getFullName();
		} else  {
			return null;
		}
	}
	
	public boolean removeVariable(String initialName) {
		for(Variable each : variables) {
			if(each.getInitialName().equals(initialName))
				return variables.remove(each);
		}
		
		return false;
	}
	
	public List<Variable> getVariables() {
		return variables;
	}


	public void printVariables() {
		variables.forEach(each -> System.out.println(each.toString()));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		
		if(!(o instanceof SymbolTable)) return false;
		
		SymbolTable v = (SymbolTable) o;
		
		return this.contextName.equals(v.contextName);
	}

}
