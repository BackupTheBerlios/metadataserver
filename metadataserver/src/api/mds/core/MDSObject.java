package api.mds.core;

import api.mds.persistence.PersistenceHandler;

/**
 * Superinterface aller MDS-Datatypes
 * 
 * @author Thomas Chille
 */
public interface MDSObject {
	
	/**
	 * Gets the id
	 * @return Returns a String
	 */
	public String getId();
	
	/**
	 * Sets the id
	 * @param id The id to set
	 */
	public void setId(String id);
	
	/**
	 * Gets the href
	 * @return Returns a MDSHref
	 */
	public MDSHref getHref();
	
	/**
	 * Sets the href
	 * @param href The href to set
	 */
	public void setHref(MDSHref href);
	
	/**
	 * Gets the label
	 * @return Returns a String
	 */
	public String getLabel();
	
	/**
	 * Sets the label
	 * @param label The label to set
	 */
	public void setLabel(String label);
	
	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler();

	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler);

	
}