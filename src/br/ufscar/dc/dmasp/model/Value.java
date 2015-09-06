package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Value {
	private String id;
	private String name;	 
	private Type type;	 
	protected List<Concern> concerns;	
	private List<Modifier> modifiers;	
	
	public Value(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.concerns = new ArrayList<Concern>();
		this.modifiers = new ArrayList<Modifier>();
	}

	public Value(Value other) {
		super();
		this.id = other.getId();
		this.name = other.getName();
		this.type = new Type(other.getType());
		this.concerns = new ArrayList<Concern>();
		Iterator<Concern> itC = other.getConcerns().iterator();
		while(itC.hasNext()) {
			Concern cAux = itC.next();			
			this.concerns.add(new Concern(cAux));	
		}
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

	public void removeAllConcerns() {
		concerns.clear();
	}

	public Concern findConcernById(String id) {
		boolean achou = false;
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			retConcern = it.next();
			achou = id.equals(retConcern.getId());
		}
		
		if (achou) return new Concern(retConcern);
		else return null;		
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

	public boolean hasConcern() {
		return (!concerns.isEmpty());
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
	
	public String toString() {
		return (getType() + " " + name);
	}
}
