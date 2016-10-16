package semantics;

public class Variable {
	private String fullName;
	private String initialName;
	private String value;
	private String type;
	private String context;
	
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getInitialName() {
		return initialName;
	}
	public void setInitialName(String initialName) {
		this.initialName = initialName;
	}
	
	public Variable(String initialName, String value, String type) {
		super();
		this.fullName = "";
		this.initialName = initialName;
		this.value = value;
		this.type = type;
	}

	
	
	@Override
	public String toString() {
		return "Variable [fullName=" + fullName + ", initialName=" + initialName + ", value=" + value + ", type=" + type
				+ ", context=" + context + "]";
	}
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		
		if(!(o instanceof Variable)) return false;
		
		Variable v = (Variable) o;
		
		return this.fullName.equals(v.fullName);
	}
}
