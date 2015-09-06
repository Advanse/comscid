package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pointcut extends Attribute implements Term {
	
	private List<Operation> operations;
	private boolean isAbstract;
	protected List<Parameter> parameters;
	public static String PC = "PC";

	public Pointcut(String id, String name, Type type) {
		super(id, name, type);
		this.operations = new ArrayList<Operation>();
		this.parameters = new ArrayList<Parameter>();
		this.isAbstract = false;		
	}

	public Pointcut(Pointcut other) {
		super(other);
		this.operations = new ArrayList<Operation>();
		Iterator<Operation> itO = other.getOperations().iterator();
		while(itO.hasNext()) {
			Operation oAux = itO.next();			
			this.operations.add(new Operation(oAux));	
		}
		this.parameters = new ArrayList<Parameter>();
		Iterator<Parameter> itP = other.getParameters().iterator();
		while(itP.hasNext()) {
			Parameter pAux = itP.next();			
			this.parameters.add(new Parameter(pAux));	
		}
		
		this.isAbstract = other.isAbstract();		
	}

	public Pointcut(String id, String name, Type type, boolean isAbstract) {
		super(id, name, type);
		this.operations = new ArrayList<Operation>();
		this.parameters = new ArrayList<Parameter>();
		this.isAbstract = isAbstract;		
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getTermType() {
		return PC;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public void addOperation(Operation operation) {
		operations.add(operation);
	}	

	public void addOperations(List<Operation> operations) {
		this.operations.addAll(operations);
	}	

	public Operation getOperation(int index) {
		return operations.get(index);
	}
	
	public void removeOperation(int index) {
		operations.remove(index);
	}

	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

	public void addParameters(List<Parameter> lstParameters) {
		if (lstParameters.size() > 0) this.parameters.addAll(lstParameters);
	}
}
