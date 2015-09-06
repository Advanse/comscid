package br.ufscar.dc.dmaspplus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufscar.dc.dmasp.model.Class;
import br.ufscar.dc.dmasp.model.Concern;
import br.ufscar.dc.dmasp.model.Package;
import br.ufscar.dc.dmasp.model.Project;

public class RefactoringManager {
	private Project prj;
	
	public RefactoringManager(Project prj) {
		this.prj = prj;
	}
	
	public void ref4() {
		List<Package> pcks = new ArrayList<Package>();
		pcks = prj.getPackages();
		String secStereotype = "";			
		
		Iterator itPcks = pcks.iterator();
		while(itPcks.hasNext()) {
			Package pck = (Package)itPcks.next();

			Iterator itClasses = pck.getClasses().iterator();
			boolean ret = false;
			Concern cn = null;
			while((itClasses.hasNext()) && (ret == false)) {
				Class cl = (Class)itClasses.next();				
				cn = cl.findConcernByName("Sec_");
				if (cn != null) ret = true;
			}
			if (cn != null) System.out.println(cn.getName());			
		}
	}
}
