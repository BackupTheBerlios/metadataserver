package mds.core;

import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

public class MDSRepositoryImpl extends MDSObjectImpl implements MDSRepository {

	/**
	 * zum speichern des MDSRepository
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * @see MDSRepository#insert()
	 */
	public String insert() throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSRepository#delete()
	 */
	public void delete() throws MDSCoreException {
	}

	/**
	 * @see MDSRepository#query(String)
	 */
	public String[] query(String query) throws MDSCoreException {
		return null;
	}

	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}
}