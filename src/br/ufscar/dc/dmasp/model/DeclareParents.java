package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeclareParents {

	private String id;
	private Class clBase;
	private List<Class> sonClasses;
	private String dpType;
	public static String implementsType = "implements";
	public static String extendsType = "extends";
	
	public DeclareParents(String id, String dpType) {
		this.id = id;
		this.dpType = dpType;
		this.clBase = null;
		this.sonClasses = new ArrayList<Class>();
	}

	public DeclareParents(DeclareParents other) {
		this.id = other.getId();
		this.dpType = other.getDpType();
		if (other.getClBase() != null) this.clBase = new Class(other.getClBase());
		else clBase = null;
		this.sonClasses = new ArrayList<Class>();
		Iterator<Class> itSC = other.getSonClasses().iterator();		
		while(itSC.hasNext()) {
			Class scAux = itSC.next();			
			this.sonClasses.add(new Class(scAux));	
		}		
	}

	public Class getClBase() {
		return this.clBase;
	}

	public void setClBase(Class clBase) {
		this.clBase = clBase;
	}

	public void addSonClass(Class cl) {
		this.sonClasses.add(cl);
	}	

	public List<Class> getSonClasses() {
		return this.sonClasses;
	}

	public void setSonClasses(List<Class> sonClasses) {
		this.sonClasses = sonClasses;
	}

	public String getDpType() {
		return dpType;
	}

	public void setDpType(String dpType) {
		this.dpType = dpType;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
