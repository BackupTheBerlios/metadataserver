package de.chille.mds.soap;

public class MDSObjectBean {
	
	/**
	 * in Hierarchieebene eindeutige Kennung
	 */
	private String id = null;

	/**
	 * global eindeutige Kennung
	 */
	private String href = null;

	/**
	 * Bezeichnung des Objektes
	 */
	private String label = null;

	/**
	 * Constructor for MDSObjectBean.
	 */
	public MDSObjectBean() {
		super();
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#getId()
	 */
	public String getId() {
		return id;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#setId(String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#setLabel(String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#getHref()
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#setHref(MDSHref)
	 */
	public void setHref(String href) {
		this.href = href;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getLabel();
	}

}
