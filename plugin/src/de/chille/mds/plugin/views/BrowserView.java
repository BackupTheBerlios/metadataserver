package de.chille.mds.plugin.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;

import de.chille.mds.plugin.MDSPlugin;
import de.chille.mds.plugin.MDSPluginConstants;
import de.chille.mds.soap.MDSAssociationBean;
import de.chille.mds.soap.MDSAssociationEndBean;
import de.chille.mds.soap.MDSClassBean;
import de.chille.mds.soap.MDSGeneralizationBean;
import de.chille.mds.soap.MDSModelBean;
import de.chille.mds.soap.MDSObjectBean;
import de.chille.mds.soap.MDSRepositoryBean;
import de.chille.mds.soap.SOAPClientImpl;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import java.util.*;
import org.eclipse.core.runtime.IAdaptable;

/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class BrowserView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action createRepository;
	private Action action2;
	private Action doubleClickAction;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */

	class TreeObject implements IAdaptable {

		private MDSObjectBean bean;

		private Image image =
			PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ELEMENT);

		private TreeParent parent;

		public TreeObject(MDSObjectBean bean) {
			this.bean = bean;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		public String toString() {
			return this.bean.getLabel();
		}
		public Object getAdapter(Class key) {
			return null;
		}
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}

		/**
		 * Returns the bean.
		 * @return MDSObjectBean
		 */
		public MDSObjectBean getBean() {
			return bean;
		}

	}

	class TreeParent extends TreeObject {
		private ArrayList children;
		public TreeParent(MDSObjectBean bean) {
			super(bean);
			children = new ArrayList();
			setImage(
				PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_FOLDER));
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(
				new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size() > 0;
		}
	}

	class TreeServer extends TreeParent {
		public TreeServer(MDSObjectBean bean) {
			super(bean);
			setImage(new Image(null, MDSPluginConstants.I_SERVER));

		}

	}

	class TreeRepository extends TreeParent {
		public TreeRepository(MDSObjectBean bean) {
			super(bean);
			setImage(new Image(null, MDSPluginConstants.I_REPOSITORY));
		}

	}

	class TreeModel extends TreeParent {
		public TreeModel(MDSObjectBean bean) {
			super(bean);
		}

	}

	class TreeAssociation extends TreeParent {
		public TreeAssociation(MDSObjectBean bean) {
			super(bean);
		}

	}

	class TreeGeneralization extends TreeParent {
		public TreeGeneralization(MDSObjectBean bean) {
			super(bean);
		}

	}

	class TreeClass extends TreeObject {
		public TreeClass(MDSObjectBean bean) {
			super(bean);
		}

	}

	class ViewContentProvider
		implements IStructuredContentProvider, ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(ResourcesPlugin.getWorkspace())) {
				if (invisibleRoot == null)
					initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}
		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}
		/*
		 * We will set up a dummy model to initialize tree heararchy.
		 * In a real code, you will connect to a real model and
		 * expose its hierarchy.
		 */
		private void initialize() {

			invisibleRoot = new TreeParent(new MDSObjectBean());
			MDSObjectBean bean = new MDSObjectBean();
			bean.setLabel("server");
			TreeParent root = new TreeServer(bean);
			invisibleRoot.addChild(root);

			SOAPClientImpl.setEndPoint(
				MDSPlugin.getDefault().getPreferenceStore().getString(
					MDSPluginConstants.P_ENDPOINT));
			Vector repositories =
				(Vector) SOAPClientImpl.call("query", null, null, null);

			TreeParent rep = null, mod = null, elem = null;
			TreeObject obj = null;
			MDSRepositoryBean repository = null;
			MDSModelBean model = null;
			MDSObjectBean element = null;
			for (int i = 0; i < repositories.size(); i++) {
				repository = (MDSRepositoryBean) repositories.get(i);
				rep = new TreeRepository(repository);
				for (int j = 0; j < repository.getModels().size(); j++) {
					model = (MDSModelBean) repository.getModels().get(j);
					mod = new TreeModel(model);
					for (int k = 0; k < model.getElements().size(); k++) {
						try {
							element =
								(MDSObjectBean) model.getElements().get(k);
							if (element instanceof MDSClassBean) {
								obj = new TreeClass(element);
								mod.addChild(obj);
							} else if (element instanceof MDSAssociationBean) {
								elem = new TreeAssociation(element);
								obj =
									new TreeClass(
										(
											(MDSAssociationEndBean)
												((MDSAssociationBean) element)
											.getAssociationEnds()
											.get(0))
											.getMdsClass());
								elem.addChild(obj);
								obj =
									new TreeClass(
										(
											(MDSAssociationEndBean)
												((MDSAssociationBean) element)
											.getAssociationEnds()
											.get(1))
											.getMdsClass());
								elem.addChild(obj);
								mod.addChild(elem);
							} else if (
								element instanceof MDSGeneralizationBean) {
								elem = new TreeGeneralization(element);
								obj =
									new TreeClass(
										((MDSGeneralizationBean) element)
											.getSuperClass());
								elem.addChild(obj);
								obj =
									new TreeClass(
										((MDSGeneralizationBean) element)
											.getSubClass());
								elem.addChild(obj);

								mod.addChild(elem);
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
							System.exit(0);
						}
					}
					rep.addChild(mod);
				}
				root.addChild(rep);
			}
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			return ((TreeObject) obj).getImage();
		}
	}

	class NameSorter extends ViewerSorter {
	}

	class MDSAction extends Action {

		private String methodName;

		public MDSAction(
			String methodName,
			String text,
			ImageDescriptor image) {

			super(text, image);
			this.methodName = methodName;
		}

		public void run() {
			ViewContentProvider vcp =
				(ViewContentProvider) viewer.getContentProvider();
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();

			if (methodName.equals("createRepository")) {
				SOAPClientImpl.setEndPoint(
					MDSPlugin.getDefault().getPreferenceStore().getString(
						MDSPluginConstants.P_ENDPOINT));

				InputDialog id =
					new InputDialog(
						getSite().getShell(),
						"Create New Repository",
						"Repositoryname:",
						"",
						new LabelValidator());
				id.open();
				if (id.getReturnCode() == InputDialog.OK) {
					MDSRepositoryBean repository = new MDSRepositoryBean();
					repository.setLabel(id.getValue());
					repository =
						(MDSRepositoryBean) SOAPClientImpl.call(
							methodName,
							"repository",
							MDSRepositoryBean.class,
							repository);
					TreeObject[] invisibleRootChilds =
						vcp.invisibleRoot.getChildren();
					((TreeParent) invisibleRootChilds[0]).addChild(
						new TreeRepository(repository));
					viewer.refresh();
				}

			} else
				showMessage(methodName + "::" + obj.toString());
		}
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
			if (obj instanceof TreeServer) {
				createRepository.setEnabled(true);
			} else {
				createRepository.setEnabled(false);
			}
		}
	}

	/**
	 * The constructor.
	 */
	public BrowserView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer =
			new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(ResourcesPlugin.getWorkspace());
		makeActions();
		viewer.addSelectionChangedListener(new MDSSelectionChangedListener());
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				BrowserView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(createRepository);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(createRepository);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator("Additions"));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(createRepository);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		createRepository =
			new MDSAction(
				"createRepository",
				"createRepository",
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
					ISharedImages.IMG_OBJS_INFO_TSK));
		createRepository.setToolTipText("create a new repository");

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(
			PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_OBJS_TASK_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj =
					((IStructuredSelection) selection).getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"metadata.server",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}