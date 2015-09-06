package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class PendencyVariable extends Pendency {
	
	private MethodDeclaration methodDecl;
	private VariableDeclarationFragment variableDeclarationFragment;
	private VariableDeclarationStatement variableDeclarationStatement;

	public PendencyVariable(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, Concern concern,
			VariableDeclarationFragment variableDeclarationFragment,
			VariableDeclarationStatement variableDeclarationStatement) {
		super(pckDecl, typeDecl, concern);
		this.methodDecl = methodDecl;
		this.variableDeclarationFragment = variableDeclarationFragment;
		this.variableDeclarationStatement = variableDeclarationStatement;
	}

	public MethodDeclaration getMethodDecl() {
		return methodDecl;
	}

	public void setMethodDecl(MethodDeclaration methodDecl) {
		this.methodDecl = methodDecl;
	}

	public VariableDeclarationFragment getVariableDeclarationFragment() {
		return variableDeclarationFragment;
	}

	public void setVariableDeclarationFragment(VariableDeclarationFragment variableDeclarationFragment) {
		this.variableDeclarationFragment = variableDeclarationFragment;
	}

	public VariableDeclarationStatement getVariableDeclarationStatement() {
		return variableDeclarationStatement;
	}
	
	public void setVariableDeclarationStatement(VariableDeclarationStatement variableDeclarationStatement) {
		this.variableDeclarationStatement = variableDeclarationStatement;
	}
}