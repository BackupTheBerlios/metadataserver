package de.chille.mds.plugin.tree;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.chille.mds.soap.MDSObjectBean;

public class TreeObject {

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