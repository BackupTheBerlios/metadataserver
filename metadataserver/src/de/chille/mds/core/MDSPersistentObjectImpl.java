package de.chille.mds.core;

import de.chille.mds.persistence.FilesystemHandlerImpl;
import de.chille.mds.persistence.PersistenceHandlerException;
import de.chille.mds.soap.MDSObjectBean;

import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSObject;
import de.chille.api.mds.core.MDSPersistentObject;
import de.chille.api.mds.persistence.PersistenceHandler;

/**
 * @see MDSObject
 * 
 * @author Thomas Chille
 */
public class MDSPersistentObjectImpl
	extends MDSObjectImpl
	implements MDSPersistentObject {

	/**
	 * zum speichern 
	 */
	private PersistenceHandler persistenceHandler = new FilesystemHandlerImpl();
	
	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSPersistentObject#save()
	 */
	public void save() throws PersistenceHandlerException {
		getPersistenceHandler().save(this);
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSPersistentObject#load(String)
	 */
	public MDSPersistentObject load(String version)
		throws PersistenceHandlerException {
		
		return getPersistenceHandler().load(getHref(), version);
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSPersistentObject#delete(String)
	 */
	public void delete(String version)
		throws PersistenceHandlerException {
		getPersistenceHandler().delete(this, version);
	}
	
	/**
	 * Returns the persistenceHandler.
	 * @return PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * Sets the persistenceHandler.
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}

}
