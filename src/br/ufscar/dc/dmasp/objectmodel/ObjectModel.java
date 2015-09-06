package br.ufscar.dc.dmasp.objectmodel;

import java.util.ArrayList;

public class ObjectModel {
	private String className;
	private ArrayList attributeName;
	
	public ObjectModel(String name) {
		className = name;
		attributeName = new ArrayList();
	}

	public String getClassName() {
		return className;
	}
	
/*	public void addAttribute(String name){
		attribute.add(name);
	}
	
	public String getType() {
		return sType;
	}
	
	public String getModifiers() {
		return sModifiers;
	}
	
	public String getInitializer() {
		return sInitializer;
	}
	
	public FileInfo getFile() {
		return fileParent;
	}
	
	public TypeInfo getTypeParent() {
		return typeParent;
	}
	
	public ArrayList getEnclosuredTypes() {
		ArrayList lstTypes = new ArrayList();
		TypeInfo typeInfo = typeParent;
		while( typeInfo != null ) {
			lstTypes.add(typeInfo);
			typeInfo = typeInfo.getTypeParent();
		}
		// Inverting list
		ArrayList lstReturn = new ArrayList();
		for ( int i = lstTypes.size() - 1; i >= 0 ; i-- ) {
			lstReturn.add(lstTypes.get(i));
		}
		return lstReturn;
	}
	
	public void setFile( FileInfo fileParent ) {
		this.fileParent = fileParent;
	}
	
	public void setTypeParent( TypeInfo typeParent ) {
		this.typeParent = typeParent;
	}*/
}
