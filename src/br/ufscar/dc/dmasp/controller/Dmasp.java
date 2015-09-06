package br.ufscar.dc.dmasp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WildcardType;
import org.osgi.framework.Bundle;

import br.ufscar.dc.dmasp.model.Advice;
import br.ufscar.dc.dmasp.model.Aspect;
import br.ufscar.dc.dmasp.model.Association;
import br.ufscar.dc.dmasp.model.Attribute;
import br.ufscar.dc.dmasp.model.Class;
import br.ufscar.dc.dmasp.model.CompositionPointcut;
import br.ufscar.dc.dmasp.model.Concern;
import br.ufscar.dc.dmasp.model.CrossCuttingConcern;
import br.ufscar.dc.dmasp.model.DeclareParents;
import br.ufscar.dc.dmasp.model.Dependency;
import br.ufscar.dc.dmasp.model.ExistingConcern;
import br.ufscar.dc.dmasp.model.IntroductionAttribute;
import br.ufscar.dc.dmasp.model.IntroductionMethod;
import br.ufscar.dc.dmasp.model.Modifier;
import br.ufscar.dc.dmasp.model.Operation;
import br.ufscar.dc.dmasp.model.Operator;
import br.ufscar.dc.dmasp.model.Package;
import br.ufscar.dc.dmasp.model.Parameter;
import br.ufscar.dc.dmasp.model.Pendency;
import br.ufscar.dc.dmasp.model.PendencyAttribute;
import br.ufscar.dc.dmasp.model.PendencyCalledOperation;
import br.ufscar.dc.dmasp.model.PendencyClass;
import br.ufscar.dc.dmasp.model.PendencyImplements;
import br.ufscar.dc.dmasp.model.PendencyInheritance;
import br.ufscar.dc.dmasp.model.PendencyOperation;
import br.ufscar.dc.dmasp.model.PendencyVariable;
import br.ufscar.dc.dmasp.model.Pointcut;
import br.ufscar.dc.dmasp.model.Project;
import br.ufscar.dc.dmasp.model.TargetPointcut;
import br.ufscar.dc.dmasp.model.Term;
import br.ufscar.dc.dmasp.model.Type;
import br.ufscar.dc.dmasp.model.Variable;
import br.ufscar.dc.rejasp.indication.model.Indication;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Dmasp {

	private Project project;
	private String diagramTemplateXSLT;
	private String modelXML;
	private String diagramXMIFile;
	private String packageTemplateXSLT;
	private String packageXMIFile;
	private String judeStandard;
	private String stereotypedModel;
	private XStream stream;
	private TransformerFactory transformerFactory;
	private List<CompilationUnit> units;
	private List<Pendency> pendencies;
	private boolean useSameStructure = false;
	public static int PRIMARYCC = 0;
	public static int SECONDARYCC = 1;
	
	public boolean isUseSameStructure() {
		return useSameStructure;
	}
	
	public void setUseSameStructure(boolean useSameStructure) {
		this.useSameStructure = useSameStructure;
	}	
	
	public Dmasp() {
		this.diagramTemplateXSLT = "";
		this.diagramXMIFile = "";
		this.packageTemplateXSLT = "";
		this.packageXMIFile = "";
		this.modelXML = "";
		this.judeStandard = "";
		this.stereotypedModel = "";
		this.project = null;
		this.stream = new XStream(new DomDriver());
		this.transformerFactory = TransformerFactory.newInstance();
		this.units = new ArrayList<CompilationUnit>();
		this.pendencies = new ArrayList<Pendency>();
	}
	
	private void createAliases() {
		stream.alias("project", Project.class);
		stream.alias("association", Association.class);
		stream.alias("dependency", Dependency.class);
		stream.alias("package", Package.class);
		stream.alias("class", Class.class);
		stream.alias("attribute", Variable.class);
		stream.alias("operation", Operation.class);
		stream.alias("parameter", Parameter.class);
		stream.alias("concern", Concern.class);
		stream.alias("modifier", Modifier.class);
		stream.alias("type", Type.class);
		stream.alias("aspect", Aspect.class);
		stream.alias("crosscuttingconcern", CrossCuttingConcern.class);
		stream.alias("pointcut", Pointcut.class);	
		stream.alias("advice", Advice.class);
		stream.alias("declareparents", DeclareParents.class);
		stream.alias("compositionpointcut", CompositionPointcut.class);
		stream.alias("term", Term.class);
		stream.alias("pointcut", Operator.class);
		stream.alias("pointcut", TargetPointcut.class);
		stream.alias("introductionAttribute", IntroductionAttribute.class);
		stream.alias("introductionMethod", IntroductionMethod.class);
		stream.alias("existingConcern", ExistingConcern.class);
	}
	
	private String getInstallLocation() throws Exception {
		Bundle bundle = Platform.getBundle("br.ufscar.dc.rejasp");;
		URL locationUrl = FileLocator.find(bundle,new Path("/"), null);
		URL fileUrl =FileLocator.toFileURL(locationUrl);
		return fileUrl.getFile();
	}
	
	public void initializeProject(String projectName, String projectLocation) {
		try {
			String sLocation = getInstallLocation();		
			sLocation = sLocation + "/dmasp/";
			String projectDirectory = projectLocation + "/xmi/";
			
			// Create project directory begin
			File fF = new File(projectDirectory);
			fF.mkdir();
			// Create project directory end
			
			this.modelXML = projectDirectory + "XMLModel.xml";
			this.stereotypedModel = projectDirectory + "stereotypedModel.xmi";
			
			this.diagramTemplateXSLT = sLocation + "diagram-template-xslt-jude.xsl";
			this.diagramXMIFile = sLocation + "diagramXMIFileJude.xmi";
			this.packageTemplateXSLT = sLocation + "package-template-xslt-jude.xsl";
			this.packageXMIFile = sLocation + "packageXMIFileJude.xmi";
			this.judeStandard = sLocation + "judeStandard.xmi";
			
			this.project = new Project(projectName, projectLocation);	
			
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void reinitializeProject(String projectName, String projectLocation) {
		project = new Project(projectName, projectLocation);
		units.clear();
		setUseSameStructure(false);
	}

	public void ObjectToXML() {
		createAliases();
		try {
			stream.toXML(project, new FileOutputStream(modelXML));
			Transformer transformer1 = transformerFactory
					.newTransformer(new StreamSource(diagramTemplateXSLT));
			transformer1.transform(new StreamSource(modelXML),
					new StreamResult(new FileOutputStream(diagramXMIFile, false)));

			stream.toXML(project, new FileOutputStream(modelXML));
			Transformer transformer2 = transformerFactory
					.newTransformer(new StreamSource(packageTemplateXSLT));
			transformer2.transform(new StreamSource(modelXML),
					new StreamResult(new FileOutputStream(packageXMIFile, false)));
			
			createXMLFile();

		} catch (Exception ex) {
			System.out.println("Ocorreu um erro ao gerar o arquivo XML.");
		}
	}
	
	public void createXMLFile(){
		BufferedReader inputFile;
		BufferedWriter outputFile;
		String line;
		
		try{			
			try {
		        // Create channel on the source
		        FileChannel srcChannel = new FileInputStream(judeStandard).getChannel();
		    
		        // Create channel on the destination
		        FileChannel dstChannel = new FileOutputStream(stereotypedModel).getChannel();
		    
		        // Copy file contents from source to destination
		        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
		    
		        // Close the channels
		        srcChannel.close();
		        dstChannel.close();
		    } catch (IOException e) {
				System.out.println("Ocorreu um erro ao copiar o arquivo.");
		    }
			
			outputFile = new BufferedWriter(new FileWriter(stereotypedModel,true));
			inputFile = new BufferedReader(new FileReader(packageXMIFile));
			
			line = inputFile.readLine();
			line = inputFile.readLine();
			while ((line.substring(0, 3)).equals("<ns") && line != null){
				line = inputFile.readLine();
			}

			while (line != null){
				outputFile.write(line);
				outputFile.newLine();
				line = inputFile.readLine();
			}
			
			inputFile.close();
			
			outputFile.write("          </UML:Namespace.ownedElement>");
			outputFile.newLine();
			outputFile.write("        </UML:Package>");
			outputFile.newLine();
			
			inputFile = new BufferedReader(new FileReader(diagramXMIFile));
			
			line = inputFile.readLine();
			line = inputFile.readLine();
			while ((line.substring(0, 3)).equals("<ns") && line != null){
				line = inputFile.readLine();
			}
			
			boolean sair = false;
			while (line != null && !sair){
				if (!(line.substring(0, 17)).equals("    <UML:Primitive")){
					outputFile.write("      </UML:Namespace.ownedElement>");
					outputFile.newLine();
					outputFile.write("    </UML:Model>");
					outputFile.newLine();
					outputFile.write(line);
					outputFile.newLine();
					sair = true;
				} else {
					outputFile.write(line);
					outputFile.newLine();
					line = inputFile.readLine();
				}
			}
			
			if (sair) {
				line = inputFile.readLine();
				while (line != null){
					outputFile.write(line);
					outputFile.newLine();
					line = inputFile.readLine();
				}
			}
			inputFile.close();

			outputFile.write("  </XMI.content>");
			outputFile.newLine();
			outputFile.write("</XMI>");
			outputFile.newLine();					

			outputFile.close();

		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao gerar o arquivo.");
		}
	}

	public void XMLToObject() {
		createAliases();
		try {
			File fileXmlIn = new File(modelXML);
			if (fileXmlIn.exists()) {
				project = (Project) stream.fromXML(new FileInputStream(
						modelXML));
			} else {
				System.out
						.println("Arquivo \"" + modelXML + "\" inexistente.");
			}
		} catch (Exception ex) {
			System.out.println("Ocorreu um erro ao gerar o arquivo XML.");
		}
	}

	public void addUnit(CompilationUnit unit) {
		units.add(unit);
	}
	
	public void createStructure() {
		if (!isUseSameStructure()){
			Iterator<CompilationUnit> itUnit = units.iterator();
			while (itUnit.hasNext()) {
				CompilationUnit unit = itUnit.next();
				createTypes(unit);
			}

			itUnit = units.iterator();
			while (itUnit.hasNext()) {
				CompilationUnit unit = itUnit.next();
				PackageDeclaration pckDecl = unit.getPackage();
				List<TypeDeclaration> lsTypeDecl = unit.types();
				createAttributesAndMethods(pckDecl, lsTypeDecl);
			}
			//setUseSameStructure(true);
		} 
	}
	
	private void createTypes(CompilationUnit unit) {
		PackageDeclaration pckDecl = unit.getPackage();
		List<TypeDeclaration> lsTypeDecl = unit.types();
		List<ImportDeclaration> lsImportDecl = unit.imports();

		// Generating list of types.
		Iterator<ImportDeclaration> itTypes = lsImportDecl.iterator();
		while (itTypes.hasNext()) {
			ImportDeclaration importDecl = itTypes.next();
			createImports(importDecl);
		}

		// Generating packages.
		createPackage(pckDecl);

		// Generating classes.
		Iterator itClasses = lsTypeDecl.iterator();
		while (itClasses.hasNext()) {
			Object obj = itClasses.next(); 
			if (obj instanceof TypeDeclaration) { 
				TypeDeclaration typeDecl = (TypeDeclaration) obj;
				createClass(pckDecl, typeDecl);
			}
		}
		
	}
	
	private void createAttributesAndMethods(PackageDeclaration pckDecl, List<TypeDeclaration> lsTypeDecl) {	
		// Generating Attributes and Methods
		Iterator itClasses = lsTypeDecl.iterator();
		
		while (itClasses.hasNext()) {
			Object obj = itClasses.next();
			if (obj instanceof TypeDeclaration) {
				TypeDeclaration typeDecl = (TypeDeclaration) obj;
				FieldDeclaration fields[] = typeDecl.getFields();
				MethodDeclaration methods[] = typeDecl.getMethods();


				int fieldsSize = fields.length;
				int methodsSize = methods.length;

				// Generating attributes.
				for (int i = 0; i < fieldsSize; i++) {
					FieldDeclaration fieldDecl = fields[i];
					createAttribute(pckDecl, typeDecl, fieldDecl);
				}

				// Generating attributes.
				for (int i = 0; i < methodsSize; i++) {
					MethodDeclaration methodDecl = methods[i];
					createOperation(pckDecl, typeDecl, methodDecl);
				}
			}
		}
	}
	
	private void createImports(ImportDeclaration importDecl) {
		String importName = importDecl.getName().toString();
		int separadorClassePacote = importName.lastIndexOf(".");
		String className = importName.substring(separadorClassePacote + 1, importName.length());		
		
		Type type = new Type(importName, className);
		type.setPrimitive(false);
		if (project.findTypeById(type.getId()) == null) {
			project.addType(type);
		}	
	}
	
	private void createImports(String importName) {
		int separadorClassePacote = importName.lastIndexOf(".");
		String className = importName.substring(separadorClassePacote + 1, importName.length());
		String idImport = importName + "." + className;
		
		Type type = new Type(idImport, importName);
		type.setPrimitive(false);
		if (project.findTypeById(type.getId()) == null) {
			project.addType(type);
		}	
	}

	private void createPackage(PackageDeclaration pckDecl) {
		String id = pckDecl.getName().getFullyQualifiedName();
		Package pck = new Package(id);
		project.addPackage(pck);		
	}

	public void createClass(PackageDeclaration pckDecl, TypeDeclaration typeDecl) {
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String idClass = namePck + "." + nameClass;
		Package pck = project.findPackageByName(namePck);
		Class cl = new Class(idClass, nameClass);

		// Add modifiers
		List lsModifiers = typeDecl.modifiers();
		Iterator it = lsModifiers.iterator();
		while (it.hasNext()) {
			Object aux = it.next();
			if (aux instanceof org.eclipse.jdt.core.dom.Modifier) {
				org.eclipse.jdt.core.dom.Modifier modif = (org.eclipse.jdt.core.dom.Modifier) aux;
				Modifier modifierModel = new Modifier(modif.getKeyword().toString());
				cl.addModifier(modifierModel);				
			}
		}
		
		if (typeDecl.isInterface()) cl.setInterface(true);
		pck.addClass(cl);

		// Add type, if necessary.
		Type type = project.findTypeById(cl.getId());
		if (type == null) {
			type = new Type(cl);
			type.setUser(true);
			type.setPrimitive(false);
			project.addType(type);
		} else {
			type.setUser(true);
			project.updateType(type);
		}
	}

	public void createOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl) {
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameMethod = methodDecl.getName().getFullyQualifiedName();
		String idClass = namePck + "." + nameClass;	
		String idMethod = namePck + "." + nameClass + "." + nameMethod;	
		String nameReturnType = getTypeName(methodDecl.getReturnType2());

		// Paulo 23/11
		if (nameReturnType.indexOf('.') > 0) {
			createImports(nameReturnType);
		}	
		// Paulo 23/11		
		
		
		Package pck = project.findPackageByName(namePck);
		Class cl = pck.findClassById(idClass);		
		Operation operation = new Operation("", nameMethod, new Type("", ""));

		// Find type.
		Type type = project.findTypeByName(nameReturnType);
		if (type != null) {
			operation = new Operation("", nameMethod, type);
		} 

		// Add modifiers
		List lsModifiers = methodDecl.modifiers();
		Iterator it = lsModifiers.iterator();
		while (it.hasNext()) {
			Object aux = it.next();
			if (aux instanceof org.eclipse.jdt.core.dom.Modifier) {
				org.eclipse.jdt.core.dom.Modifier modif = (org.eclipse.jdt.core.dom.Modifier) aux;
				Modifier modifierModel = new Modifier(modif.getKeyword().toString());
				operation.addModifier(modifierModel);				
			}
		}

		// Add parameters
		List<SingleVariableDeclaration> lsParameters = methodDecl.parameters();
		
		Iterator<SingleVariableDeclaration> itParameter = lsParameters.iterator();
		
		while (itParameter.hasNext()) {
			SingleVariableDeclaration parameter = itParameter.next();
			String nameTypeParameter = getTypeName(parameter.getType());			

			// Paulo 23/11
			if (nameTypeParameter.indexOf('.') > 0) {
				createImports(nameTypeParameter);
			}	
			// Paulo 23/11		
			
			idMethod = idMethod + "." + nameTypeParameter;
		}
		
		itParameter = lsParameters.iterator();
		while (itParameter.hasNext()) {
			SingleVariableDeclaration parameter = itParameter.next();
			String nameParameter = parameter.getName().getFullyQualifiedName();
			String idParameter = idMethod + "." + nameParameter;
			String nameTypeParameter = getTypeName(parameter.getType());			
			
			Parameter parameterModel = new Parameter(idParameter, nameParameter, new Type("", ""));
			
			// Find type.
			Type typeParameter = project.findTypeByName(nameTypeParameter);
			
			if (typeParameter != null) {
				parameterModel = new Parameter(idParameter, nameParameter, typeParameter);
			} 
			
			operation.addParameter(parameterModel);			
		}
		
		operation.setId(idMethod);
		cl.addOperation(operation);
	}

	public void createAttribute(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, FieldDeclaration fldDecl) {
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameAttr = ((VariableDeclarationFragment) fldDecl.fragments()
				.get(0)).getName().getFullyQualifiedName();
		String idClass = namePck + "." + nameClass;
		String nameType = getTypeName(fldDecl.getType());
	
		// Paulo 23/11
		if (nameType.indexOf('.') > 0) {
			createImports(nameType);
		}	
		// Paulo 23/11		

		String idAttr = namePck + "." + nameClass + "." + nameType + "." + nameAttr;
		Package pck = project.findPackageByName(namePck);
		Class cl = pck.findClassById(idClass);
		Attribute attr = new Attribute(idAttr, nameAttr, new Type("", ""));

		// Find type.
		Type type = project.findTypeByName(nameType);
		if (type != null) {
			attr = new Attribute(idAttr, nameAttr, type);
		} 

		// Add modifiers
		List lsModifiers = fldDecl.modifiers();
		Iterator it = lsModifiers.iterator();
		while (it.hasNext()) {
			Object aux = it.next();
			if (aux instanceof org.eclipse.jdt.core.dom.Modifier) {
				org.eclipse.jdt.core.dom.Modifier modif = (org.eclipse.jdt.core.dom.Modifier) aux;
				Modifier modifierModel = new Modifier(modif.getKeyword().toString());
				attr.addModifier(modifierModel);				
			}
		}
		cl.addAttribute(attr);
	}
	
	public void createAssociation() {
		if (!isUseSameStructure()) {
			List<Package> lsPackages = project.getPackages();
			Iterator<Package> it = lsPackages.iterator();
			while (it.hasNext()) {
				Package pck = it.next();
				List<Class> lsClasses = pck.getClasses();
				Iterator<Class> itClass = lsClasses.iterator();
				while (itClass.hasNext()) {
					Class cl = itClass.next();
					List<Attribute> lsAttributes = cl.getAttributes();
					Iterator<Attribute> itAttribute = lsAttributes.iterator();
					while (itAttribute.hasNext()) {
						Attribute attr = itAttribute.next();
						if ((attr.getType().isUser())
								&& (!project.isAssociationExist(cl.getId(),
										attr.getType().getId()))) {
							String idClass1 = attr.getType().getId();
							String idClass2 = cl.getId();
							String idAssociation = idClass1 + "." + idClass2;
							Association assoc = new Association(idAssociation,
									idClass1, idClass2);
							project.addAssociation(assoc);
						}
					}
				}
			}
			//setUseSameStructure(true);
		}
	}
	

	public void createDependency(String idClassDecl, String idClassCalled, List<Concern> lsConcern) {
		String idDependency = idClassDecl + "." + idClassCalled;
		Type typeClassCalled = project.findTypeById(idClassCalled);

		if ((typeClassCalled != null) && (!idClassDecl.equals(idClassCalled)) && (typeClassCalled.isUser()) && (!project.isAssociationExist(idClassDecl, idClassCalled))) {			
			boolean isNew = false;
			Dependency dep = project.findDependencyById(idClassDecl, idClassCalled); 
			if (dep == null) {
				isNew = true;
				dep = new Dependency(idDependency, idClassDecl, idClassCalled);
			}

			if (lsConcern != null) {
				Iterator<Concern> it = lsConcern.iterator();
				while(it.hasNext()) {
					Concern concern = it.next();
					if (concern.isPrimary()) {
						Concern newConcern = new Concern(concern);
						if (!dep.isConcernExist(newConcern.getId())) {
							dep.addConcern(newConcern);						
						}
					}
				}
			}

			if ((isNew) && (!dep.getConcerns().isEmpty())) {
				project.addDependency(dep);
			}
		}
	}
	
	public void cleanConcerns(Indication indication) {
		String idConcern = "";
		String nameConcern = "";
		List<Concern> setConcerns = this.getProject().getConcerns();
		Boolean achou = false;
		Concern concern;
		
		Iterator<Concern> it = setConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			concern = it.next();
			if ((indication.getName().replaceAll(" ", "")).equals(concern.getName())) {
				idConcern = concern.getName();
				nameConcern = concern.getName();
				achou = true;
			}
		}

		concern = project.findConcernById(idConcern);
		if (concern == null) {
			concern = new Concern(idConcern, nameConcern);
		}
		
		Iterator<Pendency> itPendencies = pendencies.iterator();
		while(itPendencies.hasNext()) {
			Pendency pendency = itPendencies.next();
			Concern aux = pendency.getConcern();			
			if (aux != null) {
				if (aux.getId().equals(idConcern)) { itPendencies.remove(); }
			}
		}
				
	}
	
	public void createPendencyAttribute(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, FieldDeclaration fldDecl, Indication indication, int indicationType) {
		
		String idConcern = "";
		String nameConcern = "";
		List<Concern> setConcerns = this.getProject().getConcerns();
		Boolean achou = false;
		Concern concern;
		
		Iterator<Concern> it = setConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			concern = it.next();
			if ((indication.getName().replaceAll(" ", "")).equals(concern.getName())) {
				idConcern = concern.getName();
				nameConcern = concern.getName();
				achou = true;
			}
		}

		concern = project.findConcernById(idConcern);
		if (concern == null) {
			concern = new Concern(idConcern, nameConcern);
		}		

		// Paulo 16/11/10
		if (indicationType == PRIMARYCC) {			
			String cn = concern.getName();
			cn = "Pri_" + cn;
			concern.setName(cn);
		} else {
			String cn = concern.getName();
			cn = "Sec_" + cn;
			concern.setName(cn);
		}		
		// Paulo 16/11/10

		
		PendencyAttribute pendencyAttr = new PendencyAttribute(pckDecl, typeDecl, fldDecl, concern);
		pendencies.add(pendencyAttr);
	}
	
	public void createPendencyOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, Indication indication, int indicationType) {
		
		String idConcern = "";
		String nameConcern = "";
		List<Concern> setConcerns = this.getProject().getConcerns();
		Boolean achou = false;
		Concern concern;

		Iterator<Concern> it = setConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			concern = it.next();
			if ((indication.getName().replaceAll(" ", "")).equals(concern.getName())) {
				idConcern = concern.getName();
				nameConcern = concern.getName();
				achou = true;
			}
		}

		concern = project.findConcernById(idConcern);
		if (concern == null) {
			concern = new Concern(idConcern, idConcern);
		}		

		// Paulo 16/11/10	
		if (indicationType == PRIMARYCC) {			
			String cn = concern.getName();
			cn = "Pri_" + cn;
			concern.setName(cn);
		} else {
			String cn = concern.getName();
			String cid = concern.getId();
			cn = "Sec_" + cn;
			concern.setName(cn);
		}		
		// Paulo 16/11/10

		PendencyOperation pendencyOperation = new PendencyOperation(pckDecl, typeDecl, methodDecl, concern);
		pendencies.add(pendencyOperation);		
	}

	public void createPendencyCalledOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, ITypeBinding itype, IMethodBinding imethod) {
		PendencyCalledOperation pendencyCalledOperation = new PendencyCalledOperation(pckDecl, typeDecl, methodDecl, itype, imethod);
		pendencies.add(pendencyCalledOperation);
	}

	public void createPendencyCalledOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, String methodName) {
		PendencyCalledOperation pendencyCalledOperation = new PendencyCalledOperation(pckDecl, typeDecl, methodDecl, methodName);
		pendencies.add(pendencyCalledOperation);
	}

	public void createPendencyInheritance(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, ITypeBinding itype) {
		PendencyInheritance pendencyInheritance = new PendencyInheritance(pckDecl, typeDecl, itype);
		pendencies.add(pendencyInheritance);
	}

	public void createPendencyImplements(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, ITypeBinding itype) {
		PendencyImplements PendencyImplements = new PendencyImplements(pckDecl, typeDecl, itype);
		pendencies.add(PendencyImplements);
	}

	public void createPendencyClass(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, Indication indication, int indicationType, boolean isForClassName) {
		
		String idConcern = "";
		String nameConcern = "";
		List<Concern> setConcerns = this.getProject().getConcerns();
		Boolean achou = false;
		Concern concern;
		
		Iterator<Concern> it = setConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			concern = it.next();
			if ((indication.getName().replaceAll(" ", "")).equals(concern.getName())) {
				idConcern = concern.getName();
				nameConcern = concern.getName();
				achou = true;
			}
		}

		concern = project.findConcernById(idConcern);
		if (concern == null) {
			concern = new Concern(idConcern, nameConcern);
		}		
		
		// Paulo 16/11/10	
		if (indicationType == PRIMARYCC) {			
			String cn = concern.getName();
			cn = "Pri_" + cn;
			concern.setName(cn);
		} else {
			String cn = concern.getName();
			String cid = concern.getId();
			cn = "Sec_" + cn;
			concern.setName(cn);
		}		
		// Paulo 16/11/10


		// Paulo 16/12/10 - Evita que se apague estereótipos que foram colocados por conta do nome da classe ser um indication
		concern.setForClassName(isForClassName);
		// Paulo 16/12/10
		
		PendencyClass pendencyClass = new PendencyClass(pckDecl, typeDecl, concern);
		pendencies.add(pendencyClass);
	}
	
	public void createVariable(String namePck, String idClass, String idMethod, String idVar, String nameVar, String typeVar) {
		Package pck = project.findPackageByName(namePck);
		if (pck != null) {
			Class cl = pck.findClassById(idClass);
			if (cl != null) {
				Operation op = cl.findOperationById(idMethod);
				Variable var = new Variable(idVar, nameVar, new Type("", ""));

				// Find type.
				Type type = project.findTypeByName(typeVar);
				if (type != null) {

					// Paulo 23/11
					if (typeVar.indexOf('.') > 0) {
						createImports(typeVar);
					}	
					// Paulo 23/11		
					
					var = new Variable(idVar, nameVar, type);
				}
				op.addVariable(var);
			}
		}
		
	}	
	
	public void resolvePendencies() {
		Iterator<Pendency> it = pendencies.iterator();
		while(it.hasNext()) {
			Pendency pendency = it.next();
			if (pendency instanceof PendencyClass) {
				addConcernInClass(pendency.getPckDecl(), pendency.getTypeDecl(), pendency.getConcern());
			} else if (pendency instanceof PendencyAttribute) {
				addConcernInAttribute(pendency.getPckDecl(), pendency.getTypeDecl(), ((PendencyAttribute) pendency).getFieldDecl(), pendency.getConcern());
				//addConcernInClass(pendency.getPckDecl(), pendency.getTypeDecl(), pendency.getConcern());
			} else if (pendency instanceof PendencyOperation) {
				addConcernInOperation(pendency.getPckDecl(), pendency.getTypeDecl(), ((PendencyOperation) pendency).getMethodDecl(), pendency.getConcern());
				//addConcernInClass(pendency.getPckDecl(), pendency.getTypeDecl(), pendency.getConcern());
			} else if (pendency instanceof PendencyVariable){
				addConcernInVariable(pendency.getPckDecl(), pendency.getTypeDecl(), ((PendencyVariable) pendency).getMethodDecl(), ((PendencyVariable) pendency).getVariableDeclarationFragment(), ((PendencyVariable) pendency).getVariableDeclarationStatement(), pendency.getConcern());
			}
			
		}
		resolvePendenciesCalledMethods();
		resolvePendenciesInheritance();
		resolvePendenciesImplements();
	}
	
	public void resolvePendenciesCalledMethods() {
		Iterator<Pendency> it = pendencies.iterator();
		while(it.hasNext()) {
			Pendency pendency = it.next();
			if (pendency instanceof PendencyCalledOperation) {
				if (((PendencyCalledOperation) pendency).getMethodCalled() != null) {
					addConcernInOperation(pendency.getPckDecl(), pendency.getTypeDecl(), ((PendencyCalledOperation) pendency).getMethodDecl(), ((PendencyCalledOperation) pendency).getTypeMethodCalled(), ((PendencyCalledOperation) pendency).getMethodCalled());
				} else addConcernInOperation(pendency.getPckDecl(), pendency.getTypeDecl(), ((PendencyCalledOperation) pendency).getMethodDecl(), ((PendencyCalledOperation) pendency).getMethodName());
				it.remove();			
			}
		}		
	}	

	public void resolvePendenciesImplements() {
		Iterator<Pendency> it = pendencies.iterator();
		while(it.hasNext()) {
			Pendency pendency = it.next();
			if (pendency instanceof PendencyImplements) {
				PendencyImplements pim = (PendencyImplements) pendency;
				addImplementedInterface(pim.getPckDecl(), pim.getTypeDecl(), pim.getItype());
				it.remove();
			}
		}		
	}	

	
	public void resolvePendenciesInheritance() {
		Iterator<Pendency> it = pendencies.iterator();
		while(it.hasNext()) {
			Pendency pendency = it.next();
			if (pendency instanceof PendencyInheritance) {
				PendencyInheritance pi = (PendencyInheritance) pendency;
				addSuperClass(pi.getPckDecl(), pi.getTypeDecl(), pi.getItype());
				it.remove();
			}
		}		
	}	
		
	public void addImplementedInterface(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, ITypeBinding itype) {		
		String nameBasePck = pckDecl.getName().getFullyQualifiedName();
		String nameBaseClass = typeDecl.getName().getFullyQualifiedName();
		String idBaseClass= nameBasePck + "." + nameBaseClass;

		String nameInterfacePck = itype.getPackage().getName();
		String nameInterfaceClass = itype.getName();
		String idInterfaceClass = nameInterfacePck + "." + nameInterfaceClass;
		
		Class baseClass = project.findClassById(idBaseClass);	
		Class implementedInterface = project.findClassById(idInterfaceClass);	

		if ((baseClass != null)&&(implementedInterface != null)){
			baseClass.addInterface(new Class(implementedInterface));
		}
	}

	public void addSuperClass(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, ITypeBinding itype) {		
		String nameBasePck = pckDecl.getName().getFullyQualifiedName();
		String nameBaseClass = typeDecl.getName().getFullyQualifiedName();
		String idBaseClass= nameBasePck + "." + nameBaseClass;

		String nameSuperPck = itype.getPackage().getName();
		String nameSuperClass = itype.getName();
		String idSuperClass = nameSuperPck+ "." + nameSuperClass;
		
		Class baseClass = project.findClassById(idBaseClass);	
		Class superClass = project.findClassById(idSuperClass);	

		if ((baseClass != null)&&(superClass != null)){
			baseClass.setInherit(new Class(superClass));
		}
	}

	
	public void addConcernInAttribute(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, FieldDeclaration fldDecl, Concern concern) {		
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameAttr = ((VariableDeclarationFragment) fldDecl.fragments().get(0)).getName().getFullyQualifiedName();
		String nameType = getTypeName(fldDecl.getType());
		String idAttr = namePck + "." + nameClass + "." + nameType + "." + nameAttr;

		Attribute attr = project.findAttributeById(idAttr);	
		if ((attr != null) && (!attr.isConcernExist(concern.getId()))){
			attr.addConcern(concern);
			
			ExistingConcern ec = project.isEConcernExist(concern.getName());
			if (ec == null) {
				ec = new ExistingConcern(concern);
				ec.setInAttribute(true);
				project.addExistingConcern(ec);
			} else {
				ec.setInAttribute(true);
			}
		}
	}
	
	public void addConcernInOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, Concern concern) { 
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameOperation = methodDecl.getName().getFullyQualifiedName();
		String idMethod = namePck + "." + nameClass + "." + nameOperation;

		List<SingleVariableDeclaration> lsParameters = methodDecl.parameters();
		Iterator<SingleVariableDeclaration> itParameter = lsParameters.iterator();
		while (itParameter.hasNext()) {
			SingleVariableDeclaration parameter = itParameter.next();
			String nameTypeParameter = getTypeName(parameter.getType());				
			idMethod = idMethod + "." + nameTypeParameter;			
		}

		Operation operation = project.findOperationById(idMethod);	

		if ((operation != null) && (!operation.isConcernExist(concern.getId()))){
			operation.addConcern(concern);
			
			ExistingConcern ec = project.isEConcernExist(concern.getName());
			if (ec == null) {
				ec = new ExistingConcern(concern);
				ec.setInOperation(true);
				project.addExistingConcern(ec);
			} else {
				ec.setInOperation(true);
			}
		}
	}
	
	public void addConcernInOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, String methodName) {
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameOperation = methodDecl.getName().getFullyQualifiedName();
		String idMethod = namePck + "." + nameClass + "." + nameOperation;

		List<SingleVariableDeclaration> lsParameters = methodDecl.parameters();
		Iterator<SingleVariableDeclaration> itParameter = lsParameters.iterator();
		while (itParameter.hasNext()) {
			SingleVariableDeclaration parameter = itParameter.next();
			String nameTypeParameter = getTypeName(parameter.getType());				
			idMethod = idMethod + "." + nameTypeParameter;			
		}

		Operation operation = project.findOperationById(idMethod);	

		if ((operation != null) && (!operation.isCalledOperationExist(methodName))){
			operation.addCalledOperation(methodName);
		}		
	}

	public void addConcernInOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, ITypeBinding itype, IMethodBinding imethod) { 
		String namePckDecl = pckDecl.getName().getFullyQualifiedName();
		String nameClassDecl = typeDecl.getName().getFullyQualifiedName();
		
		// Paulo 30-11
		String idClassDecl = namePckDecl + "." + nameClassDecl;
		// Paulo 30-11
		
		String nameOperationDecl = methodDecl.getName().getFullyQualifiedName();
		String idMethodDecl = namePckDecl + "." + nameClassDecl + "." + nameOperationDecl;

		List<SingleVariableDeclaration> lsParameters = methodDecl.parameters();
		Iterator<SingleVariableDeclaration> itParameter = lsParameters.iterator();
		while (itParameter.hasNext()) {
			SingleVariableDeclaration parameter = itParameter.next();
			String nameTypeParameter = getTypeName(parameter.getType());				
			idMethodDecl = idMethodDecl + "." + nameTypeParameter;			
		}

		String namePckCalled = itype.getPackage().getName();
		String nameClassCalled = itype.getName();

		// Paulo 30-11
		String idClassCalled = namePckCalled + "." + nameClassCalled;
		// Paulo 30-11
		
		String nameOperationCalled = imethod.getName();
		String idMethodCalled = namePckCalled + "." + nameClassCalled + "." + nameOperationCalled;
		
		ITypeBinding[] lsParametersTypes = imethod.getParameterTypes();
		for (int i = 0; i < lsParametersTypes.length; i++) {
			ITypeBinding parameter = lsParametersTypes[i];
			String nameTypeParameter = parameter.getName();				
			idMethodCalled = idMethodCalled + "." + nameTypeParameter;			
		}

		//System.out.println("id method decl: " + idMethodDecl + " - id method called: " + idMethodCalled);
		
		Operation operation = project.findOperationById(idMethodCalled);	

		if ((operation != null) && (operation.hasConcern())) {		
			List<Concern> lsConcerns = operation.getConcerns();
			
			createDependency(idClassDecl, idClassCalled, lsConcerns);
			
			Iterator<Concern> itConcerns = lsConcerns.iterator();
			while(itConcerns.hasNext()) {
				Concern concern = itConcerns.next();
				if (concern.isPrimary()) {
					Operation operationDecl = project.findOperationById(idMethodDecl);
					if ((operationDecl != null) && (!operationDecl.isConcernExist(concern.getId()))) {
						Concern newConcern = new Concern(concern);
						
						newConcern.setPrimary(false);
						
						String cn = newConcern.getId();
						cn = "Sec_" + cn;
						newConcern.setName(cn);						

						operationDecl.addConcern(newConcern);
						
						ExistingConcern ec = project.isEConcernExist(newConcern.getName());
						if (ec == null) {
							ec = new ExistingConcern(newConcern);
							ec.setInOperation(true);
							project.addExistingConcern(ec);
						} else {
							ec.setInOperation(true);
						}
						
						addConcernInClass(pckDecl, typeDecl, newConcern);						
					}					
				}
			}
		} else if (operation != null) {
			// createDependency(idClassDecl, idClassCalled, null);			
		}
	}

	public void addConcernInVariable(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl,  
			VariableDeclarationFragment variableDeclarationFragment, 
			VariableDeclarationStatement variableDeclarationStatement, Concern concern) { 
		System.out.println("aaaaaaaaaaaaaa: " + project.getPackages().size() );
		
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameOperation = methodDecl.getName().getFullyQualifiedName();
		String nameVar = variableDeclarationFragment.getName().getIdentifier();
		String typeVar = getTypeName(variableDeclarationStatement.getType());
		String idClass = namePck + "." + nameClass;
		String idMethod = namePck + "." + nameClass + "." + nameOperation;
		Concern newConcern = new Concern(concern);

		List<SingleVariableDeclaration> lsParameters = methodDecl.parameters();

		if (lsParameters != null) {
			Iterator<SingleVariableDeclaration> itParameter = lsParameters.iterator();
			while (itParameter.hasNext()) {
				SingleVariableDeclaration parameter = itParameter.next();
				String nameTypeParameter = getTypeName(parameter.getType());				
				idMethod = idMethod + "." + nameTypeParameter;
			}
		}
		
		String idVar = idMethod + "." + nameVar;
		createVariable(namePck, idClass, idMethod, idVar, nameVar, typeVar);
		
		Variable var = project.findVariableById(namePck, idClass, idMethod, idVar);
		
		if ((var != null) && (!var.isConcernExist(newConcern.getId()))){
			var.addConcern(newConcern);
		}	
		System.out.println("sss4");
		
	}
	
	public void addConcernInClass(PackageDeclaration pckDecl, TypeDeclaration typeDecl, Concern concern) {		
		if (concern != null) {
				Concern newConcern = new Concern(concern);
				String namePck = pckDecl.getName().getFullyQualifiedName();
				String nameClass = typeDecl.getName().getFullyQualifiedName();
				String idClass = namePck + "." + nameClass;
		
				Class cl = project.findClassById(idClass);
				if ((cl != null) && (!cl.isConcernExist(newConcern.getId()))) {
						cl.addConcern(newConcern);
						
						ExistingConcern ec = project.isEConcernExist(newConcern.getName());
						if (ec == null) {
							ec = new ExistingConcern(newConcern);
							ec.setInClass(true);
							project.addExistingConcern(ec);
						} else {
							ec.setInClass(true);
						}
						
				}
			}
	}
	

	public void createPendencyVariable (PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl,
			VariableDeclarationStatement variableDeclarationStatement,
			VariableDeclarationFragment variableDeclarationFragment, Indication indication){
		
		String namePck = pckDecl.getName().getFullyQualifiedName();
		String nameClass = typeDecl.getName().getFullyQualifiedName();
		String nameOperation = methodDecl.getName().getFullyQualifiedName();
		String nameVariable = variableDeclarationFragment.getName().getIdentifier();
		String typeVariable = getTypeName(variableDeclarationStatement.getType());

		/*System.out.println("Identificador " + nameVariable);
		System.out.println("Tipo          " + typeVariable);
		System.out.println("Operacao      " + nameOperation);
		System.out.println("Classe        " + nameClass);
		System.out.println("Pacote        " + namePck);
		System.out.println("Indicio       " + indication.getName());*/
		
		String idConcern = "";
		String nameConcern = "";
		List<Concern> setConcerns = this.getProject().getConcerns();
		Boolean achou = false;
		Concern concern;

		Iterator<Concern> it = setConcerns.iterator();
		while((!achou) && (it.hasNext())) {
			concern = it.next();
			if ((indication.getName().replaceAll(" ", "")).equals(concern.getName())) {
				idConcern = concern.getName();
				nameConcern = concern.getName();
				achou = true;
			}
		}

		concern = project.findConcernById(idConcern);
		if (concern == null) {
			concern = new Concern(idConcern, idConcern);
		}		
		
		PendencyVariable pendencyVariable = new PendencyVariable(pckDecl, typeDecl, methodDecl, concern, variableDeclarationFragment, variableDeclarationStatement);
		pendencies.add(pendencyVariable);
	}
	
	private String getTypeName(org.eclipse.jdt.core.dom.Type type) {
		/*
		 * Type: PrimitiveType ArrayType SimpleType QualifiedType
		 * ParameterizedType WildcardType PrimitiveType: byte short char int
		 * long float double boolean void ArrayType: Type [ ] SimpleType:
		 * TypeName ParameterizedType: Type < Type { , Type } > QualifiedType:
		 * Type . SimpleName WildcardType: ? [ ( extends | super) Type ]
		 */
		String sReturn = "";
		if (type instanceof ArrayType)
			return getTypeName(((ArrayType) type).getElementType());
		else if (type instanceof ParameterizedType)
			return getTypeName(((ParameterizedType) type).getType());
		else if (type instanceof PrimitiveType) {
			PrimitiveType pt = (PrimitiveType) type;
			return pt.toString();
		}
		else if (type instanceof QualifiedType)
			return (((QualifiedType) type).getName().getIdentifier());
		else if (type instanceof SimpleType) {
			SimpleType simpleType = (SimpleType) type;
			if (simpleType.getName().isSimpleName())
				return ((SimpleName) simpleType.getName()).getIdentifier();
			else if (simpleType.getName().isQualifiedName())
				return ((QualifiedName) simpleType.getName())
						.getFullyQualifiedName();
		} else if (type instanceof WildcardType)
			return getTypeName(((WildcardType) type).getBound());
		return sReturn;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
