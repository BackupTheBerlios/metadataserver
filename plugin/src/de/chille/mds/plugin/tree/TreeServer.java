package de.chille.mds.plugin.tree;

import org.eclipse.swt.graphics.Image;

import de.chille.mds.plugin.MDSPluginConstants;
import de.chille.mds.soap.MDSObjectBean;

public class TreeServer extends TreeParent {
	public TreeServer(MDSObjectBean bean) {
		super(bean);
		setImage(new Image(null, MDSPluginConstants.I_SERVER));
	}
}
