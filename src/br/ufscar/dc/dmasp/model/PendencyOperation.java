package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class PendencyOperation extends Pendency {
	
	private MethodDeclaration methodDecl;
	
	public PendencyOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, Concern concern) {
		super(pckDecl, typeDecl, concern);
		this.methodDecl = methodDecl;
	}

	public MethodDeclaration getMethodDecl() {
		return methodDecl;
	}

	public void setMethodDecl(MethodDeclaration methodDecl) {
		this.methodDecl = methodDecl;
	}
}
