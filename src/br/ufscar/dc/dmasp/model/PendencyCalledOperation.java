package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class PendencyCalledOperation extends Pendency {
	
	private MethodDeclaration methodDecl;
	private ITypeBinding typeMethodCalled;
	private IMethodBinding methodCalled;
	private String methodName;
	
	public PendencyCalledOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, ITypeBinding itype, IMethodBinding imethod) {
		super(pckDecl, typeDecl, null);
		this.methodDecl = methodDecl;
		this.typeMethodCalled = itype;
		this.methodCalled = imethod;
		this.methodName = imethod.getName();
	}

	public PendencyCalledOperation(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, MethodDeclaration methodDecl, String methodName) {
		super(pckDecl, typeDecl, null);
		this.methodDecl = methodDecl;
		this.methodName = methodName;
		this.typeMethodCalled = null;
		this.methodCalled = null;
	}

	public ITypeBinding getTypeMethodCalled() {
		return typeMethodCalled;
	}

	public void setTypeMethodCalled(ITypeBinding typeMethodCalled) {
		this.typeMethodCalled = typeMethodCalled;
	}

	public IMethodBinding getMethodCalled() {
		return methodCalled;
	}

	public void setMethodCalled(IMethodBinding methodCalled) {
		this.methodCalled = methodCalled;
	}

	public MethodDeclaration getMethodDecl() {
		return methodDecl;
	}

	public void setMethodDecl(MethodDeclaration methodDecl) {
		this.methodDecl = methodDecl;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


}
