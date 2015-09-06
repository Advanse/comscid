package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Pendency {
	private PackageDeclaration pckDecl;
	private TypeDeclaration typeDecl;
	private Concern concern;
	
	public Pendency(PackageDeclaration pckDecl, TypeDeclaration typeDecl, Concern concern) {
		this.pckDecl = pckDecl;
		this.typeDecl = typeDecl;
		this.concern = concern;
	}

	public PackageDeclaration getPckDecl() {
		return pckDecl;
	}

	public void setPckDecl(PackageDeclaration pckDecl) {
		this.pckDecl = pckDecl;
	}

	public TypeDeclaration getTypeDecl() {
		return typeDecl;
	}

	public void setTypeDecl(TypeDeclaration typeDecl) {
		this.typeDecl = typeDecl;
	}

	public Concern getConcern() {
		return concern;
	}

	public void setConcern(Concern concern) {
		this.concern = concern;
	}
}
