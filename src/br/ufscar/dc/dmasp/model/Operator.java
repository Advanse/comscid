package br.ufscar.dc.dmasp.model;

public class Operator implements Term {

	private String name;
	public static String openPar = "(";
	public static String closePar = ")";
	public static String andCond = "&&";
	public static String orCond = "||";
	public static String not = "!";
	
	public Operator(String opType) {
		this.name = opType;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getTermType() {
		// TODO Auto-generated method stub
		return this.name;
	}	
}
