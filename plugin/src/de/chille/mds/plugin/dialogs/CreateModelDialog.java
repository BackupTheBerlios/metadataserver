package de.chille.mds.plugin.dialogs;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.dialogs.ViewContentProvider;
import org.eclipse.ui.part.DrillDownAdapter;

import de.chille.mds.plugin.tree.*;

public class CreateModelDialog extends InputDialog {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Label metamodelLabel;
	private TreeModel metamodel = null;
	private String metamodelName = null;
	
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

		metamodelLabel = new Label(composite, SWT.WRAP);
		if (metamodelName != null) {
			metamodelLabel.setText("Metamodel: " + metamodelName);
		} else {
			metamodelLabel.setText("Metamodel: not selected");
		}
		GridData data = new GridData(GridData.GRAB_VERTICAL);
		metamodelLabel.setLayoutData(data);
		metamodelLabel.setFont(parent.getFont());

		viewer =
			new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new TreeLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(ResourcesPlugin.getWorkspace());
		viewer.addSelectionChangedListener(new MDSSelectionChangedListener());

		viewer.addFilter(new ModelFilter());
		GridData mydata =
			new GridData(
				GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL
					| GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
		mydata.heightHint = 100;
		viewer.getTree().setLayoutData(mydata);
		return composite;
	}

	class ModelFilter extends ViewerFilter {

		public boolean select(
			Viewer viewer,
			Object parentElement,
			Object element) {
			if (element instanceof TreeServer
				|| element instanceof TreeRepository
				|| element instanceof TreeModel) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	class MDSSelectionChangedListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof TreeModel) {
				metamodelLabel.setText("Metamodel: "+ obj.toString());
				metamodel = (TreeModel)obj;	
			} else {
				metamodelLabel.setText("Metamodel: not selected");
				metamodelName = null;	
			}
			viewer.refresh();
		}
	}

	/**
	 * Returns the metamodel.
	 * @return TreeModel
	 */
	public TreeModel getMetamodel() {
		return metamodel;
	}


	/**
	 * Sets the metamodelHref.
	 * @param metamodelHref The metamodelHref to set
	 */
	public void setMetamodelName(String metamodelHref) {
		this.metamodelName = metamodelHref;
	}

	/**
	 * Returns the metamodelName.
	 * @return String
	 */
	public String getMetamodelName() {
		return metamodelName;
	}

}
