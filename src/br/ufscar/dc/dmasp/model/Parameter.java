package br.ufscar.dc.dmasp.model;

public class Parameter { 
	private String id;	 
	private String name;	 
	private Type type;	
	
	public Parameter(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public Parameter(Parameter other) {
		super();
		this.id = other.getId();
		this.name = other.getName();
		this.type = new Type(other.getType());
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}

	public String toString() {
		return (getName());
	}
	
}
 
