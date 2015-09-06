package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositionPointcut extends Pointcut {
	
	private List<Term> terms;
	private int it;
	private int par;

	public CompositionPointcut(String id, String name, Type type, boolean isAbstract) {
		super(id, name, type, isAbstract);
		this.terms = new ArrayList<Term>();
		this.it = 0;
		this.par = 0;
	}
	
	public void addTerm(Term t) {
		this.terms.add(t);
		if (t.getTermType().equalsIgnoreCase(Pointcut.PC)) {
			Pointcut pc = (Pointcut) t;
			Iterator<Parameter> itP = pc.getParameters().iterator();
			while(itP.hasNext()) {
				Parameter pAux = itP.next();			
				this.parameters.add(new Parameter(pAux));	
			}		
		}
	}
	
	public boolean validate() {
		System.out.println("entrou0");
		Term t = null;
		if (it < terms.size()) { t = terms.get(it); it++; }
		else return false;
		String type = t.getTermType();
		
		if (type.equals(Pointcut.PC)) return validatePC1();
		if (type.equals(Operator.not)) return validate();
		if (type.equals(Operator.openPar)) {
			par++;
			return validate();
		}
		else return false;
	}
	
	public boolean validatePC1() {
		System.out.println("entrou1");
		Term t = null;
		if (it < terms.size()) { t = terms.get(it); it++; }
		else return false;
		
		String type = t.getTermType();
		
		if (type.equals(Pointcut.PC)) return false;
		if (type.equals(Operator.not)) return validatePC1();
		else if ((type.equals(Operator.andCond)) || (type.equals(Operator.orCond))) return validateAndOrCond();
		else return false;
	}
		
	public boolean validatePC2() {
		System.out.println("entrou1");
		Term t = null;
		if (it < terms.size()) { t = terms.get(it); it++; }
		else {
			if (par == 0) return true;
			else return false;
		}
		
		String type = t.getTermType();
		
		if (type.equals(Pointcut.PC)) return false;
		if (type.equals(Operator.not)) return validatePC2();
		else if ((type.equals(Operator.andCond)) || (type.equals(Operator.orCond))) return validateAndOrCond();
		else if (type.equals(Operator.closePar)) {
			par--;
			return validatePC2();
		}
		else return false;
	}

	public boolean validateAndOrCond() {
		System.out.println("entrou2");
		Term t = null;
		if (it < terms.size()) { t = terms.get(it); it++; }
		else return false;
		
		String type = t.getTermType();
		
		if (type.equals(Operator.not)) return validateAndOrCond();
		if (type.equals(Pointcut.PC)) return validatePC2();
		if (type.equals(Operator.openPar)) {
			par++;
			return validateAndOrCond();
		}
		else return false;
	}
	
	public String toString() {
		Iterator<Term> it = terms.iterator();
		String ret = "";
		while(it.hasNext()) {
			Term t = it.next();
			ret = ret + t.getName() + " ";
		}
		return ret;
	}
}
