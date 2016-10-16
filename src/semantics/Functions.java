package semantics;

import java.util.List;

public class Functions {
	private String functionName;
	private String returnType;
	private List<Paramter> paramters;
	
	public Functions(String functionName, String returnType, List<Paramter> paramters) {
		super();
		this.functionName = functionName;
		this.returnType = returnType;
		this.paramters = paramters;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<Paramter> getParamters() {
		return paramters;
	}

	public void setParamters(List<Paramter> paramters) {
		this.paramters = paramters;
	}
	
	
	
	
}
