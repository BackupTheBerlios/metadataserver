package de.chille.mds.core;

import org.apache.soap.util.Bean;

import de.chille.mds.persistence.FilesystemHandlerImpl;
import de.chille.mds.soap.MDSObjectBean;

import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSObject;
import de.chille.api.mds.persistence.PersistenceHandler;

/**
 * @see MDSObject
 * 
 * @author Thomas Chille
 */
public class MDSObjectImpl implements MDSObject {

	/**
	 * zum speichern 
	 */
	//private PersistenceHandler persistenceHandler = new FilesystemHandlerImpl();

	/**
	 * in Hierarchieebene eindeutige Kennung
	 */
	private String id = null;

	/**
	 * global eindeutige Kennung
	 */
	private MDSHref href = null;

	/**
	 * Bezeichnung des Objektes
	 */
	private String label = null;

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
	 * @see MDSObject#getPersistenceHandler()
	 */
	/*public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}
*/
	/**
	 * @see MDSObject#setPersistenceHandler(PersistenceHandler)
	 */
	/*public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}*/

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#getHref()
	 */
	public MDSHref getHref() {
		return href;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSObject#setHref(MDSHref)
	 */
	public void setHref(MDSHref href) {
		this.href = href;
	}
	

}
