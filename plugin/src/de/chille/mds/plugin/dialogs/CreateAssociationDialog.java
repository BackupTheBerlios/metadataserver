package de.chille.mds.plugin.dialogs;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.NewConfigurationInfo;
import org.eclipse.ui.part.DrillDownAdapter;

import de.chille.mds.plugin.tree.*;

public class CreateAssociationDialog extends org.eclipse.jface.dialogs.Dialog {

	private TreeViewer viewer, subviewer;
	private DrillDownAdapter drillDownAdapter;
	private Label end1Label, end2Label, classErrorLabel;
	private TreeClass end1Class, end2Class;
	private int end1Aggregation = 0, end2Aggregation = 0;
	private TreeModel model;
	private Button radioButton1, radioButton2, radioButton3;
	private Button radioButton4, radioButton5, radioButton6;
	private Composite composite_radioButton2, composite_radioButton;
	private String title;

	private String message;

	private String value = "";

	private IInputValidator validator;

	private Button okButton;

	private Text text;

	private Label errorMessageLabel;

	public CreateAssociationDialog(
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

		end1Label = new Label(composite, SWT.WRAP);
		if (end1Class != null) {
			end1Label.setText("Associationend 1: " + end1Class.toString());
		} else {
			end1Label.setText("Associationend 1: not selected");
		}
		end1Label.setLayoutData(new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		end1Label.setFont(parent.getFont());

		Composite radio_tab = createComposite(composite, 4);
		Label label1 = createLabel(radio_tab, "Type:");

		composite_radioButton = createComposite(radio_tab, 3);
		radioButton1 = createRadioButton(composite_radioButton, "none");
		//radioButton1.setSelection(true);
		radioButton2 = createRadioButton(composite_radioButton, "aggregation");
		radioButton3 = createRadioButton(composite_radioButton, "composition");

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

		end2Label = new Label(composite, SWT.WRAP);
		if (end2Class != null) {
			end2Label.setText("Associationend 2: " + end2Class.toString());
		} else {
			end2Label.setText("Associationend 2: not selected");
		}
		end2Label.setLayoutData(new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		end2Label.setFont(parent.getFont());

		Composite radio_tab2 = createComposite(composite, 4);
		Label label2 = createLabel(radio_tab2, "Type:");

		composite_radioButton2 = createComposite(radio_tab2, 3);
		radioButton4 = createRadioButton(composite_radioButton2, "none");
		//radioButton4.setSelection(true);
		radioButton5 = createRadioButton(composite_radioButton2, "aggregation");
		radioButton6 = createRadioButton(composite_radioButton2, "composition");

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
			errorMessage == null && end1Class != null && end2Class != null);

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
			end1Label.setText("Associationend 1: " + obj.toString());
			end1Class = (TreeClass) obj;
			validateInput();
		}
	}

	class SubSelectionChangedListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = subviewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			end2Label.setText("Associationend 2: " + obj.toString());
			end2Class = (TreeClass) obj;
			validateInput();
		}
	}

	public TreeClass getEnd2Class() {
		return end2Class;
	}

	public TreeClass getEnd1Class() {
		return end1Class;
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	private Button createRadioButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(label);
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				checkRadioSelection();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		GridData data = new GridData();
		button.setLayoutData(data);
		if (parent.equals(composite_radioButton)) {
			if (label.equals("none")) {
				button.setSelection(end1Aggregation == 0);
			} else if (label.equals("aggregation")) {
				button.setSelection(end1Aggregation == 1);
			} else {
				button.setSelection(end1Aggregation == 2);
			}
		} else {
			if (label.equals("none")) {
				button.setSelection(end2Aggregation == 0);
			} else if (label.equals("aggregation")) {
				button.setSelection(end2Aggregation == 1);
			} else {
				button.setSelection(end2Aggregation == 2);
			}
		}
		return button;
	}

	private Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		return composite;
	}

	private void checkRadioSelection() {

		if (radioButton1.getSelection())
			end1Aggregation = 0;
		else if (radioButton2.getSelection())
			end1Aggregation = 1;
		else if (radioButton3.getSelection())
			end1Aggregation = 2;
		if (radioButton4.getSelection())
			end2Aggregation = 0;
		else if (radioButton5.getSelection())
			end2Aggregation = 1;
		else if (radioButton6.getSelection())
			end2Aggregation = 2;
	} /**
								 * Returns the end1Aggregation.
								 * @return int
								 */
	public int getEnd1Aggregation() {
		return end1Aggregation;
	} /**
		 * Returns the end2Aggregation.
		 * @return int
		 */
	public int getEnd2Aggregation() {
		return end2Aggregation;
	} /**
		 * Sets the end1Aggregation.
		 * @param end1Aggregation The end1Aggregation to set
		 */
	public void setEnd1Aggregation(int end1Aggregation) {
		this.end1Aggregation = end1Aggregation;
	} /**
		 * Sets the end1Class.
		 * @param end1Class The end1Class to set
		 */
	public void setEnd1Class(TreeClass end1Class) {
		this.end1Class = end1Class;
	} /**
		 * Sets the end2Aggregation.
		 * @param end2Aggregation The end2Aggregation to set
		 */
	public void setEnd2Aggregation(int end2Aggregation) {
		this.end2Aggregation = end2Aggregation;
	} /**
		 * Sets the end2Class.
		 * @param end2Class The end2Class to set
		 */
	public void setEnd2Class(TreeClass end2Class) {
		this.end2Class = end2Class;
	}

}
