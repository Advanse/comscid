package br.ufscar.dc.dmasp.model;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Project prj = new Project("Class Model", "c:\test");
		Package pck1 = new Package("model");
		
		Class cl = new Class("model.Account", "Account");
		Attribute attr = new Attribute("model.Account.connection", "connection", 
				new Type("java.sql.Connection", ".Connection"));
		Concern concern = new Concern("DatabasePersistence", "DatabasePersistence");
		attr.addConcern(concern);
		
		pck1.addClass(cl);
		prj.addPackage(pck1);
	}
}
