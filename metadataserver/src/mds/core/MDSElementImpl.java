package mds.core;

import api.mds.core.MDSElement;
import api.mds.core.MDSHref;

/**
 * @see MDSElement
 * 
 * @author Thomas Chille
 */
public class MDSElementImpl extends MDSObjectImpl implements MDSElement {
	private String prefix = "element_";

	public MDSElementImpl(String prefix) {
		this.setPrefix(prefix + "_");
	}
	
	/**
	 * @see api.mds.core.MDSElement#getPrefix()
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @see api.mds.core.MDSElement#setPrefix(String)
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
