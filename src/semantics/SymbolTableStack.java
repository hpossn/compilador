package semantics;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableStack {
	
	private List<SymbolTable> stackList;
	
	public SymbolTableStack() {
		stackList = new ArrayList<>();
		stackList.add(0, new SymbolTable("gbl"));
	}
	
	public boolean push(SymbolTable table) {
		if(stackList.contains(table)) return false;
		
		if(stackList.contains(table))
			return false;
		
		return stackList.add(table);
	}
	
	public SymbolTable pop() {
		if(stackList.size() > 0)
			return stackList.remove(stackList.size() - 1);
		
		return null;
	}
	
	public boolean addGlobalVariable(Variable v) {
		SymbolTable global = stackList.remove(0);
		if(global.addVariable(v) != null) {
			stackList.add(0, global);
			return true;
		}
		return false;
	}
	
	public SymbolTable getGlobalContext() {
		return stackList.get(0);
	}

}
