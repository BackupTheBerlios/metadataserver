package mds.core;

import mds.persistence.FilesystemHandlerImpl;

import api.mds.core.MDSHref;
import api.mds.core.MDSObject;
import api.mds.persistence.PersistenceHandler;

/**
 * @see MDSObject
 * 
 * @author Thomas Chille
 */
public class MDSObjectImpl implements MDSObject {

	/**
	 * zum speichern 
	 */
	private PersistenceHandler persistenceHandler = new FilesystemHandlerImpl();

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

	/**
	 * @see MDSObject#getPersistenceHandler()
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * @see MDSObject#setPersistenceHandler(PersistenceHandler)
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}

	/**
	 * @see api.mds.core.MDSObject#getHref()
	 */
	public MDSHref getHref() {
		return href;
	}

	/**
	 * @see api.mds.core.MDSObject#setHref(MDSHref)
	 */
	public void setHref(MDSHref href) {
		this.href = href;
	}

}
