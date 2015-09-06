package br.ufscar.dc.dmasp.model;

public class ExistingConcern extends Concern {
	
	private boolean inClass;
	private boolean inAttribute;
	private boolean inOperation;

	public ExistingConcern(Concern c) {
		super(c);
		this.inClass = false;
		this.inAttribute = false;
		this.inOperation = false;
	}
	
	public ExistingConcern(ExistingConcern ec) {
		super(ec);
		this.inClass = ec.isInClass();
		this.inAttribute = ec.isInAttribute();
		this.inOperation = ec.isInOperation();
	}

	public boolean isInClass() {
		return inClass;
	}

	public void setInClass(boolean inClass) {
		this.inClass = inClass;
	}

	public boolean isInAttribute() {
		return inAttribute;
	}

	public void setInAttribute(boolean inAttribute) {
		this.inAttribute = inAttribute;
	}

	public boolean isInOperation() {
		return inOperation;
	}

	public void setInOperation(boolean inOperation) {
		this.inOperation = inOperation;
	}
	
}
