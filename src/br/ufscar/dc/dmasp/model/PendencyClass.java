package br.ufscar.dc.dmasp.model;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class PendencyClass extends Pendency {

	public PendencyClass(PackageDeclaration pckDecl,
			TypeDeclaration typeDecl, Concern concern) {
		super(pckDecl, typeDecl, concern);
	}
}
