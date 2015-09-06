package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Project { 
	private String name;	 
	private String path;
	private List<Package> packages;
	private List<CrossCuttingConcern> cc;
	private List<Type> types;
	private List<Modifier> modifiers;
	private List<Concern> concerns;
	private List<Association> associations;
	private List<Dependency> dependencies;
	private List<ExistingConcern> existingConcerns;
	
	public Project(String name, String path) {
		this.name = name;
		this.path = path;
		this.packages = new ArrayList<Package>(); 
		this.cc = new ArrayList<CrossCuttingConcern>(); 
		this.types = new ArrayList<Type>();
		this.modifiers = new ArrayList<Modifier>();
		this.concerns = new ArrayList<Concern>();
		this.associations = new ArrayList<Association>();
		this.dependencies = new ArrayList<Dependency>();
		this.existingConcerns = new ArrayList<ExistingConcern>();
		initializeBaseTypes();		
		initializeBaseModifiers();		
		initializeBaseConcerns();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<CrossCuttingConcern> getCrossCuttingConcern() {
		return cc;
	}

	public void setCrossCuttingConcern(List<CrossCuttingConcern> cc) {
		this.cc = cc;
	}

	public List<Class> getClasses() {
		List<Class> lsClasses = new ArrayList<Class>();
		Iterator<Package> itPackage = packages.iterator();
		while(itPackage.hasNext()) {
			Package pckAux = itPackage.next();
			lsClasses.addAll(pckAux.getClasses());
		}
		Iterator<CrossCuttingConcern> itCC = cc.iterator();
		while(itCC.hasNext()) {
			CrossCuttingConcern ccAux = itCC.next();
			lsClasses.addAll(ccAux.getClasses());
		}
		return lsClasses;
	}
	
	public void removeClassById(String idClass) {
		boolean achou = false;
		Iterator<Package> itPackage = packages.iterator();
		while((itPackage.hasNext()) && (!achou)) {
			Package pckAux = itPackage.next();
			if (pckAux.findClassById(idClass) != null) {
				pckAux.removeClassById(idClass);
				System.out.println("aquiiiii");
				achou = true;
			}
		}
		Iterator<CrossCuttingConcern> itCC = cc.iterator();
		while((itCC.hasNext()) && (!achou)) {
			CrossCuttingConcern ccAux = itCC.next();
			if (ccAux.findClassById(idClass) != null) {
				ccAux.removeClassById(idClass);
				achou = true;
			}
		}
	}

	
	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public List<Concern> getConcerns() {
		return concerns;
	}

	public void setConcerns(List<Concern> concerns) {
		this.concerns = concerns;
	}

	public void addPackage(Package pck) {
		packages.add(pck);
	}	

	public Package getPackage(int index) {
		return packages.get(index);
	}
	
	public void removePackage(int index) {
		packages.remove(index);
	}
	
	public void addExistingConcern(ExistingConcern ec) {
		this.existingConcerns.add(ec);
	}	
	
	public List<ExistingConcern> getExistingConcerns() {
		return existingConcerns;
	}

	public void setExistingConcerns(List<ExistingConcern> existingConcerns) {
		this.existingConcerns = existingConcerns;
	}

	public void addCrossCuttingConcern(CrossCuttingConcern cc) {
		this.cc.add(cc);
	}	

	public CrossCuttingConcern getCrossCuttingConcern(int index) {
		return cc.get(index);
	}
	
	public void removeCrossCuttingConcern(int index) {
		cc.remove(index);
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public void addType(Type type) {
		types.add(type);
	}	

	public Type getType(int index) {
		return types.get(index);
	}
	
	public void removeType(int index) {
		types.remove(index);
	}

	public void updateType(Type type) {
		boolean achou = false;
		Iterator<Type> it = types.iterator();
		while((!achou) && (it.hasNext())) {
			Type t = it.next();
			achou = t.getId().equals(type.getId());
			if (achou) it.remove();
		}
		if (achou) {
			addType(type);
		}

	}
	
	public ExistingConcern isEConcernExist(String idEConcern){
		boolean achou = false;
		ExistingConcern ret = null;
		Iterator<ExistingConcern> it = existingConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			ExistingConcern eConcern = it.next();
			if (eConcern.getName().equals(idEConcern)) {
				achou = true;
				ret = eConcern;
			}
		}
		return ret;
	}	

	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}	

	public Modifier getModifier(int index) {
		return modifiers.get(index);
	}
	
	public void removeModifier(int index) {
		modifiers.remove(index);
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

	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}


	public void addAssociation(Association association) {
		associations.add(association);
	}	

	public Association getAssociation(int index) {
		return associations.get(index);
	}
	
	public void removeAssociation(int index) {
		associations.remove(index);
	}
	
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}


	public void addDependency(Dependency dependency) {
		dependencies.add(dependency);
	}	

	public Dependency getDependency(int index) {
		return dependencies.get(index);
	}
	
	public void removeDependency(int index) {
		dependencies.remove(index);
	}

	private void initializeBaseTypes() {
		this.types.add(new Type("int", "int"));
		this.types.add(new Type("float", "float"));
		this.types.add(new Type("double", "double"));
		this.types.add(new Type("char", "char"));
		this.types.add(new Type("boolean", "boolean"));
		this.types.add(new Type("byte", "byte"));
		this.types.add(new Type("long", "long"));
		this.types.add(new Type("short", "short"));
		this.types.add(new Type("void", "void"));
		this.types.add(new Type("String", "String"));
		this.types.add(new Type("Boolean", "Boolean"));
		this.types.add(new Type("Byte", "Byte"));
		this.types.add(new Type("Character", "Character"));
		this.types.add(new Type("Double", "Double"));
		this.types.add(new Type("Float", "Float"));
		this.types.add(new Type("Integer", "Integer"));
		this.types.add(new Type("Long", "Long"));
		this.types.add(new Type("Short", "Short"));
		this.types.add(new Type("Object", "Object"));
		this.types.add(new Type("Throwable", "Throwable"));
		this.types.add(new Type("Thread", "Thread"));
		this.types.add(new Type("Exception", "Exception"));
		this.types.add(new Type("Class", "Class"));
		this.types.add(new Type("Date", "Date"));
/*		this.types.add(new Type("Math", "Math"));
		this.types.add(new Type("Number", "Number"));
		this.types.add(new Type("StringBuffer", "StringBuffer"));
		this.types.add(new Type("System", "System"));
		this.types.add(new Type("ArrayList", "ArrayList"));
		this.types.add(new Type("Arrays", "Arrays"));
		this.types.add(new Type("BitSet", "BitSet"));
		this.types.add(new Type("Calendar", "Calendar"));
		this.types.add(new Type("Collections", "Collections"));
		this.types.add(new Type("Currency", "Currency"));
		this.types.add(new Type("Dictionary", "Dictionary"));
		this.types.add(new Type("EventListenerProxy", "EventListenerProxy"));
		this.types.add(new Type("EventObject", "EventObject"));
		this.types.add(new Type("HashMap", "HashMap"));
		this.types.add(new Type("HashSet", "HashSet"));
		this.types.add(new Type("Hashtable", "Hashtable"));
		this.types.add(new Type("IdentityHashMap", "IdentityHashMap"));
		this.types.add(new Type("LinkedHashMap", "LinkedHashMap"));
		this.types.add(new Type("LinkedHashSet", "LinkedHashSet"));
		this.types.add(new Type("LinkedList", "LinkedList"));
		this.types.add(new Type("Observable", "Observable"));
		this.types.add(new Type("Stack", "Stack"));
		this.types.add(new Type("StringTokenizer", "StringTokenizer"));
		this.types.add(new Type("TreeMap", "TreeMap"));
		this.types.add(new Type("TreeSet", "TreeSet"));
		this.types.add(new Type("Vector", "Vector"));
		this.types.add(new Type("WeakHashMap", "WeakHashMap"));*/
	}

	private void initializeBaseModifiers() {
		this.modifiers.add(new Modifier("public"));
		this.modifiers.add(new Modifier("private"));
		this.modifiers.add(new Modifier("protected"));
		this.modifiers.add(new Modifier("abstract"));
		this.modifiers.add(new Modifier("final"));
		this.modifiers.add(new Modifier("static"));
	}

	private void initializeBaseConcerns() {
		/*this.concerns.add(new Concern("database", "DatabasePersistence"));
		this.concerns.add(new Concern("logging", "Logging"));
		this.concerns.add(new Concern("buffering", "Buffering"));
		this.concerns.add(new Concern("security", "Security"));*/
	}

	public Package findPackageByName(String name) {
		boolean achou = false;
		Package retPackage = null;
		Iterator<Package> it = packages.iterator();
		while((!achou) && (it.hasNext())) {
			retPackage = it.next();
			achou = name.toLowerCase().equals(retPackage.getName().toLowerCase());
		}
		
		if (achou) return retPackage;
		else return null;
	}

	public CrossCuttingConcern findCrossCuttingConcernByName(String name) {
		boolean achou = false;
		CrossCuttingConcern retCrossCuttingConcern = null;
		Iterator<CrossCuttingConcern> it = cc.iterator();
		while((!achou) && (it.hasNext())) {
			retCrossCuttingConcern = it.next();
			achou = name.toLowerCase().equals(retCrossCuttingConcern.getName().toLowerCase());
		}
		
		if (achou) return retCrossCuttingConcern;
		else return null;
	}

	public Type findTypeById(String id) {
		boolean achou = false;
		Type retType = null;
		Iterator<Type> it = types.iterator();
		while((!achou) && (it.hasNext())) {
			retType = it.next();
			achou = id.equals(retType.getId());
		}
		
		if (achou) return new Type(retType);
		else return null;		
	}
	
	public Type findTypeByName(String name) {
		boolean achou = false;
		Type retType = null;
		Iterator<Type> it = types.iterator();
		while((!achou) && (it.hasNext())) {
			retType = it.next();
			achou = name.toLowerCase().equals(retType.getName().toLowerCase());
		}
		
		if (achou) return new Type(retType); 
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

	public Concern findConcernById(String id) {
		boolean achou = false;
		Concern retConcern = null;
		Iterator<Concern> it = concerns.iterator();
		while((!achou) && (it.hasNext())) {
			retConcern = it.next();
			achou = id.equalsIgnoreCase(retConcern.getId()) ;
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
	
	public boolean isAssociationExist(String idClass1, String idClass2){
		boolean achou = false;
		Iterator<Association> it = associations.iterator();
		String auxassoc1 = idClass1 + "." + idClass2;
		String auxassoc2 = idClass2 + "." + idClass1;
		while((!achou) && (it.hasNext())) {
			Association association = it.next();
			if (auxassoc1.equals(association.getId()) || auxassoc2.equals(association.getId())){
				achou = true;
			}
		}
		return achou;
	}

	public void removeAssociationByClassId(String idClass){
		boolean achou = false;
		Iterator<Association> it = associations.iterator();
		while((!achou) && (it.hasNext())) {
			Association association = it.next();
			if (association.getId().toLowerCase().contains(idClass.toLowerCase())){
				it.remove();
			}
		}
	}

	public void removeAssociationByClassId(String idClass1, String idClass2){
		boolean achou = false;
		String id1 = idClass1+"."+idClass2;
		String id2 = idClass2+"."+idClass1;
		Iterator<Association> it = associations.iterator();
		while((!achou) && (it.hasNext())) {
			Association association = it.next();
			if ((association.getId().equalsIgnoreCase(id1)) || (association.getId().equalsIgnoreCase(id2))) {
				it.remove();
			}
		}
	}
	
	public String toString() {
		return (getName());
	}
	
	public Class findClassById(String id) {
		boolean achou = false;
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		Class retClass = null;
		while ((!achou) && (it.hasNext())) {
			Package pck = it.next();
			retClass = pck.findClassById(id);
			if (retClass != null) achou = true;
		}
		if (achou) return retClass; 
		else return null;		
	}
	 
	public Class findClassByName(String name) {
		boolean achou = false;
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		Class retClass = null;
		while ((!achou) && (it.hasNext())) {
			Package pck = it.next();
			retClass = pck.findClassByName(name);
			if (retClass != null) achou = true;
		}

		if (!achou) { 
			List<CrossCuttingConcern> lsCCs = getCrossCuttingConcern();
			Iterator<CrossCuttingConcern> itCC = lsCCs.iterator();
			while ((!achou) && (itCC.hasNext())) {
				CrossCuttingConcern cc = itCC.next();
				retClass = cc.findClassByName(name);
				if (retClass != null) achou = true;
			}
		}
		
		if (achou) return retClass; 
		else return null;		
	}

	public Aspect findAspectById(String id) {
		boolean achou = false;
		List<CrossCuttingConcern> cc = getCrossCuttingConcern();
		Iterator<CrossCuttingConcern> it = cc.iterator();
		Aspect retAspect = null;
		while ((!achou) && (it.hasNext())) {
			CrossCuttingConcern c = it.next();
			retAspect = c.findAspectById(id);
			if (retAspect != null) achou = true;
		}
		
		if (achou) return retAspect; 
		else return null;		
	}

	public CrossCuttingConcern findCrossCuttingConcernByAspectId(String id) {
		boolean achou = false;
		List<CrossCuttingConcern> cc = getCrossCuttingConcern();
		Iterator<CrossCuttingConcern> it = cc.iterator();
		CrossCuttingConcern c = null;
		while ((!achou) && (it.hasNext())) {
			c = it.next();
			Aspect retAspect = c.findAspectById(id);
			if (retAspect != null) achou = true;
		}
		
		if (achou) return c; 
		else return null;		
	}

	public Attribute findAttributeById(String id) {
		boolean achou = false;
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		Attribute retAttribute = null;
		while ((!achou) && (it.hasNext())) {
			Package pck = it.next();
			List<Class> lsClasses = pck.getClasses();
			Iterator<Class> itClass = lsClasses.iterator();
			while ((!achou) && (itClass.hasNext())) {
				Class cl = itClass.next();
				retAttribute =  cl.findAttributeById(id);
				if (retAttribute != null) achou = true;					
			}
		}
		
		if (achou) return retAttribute; 
		else return null;		
	}

	public Variable findVariableById(String namePck, String idClass, String idMethod, String idVar) {
		Variable var = null;
		Package pck = findPackageByName(namePck);
		if (pck != null) {
			Class cl = pck.findClassById(idClass);
			if (cl != null) {
				Operation op = cl.findOperationById(idMethod);
				if (op != null) {
					var = op.findVariableByName(idVar);
				}
			}
		}
		if (var != null) return var; 
		else return null;		
	}	
	
	public Operation findOperationById(String id) {
		boolean achou = false;
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		Operation retOperation = null;
		while ((!achou) && (it.hasNext())) {
			Package pck = it.next();
			List<Class> lsClasses = pck.getClasses();
			Iterator<Class> itClass = lsClasses.iterator();
			while ((!achou) && (itClass.hasNext())) {
				Class cl = itClass.next();
				retOperation = cl.findOperationById(id);
				if (retOperation != null) achou = true;					
			}
		}
		
		if (achou) return retOperation; 
		else return null;		
	}
	
	public List<Class> getClassesWithStereotypeId(String stereotypeId) {
		boolean achou = false;
		List<Class> lsClassesRet = new ArrayList<Class>();
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		while (it.hasNext()) {
			Package pck = it.next();
			List<Class> lsClasses = pck.getClasses();
			Iterator<Class> itClass = lsClasses.iterator();
			while (itClass.hasNext()) {
				Class cl = itClass.next();				
				if (cl.findConcernById(stereotypeId) != null) lsClassesRet.add(cl);
			}
		}		
		return lsClassesRet;
	}


	public List<Operation> getOperationsWithStereotypeId(String stereotypeId) {
		boolean achou = false;
		List<Operation> lsOperationsRet = new ArrayList<Operation>();
		List<Package> lsPackages = getPackages();
		Iterator<Package> it = lsPackages.iterator();
		while (it.hasNext()) {
			Package pck = it.next();
			List<Class> lsClasses = pck.getClasses();
			Iterator<Class> itClass = lsClasses.iterator();
			while (itClass.hasNext()) {
				Class cl = itClass.next();
				
				if (cl.findConcernById(stereotypeId) != null) {
				
					List<Operation> lsOperations = new ArrayList<Operation>(cl.getOperations());
					Iterator<Operation> itOperation = lsOperations.iterator();
					while (itOperation.hasNext()) {
						Operation op = itOperation.next();
						Concern c = op.findConcernById(stereotypeId);
						if (c != null) lsOperationsRet.add(op);
					}
				}
			}
		}		
		return lsOperationsRet;
	}
	
	
	public Dependency findDependencyById(String idClass1, String idClass2){
		boolean achou = false;
		String auxdep1 = idClass1 + "." + idClass2;
		String auxdep2 = idClass2 + "." + idClass1;		
		Dependency dependency = null;
		Iterator<Dependency> it = dependencies.iterator();

		while((!achou) && (it.hasNext())) {
			dependency = it.next();
			if ((auxdep1.equals(dependency.getId())) || (auxdep2.equals(dependency.getId()))) {
				achou = true;
			}
		}
		
		if (achou) return dependency;
		else return null;			
	}
	
	public void removeDependencyById(String idDependency){
		boolean achou = false;
		Iterator<Dependency> it = dependencies.iterator();
		while((!achou) && (it.hasNext())) {
			Dependency dependency = it.next();
			if (idDependency.equals(dependency.getId())) {
				it.remove();
				achou = true;
			}
		}
	}	
}
 
