package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Operation { 
	private String id;	 
	private String name;	 
	protected List<Parameter> parameters;	 
	private Type returnType;	 
	private List<Concern> concerns;	
	private List<Modifier> modifiers;
	private List<Variable> variables;
	private List<String> calledOperations;
	
	public Operation(String id, String name, Type returnType) {
		super();
		this.id = id;
		this.name = name;
		this.returnType = returnType;
		this.parameters = new ArrayList<Parameter>();
		this.concerns = new ArrayList<Concern>();
		this.modifiers = new ArrayList<Modifier>();	
		this.variables = new ArrayList<Variable>();
		this.calledOperations = new ArrayList<String>();
	}

	public Operation(Operation other) {
		this.id = other.getId();
		this.name = other.getName();
		this.parameters = new ArrayList<Parameter>();
		Iterator<Parameter> itP = other.getParameters().iterator();
		while(itP.hasNext()) {
			Parameter pAux = itP.next();			
			this.parameters.add(new Parameter(pAux));	
		}

		this.concerns = new ArrayList<Concern>();
		Iterator<Concern> itC = other.getConcerns().iterator();
		while(itC.hasNext()) {
			Concern cAux = itC.next();			
			this.concerns.add(new Concern(cAux));	
		}
		
		this.variables = new ArrayList<Variable>();
		Iterator<Variable> itV = other.getVariables().iterator();
		while(itV.hasNext()) {
			Variable vAux = itV.next();			
			this.variables.add(new Variable(vAux));	
		}
		
		this.modifiers = new ArrayList<Modifier>();
		Iterator<Modifier> itM = other.getModifiers().iterator();
		while(itM.hasNext()) {
			Modifier mAux = itM.next();			
			this.modifiers.add(new Modifier(mAux));	
		}

		this.calledOperations = new ArrayList<String>();
		Iterator<String> itS = other.getCalledOperations().iterator();
		while(itS.hasNext()) {
			String sAux = itS.next();			
			this.calledOperations.add(new String(sAux));	
		}
		
		this.returnType = new Type(other.getReturnType());
	}

	public boolean isEqual(Operation other) {
		boolean ret= true;

		if (!name.equalsIgnoreCase(other.getName())) return false;

		List<Parameter> lsOtherParameters = other.getParameters();
		if (lsOtherParameters == null) return false;
		
		if (parameters.size() != lsOtherParameters.size()) return false;
		
		Iterator<Parameter> it = parameters.iterator();
		Iterator<Parameter> itOther = lsOtherParameters.iterator(); 
		while ((ret) && (it.hasNext())) {
			Parameter pAux = it.next();
			Parameter pOtherAux = itOther.next();
			if (!pAux.getType().getName().equalsIgnoreCase(pOtherAux.getType().getName()))
				ret = false;
		}
		return ret;
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
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}	

	public Parameter getParameter(int index) {
		return parameters.get(index);
	}
	
	public void removeParameter(int index) {
		parameters.remove(index);
	}

	public Type getReturnType() {
		return returnType;
	}
	
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	public List<Concern> getConcerns() {
		return concerns;
	}
	
	public void setConcerns(List<Concern> concerns) {
		this.concerns = concerns;
	}
	
	public void addConcern(Concern concern) {
		concerns.add(concern);
	}	

	public Concern getConcern(int index) {
		return concerns.get(index);
	}
	
	public void removeConcern(int index) {
		concerns.remove(index);
	}
	
	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public void addModifier(Modifier Modifier) {
		modifiers.add(Modifier);
	}	

	public Modifier getModifier(int index) {
		return modifiers.get(index);
	}
	
	public void removeModifier(int index) {
		modifiers.remove(index);
	}
	
	public void addVariable(Variable variable) {
		variables.add(variable);
	}		
	
	public List<Variable> getVariables() {
		return variables;
	}
	
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Variable getVariable(int index) {
		return variables.get(index);
	}
	
	public void removeVariable(int index) {
		variables.remove(index);
	}
	
	public Concern findConcernById(String id) {
		boolean achou = false;
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			retConcern = it.next();
			achou = id.equalsIgnoreCase(retConcern.getId());
		}
		
		if (achou) return new Concern(retConcern);
		else return null;		
	}
	
	public void removeAllConcerns() {
		concerns.clear();
	}

	public void removeConcernById(String id) {
		boolean achou = false;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			Concern cAux = it.next();
			achou = id.equalsIgnoreCase(cAux.getId());
		}
		
		if (achou) it.remove();
	}
	
	public Concern findConcernByName(String name) {
		boolean achou = false;
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			retConcern = it.next();
			achou = name.toLowerCase().equals(retConcern.getName().toLowerCase());
		}
		
		if (achou) return new Concern(retConcern);
		else return null;		
	}	 

	public Parameter findParameterById(String id) {
		boolean achou = false;
		Parameter retParameter = null;
		Iterator<Parameter> it = parameters.iterator();
		while((!achou) && (it.hasNext())) {
			retParameter = it.next();
			achou = id.equals(retParameter.getId());
		}
		
		if (achou) return retParameter;
		else return null;		
	}
	
	public boolean hasConcern() {
		return (!concerns.isEmpty());
	}
	
	public Modifier findModifierByName(String name) {
		boolean achou = false;
		Modifier retModifier = null;
		Iterator<Modifier> it = modifiers.iterator();
		while((!achou) && (it.hasNext())) {
			retModifier = it.next();
			achou = name.toLowerCase().equals(retModifier.getName().toLowerCase());
		}
		
		if (achou) return new Modifier(retModifier);
		else return null;		
	}
	

	public Parameter findParameterByName(String name) {
		boolean achou = false;
		Parameter retParameter = null;
		Iterator<Parameter> it = parameters.iterator();
		while((!achou) && (it.hasNext())) {
			retParameter = it.next();
			achou = name.toLowerCase().equals(retParameter.getName().toLowerCase());
		}
		
		if (achou) return retParameter;
		else return null;		
	}
	
	public Variable findVariableByName(String name) {
		boolean achou = false;
		Variable retVariable = null;
		Iterator<Variable> it = variables.iterator();
		while((!achou) && (it.hasNext())) {
			retVariable = it.next();
			achou = name.toLowerCase().equals(retVariable.getId().toLowerCase());		
		}
		
		if (achou) return new Variable(retVariable);
		else return null;		
	}	 

	public String findCalledOperationByName(String name) {
		boolean achou = false;
		String retCalledOperation = null;
		Iterator<String> it = calledOperations.iterator();
		while((!achou) && (it.hasNext())) {
			retCalledOperation = it.next();
			achou = name.toLowerCase().equals(retCalledOperation.toLowerCase());		
		}
		
		if (achou) return new String(retCalledOperation);
		else return null;		
	}	 

	public boolean isConcernExist(String idConcern){
		boolean achou = false;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			Concern concern = it.next();
			if (concern.getId().equals(idConcern)) {
				achou = true;
			}
		}
		return achou;
	}	

	public boolean isCalledOperationExist(String methodName){
		boolean achou = false;
		Iterator<String> it = calledOperations.iterator();
		while((!achou) && (it.hasNext())) {
			String calledOperation = it.next();
			if (calledOperation.equals(methodName)) {
				achou = true;
			}
		}
		return achou;
	}	

	public String toString() {
		return (getReturnType() + " " + getName());
	} 
	
	public List<String> getCalledOperations() {
		return calledOperations;
	}

	public void setCalledOperations(List<String> calledOperations) {
		this.calledOperations = calledOperations;
	}

	public void addCalledOperation(String calledOperation) {
		calledOperations.add(calledOperation);
	}	


}