package de.chille.mds.core;

import de.chille.mds.persistence.FilesystemHandlerImpl;
import de.chille.mds.persistence.PersistenceHandlerException;

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

}
