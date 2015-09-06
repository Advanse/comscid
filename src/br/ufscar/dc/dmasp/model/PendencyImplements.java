package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class PendencyImplements extends Pendency {

	private ITypeBinding itype;
	
	public ITypeBinding getItype() {
		return itype;
	}

	public void setItype(ITypeBinding itype) {
		this.itype = itype;
	}

	public PendencyImplements(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, ITypeBinding itype) {
		super(pckDecl, typeDecl, null);
		this.itype = itype;
	}
}
