package de.chille.mds.core;

import de.chille.api.mds.core.MDSElement;
import de.chille.api.mds.core.MDSHref;
import de.chille.mds.soap.MDSObjectBean;

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
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSElement#getPrefix()
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSElement#setPrefix(String)
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
