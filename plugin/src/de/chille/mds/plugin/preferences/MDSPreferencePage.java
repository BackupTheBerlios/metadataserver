package de.chille.mds.plugin.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import de.chille.mds.plugin.MDSPlugin;
import de.chille.mds.plugin.MDSPluginConstants;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class MDSPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	/*public static final String P_PATH = "pathPreference";
	public static final String P_BOOLEAN = "booleanPreference";
	public static final String P_CHOICE = "choicePreference";*/
	public MDSPreferencePage() {
		super(GRID);
		setPreferenceStore(MDSPlugin.getDefault().getPreferenceStore());
		setDescription("Set metadata.server Preferences");
		initializeDefaults();
	}
	/**
	 * Sets the default values of the preferences.
	 */
	private void initializeDefaults() {
		IPreferenceStore store = getPreferenceStore();
		/*store.setDefault(P_BOOLEAN, true);
		store.setDefault(P_CHOICE, "choice2");*/
		store.setDefault(
			MDSPluginConstants.P_ENDPOINT,
			"http://localhost:8080/soap/servlet/rpcrouter");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */

	public void createFieldEditors() {
		/*addField(new DirectoryFieldEditor(P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));
		
		addField(new RadioGroupFieldEditor(
			P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));*/
		addField(
			new StringFieldEditor(
				MDSPluginConstants.P_ENDPOINT,
				"&SOAP_Endpoint:",
				getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}

	protected IPreferenceStore doGetPreferences() {
		return MDSPlugin.getDefault().getPreferenceStore();
	}
}