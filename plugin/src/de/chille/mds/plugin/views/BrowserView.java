package de.chille.mds.plugin.views;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import de.chille.mds.plugin.MDSPlugin;
import de.chille.mds.plugin.MDSPluginConstants;
import de.chille.mds.plugin.Util;
import de.chille.mds.plugin.dialogs.CreateAssociationDialog;
import de.chille.mds.plugin.dialogs.CreateGeneralizationDialog;
import de.chille.mds.plugin.dialogs.CreateModelDialog;
import de.chille.mds.plugin.tree.*;
import de.chille.mds.plugin.validator.LabelValidator;
import de.chille.mds.soap.*;

public class BrowserView extends ViewPart {
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action createRepository, createModel, createAssociation;
	private Action createClass, createGeneralization, delete, update;
	private Action validate, createJava, importJava;

	//private Action action2;
	//private Action doubleClickAction;

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
			TreeContentProvider vcp =
				(TreeContentProvider) viewer.getContentProvider();
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			SOAPClientImpl.setEndPoint(
				MDSPlugin.getDefault().getPreferenceStore().getString(
					MDSPluginConstants.P_ENDPOINT));

			if (methodName.equals("createRepository")) {
				InputDialog dialog =
					new InputDialog(
						getSite().getShell(),
						"Create New Repository",
						"Repositoryname:",
						"",
						new LabelValidator());
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK) {
					MDSRepositoryBean repository = new MDSRepositoryBean();
					repository.setLabel(dialog.getValue());
					String[] paramName = { "repository" };
					Class[] paramType = { MDSRepositoryBean.class };
					Object[] paramValue = { repository };
					try {
						repository =
							(MDSRepositoryBean) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					TreeObject[] invisibleRootChilds =
						vcp.invisibleRoot.getChildren();
					((TreeParent) invisibleRootChilds[0]).addChild(
						new TreeRepository(repository));
				}

			} else if (methodName.equals("createModel")) {
				CreateModelDialog dialog =
					new CreateModelDialog(
						getSite().getShell(),
						"Create New Model In Repository '"
							+ obj.toString()
							+ "'",
						"Modelname:",
						"",
						new LabelValidator());
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK) {
					TreeRepository repository = (TreeRepository) obj;
					MDSModelBean model = new MDSModelBean();
					model.setLabel(dialog.getValue());
					if (dialog.getMetamodel() != null) {
						model.setMetamodelHref(
							dialog.getMetamodel().getBean().getHref());
						model.setMetamodelName(
							((MDSModelBean) dialog.getMetamodel().getBean())
								.getMetamodelName());
					}
					String[] paramName = { "href", "model" };
					Class[] paramType = { String.class, MDSModelBean.class };
					Object[] paramValue =
						{ repository.getBean().getHref(), model };
					try {
						model =
							(MDSModelBean) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					repository.addChild(new TreeModel(model));
				}

			} else if (methodName.equals("createClass")) {
				InputDialog dialog =
					new InputDialog(
						getSite().getShell(),
						"Create New Class In Model '" + obj.toString() + "'",
						"Classname:",
						"",
						new LabelValidator());
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK) {
					TreeModel model = (TreeModel) obj;
					MDSClassBean classb = new MDSClassBean();
					classb.setLabel(dialog.getValue());
					String[] paramName = { "href", "class" };
					Class[] paramType = { String.class, MDSClassBean.class };
					Object[] paramValue = { model.getBean().getHref(), classb };
					try {
						classb =
							(MDSClassBean) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					model.addChild(new TreeClass(classb));
					((MDSModelBean) model.getBean()).getElements().add(classb);
					if (model.getChildren().length == 2) {
						createAssociation.setEnabled(true);
						createGeneralization.setEnabled(true);
					} else if (model.getChildren().length == 1) {
						createJava.setEnabled(true);
					}
				}

			} else if (methodName.equals("createGeneralization")) {
				TreeModel model = (TreeModel) obj;
				CreateGeneralizationDialog dialog =
					new CreateGeneralizationDialog(
						getSite().getShell(),
						"Create New Generalization In Model '"
							+ obj.toString()
							+ "'",
						"Generalizationname:",
						"",
						new LabelValidator(),
						model);
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK) {
					MDSGeneralizationBean generalization =
						new MDSGeneralizationBean();
					generalization.setLabel(dialog.getValue());
					generalization.setSuperClass(
						(MDSClassBean) dialog.getSuperClass().getBean());
					generalization.setSubClass(
						(MDSClassBean) dialog.getSubClass().getBean());
					String[] paramName = { "href", "generalization" };
					Class[] paramType =
						{ String.class, MDSGeneralizationBean.class };
					Object[] paramValue =
						{ model.getBean().getHref(), generalization };
					try {
						generalization =
							(MDSGeneralizationBean) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					TreeGeneralization treeGeneralization =
						new TreeGeneralization(generalization);
					treeGeneralization.addChild(
						new TreeClass(dialog.getSuperClass().getBean()));
					treeGeneralization.addChild(
						new TreeClass(dialog.getSubClass().getBean()));
					model.addChild(treeGeneralization);
					((MDSModelBean) model.getBean()).getElements().add(
						generalization);
				}

			} else if (methodName.equals("createAssociation")) {
				TreeModel model = (TreeModel) obj;
				CreateAssociationDialog dialog =
					new CreateAssociationDialog(
						getSite().getShell(),
						"Create New Association In Model '"
							+ obj.toString()
							+ "'",
						"Associationname:",
						"",
						new LabelValidator(),
						model);
				dialog.open();
				if (dialog.getReturnCode() == InputDialog.OK) {
					MDSAssociationBean association = new MDSAssociationBean();
					association.setLabel(dialog.getValue());
					MDSAssociationEndBean end1 = new MDSAssociationEndBean();
					end1.setAggregation(dialog.getEnd1Aggregation());
					end1.setMdsClass(
						(MDSClassBean) dialog.getEnd1Class().getBean());
					MDSAssociationEndBean end2 = new MDSAssociationEndBean();
					end2.setAggregation(dialog.getEnd2Aggregation());
					end2.setMdsClass(
						(MDSClassBean) dialog.getEnd2Class().getBean());
					Vector ends = new Vector();
					ends.add(end1);
					ends.add(end2);
					association.setAssociationEnds(ends);
					String[] paramName = { "href", "association" };
					Class[] paramType =
						{ String.class, MDSAssociationBean.class };
					Object[] paramValue =
						{ model.getBean().getHref(), association };
					try {
						association =
							(MDSAssociationBean) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					TreeAssociation treeAssociation =
						new TreeAssociation(association);
					treeAssociation.addChild(
						new TreeClass(dialog.getEnd1Class().getBean()));
					treeAssociation.addChild(
						new TreeClass(dialog.getEnd2Class().getBean()));
					model.addChild(treeAssociation);
					((MDSModelBean) model.getBean()).getElements().add(
						association);
				}

			} else if (methodName.equals("validateModel")) {
				TreeModel model = (TreeModel) obj;

				String[] paramName = { "href", "type" };
				Class[] paramType = { String.class, int.class };
				Object[] paramValue =
					{ model.getBean().getHref(), new Integer(0)};
				Vector result = new Vector();
				try {
					result =
						(Vector) SOAPClientImpl.call(
							methodName,
							paramName,
							paramType,
							paramValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (result.size() == 0) {
					MessageDialog.openInformation(
						getSite().getShell(),
						"Validate Model",
						"OK");
				} else {
					MultiStatus multiStatus =
						new MultiStatus(
							"de.chille.mds.plugin",
							0,
							"Open Details for more information",
							null);
					Status status = null;
					Iterator i = result.iterator();
					while (i.hasNext()) {
						multiStatus.add(
							new Status(
								Status.ERROR,
								"de.chille.mds.plugin",
								0,
								(String) i.next(),
								null));
					}
					ErrorDialog.openError(
						getSite().getShell(),
						"Validate Model",
						"Model Is Not Valide",
						multiStatus);
				}
			} else if (methodName.equals("delete")) {
				if (obj instanceof TreeGeneralization) {
					TreeGeneralization generalization =
						(TreeGeneralization) obj;
					if (MessageDialog
						.openConfirm(
							getSite().getShell(),
							"Delete Generalization",
							"You really want delete the generalization '"
								+ obj.toString()
								+ "'?")) {
						String[] paramName = { "href", "confirm" };
						Class[] paramType = { String.class, boolean.class };
						Object[] paramValue =
							{
								((TreeGeneralization) obj).getBean().getHref(),
								new Boolean(false)};
						Vector result = new Vector();
						try {
							result =
								(Vector) SOAPClientImpl.call(
									methodName,
									paramName,
									paramType,
									paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						generalization.getParent().removeChild(generalization);
					}
				} else if (obj instanceof TreeAssociation) {
					TreeAssociation association = (TreeAssociation) obj;
					if (MessageDialog
						.openConfirm(
							getSite().getShell(),
							"Delete Association",
							"You really want delete the association '"
								+ obj.toString()
								+ "'?")) {
						String[] paramName = { "href", "confirm" };
						Class[] paramType = { String.class, boolean.class };
						Object[] paramValue =
							{
								association.getBean().getHref(),
								new Boolean(false)};
						Vector result = new Vector();
						try {
							result =
								(Vector) SOAPClientImpl.call(
									methodName,
									paramName,
									paramType,
									paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						association.getParent().removeChild(association);
					}
				} else if (obj instanceof TreeClass) {
					TreeClass tclass = (TreeClass) obj;
					String[] paramName = { "href", "confirm" };
					Class[] paramType = { String.class, boolean.class };
					Object[] paramValue =
						{ tclass.getBean().getHref(), new Boolean(true)};
					Vector result = new Vector();
					try {
						result =
							(Vector) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					MultiStatus multiStatus =
						new MultiStatus(
							"de.chille.mds.plugin",
							0,
							"Some relations will be deleted too.\n Open Details for more information",
							null);
					Iterator i = result.iterator();
					while (i.hasNext()) {
						multiStatus.add(
							new Status(
								Status.WARNING,
								"de.chille.mds.plugin",
								0,
								(String) i.next(),
								null));
					}
					boolean doit = false;
					if (result.size() > 0) {
						if (ErrorDialog
							.openError(
								getSite().getShell(),
								"Delete Class",
								"Removing this class could have side-effects on other elements.",
								multiStatus)
							== Dialog.OK) {
							doit = true;
						}
					} else if (
						MessageDialog.openConfirm(
							getSite().getShell(),
							"Delete Class",
							"You really want delete the class '"
								+ obj.toString()
								+ "'?")) {
						doit = true;
					}
					if (doit == true) {
						paramValue[1] = new Boolean(false);
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						TreeModel model = (TreeModel) tclass.getParent();
						model.removeChild(tclass);
						Iterator it = result.iterator();
						TreeObject[] children = model.getChildren();
						TreeObject relation = null;
						while (it.hasNext()) {
							String href = (String) it.next();
							for (int j = 0; j < children.length; j++) {
								relation = children[j];
								if (href
									.equals(relation.getBean().getHref())) {
									model.removeChild(relation);
								}
							}
						}
					}

				} else if (obj instanceof TreeModel) {
					TreeModel model = (TreeModel) obj;
					String[] paramName = { "href", "confirm" };
					Class[] paramType = { String.class, boolean.class };
					Object[] paramValue =
						{ model.getBean().getHref(), new Boolean(true)};
					Vector result = new Vector();
					try {
						result =
							(Vector) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					MultiStatus multiStatus =
						new MultiStatus(
							"de.chille.mds.plugin",
							0,
							"Some metamodel-entries will be deleted too.\n Open Details for more information",
							null);
					Iterator i = result.iterator();
					while (i.hasNext()) {
						multiStatus.add(
							new Status(
								Status.WARNING,
								"de.chille.mds.plugin",
								0,
								(String) i.next(),
								null));
					}
					boolean doit = false;
					if (result.size() > 0) {
						if (ErrorDialog
							.openError(
								getSite().getShell(),
								"Delete Model",
								"Removing this model could have side-effects on other models.",
								multiStatus)
							== Dialog.OK) {
							doit = true;
						}
					} else if (
						MessageDialog.openConfirm(
							getSite().getShell(),
							"Delete Model",
							"You really want delete the model '"
								+ obj.toString()
								+ "'?")) {
						doit = true;
					}
					if (doit == true) {
						paramValue[1] = new Boolean(false);
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						TreeObject repository = (TreeObject) model.getParent();
						((TreeRepository) repository).removeChild(model);

						TreeServer server = (TreeServer) repository.getParent();
						TreeObject[] repositories = server.getChildren();
						TreeObject[] models = null;
						Iterator it = result.iterator();
						while (it.hasNext()) {
							String href = (String) it.next();
							for (int k = 0; k < repositories.length; k++) {
								repository = repositories[k];
								models =
									((TreeRepository) repository).getChildren();
								for (int j = 0; j < models.length; j++) {
									model = (TreeModel) models[j];
									if (href
										.equals(
											((MDSModelBean) model.getBean())
												.getMetamodelHref())) {
										(
											(MDSModelBean) model
												.getBean())
												.setMetamodelHref(
											null);
									}
								}
							}
						}
					}

				} else if (obj instanceof TreeRepository) {
					TreeRepository repository = (TreeRepository) obj;
					String[] paramName = { "href", "confirm" };
					Class[] paramType = { String.class, boolean.class };
					Object[] paramValue =
						{ repository.getBean().getHref(), new Boolean(true)};
					Vector result = new Vector();
					try {
						result =
							(Vector) SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					MultiStatus multiStatus =
						new MultiStatus(
							"de.chille.mds.plugin",
							0,
							"Some metamodel-entries will be deleted too.\n Open Details for more information",
							null);
					Iterator i = result.iterator();
					while (i.hasNext()) {
						multiStatus.add(
							new Status(
								Status.WARNING,
								"de.chille.mds.plugin",
								0,
								(String) i.next(),
								null));
					}
					boolean doit = false;
					if (result.size() > 0) {
						if (ErrorDialog
							.openError(
								getSite().getShell(),
								"Delete Repository",
								"Removing this repository could have side-effects on other models.",
								multiStatus)
							== Dialog.OK) {
							doit = true;
						}
					} else if (
						MessageDialog.openConfirm(
							getSite().getShell(),
							"Delete Model",
							"You really want delete the repository '"
								+ obj.toString()
								+ "'?")) {
						doit = true;
					}
					if (doit == true) {
						paramValue[1] = new Boolean(false);
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						TreeServer server = (TreeServer) repository.getParent();
						server.removeChild(repository);
						TreeObject[] repositories = server.getChildren();
						TreeObject[] models = null;
						TreeModel model = null;
						Iterator it = result.iterator();
						while (it.hasNext()) {
							String href = (String) it.next();
							for (int k = 0; k < repositories.length; k++) {
								repository = (TreeRepository) repositories[k];
								models =
									((TreeRepository) repository).getChildren();
								for (int j = 0; j < models.length; j++) {
									model = (TreeModel) models[j];
									if (href
										.equals(
											((MDSModelBean) model.getBean())
												.getMetamodelHref())) {
										(
											(MDSModelBean) model
												.getBean())
												.setMetamodelHref(
											null);
									}
								}
							}
						}
					}

				}
			} else if (methodName.equals("updateElement")) {
				if (obj instanceof TreeClass) {
					TreeClass tclass = (TreeClass) obj;

					InputDialog dialog =
						new InputDialog(
							getSite().getShell(),
							"Update Class Properties",
							"Classname:",
							obj.toString(),
							new LabelValidator());
					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						MDSClassBean classb = (MDSClassBean) tclass.getBean();
						classb.setLabel(dialog.getValue());
						String[] paramName = { "bean" };
						Class[] paramType = { MDSClassBean.class };
						Object[] paramValue = { classb };
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (obj instanceof TreeAssociation) {
					MDSAssociationBean bean =
						(MDSAssociationBean) ((TreeAssociation) obj).getBean();
					CreateAssociationDialog dialog =
						new CreateAssociationDialog(
							getSite().getShell(),
							"Update Association Properties",
							"Associationname:",
							obj.toString(),
							new LabelValidator(),
							(TreeModel) ((TreeAssociation) obj).getParent());
					dialog.setEnd1Aggregation(
						((MDSAssociationEndBean) bean
							.getAssociationEnds()
							.get(0))
							.getAggregation());
					dialog.setEnd2Aggregation(
						((MDSAssociationEndBean) bean
							.getAssociationEnds()
							.get(1))
							.getAggregation());
					TreeObject[] endClasses =
						((TreeAssociation) obj).getChildren();
					dialog.setEnd1Class((TreeClass) endClasses[0]);
					dialog.setEnd2Class((TreeClass) endClasses[1]);
					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						bean.setLabel(dialog.getValue());
						MDSAssociationEndBean end1 =
							(MDSAssociationEndBean) bean
								.getAssociationEnds()
								.get(
								0);
						end1.setAggregation(dialog.getEnd1Aggregation());
						end1.setMdsClass(
							(MDSClassBean) dialog.getEnd1Class().getBean());
						((TreeAssociation) obj).removeChild(endClasses[0]);
						((TreeAssociation) obj).addChild(dialog.getEnd1Class());
						MDSAssociationEndBean end2 =
							(MDSAssociationEndBean) bean
								.getAssociationEnds()
								.get(
								1);
						end2.setAggregation(dialog.getEnd2Aggregation());
						end2.setMdsClass(
							(MDSClassBean) dialog.getEnd2Class().getBean());
						((TreeAssociation) obj).removeChild(endClasses[1]);
						((TreeAssociation) obj).addChild(dialog.getEnd2Class());
						Vector ends = new Vector();
						String[] paramName = { "bean" };
						Class[] paramType = { MDSAssociationBean.class };
						Object[] paramValue = { bean };
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				} else if (obj instanceof TreeGeneralization) {
					TreeGeneralization generalization =
						(TreeGeneralization) obj;
					CreateGeneralizationDialog dialog =
						new CreateGeneralizationDialog(
							getSite().getShell(),
							"Update Generalization Properties",
							"Generalizationname:",
							generalization.toString(),
							new LabelValidator(),
							(TreeModel) generalization.getParent());
					TreeObject[] classes = generalization.getChildren();
					dialog.setSuperClass((TreeClass) classes[0]);
					dialog.setSubClass((TreeClass) classes[1]);
					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						MDSGeneralizationBean bean =
							(MDSGeneralizationBean) generalization.getBean();
						bean.setLabel(dialog.getValue());
						bean.setSuperClass(
							(MDSClassBean) dialog.getSuperClass().getBean());
						bean.setSubClass(
							(MDSClassBean) dialog.getSubClass().getBean());
						String[] paramName = { "bean" };
						Class[] paramType = { MDSGeneralizationBean.class };
						Object[] paramValue = { bean };
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						generalization.removeChild(classes[0]);
						generalization.removeChild(classes[1]);
						generalization.addChild(dialog.getSuperClass());
						generalization.addChild(dialog.getSubClass());
					}
				} else if (obj instanceof TreeModel) {
					TreeModel model = (TreeModel) obj;
					CreateModelDialog dialog =
						new CreateModelDialog(
							getSite().getShell(),
							"Update Model Properties",
							"Modelname:",
							obj.toString(),
							new LabelValidator());
					if (((MDSModelBean) model.getBean()).getMetamodelHref()
						!= null) {
						dialog.setMetamodelName(
							((MDSModelBean) model.getBean())
								.getMetamodelName());
					}

					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						MDSModelBean bean = (MDSModelBean) model.getBean();
						bean.setLabel(dialog.getValue());
						if (dialog.getMetamodel() != null) {
							bean.setMetamodelHref(
								dialog.getMetamodel().getBean().getHref());
							bean.setMetamodelName(
								((MDSModelBean) dialog
									.getMetamodel()
									.getBean())
									.getLabel());
						} else if (dialog.getMetamodelName() == null) {
							bean.setMetamodelHref(null);
							bean.setMetamodelName(null);
						}
						String[] paramName = { "bean" };
						Class[] paramType = { MDSModelBean.class };
						Object[] paramValue = { bean };
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (obj instanceof TreeRepository) {
					InputDialog dialog =
						new InputDialog(
							getSite().getShell(),
							"Update Repository Properties",
							"Repositoryname:",
							obj.toString(),
							new LabelValidator());
					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						MDSRepositoryBean repository =
							(MDSRepositoryBean) ((TreeRepository) obj)
								.getBean();
						repository.setLabel(dialog.getValue());
						String[] paramName = { "bean" };
						Class[] paramType = { MDSRepositoryBean.class };
						Object[] paramValue = { repository };
						try {
							SOAPClientImpl.call(
								methodName,
								paramName,
								paramType,
								paramValue);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			} else if (methodName.equals("createJava")) {
				try {
					MDSModelBean model =
						(MDSModelBean) ((TreeModel) obj).getBean();
					InputDialog dialog =
						new InputDialog(
							getSite().getShell(),
							"Create Model With Javacode",
							"Modelname:",
							model.getLabel() + "_java",
							new LabelValidator());
					dialog.open();
					if (dialog.getReturnCode() == InputDialog.OK) {
						// create instance of model
						String href = model.getHref();
						String[] paramName1 = { "from", "to", "label" };
						Class[] paramType1 =
							{ String.class, String.class, String.class };
						Object[] paramValue1 =
							{
								href,
								((TreeModel) obj)
									.getParent()
									.getBean()
									.getHref(),
								dialog.getValue()};
						String copyHref =
							(String) SOAPClientImpl.call(
								"copyModel",
								paramName1,
								paramType1,
								paramValue1);
						// generate javacode
						String[] paramName2 = { "href", "mapFrom", "mapTo" };
						Class[] paramType2 =
							{ String.class, String.class, String.class };
						Object[] paramValue2 = { copyHref, "mds", "java" };
						MDSModelBean newModel =
							(MDSModelBean) SOAPClientImpl.call(
								"convertModel",
								paramName2,
								paramType2,
								paramValue2);
						Util.getInstance().addModel(
							(TreeRepository) ((TreeModel) obj).getParent(),
							newModel);
						// extract files
						DirectoryDialog saveDialog =
							new DirectoryDialog(getSite().getShell());
						saveDialog.setMessage(
							"Please point the directory to save the java-sourcefiles.");
						String dir = saveDialog.open();
						if (dir != null) {
							Iterator it =
								newModel.getAdditionalFiles().iterator();
							FileWriter fw;
							while (it.hasNext()) {
								MDSFileBean file = (MDSFileBean) it.next();
								fw = new FileWriter(dir + "/" + file.getPath());
								fw.write(file.getContent());
								fw.close();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (methodName.equals("importJava")) {
				try {
					DirectoryDialog dialog =
						new DirectoryDialog(getSite().getShell());
					dialog.setMessage(
						"Please point to the directory containing the java-sourcefiles.");
					String dir = dialog.open();
					if (dir != null) {
						MDSModelBean model =
							(MDSModelBean) ((TreeModel) obj).getBean();
						Util.getInstance().addFiles(model, dir);
						model.setElements(new Vector());
						String[] paramName = { "bean" };
						Class[] paramType = { MDSModelBean.class };
						Object[] paramValue = { model };
						SOAPClientImpl.call(
							"updateElement",
							paramName,
							paramType,
							paramValue);
						MDSModelBean newModel =
							(MDSModelBean) SOAPClientImpl.call(
								"convertModel",
								new String[] { "href", "mapFrom", "mapTo" },
								new Class[] {
									String.class,
									String.class,
									String.class },
								new Object[] {
									model.getHref(),
									"java",
									"mds" });
						if (!newModel.getId().equals("error")) {
							TreeRepository rep =
								(TreeRepository) ((TreeModel) obj).getParent();
							rep.removeChild((TreeModel) obj);
							Util.getInstance().addModel(rep, newModel);
						} else {
							String msg;
							if (newModel.getHref().equals("exception")) {
								msg =
									"Error.\n Open Details for more information.";
							} else {
								msg =
									"Parsererror in file '"
										+ newModel.getHref()
										+ "'.\n Open Details for more information";
							}
							MultiStatus multiStatus =
								new MultiStatus(
									"de.chille.mds.plugin",
									0,
									msg,
									null);
							String[] lines = newModel.getLabel().split("\n");
							for (int i = 0; i < lines.length; i++) {
								multiStatus.add(
									new Status(
										Status.ERROR,
										"de.chille.mds.plugin",
										0,
										lines[i],
										null));
							}
							ErrorDialog.openError(
								getSite().getShell(),
								"Import Javacode",
								"Error occured during import.",
								multiStatus);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				showMessage(methodName + "::" + obj.toString());
			viewer.refresh();
		}
	}

	class MDSSelectionChangedListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = viewer.getSelection();
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof TreeServer) {
				createRepository.setEnabled(true);
				createModel.setEnabled(false);
				createClass.setEnabled(false);
				createAssociation.setEnabled(false);
				createGeneralization.setEnabled(false);
				update.setEnabled(false);
				delete.setEnabled(false);
				validate.setEnabled(false);
				createJava.setEnabled(false);
				importJava.setEnabled(false);
			} else if (obj instanceof TreeRepository) {
				createRepository.setEnabled(false);
				createModel.setEnabled(true);
				createClass.setEnabled(false);
				createAssociation.setEnabled(false);
				createGeneralization.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
				validate.setEnabled(false);
				createJava.setEnabled(false);
				importJava.setEnabled(false);
			} else if (obj instanceof TreeModel) {
				createRepository.setEnabled(false);
				createModel.setEnabled(false);
				createClass.setEnabled(true);
				createAssociation.setEnabled(
					((TreeModel) obj).getChildren().length > 1);
				createGeneralization.setEnabled(
					((TreeModel) obj).getChildren().length > 1);
				update.setEnabled(true);
				delete.setEnabled(true);
				validate.setEnabled(true);
				createJava.setEnabled(
					((TreeModel) obj).getChildren().length > 0);
				importJava.setEnabled(true);
			} else if (obj instanceof TreeClass) {
				createRepository.setEnabled(false);
				createModel.setEnabled(false);
				createClass.setEnabled(false);
				createAssociation.setEnabled(false);
				createGeneralization.setEnabled(false);
				if (((TreeClass) obj).getParent() instanceof TreeModel) {
					update.setEnabled(true);
					delete.setEnabled(true);
				} else {
					update.setEnabled(false);
					delete.setEnabled(true);
				}
				validate.setEnabled(false);
				createJava.setEnabled(false);
				importJava.setEnabled(false);
			} else if (obj instanceof TreeAssociation) {
				createRepository.setEnabled(false);
				createModel.setEnabled(false);
				createClass.setEnabled(false);
				createAssociation.setEnabled(false);
				createGeneralization.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
				validate.setEnabled(false);
				createJava.setEnabled(false);
				importJava.setEnabled(false);
			} else if (obj instanceof TreeGeneralization) {
				createRepository.setEnabled(false);
				createModel.setEnabled(false);
				createClass.setEnabled(false);
				createAssociation.setEnabled(false);
				createGeneralization.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
				validate.setEnabled(false);
				createJava.setEnabled(false);
				importJava.setEnabled(false);
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
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new TreeLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(ResourcesPlugin.getWorkspace());
		makeActions();
		viewer.addSelectionChangedListener(new MDSSelectionChangedListener());
		hookContextMenu();
		//hookDoubleClickAction();
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
		manager.add(createModel);
		manager.add(createClass);
		manager.add(createAssociation);
		manager.add(createGeneralization);
		manager.add(new Separator());
		manager.add(delete);
		manager.add(update);
		manager.add(new Separator());
		manager.add(validate);
		manager.add(new Separator());
		manager.add(createJava);
		manager.add(importJava);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(createRepository);
		manager.add(createModel);
		manager.add(createClass);
		manager.add(createAssociation);
		manager.add(createGeneralization);
		manager.add(new Separator());
		manager.add(delete);
		manager.add(update);
		manager.add(new Separator());
		manager.add(validate);
		manager.add(new Separator());
		manager.add(createJava);
		manager.add(importJava);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator("Additions"));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		//manager.add(createRepository);
		//manager.add(action2);
		//manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {

		createRepository =
			new MDSAction("createRepository", "create Repository", null);
		createRepository.setToolTipText("Create New Repository");
		createModel = new MDSAction("createModel", "create Model", null);
		createModel.setToolTipText("Create New Model");
		createClass = new MDSAction("createClass", "create Class", null);
		createClass.setToolTipText("Create New Class");
		createAssociation =
			new MDSAction("createAssociation", "create Association", null);
		createAssociation.setToolTipText("Create New Association");
		createGeneralization =
			new MDSAction(
				"createGeneralization",
				"create Generalization",
				null);
		createGeneralization.setToolTipText("Create New Generalization");
		delete = new MDSAction("delete", "delete", null);
		delete.setToolTipText("Delete");
		update = new MDSAction("updateElement", "update", null);
		update.setToolTipText("Update");
		/*
				update =
					new MDSAction(
						"update",
						"update",
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
							ISharedImages.IMG_OBJS_INFO_TSK));
				update.setToolTipText("Update");*/

		validate = new MDSAction("validateModel", "validate", null);
		validate.setToolTipText("Validate This Model");
		createJava = new MDSAction("createJava", "create Javacode", null);
		createJava.setToolTipText("Create Javacode from Model");
		importJava = new MDSAction("importJava", "import Javacode", null);
		importJava.setToolTipText("Create Model from Javacode");

		/*
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
		};*/
	}
	/*
		private void hookDoubleClickAction() {
			viewer.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					doubleClickAction.run();
				}
			});
		}*/
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"metadata.server",
			message);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}