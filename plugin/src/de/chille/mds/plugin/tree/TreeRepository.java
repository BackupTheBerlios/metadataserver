package de.chille.mds.plugin.tree;

import org.eclipse.swt.graphics.Image;

import de.chille.mds.plugin.MDSPluginConstants;
import de.chille.mds.soap.MDSObjectBean;

public class TreeRepository extends TreeParent {
	public TreeRepository(MDSObjectBean bean) {
		super(bean);
		setImage(new Image(null, MDSPluginConstants.I_REPOSITORY));
	}
}