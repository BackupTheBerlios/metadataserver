package mds.core;

import mds.persistence.FilesystemHandlerImpl;
import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSHref;
import api.mds.core.MDSObject;
import api.mds.core.MDSPersistentObject;
import api.mds.persistence.PersistenceHandler;

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
	 * @see api.mds.core.MDSPersistentObject#save()
	 */
	public void save() throws PersistenceHandlerException {
		getPersistenceHandler().save(this);
	}

	/**
	 * @see api.mds.core.MDSPersistentObject#load(String)
	 */
	public MDSPersistentObject load(String version)
		throws PersistenceHandlerException {
		return getPersistenceHandler().load(getHref(), version);
	}

	/**
	 * @see api.mds.core.MDSPersistentObject#delete(String)
	 */
	public void delete(String version)
		throws PersistenceHandlerException {
		getPersistenceHandler().delete(this, version);
	}

}
