package br.ufscar.dc.dmasp.model;

public class Association {
	private String id;
	private String idClass1;
	private String idClass2;

	public Association(String id, String idClass1, String idClass2) {
		super();
		this.id = id;
		this.idClass1 = idClass1;
		this.idClass2 = idClass2;
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
}
