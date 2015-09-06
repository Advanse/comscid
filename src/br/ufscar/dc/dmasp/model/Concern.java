package br.ufscar.dc.dmasp.model;

public class Concern { 
	private String id;
	private String name;
	private boolean primary;
	private boolean isForClassName;

	
	public Concern(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.primary = true;
		this.isForClassName = false;
	}
	
	public Concern(String id, String name, boolean primary) {
		super();
		this.id = id;
		this.name = name;
		this.primary = primary;
		this.isForClassName = false;
	}
	
	public Concern(Concern other) {
		super();
		this.id = other.getId();
		this.name = other.getName();
		this.primary = other.isPrimary();
		this.isForClassName = other.isForClassName;
	}

	public boolean isForClassName() {
		return isForClassName;
	}

	public void setForClassName(boolean isForClassName) {
		this.isForClassName = isForClassName;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
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

	public String toString() {
		return (id + " - " + name);
	}	 
}
 
