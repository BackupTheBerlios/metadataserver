package de.chille.mds.plugin.tree;

import java.util.Vector;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.*;

import de.chille.mds.plugin.MDSPlugin;
import de.chille.mds.plugin.MDSPluginConstants;
import de.chille.mds.soap.*;

public class TreeContentProvider
	implements IStructuredContentProvider, ITreeContentProvider {
	public TreeParent invisibleRoot;
	
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

	private void initialize() {

		invisibleRoot = new TreeParent(new MDSObjectBean());
		MDSObjectBean bean = new MDSObjectBean();
		
		SOAPClientImpl.setEndPoint(
			MDSPlugin.getDefault().getPreferenceStore().getString(
				MDSPluginConstants.P_ENDPOINT));
				
		Vector repositories = new Vector();
		Vector serverInfo = new Vector();
		
		try {
			serverInfo =
				(Vector) SOAPClientImpl.call("getServerInfo", null, null, null);
			repositories =
				(Vector) SOAPClientImpl.call("query", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bean.setId((String)serverInfo.get(0));
		bean.setLabel((String)serverInfo.get(1));
		TreeParent root = new TreeServer(bean);
		invisibleRoot.addChild(root);
		
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
						element = (MDSObjectBean) model.getElements().get(k);
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
						} else if (element instanceof MDSGeneralizationBean) {
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
