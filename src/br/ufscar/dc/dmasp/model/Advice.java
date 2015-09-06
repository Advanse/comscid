package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Advice extends Operation {

	private Pointcut pointcut;
	private String adType;
	public static String beforeType = "Before";
	public static String afterType = "After";
	public static String aroundType = "Around";
	
	public Advice(String id, String name, Type returnType, String adType) {
		super(id, name, returnType);
		this.adType = adType;
		this.pointcut = null;
	}

	public Advice(Advice other) {		
		super(other);
		this.adType = other.getAdType();
		this.pointcut = new Pointcut(other.getPointcut());
	}

	public Pointcut getPointcut() {
		return pointcut;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = new Pointcut(pointcut);
		Iterator<Parameter> itP = pointcut.getParameters().iterator();
		while(itP.hasNext()) {
			Parameter pAux = itP.next();			
			this.parameters.add(new Parameter(pAux));	
		}		
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}
}
