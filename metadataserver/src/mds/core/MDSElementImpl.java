package mds.core;

import api.mds.core.MDSElement;
import api.mds.core.MDSHref;

/**
 * @see MDSElement
 * 
 * @author Thomas Chille
 */
public class MDSElementImpl extends MDSObjectImpl implements MDSElement {
	private static int counter = 0;

	public MDSElementImpl(String prefix) {
		this.setId(prefix + "_" + counter++);
	}
}
