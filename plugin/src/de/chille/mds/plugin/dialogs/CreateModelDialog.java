package de.chille.mds.plugin.dialogs;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.dialogs.ViewContentProvider;
import org.eclipse.ui.part.DrillDownAdapter;

import de.chille.mds.plugin.tree.*;

public class CreateModelDialog extends InputDialog {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action createRepository, createModel;

	/**
	 * Constructor for CreateModelDialog.
	 * @param parentShell
	 * @param dialogTitle
	 * @param dialogMessage
	 * @param initialValue
	 * @param validator
	 */
	public CreateModelDialog(
		Shell parentShell,
		String dialogTitle,
		String dialogMessage,
		String initialValue,
		IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Label label = new Label(composite, SWT.WRAP);
		label.setText("Metamodel:");
		GridData data =
			new GridData(
				GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL
					| GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
		label.setLayoutData(data);
		label.setFont(parent.getFont());

		viewer =
			new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(ResourcesPlugin.getWorkspace());
		//makeActions();
		viewer.addSelectionChangedListener(new MDSSelectionChangedListener());
		//hookContextMenu();
		//hookDoubleClickAction();
		//contributeToActionBars();

		/*
		Label = new Label(composite, SWT.NONE);
		errorMessageLabel.setLayoutData(
			new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		errorMessageLabel.setFont(parent.getFont());
		*/
		return composite;
	}
	
	class LabelValidator implements IInputValidator {
		public String isValid(String newText) {
			if (newText.length() == 0) {
				return "";
			} else if (newText.length() > 50) {
				return "Maximum are 50 Chars.";
			} else if (!newText.matches("[0-9a-zA-Z_]*")) {
				return "Please Use Only Alphanumeric Chars.";
			} else {
				return null;
			}
		}
	}

	class MDSSelectionChangedListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof TreeRepository) {
				createRepository.setEnabled(false);
				createModel.setEnabled(true);
			} else {
				createRepository.setEnabled(true);
				createModel.setEnabled(false);
			}
		}
	}

}
