package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrossCuttingConcern extends Package {

	private List<Aspect> aspects;
	
	public CrossCuttingConcern(String name) {
		super(name);
		this.aspects = new ArrayList<Aspect>();
	}
	
	public CrossCuttingConcern(CrossCuttingConcern other) {
		super(other);
		this.aspects = new ArrayList<Aspect>();
		Iterator<Aspect> itI = other.getAspects().iterator();
		while(itI.hasNext()) {
			Aspect cAspect = itI.next();			
			this.aspects.add(new Aspect(cAspect));	
		}	
		
	}

	public List<Aspect> getAspects() {
		return aspects;
	}

	public void setAspects(List<Aspect> aspects) {
		this.aspects = aspects;
	}	
	 
	public void addAspect(Aspect a) {
		aspects.add(a);
	}	

	public Aspect getAspect(int index) {
		return aspects.get(index);
	}
	
	public void removeAspect(int index) {
		aspects.remove(index);
	}
	
	public Aspect findAspectById(String id) {
		boolean achou = false;
		Aspect retAspect = null;
		Iterator<Aspect> it = aspects.iterator();

		while((!achou) && (it.hasNext())) {
			retAspect = it.next();
			achou = id.equals(retAspect.getId());
		}
		
		if (achou) return retAspect;
		else return null;		
	}
	
	public Aspect findAspectByName(String name) {
		boolean achou = false;
		Aspect retAspect = null;
		Iterator<Aspect> it = aspects.iterator();
		while((!achou) && (it.hasNext())) {
			retAspect = it.next();
			achou = name.toLowerCase().equals(retAspect.getName().toLowerCase());
		}
		
		if (achou) return retAspect;
		else return null;		
	}

	public String toString() {
		return (getName());
	}
}
