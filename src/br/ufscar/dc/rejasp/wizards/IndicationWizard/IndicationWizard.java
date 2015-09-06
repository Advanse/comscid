package br.ufscar.dc.rejasp.wizards.IndicationWizard;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;

import br.ufscar.dc.rejasp.indication.model.Indication;
import br.ufscar.dc.rejasp.indication.model.IndicationPackage;
import br.ufscar.dc.rejasp.indication.model.MatchText;
import br.ufscar.dc.rejasp.views.IndicationTree;
import br.ufscar.dc.rejasp.views.IndicationTree.ViewContentProvider;

/**
 * @author Daniel Kawakami
 * Implementation of indication management wizard 
 */
public class IndicationWizard extends Wizard implements INewWizard {
	/**
	 * Workbench selection when the wizard was started
	 */
	protected IStructuredSelection selection;
	/**
	 * The workbench instance
	 */
	protected IWorkbench workbench;

	// Wizard Pages
	IndicationsPage indicationsPage;
	IndicationHeadPage headPage;
	PackageMainPage mainPage;
	PackageHeadPage packageHeadPage;
	InterfaceManagementPage interfacePage;
	ClassManagementPage classPage;
	ExceptionManagementPage exceptionPage;
	RemovePackagePage removePackagePage;
	ViewPackagePage viewPage;
	RemoveIndicationPage removePage;
	RuleMainPage ruleMainPage;
	RemoveRulePage removeRulePage;
	ViewRulePage viewRulePage;
	CreationRulePage creationRulePage;
	
	// Model
	ArrayList lstIndications;
	Indication currentIndication;
	IndicationPackage currentPackage;
	MatchText currentRule;
	Properties propertiesFile;
	
	// Auxiliary fields
	boolean newIndication;
	boolean newPackage;
	boolean newRule;
	boolean canFinish;
	
	/**
	 * Default contructor
	 */
	public IndicationWizard() {
		super();
		canFinish = true;
		currentIndication = null;
		newIndication = true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		setWindowTitle("Manage Indications");
		indicationsPage = new IndicationsPage();
		addPage(indicationsPage);
		headPage = new IndicationHeadPage();
		addPage(headPage);
		mainPage = new PackageMainPage();
		addPage(mainPage);
		packageHeadPage = new PackageHeadPage();
		addPage(packageHeadPage);
		interfacePage = new InterfaceManagementPage();
		addPage(interfacePage);
		classPage = new ClassManagementPage();
		addPage(classPage);
		exceptionPage = new ExceptionManagementPage();
		addPage(exceptionPage);
		removePackagePage = new RemovePackagePage();
		addPage(removePackagePage);
		removePage = new RemoveIndicationPage();
		addPage(removePage);
		viewPage = new ViewPackagePage();
		addPage(viewPage);
		ruleMainPage = new RuleMainPage();
		addPage(ruleMainPage);
		removeRulePage = new RemoveRulePage();
		addPage(removeRulePage);
		viewRulePage = new ViewRulePage();
		addPage(viewRulePage);
		creationRulePage = new CreationRulePage();
		addPage(creationRulePage);
	}
	
	private String getInstallLocation() throws Exception {
		Bundle bundle = Platform.getBundle("br.ufscar.dc.rejasp");;
		URL locationUrl = FileLocator.find(bundle,new Path("/"), null);
		URL fileUrl =FileLocator.toFileURL(locationUrl);
		return fileUrl.getFile();
	}

	public IProject getCurrentProject() {
		IndicationTree viewer = (IndicationTree) workbench.getActiveWorkbenchWindow().getActivePage().findView("br.ufscar.dc.rejasp.views.IndicationTree");
		return viewer.getCurrentProject();
	}

	public String getProjectLocation() {
		String sLocation = ((IResource) getCurrentProject())
				.getLocationURI().toString();
		sLocation = sLocation.replaceAll("file:/", "");
		return sLocation;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 * Init wizard and get clones of existing indications
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		IViewPart viewPart = workbench.getActiveWorkbenchWindow().getActivePage().findView("br.ufscar.dc.rejasp.views.IndicationTree");
		if (viewPart == null) {
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Warning", "Please, close the next page and open indication tree view before managing indications.");
		}
		else {
			lstIndications = ((IndicationTree)viewPart).getIndications();
		}
		setNeedsProgressMonitor(true);
		propertiesFile = new Properties();
		try {			
			propertiesFile.load(new FileInputStream(getProjectLocation()+"/indications/propertiesIndications.properties"));			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("It was not possible to open the properties file.");
		} 
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	public boolean canFinish() {
		return canFinish;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		if ( indicationsPage.isChanged() && 
			 MessageDialog.openQuestion(workbench.getActiveWorkbenchWindow().getShell(),
				"Save", "Changes have been done. Do you like to save the changes?")	) {
			// Update indications of tree view
			IViewPart viewPart = workbench.getActiveWorkbenchWindow().getActivePage().findView("br.ufscar.dc.rejasp.views.IndicationTree");
			if (viewPart == null) {
				MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
						"Warning", "Please, cloose the next page and open indication tree view before managing indications.");
				return false;
			}
			((IndicationTree)viewPart).setIndications(lstIndications);
			
			/*String sXmlFilePath;
			sXmlFilePath = ((IndicationTree)viewPart).getXmlFilePath();
			
			// Save xml file of indications
			((IndicationTree)viewPart).setXmlFilePath(sXmlFilePath);
			FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE);
			String[] extensions = {"*.xml"};
			String[] names = {"eXtensible Markup Language File (*.xml)"};
			dialog.setFilterExtensions(extensions);
			dialog.setFilterNames(names);
			dialog.setFileName(sXmlFilePath);
			sXmlFilePath = dialog.open();
			((IndicationTree)viewPart).setXmlFilePath(sXmlFilePath);*/
			if ( ! ((IndicationTree)viewPart).saveIndicationsToXml() )
				MessageDialog.openError(workbench.getActiveWorkbenchWindow().getShell(), 
						"Error", "Couldn't create xml file.");
			try { 
				propertiesFile.store(new FileOutputStream(getProjectLocation()+"/indications/propertiesIndications.properties"), null);  
			} catch (Exception e) { 
				System.out.println("It was not possible to open the properties file.");
			} 

		}
		return true;
	}
	
	public boolean performCancel() {
		if ( MessageDialog.openQuestion(workbench.getActiveWorkbenchWindow().getShell(), 
				"Warning", "Indications haven't been saved yet. Do you want to cancel anyway?"))
			return true;
		else {
			MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), 
					"Hint", "Use Finish button to save indications before exit.");
			return false;
		}
	}

	/**
	 * Some pages need to access workbench. This method do that. 
	 * @return
	 */
	public IWorkbench getWorkbench() {
		return workbench;
	}
	
	/**
	 * Find indication by name.
	 * @param sName indication name
	 * @return an indication or null if no indication was found 
	 */
	public Indication findIndication(String sName) {
		for ( int i = 0; i < lstIndications.size(); i++ )
			if ( sName.equals(((Indication)lstIndications.get(i)).getName()) )
				return (Indication)lstIndications.get(i);
		return null;
	}
	
	/**
	 * Remove an indication that matches to the argument sName.
	 * @param sName indication name
	 * @return removed indication or null if no indication was found
	 */
	public Indication removeIndication(String sName) {
		for ( int i = 0; i < lstIndications.size(); i++ )
			if ( sName.equals(((Indication)lstIndications.get(i)).getName()) ) {
				propertiesFile.remove(sName);
				return (Indication)lstIndications.remove(i);
			}
		return null;
	}
	
}
