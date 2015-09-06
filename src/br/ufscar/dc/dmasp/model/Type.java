package br.ufscar.dc.dmasp.model;

public class Type { 
	private String id;	 
	private String name;
	private boolean user;
	private boolean primitive;
	
	public boolean isPrimitive() {
		return primitive;
	}

	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}

	public Type(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.user = false;
		this.primitive = true;
	}

	public Type(Type other) {
		super();
		this.id = other.getId();
		this.name = other.getName();
		this.user = other.isUser();
		this.primitive = other.isPrimitive();
	}

	public Type(Class c1) {
		super();
		this.id = c1.getId();
		this.name = c1.getName();
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
	
	public boolean isUser() {
		return user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}

	
	public String toString() {
		return getName();
	}
}
 
