package de.chille.mds.plugin.tree;

import java.util.ArrayList;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.chille.mds.soap.MDSObjectBean;

public class TreeParent extends TreeObject {
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
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}
	public boolean hasChildren() {
		return children.size() > 0;
	}
}
