package mds.core;

import api.mds.core.MDSObject;

/**
 * @see MDSObject
 * 
 * @author Thomas Chille
 */
public class MDSObjectImpl implements MDSObject {

	/**
	 * in Hierarchieebene eindeutige Kennung
	 */
	private String id = null;
	
	/**
	 * Bezeichnung des Objektes
	 */
	private String label = null;

	/**
	 * @see api.mds.core.MDSObject#getId()
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @see api.mds.core.MDSObject#setId(String)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @see api.mds.core.MDSObject#getLabel()
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @see api.mds.core.MDSObject#setLabel(String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}

