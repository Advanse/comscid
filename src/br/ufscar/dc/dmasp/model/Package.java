package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Package { 
	private String name;	 
	private List<Class> classes;
	
	public Package(String name) {
		super();
		this.name = name;
		this.classes = new ArrayList<Class>();
	}

	public Package(Package other) {
		this.name = other.getName();
		this.classes = new ArrayList<Class>();
		Iterator<Class> itI = other.getClasses().iterator();
		while(itI.hasNext()) {
			Class cAux = itI.next();			
			this.classes.add(new Class(cAux));	
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}	
	 
	public void addClass(Class c) {
		classes.add(c);
	}	

	public Class getClass(int index) {
		return classes.get(index);
	}
	
	public void removeClass(int index) {
		classes.remove(index);
	}
	
	public Class findClassById(String id) {
		boolean achou = false;
		Class retClass = null;
		Iterator<Class> it = classes.iterator();

		while((!achou) && (it.hasNext())) {
			retClass = it.next();
			achou = id.equals(retClass.getId());
		}
		
		if (achou) return retClass;
		else return null;		
	}
	
	public Class findClassByName(String name) {
		boolean achou = false;
		Class retClass = null;
		Iterator<Class> it = classes.iterator();
		while((!achou) && (it.hasNext())) {
			retClass = it.next();
			achou = name.toLowerCase().equals(retClass.getName().toLowerCase());
		}
		
		if (achou) return retClass;
		else return null;		
	}
	
	public void removeClassById(String idClass, String nameClass){
		Iterator<Class> itClasses = classes.iterator();

		// Remove os relacionamentos dessa classe
		while(itClasses.hasNext()) {
			Class aux = itClasses.next();			
			if (aux.getId().equalsIgnoreCase(idClass)) {
				itClasses.remove();
			} else {
				if (aux.getInherit() != null) {
					if (aux.getInherit().getId().equalsIgnoreCase(idClass)) aux.setInherit(null);
				}
				aux.removeAllAttributeWhoseIdContains(nameClass);
				aux.updateListOfConcerns(false);
			}
		}
	}
	
	public void removeClassById(String idClass){
		Iterator<Class> itClasses = classes.iterator();

		while(itClasses.hasNext()) {
			Class aux = itClasses.next();			
			if (aux.getId().equalsIgnoreCase(idClass)) {
				itClasses.remove();
			} 
		}
	}

	public String toString() {
		return (getName());
	}
	
	
}
 
