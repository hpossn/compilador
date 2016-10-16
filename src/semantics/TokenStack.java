package semantics;

import java.util.ArrayList;
import java.util.List;

public class TokenStack {
	
	private List<String> tokenStack = new ArrayList<>();
	
	public void push(String token) {
		tokenStack.add(token);
	}
	
	public String pop() {
		if(tokenStack.size() > 0) return tokenStack.remove(tokenStack.size() - 1);
		
		return null;
	}
	
	public String getHead() {
		if(tokenStack.size() > 0) return tokenStack.get(tokenStack.size() - 1);
		
		return null;
	}
	
	public void clear() {
		tokenStack.clear();
	}
	
	public void printStack() {
		for(String each : tokenStack) {
			System.out.println(each);
		}
	}

}
