package br.ufscar.dc.dmasp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dependency {
	private String id;
	private String idClass1;
	private String idClass2;
	private List<Concern> concerns;

	public Dependency(String id, String idClass1, String idClass2) {
		super();
		this.id = id;
		this.idClass1 = idClass1;
		this.idClass2 = idClass2;
		this.concerns = new ArrayList<Concern>();
	}

	public Dependency(Dependency other) {
		super();
		this.id = other.getId();
		this.idClass1 = other.getIdClass1();
		this.idClass2 = other.getIdClass2();
		this.concerns = new ArrayList<Concern>();
		this.concerns.addAll(other.getConcerns());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdClass1() {
		return idClass1;
	}

	public void setIdClass1(String idClass1) {
		this.idClass1 = idClass1;
	}

	public String getIdClass2() {
		return idClass2;
	}

	public void setIdClass2(String idClass2) {
		this.idClass2 = idClass2;
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
		String idConcern = concern.getId();
		Iterator<Concern> itConcern = concerns.iterator();
		while(itConcern.hasNext()) {
			Concern aux = itConcern.next();
			if (aux.getId().equals(idConcern)) {
				itConcern.remove();
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

}
