package br.ufscar.dc.dmasp.model;

public class Modifier { 
	private String name;

	public Modifier(String name) {
		super();
		this.name = name;
	}

	public Modifier(Modifier other) {
		super();
		this.name = other.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	 
}
 
