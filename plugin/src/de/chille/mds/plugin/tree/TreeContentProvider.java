package de.chille.mds.plugin.tree;

import java.util.Vector;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.*;

import de.chille.mds.plugin.*;
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
		bean.setId((String) serverInfo.get(0));
		bean.setLabel((String) serverInfo.get(1));
		TreeParent root = new TreeServer(bean);
		invisibleRoot.addChild(root);

		TreeRepository rep = null;
		MDSRepositoryBean repository = null;
		for (int i = 0; i < repositories.size(); i++) {
			repository = (MDSRepositoryBean) repositories.get(i);
			rep = new TreeRepository(repository);
			for (int j = 0; j < repository.getModels().size(); j++) {
				Util.getInstance().addModel(
					rep,
					(MDSModelBean) repository.getModels().get(j));
			}
			root.addChild(rep);
		}
	}
}