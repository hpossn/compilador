package semantics;

public class Paramter {
	private String name;
	private String type;
	
	public Paramter(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		
		if(!(o instanceof Paramter)) return false;
		
		Paramter p = (Paramter) o;
		
		return this.name.equals(p.name) && this.name.equals(p.type);
	}

}
