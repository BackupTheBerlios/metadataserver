package de.chille.mds.plugin.dialogs;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.DrillDownAdapter;

import de.chille.mds.plugin.tree.*;

public class CreateGeneralizationDialog
	extends org.eclipse.jface.dialogs.Dialog {

	private TreeViewer viewer, subviewer;
	private DrillDownAdapter drillDownAdapter;
	private Label superLabel, subLabel, classErrorLabel;
	private TreeClass superClass = null, subClass = null;
	private TreeModel model;

	private String title;

	private String message;

	private String value = "";

	private IInputValidator validator;

	private Button okButton;

	private Text text;

	private Label errorMessageLabel;

	public CreateGeneralizationDialog(
		Shell parentShell,
		String dialogTitle,
		String dialogMessage,
		String initialValue,
		IInputValidator validator,
		TreeModel model) {
		super(parentShell);
		this.title = dialogTitle;
		message = dialogMessage;
		if (initialValue == null)
			value = "";
		else
			value = initialValue;
		this.validator = validator;

		this.model = model;
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			value = text.getText();
		} else {
			value = null;
		}
		super.buttonPressed(buttonId);
	}
	/* (non-Javadoc)
	 * Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null)
			shell.setText(title);
	}
	/* (non-Javadoc)
	 * Method declared on Dialog.
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		okButton =
			createButton(
				parent,
				IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL,
				true);
		createButton(
			parent,
			IDialogConstants.CANCEL_ID,
			IDialogConstants.CANCEL_LABEL,
			false);

		text.setFocus();
		if (value != null) {
			text.setText(value);
			text.selectAll();
		}
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		if (message != null) {
			Label label = new Label(composite, SWT.WRAP);
			label.setText(message);
			GridData data =
				new GridData(
					GridData.GRAB_HORIZONTAL
						| GridData.GRAB_VERTICAL
						| GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_CENTER);
			data.widthHint =
				convertHorizontalDLUsToPixels(
					IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			;
			label.setLayoutData(data);
			label.setFont(parent.getFont());
		}

		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(
			new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		errorMessageLabel = new Label(composite, SWT.NONE);
		errorMessageLabel.setLayoutData(
			new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		errorMessageLabel.setFont(parent.getFont());

		superLabel = new Label(composite, SWT.WRAP);
		if (superClass != null) {
			superLabel.setText("Superclass: " + superClass.toString());
		} else {
			superLabel.setText("Superclass: not selected");
		}
		superLabel.setLayoutData(new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		superLabel.setFont(parent.getFont());

		viewer = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new TreeLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(ResourcesPlugin.getWorkspace());
		viewer.addSelectionChangedListener(new SuperSelectionChangedListener());
		viewer.addFilter(new ModelFilter());
		GridData mydata =
			new GridData(
				GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL
					| GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
		mydata.heightHint = 100;
		viewer.getTree().setLayoutData(mydata);
		drillDownAdapter.goInto(model);

		subLabel = new Label(composite, SWT.WRAP);
		if (subClass != null) {
			subLabel.setText("Subclass: " + subClass.toString());
		} else {
			subLabel.setText("Subclass: not selected");
		}
		subLabel.setLayoutData(new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		subLabel.setFont(parent.getFont());

		subviewer = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(subviewer);
		subviewer.setContentProvider(new TreeContentProvider());
		subviewer.setLabelProvider(new TreeLabelProvider());
		subviewer.setSorter(new NameSorter());
		subviewer.setInput(ResourcesPlugin.getWorkspace());
		subviewer.addSelectionChangedListener(
			new SubSelectionChangedListener());
		subviewer.addFilter(new ModelFilter());
		GridData submydata =
			new GridData(
				GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL
					| GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
		submydata.heightHint = 100;
		subviewer.getTree().setLayoutData(submydata);
		drillDownAdapter.goInto(model);

		return composite;
	}

	protected Label getErrorMessageLabel() {
		return errorMessageLabel;
	}

	protected Button getOkButton() {
		return okButton;
	}

	protected Text getText() {
		return text;
	}

	protected IInputValidator getValidator() {
		return validator;
	}

	public String getValue() {
		return value;
	}

	protected void validateInput() {

		String errorMessage = null;

		if (validator != null) {
			errorMessage = validator.isValid(text.getText());
		}

		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage);

		okButton.setEnabled(
			errorMessage == null && superClass != null && subClass != null);

		errorMessageLabel.getParent().update();

	}

	class ModelFilter extends ViewerFilter {

		public boolean select(
			Viewer viewer,
			Object parentElement,
			Object element) {
			if (element instanceof TreeClass) {
				return true;
			} else {
				return false;
			}
		}
	}

	class SuperSelectionChangedListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			superLabel.setText("Superclass: " + obj.toString());
			superLabel.getParent().update();
			superClass = (TreeClass) obj;
			validateInput();
		}
	}

	class SubSelectionChangedListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = subviewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			subLabel.setText("Subclass: " + obj.toString());
			subLabel.getParent().update();
			subClass = (TreeClass) obj;
			validateInput();
		}
	}

	public TreeClass getSubClass() {
		return subClass;
	}

	public TreeClass getSuperClass() {
		return superClass;
	}

	/**
	 * Sets the subClass.
	 * @param subClass The subClass to set
	 */
	public void setSubClass(TreeClass subClass) {
		this.subClass = subClass;
	}

	/**
	 * Sets the superClass.
	 * @param superClass The superClass to set
	 */
	public void setSuperClass(TreeClass superClass) {
		this.superClass = superClass;
	}

}
