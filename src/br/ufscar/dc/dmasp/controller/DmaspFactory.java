package br.ufscar.dc.dmasp.controller;

public class DmaspFactory {
	private static Dmasp dmasp = null;	
	
	public static Dmasp getInstance() {
		if (dmasp == null) {
			dmasp = new Dmasp();
			return dmasp; 
		} else return dmasp;
	}	
	
}
