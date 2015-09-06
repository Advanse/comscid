package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TargetPointcut extends Pointcut {
	
	private List<Class> targets;

	public TargetPointcut(String id, String name, Type type, boolean isAbstract) {
		super(id, name, type, isAbstract);
		this.targets = new ArrayList<Class>();
	}


	public TargetPointcut(TargetPointcut other) {
		super(other);
		this.targets = new ArrayList<Class>();
		Iterator<Class> itCl = other.getTargets().iterator();
		while(itCl.hasNext()) {
			Class clAux = itCl.next();			
			this.targets.add(new Class(clAux));	
		}
		
	}
	
	public void addTarget(Class target) {
		this.targets.add(target);
	}

	public List<Class> getTargets() {
		return targets;
	}

	public void setTargets(List<Class> targets) {
		this.targets = targets;
	}	
	
	public boolean isExistTarget(String id) {
		boolean ret = false;
		Iterator<Class> itTargets = targets.iterator();
		while((!ret) && (itTargets.hasNext())) {
			Class target = itTargets.next();
			ret = (target.getId().equalsIgnoreCase(id));
		}
		return ret;
	}

}
