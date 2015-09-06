package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Aspect extends Class {

	private List<Pointcut> pointcuts;
	private List<Advice> advices;
	private List<DeclareParents> declareParents;
	
	public Aspect(String id, String name) {
		super(id, name);
		this.pointcuts = new ArrayList<Pointcut>();
		this.advices = new ArrayList<Advice>();
		this.declareParents = new ArrayList<DeclareParents>();
	}
	
	public Aspect(Aspect other) {
		super(other);
		this.pointcuts = new ArrayList<Pointcut>();
		Iterator<Pointcut> itP = other.getPointcuts().iterator();
		while(itP.hasNext()) {
			Pointcut pAux = itP.next();			
			this.pointcuts.add(new Pointcut(pAux));	
		}		
		this.advices = new ArrayList<Advice>();
		Iterator<Advice> itA = other.getAdvices().iterator();
		while(itA.hasNext()) {
			Advice aAux = itA.next();			
			this.advices.add(new Advice(aAux));	
		}	
		this.declareParents = new ArrayList<DeclareParents>();
		Iterator<DeclareParents> itDp = other.getDeclareParents().iterator();
		while(itDp.hasNext()) {
			DeclareParents dpAux = itDp.next();
			this.declareParents.add(new DeclareParents(dpAux));	
		}		
	}

	// Converte a class in an aspect
	public Aspect(String idAspect, String nameAspect, Class other) {
		super(other.getId(), other.getName());
		this.id = idAspect;
		this.name = nameAspect;
		this.pointcuts = new ArrayList<Pointcut>();
		this.advices = new ArrayList<Advice>();
		this.declareParents = new ArrayList<DeclareParents>();
		
		Iterator<Attribute> itAttribute = other.getAttributes().iterator();
		while(itAttribute.hasNext()) {
			Attribute aux = itAttribute.next();
			IntroductionAttribute ita = new IntroductionAttribute(idAspect+"."+aux.getId(), aux.getName(), new Type(aux.getType()), false);
			ita.addTarget(new Class(other));
			pointcuts.add(ita);
		}

		Iterator<Operation> itOperations = other.getOperations().iterator();
		while(itOperations.hasNext()) {
			Operation op = itOperations.next();
			IntroductionMethod ito = new IntroductionMethod(idAspect+"."+op.getId(), op.getName(), new Type(op.getReturnType()), false);
			ito.addParameters(new ArrayList<Parameter>(op.getParameters()));
			ito.addTarget(new Class(other));
			pointcuts.add(ito);
		}
	}

	public IntroductionMethod findPointcutById(String id) {
		boolean achou = false;
		Pointcut retPointcut = null;
		Iterator<Pointcut> it = pointcuts.iterator();
		while((!achou) && (it.hasNext())) {
			retPointcut = it.next();
			if ((id.toLowerCase().equals(retPointcut.getId().toLowerCase())
					&& (retPointcut instanceof IntroductionMethod))) 
					achou = true;
		}
		
		if (achou) {
			IntroductionMethod im = (IntroductionMethod) retPointcut;
			return im;
		}
		else return null;
	}

	public IntroductionMethod findPointcutByName(String name) {
		boolean achou = false;
		Pointcut retPointcut = null;
		Iterator<Pointcut> it = pointcuts.iterator();
		while((!achou) && (it.hasNext())) {
			retPointcut = it.next();
			if ((name.toLowerCase().equals(retPointcut.getName().toLowerCase())
					&& (retPointcut instanceof IntroductionMethod))) 
					achou = true;
		}
		
		if (achou) {
			IntroductionMethod im = (IntroductionMethod) retPointcut;
			return im;
		}
		else return null;
	}

	public DeclareParents findDeclareParentsById(String id) {
		boolean achou = false;
		DeclareParents ret = null;
		Iterator<DeclareParents> it = declareParents.iterator();
		while((!achou) && (it.hasNext())) {
			ret = it.next();
			if (id.toLowerCase().equals(ret.getId().toLowerCase())) achou = true;
		}
		
		if (achou) return ret;
		else return null;
	}

	public List<Pointcut> getPointcuts() {
		return pointcuts;
	}

	public void setPointcuts(List<Pointcut> pointcuts) {
		this.pointcuts = pointcuts;
	}

	public void addDeclareParents(DeclareParents dp) {
		this.declareParents.add(dp);
	}	

	public List<DeclareParents> getDeclareParents() {
		return this.declareParents;
	}

	public void setDeclareParents(List<DeclareParents> declareParents) {
		this.declareParents = declareParents;
	}

	
	public void addPointcut(Pointcut pointcut) {
		pointcuts.add(pointcut);
	}	

	public Pointcut getPointcut(int index) {
		return pointcuts.get(index);
	}
	
	public void removePointcut(int index) {
		pointcuts.remove(index);
	}	

	public List<Advice> getAdvices() {
		return advices;
	}

	public void setAdvices(List<Advice> advices) {
		this.advices = advices;
	}
	
	public void addAdvice(Advice advice) {
		advices.add(advice);
	}	

	public Advice getAdvice(int index) {
		return advices.get(index);
	}
	
	public void removeAdvice(int index) {
		advices.remove(index);
	}
}
