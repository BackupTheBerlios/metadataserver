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
	 * @see MDSRepository#getPersistenceHandler()
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * @see MDSRepository#setPersistenceHandler(PersistenceHandler)
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}
}