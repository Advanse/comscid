package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class PendencyAttribute extends Pendency {

	private FieldDeclaration fieldDecl;
	
	public PendencyAttribute(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, FieldDeclaration fieldDecl, Concern concern) {
		super(pckDecl, typeDecl, concern);
		this.fieldDecl = fieldDecl;
	}

	public FieldDeclaration getFieldDecl() {
		return fieldDecl;
	}

	public void setFieldDecl(FieldDeclaration fieldDecl) {
		this.fieldDecl = fieldDecl;
	}
}
