package br.ufscar.dc.rejasp.wizards.IndicationWizard;

import java.util.ArrayList;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import br.ufscar.dc.rejasp.indication.model.MatchText;

public class CreationRulePage extends WizardPage implements Listener {
	/**
	 * Reference to wizard
	 */
	private IndicationWizard wizard;
	private MatchText rule;
	private Combo cbTarget;
	private Combo cbRule;
	private Button chkCaseSensity;
	private List lstWords;
	private Button btnNew;
	private Button btnInsert;
	private Button btnRemove;
	private Text txtWord; 
	
	// Constants used for the control of this page
	private final String sAdd = "&Add >>"; 
	private final String sUpdate = "&Update >>"; 
	
	/**
	 * Properties File
	 */
	private Properties properties;
	
	/**
	 * Field top enter the indication description
	 */
	private Combo comboIndications;

	/**
	 * Combo Constants
	 */
	public static final String NULL = "";
	public static final String OPEN_CONNECTION = "Open Connection";
	public static final String CLOSE_CONNECTION = "Close Connection";
	public static final String ENTRY_LOGGING = "Entry Logging";
	public static final String EXIT_LOGGING = "Exit Logging";
	public static final String NOTIFY_OBSERVER = "Notify Observer";
	public static final String SINGLECLASS = "SingleClass";
	public static final String OBSERVER_TYPE = "Observer Type";
	public static final String SUBJECT_TYPE = "Subject Type";


	public CreationRulePage() {
		super("Creation / update rule page");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 * Interface of the page is created
	 */
	public void createControl(Composite parent) {
		wizard = (IndicationWizard)getWizard();
		properties = wizard.propertiesFile;
		// create the composite to hold the widgets
		GridData gd;
		Composite composite =  new Composite(parent, SWT.NULL);

	    // create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 5;
		gl.numColumns = ncol;
		gl.makeColumnsEqualWidth = true;
		gl.horizontalSpacing = 15;
		composite.setLayout(gl);
		
		// Line 1
		Label lbTarget = new Label(composite, SWT.NONE);
		lbTarget.setText("Target");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		lbTarget.setLayoutData(gd);
		
		cbTarget = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY );
		cbTarget.setItems(new String []{"String Literal","Variable Name", "Method Name", "Class and Interface Names"});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cbTarget.setLayoutData(gd);

		fillCells (composite, 1, 9);
		
		Label lbWords = new Label(composite, SWT.NONE);
		lbWords.setText("Words of the rule");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan = 2;
		lbWords.setLayoutData(gd);
		
		// Line 2
		Label lbRule = new Label(composite, SWT.NONE);
		lbRule.setText("Rule of matching");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		lbRule.setLayoutData(gd);
		
		cbRule = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY );
		cbRule.setItems(new String []{"Contains","Starts with", "Ends with"});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		cbRule.setLayoutData(gd);

		lstWords = new List (composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalSpan = 14;
		gd.horizontalSpan = 2;
		lstWords.setItems(new String []{"","","","","","","","",""});
		lstWords.setLayoutData( gd );

		Label lbNameIndications = new Label(composite, SWT.NONE);
		lbNameIndications.setText("Indication Type:");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		lbNameIndications.setLayoutData(gd);

		comboIndications = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboIndications.add(NULL);
		comboIndications.add(OPEN_CONNECTION);
		comboIndications.add(CLOSE_CONNECTION);
		comboIndications.add(ENTRY_LOGGING);
		comboIndications.add(EXIT_LOGGING);
		comboIndications.add(NOTIFY_OBSERVER);
		comboIndications.add(SINGLECLASS);
		comboIndications.add(OBSERVER_TYPE);
		comboIndications.add(SUBJECT_TYPE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		comboIndications.setLayoutData(gd);

		// Line 3
		fillCells(composite, 1, 1);
		
		chkCaseSensity = new Button(composite, SWT.CHECK);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		chkCaseSensity.setLayoutData(gd);
		chkCaseSensity.setText("Case sensitive");

		// Line 4 - 8
		fillCells(composite, 2, 6);
		
		// Line 9
		Label lbWord = new Label(composite, SWT.NONE);
		lbWord.setText("Word");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan = 2;
		lbWord.setLayoutData(gd);

		btnNew = new Button(composite, SWT.PUSH );
		btnNew.setText("&New");
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER |GridData.HORIZONTAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL );
		btnNew.setLayoutData(gd);

		// Line 10
		txtWord = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		txtWord.setLayoutData(gd);

		btnInsert = new Button(composite, SWT.PUSH );
		btnInsert.setText(sAdd);
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL );
		btnInsert.setLayoutData(gd);
		
		// Line 11
		fillCells(composite, 2, 1);
		
		btnRemove = new Button(composite, SWT.PUSH );
		btnRemove.setText("<< &Remove");
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER |GridData.HORIZONTAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL );
		btnRemove.setLayoutData(gd);
		
		setControl(composite);
		addListeners();
	}
	
	private void addListeners() {
		cbTarget.addListener(SWT.Selection, this);
		cbRule.addListener(SWT.Selection, this);
		lstWords.addListener(SWT.Selection, this);
		btnNew.addListener(SWT.MouseUp, this);
		btnInsert.addListener(SWT.MouseUp, this);
		btnRemove.addListener(SWT.MouseUp, this);
		txtWord.addListener(SWT.KeyUp, this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 * Events are handled here.
	 */
	public void handleEvent(Event event) {
		String sSelection = null;
		if ( lstWords.getSelectionIndex() != -1 )
			sSelection = lstWords.getItem(lstWords.getSelectionIndex());
		Status status = new Status(IStatus.OK, "not_used", 0, "", null);
		if ( cbTarget.getText().length() == 0 )
			status = new Status(IStatus.ERROR, "not_used", 0, 
					"Choose a target where the rule is applied to", null);
		else if ( cbRule.getText().length() == 0 )
			status = new Status(IStatus.ERROR, "not_used", 0, 
					"Choose a rule: \"Contains\", \"Starts with\" or \"Ends with\"", null);
		else if ( lstWords.getItemCount() == 0 )
			status = new Status(IStatus.ERROR, "not_used", 0, 
					"Add one or more words", null);
		
		if ( event.widget == txtWord )
			if ( txtWord.getText().length() == 0 )
				btnInsert.setEnabled(false);
			else{
				if ( rule.findWordIndex(txtWord.getText()) != -1 && 
						status.isOK())
					// Add Mode
					if ( btnInsert.getText().equals(sAdd) ) {
						status = new Status(IStatus.WARNING, "not_used", 0, 
								"\"" + txtWord.getText() +"\" can't be added because already exists", null);
						btnInsert.setEnabled(false);
					}
					// Update mode
					else if ( txtWord.getText().equals(sSelection) ) { // No problem
						btnInsert.setEnabled(true);
					}
					else {
						status = new Status(IStatus.WARNING, "not_used", 0, 
								"\"" + txtWord.getText() +"\" can't be updated because already exists", null);
						btnInsert.setEnabled(false);
					}
				else {
					btnInsert.setEnabled(true);
					if ( status.isOK() )
						if ( btnInsert.getText().equals(sAdd) )
							status = new Status(IStatus.OK, "not_used", 0, 
								"You can click Add to include the word " + txtWord.getText(), null);
						else
							status = new Status(IStatus.OK, "not_used", 0, 
									"You can click Update to change the word " + sSelection + 
									" to " + txtWord.getText(), null);
				}
				
			}
		else if ( event.widget == lstWords ) {
			txtWord.setText(sSelection);
			btnInsert.setText(sUpdate);
			btnInsert.setEnabled(true);
			btnRemove.setEnabled(true);
		}
		else if ( event.widget == btnNew )
			resetFields();
		else if ( event.widget == btnInsert )
			// Add word
			if ( btnInsert.getText().equals(sAdd) ) {
				rule.addWord( txtWord.getText() );
				lstWords.add( txtWord.getText() );
				resetFields();
				if ( status.getMessage().equals("Add one or more words") )
					status = new Status(IStatus.OK, "not_used", 0, 
							"You can click Next when you finish the rule", null);
			}
			// Update word
			else {
				if (rule.updateWord(sSelection, txtWord.getText()) ) {
					int nIndex = lstWords.getSelectionIndex();
					lstWords.remove(nIndex);
					lstWords.add(txtWord.getText(), nIndex);
					resetFields();
				}
				else
					System.err.println("Word couldn't be found and updated");
			}
		else if ( event.widget == btnRemove )
			if ( rule.removeWord(sSelection) ) {
				int nIndex = lstWords.getSelectionIndex();
				lstWords.remove(nIndex);
				if ( lstWords.getItemCount() == 0)
					status = new Status(IStatus.ERROR, "not_used", 0, 
							"Add one or more words", null);
				btnInsert.setText(sAdd);
				btnRemove.setEnabled(false);
			}
			else
				System.err.println("Word couldn't be found and removed");
		
		if (status.getMessage().length() == 0)
			status = new Status(IStatus.OK, "not_used", 0, 
					"You can click Next when you finish the rule", null);
		
		// Update interface
		applyToStatusLine(status);
		wizard.getContainer().updateButtons();
	}
	
	/**
	 * Applies the status to the status line of a dialog page.
	 */
	private void applyToStatusLine(IStatus status) {
		String message= status.getMessage();
		if (message.length() == 0) message= null;
		switch (status.getSeverity()) {
		case IStatus.OK:
			setErrorMessage(null);
			setMessage(message);
			break;
		case IStatus.WARNING:
			setErrorMessage(null);
			setMessage(message, WizardPage.WARNING);
			break;				
		case IStatus.INFO:
			setErrorMessage(null);
			setMessage(message, WizardPage.INFORMATION);
			break;			
		default:
			setErrorMessage(message);
			setMessage(message, WizardPage.ERROR);
		break;		
		}
	}

	/**
	 * Create an empty space interface 
	 * @param composite reference to composite component
	 * @param nHorizontalSpan number of horizontal cells that the empty space will fill 
	 * @param nVerticalSpan number of vertical cells that the empty space will fill
	 */
	private void fillCells(Composite composite, int nHorizontalSpan, int nVerticalSpan) {
		Label lbEmpty = new Label(composite, SWT.NONE );
		lbEmpty.setText("");
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_END | GridData.FILL_HORIZONTAL );
		gd.horizontalSpan = nHorizontalSpan;
		gd.verticalSpan = nVerticalSpan;
		lbEmpty.setLayoutData(gd);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardPage#getNextPage()
	 * It makes all the setting before go to the next page
	 */
	public IWizardPage getNextPage() {
		properties.setProperty(txtWord.getText(), comboIndications.getText());
		String sTarget = null, sRule = null;
		switch ( cbTarget.getSelectionIndex() ){
		case 0:
			sTarget = MatchText.STRING_LITERAL;
			break;
		case 1:
			sTarget = MatchText.VARIABLE_NAME;
			break;
		case 2:
			sTarget = MatchText.METHOD_NAME;
			break;
		case 3:
			sTarget = MatchText.CLASS_NAME;
			break;
		}

		switch ( cbRule.getSelectionIndex() ){
		case 0:
			sRule = MatchText.CONTAINS;
			break;
		case 1:
			sRule = MatchText.STARTS_WITH;
			break;
		case 2:
			sRule = MatchText.ENDS_WITH;
			break;
		}
		boolean bCase = chkCaseSensity.getSelection();
		rule.setTarget(sTarget);
		rule.setRule(sRule);
		rule.setCaseSensity(bCase);
		rule.setIndicationType(comboIndications.getText());
		
		if ( wizard.newRule ) {
			wizard.currentIndication.addMatch( rule );
		} else {
			int index = wizard.currentIndication.getMatchIndex(wizard.currentRule);
			if ( index == -1 ) {
				System.err.println("Can't update the rule!");
				return null;
			}
			wizard.currentIndication.updateMatch(rule,index);
		}
			
		RuleMainPage ruleMainPage = wizard.ruleMainPage;
		ruleMainPage.onEnterPage();
		return ruleMainPage;
	}

	public boolean canFlipToNextPage() {
		if (getErrorMessage() != null) {
			setErrorMessage(null);
			return false;
		}
		return true;
	}

	/**
	 * Before show this page, the interface can be set.
	 */
	public void onEnterPage() {
		rule = (MatchText)wizard.currentRule.clone();
		
		// Reset Interface
		resetFields();
		cbTarget.deselectAll();
		cbRule.deselectAll();
		chkCaseSensity.setSelection(false);
		wizard.canFinish = false;

		if ( wizard.newRule ) {
			comboIndications.setText(NULL);

			setTitle("Register of Rule");
			setMessage("Choose a target where the rule is applied to", WizardPage.ERROR);
			// Used to disable next button
			setErrorMessage("");
		}
		else {
			// Update rule was selected
			setTitle("Updating of Rule");
			setMessage("Changing the options and list of words to update rule");
			System.out.println("WWWWWWW " + rule.getTarget());
			if ( rule.getTarget().equalsIgnoreCase(MatchText.STRING_LITERAL) ) {
				cbTarget.select(0);
			} else if ( rule.getTarget().equalsIgnoreCase(MatchText.VARIABLE_NAME) ) { 
				cbTarget.select(1);
			} else if ( rule.getTarget().equalsIgnoreCase(MatchText.METHOD_NAME) ) {
				cbTarget.select(2);
			} else if ( rule.getTarget().equalsIgnoreCase( MatchText.CLASS_NAME) ) {
				cbTarget.select(3);
			}
			
			if ( rule.getRule().equalsIgnoreCase(MatchText.CONTAINS) ) {
				cbRule.select(0);
			} else if ( rule.getRule().equalsIgnoreCase(MatchText.STARTS_WITH) ) {
				cbRule.select(1);
			} else if ( rule.getRule().equalsIgnoreCase(MatchText.ENDS_WITH) ) {
				cbRule.select(2);
			}
			
			if ( rule.isCaseSensity() )
				chkCaseSensity.setSelection(true);

			String indicationType = rule.getIndicationType();
			if (indicationType != null) comboIndications.setText(indicationType);
			else comboIndications.setText(NULL);
		}

		// Insert data in interface
		lstWords.removeAll();
		ArrayList rules = rule.getWords();
		for(int i = 0; i < rules.size(); i++)
			lstWords.add((String)rules.get(i));
	}

	private void resetFields() {
		txtWord.setText("");
		btnInsert.setText(sAdd);
		btnInsert.setEnabled(false);
		btnRemove.setEnabled(false);
		lstWords.deselectAll();
	}
}
