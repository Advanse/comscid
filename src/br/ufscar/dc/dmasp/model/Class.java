package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Class { 
	protected String id;	 
	protected String name;
	private Class inherit;
	private List<Modifier> modifiers;	 
	private List<Operation> operations;	 
	private List<Attribute> attributes;
	private List<Concern> concerns;
	private List<Class> implementedInterfaces;
	private boolean isAbstract;
	private boolean isInterface;
	 
	public Class(String id, String name) {
		this.id = id;
		this.name = name;
		this.modifiers = new ArrayList<Modifier>();
		this.attributes = new ArrayList<Attribute>();
		this.operations = new ArrayList<Operation>();
		this.concerns = new ArrayList<Concern>();
		this.implementedInterfaces = new ArrayList<Class>();
		this.isAbstract = false;
		this.isInterface = false;
		this.inherit = null;		
	}

	public Class(Class other) {
		this.id = other.getId();
		this.name = other.getName();
		this.modifiers = new ArrayList<Modifier>();
		Iterator<Modifier> itM = other.getModifiers().iterator();
		while(itM.hasNext()) {
			Modifier mAux = itM.next();			
			this.modifiers.add(new Modifier(mAux));	
		}

		this.implementedInterfaces = new ArrayList<Class>();
		Iterator<Class> itI = other.getImplementedInterfaces().iterator();
		while(itI.hasNext()) {
			Class cAux = itI.next();			
			this.implementedInterfaces.add(new Class(cAux));	
		}

		
		this.attributes = new ArrayList<Attribute>();
		Iterator<Attribute> itA = other.getAttributes().iterator();
		while(itA.hasNext()) {
			Attribute aAux = itA.next();			
			this.attributes.add(new Attribute(aAux));	
		}

		this.operations = new ArrayList<Operation>();
		Iterator<Operation> itO = other.getOperations().iterator();
		while(itO.hasNext()) {
			Operation oAux = itO.next();			
			this.operations.add(new Operation(oAux));	
		}

		this.concerns = new ArrayList<Concern>();
		Iterator<Concern> itC = other.getConcerns().iterator();
		while(itC.hasNext()) {
			Concern cAux = itC.next();			
			this.concerns.add(new Concern(cAux));	
		}
		if (other.getInherit() != null) this.inherit = new Class(other.getInherit());
		else this.inherit = null;
		this.isAbstract = other.isAbstract();
		this.isInterface = other.isInterface();
	}
	
	public boolean hasOverridedMethods(Class aux) {
		List<Operation> lsOperations = aux.getOperations();
		Iterator<Operation> itOP = lsOperations.iterator();
		while(itOP.hasNext()) {
			Operation auxOp = itOP.next();
			if (isOverrided(auxOp)) return true;
		}
		return false;
	}
	
	public boolean isOverrided(Operation op) {
		boolean ret = false;
		Operation aux ;
		if (inherit != null) {
			aux = inherit.findOperationByName(op.getName()); 
			if ((aux != null) && (aux.isEqual(op))) return true;
		}
		
		Iterator<Class> it = implementedInterfaces.iterator(); 
		while ((!ret) && (it.hasNext())) {
			Class cAux = it.next();
			aux = cAux.findOperationByName(op.getName());
			if ((aux != null) && (aux.isEqual(op))) return true;
		}		

		return ret;
	}

	public boolean isSubtypeOf(String typeName) {
		boolean achou = false;
		if (inherit != null) {
			if (typeName.equalsIgnoreCase(inherit.getName())) return true;
		}
		
		Iterator<Class> itInterfaces = implementedInterfaces.iterator();
		while ((!achou) && (itInterfaces.hasNext())) {
			Class aux = itInterfaces.next();
			if (typeName.equalsIgnoreCase(aux.getName())) achou = true;
		}

		return achou;
	}
	
	public Operation isOverridedInThisClass(Operation op) {
		boolean achou = false;
		Operation ret = null;
		Iterator<Operation> it = operations.iterator();
		while((!achou) && (it.hasNext())) {
			Operation opAux = it.next();
			if (opAux.isEqual(op)) {
				achou = true;
				ret = opAux;
			}
		}
		return ret;
	}
	
	public void clearConcerns() {
		
		concerns.clear();
		
		Iterator<Attribute> itAt = attributes.iterator();
		while(itAt.hasNext()) {
			Attribute atAux = itAt.next();
			atAux.removeAllConcerns();
		}

		Iterator<Operation> it = operations.iterator();
		while(it.hasNext()) {
			Operation opAux = it.next();
			opAux.removeAllConcerns();
		}
	}
	
	public void removeSuper(String idSuper) {
		if ((inherit != null) && (inherit.getId().equalsIgnoreCase(idSuper))) {
			this.inherit = null;
		}
		
		List<Class> lsSuper = new ArrayList<Class>(implementedInterfaces);
 		Iterator<Class> itSuper = lsSuper.iterator();
		while(itSuper.hasNext()) {
			Class clSuper = itSuper.next();
			if (clSuper.getId().equalsIgnoreCase(idSuper)) itSuper.remove();
		}
		
	}
	
	public List<Operation> overridedMethods() {
		List<Operation> lstAux = new ArrayList<Operation>();
		Iterator<Operation> it = operations.iterator();
		while(it.hasNext()) {
			Operation op = it.next();
			if (!isOverrided(op)) lstAux.add(op);
		}
		return lstAux;
	}
	

	public Class getInherit() {
		return inherit;
	}

	public void setInherit(Class inherit) {
		this.inherit = inherit;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
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
	
	public void removeConcernById(Concern concern){
		removeConcernById(concern.getId());
	}

	public void removeConcernById(String idConcern){
		Iterator<Concern> itConcern = concerns.iterator();
		while(itConcern.hasNext()) {
			Concern aux = itConcern.next();
			if (aux.getId().equals(idConcern)) {
				itConcern.remove();
			}
		}
	}
	
	public void removeAttributeById(Attribute attr){
		String idAttribute = attr.getId();
		Iterator<Attribute> itAttribute = attributes.iterator();
		boolean ret = false;
		while((itAttribute.hasNext()) && (ret == false)) {
			Attribute aux = itAttribute.next();
			if (aux.getId().equals(idAttribute)) {
				itAttribute.remove();
				ret = true;
			}
		}
	}

	public void removeOperationById(String idOP){
		Iterator<Operation> itOperation = operations.iterator();
		boolean ret = false;
		while((itOperation.hasNext()) && (ret == false)) {
			Operation aux = itOperation.next();
			if (aux.getId().equals(idOP)) {
				itOperation.remove();
				ret = true;
			}
		}
	}

	public List<Modifier> getModifiers() {
		return modifiers;
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

	public Operation getOperation(int index) {
		return operations.get(index);
	}
	
	public void removeOperation(int index) {
		operations.remove(index);
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attribute) {
		this.attributes = attribute;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}	

	public Attribute getAttribute(int index) {
		return attributes.get(index);
	}
	
	public void removeAttribute(int index) {
		attributes.remove(index);
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

	public Operation findOperationById(String id) {
		boolean achou = false;
		Operation retOperation = null;
		Iterator<Operation> it = operations.iterator();
		while((!achou) && (it.hasNext())) {
			retOperation = it.next();
			achou = id.equals(retOperation.getId());
		}
		
		if (achou) return retOperation;
		else return null;		
	}

	public Operation findOperationByName(String name) {
		boolean achou = false;
		Operation retOperation = null;
		Iterator<Operation> it = operations.iterator();
		while((!achou) && (it.hasNext())) {
			retOperation = it.next();
			achou = name.toLowerCase().equals(retOperation.getName().toLowerCase());
		}
		
		if (achou) return retOperation;
		else return null;		
	}

	public Attribute findAttributeById(String id) {
		boolean achou = false;
		Attribute retAttribute = null;
		Iterator<Attribute> it = attributes.iterator();
		while((!achou) && (it.hasNext())) {
			retAttribute = it.next();
			achou = id.equals(retAttribute.getId());
		}
		
		if (achou) return retAttribute;
		else return null;		
	}

	public Attribute findAttributeByName(String name) {
		boolean achou = false;
		Attribute retAttribute = null;
		Iterator<Attribute> it = attributes.iterator();
		while((!achou) && (it.hasNext())) {
			retAttribute = it.next();
			achou = name.toLowerCase().equals(retAttribute.getName().toLowerCase());
		}
		
		if (achou) return retAttribute;
		else return null;		
	}

	public Concern findConcernById(String id) {
		boolean achou = false;
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			retConcern = it.next();
			achou = id.equals(retConcern.getId());
		}
		
		if (achou) return retConcern;
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
	
	/*
	 * boolean withoutSubconcern = do not consider the sub-concerns
	 */
	public List<Concern> findConcernWhoseNameStartsWith(String name) {
		List<Concern> lstConcerns = new ArrayList<Concern>();
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while(it.hasNext()) {
			retConcern = it.next();
			if (retConcern.getName().toLowerCase().startsWith(name.toLowerCase())) {
				lstConcerns.add(retConcern);
			}
		}		
		return lstConcerns;		
	}

	public int countConcerns() {
		int ret = 0;
		Iterator<Concern> it = concerns.iterator();
		while(it.hasNext()) {
			Concern retConcern = it.next();
			if (!retConcern.getName().contains("Sub_")) {
				ret++;
			}
		}		
		return ret;		
	}

	public void removeAllAttributeWhoseIdContains(String id) {
		Iterator<Attribute> it = attributes.iterator();
		while(it.hasNext()) {
			Attribute retAttribute = it.next();
			if (retAttribute.getId().toLowerCase().contains(id.toLowerCase())) {
				it.remove();
			}
		}		
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
	
	
	public void updateListOfConcerns(boolean removeClassConcern) {
		Iterator<Concern> it = concerns.iterator();
		while(it.hasNext()) {
			Concern concern = it.next();
			if ((!removeClassConcern) && (concern.isForClassName())) continue;
			Iterator<Attribute> itAttr = attributes.iterator();
			boolean achouAttr = false;
			while((!achouAttr) && (itAttr.hasNext())) {
				Attribute attr = itAttr.next();
				if (attr.findConcernById(concern.getId()) != null) achouAttr = true;
			}
			if (achouAttr == false) {
				Iterator<Operation> itOp = operations.iterator();
				boolean achouOp = false;
				while((!achouOp) && (itOp.hasNext())) {
						Operation op = itOp.next();
						if (op.findConcernById(concern.getId()) != null) achouOp = true;
				}
				if (achouOp == false) it.remove();
			}			
		}
	}
	

	public boolean hasConcern() {
		return (!concerns.isEmpty());
	}
	
	public String toString() {
		return (getName());
	}	
	
	public List<Class> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public void setImplementedInterfaces(List<Class> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}
	
	public void addInterface(Class ci) {
		implementedInterfaces.add(ci);
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	
}
 
