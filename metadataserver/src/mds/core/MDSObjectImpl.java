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
	 * Gets the id
	 * @return Returns a String
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the label
	 * @return Returns a String
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the label
	 * @param label The label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}

