package mds.core;

import java.util.ArrayList;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

public class MDSRepositoryImpl extends MDSObjectImpl implements MDSRepository {

	/**
	 * zum speichern des MDSRepository
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList models = null;

	/**
	 * @see MDSRepository#insert()
	 */
	public String insert() throws MDSCoreException {
		this.setId("neue_unique_id");
		try {
			persistenceHandler.save(this);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#insert()");
		}
		return this.getId();
	}

	/**
	 * @see MDSRepository#delete()
	 */
	public void delete() throws MDSCoreException {
		try {
			persistenceHandler.delete(this, null);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#delete()");
		}
	}

	/**
	 * @see MDSRepository#query(String)
	 */
	public String[] query(String query) throws MDSCoreException {
		return null;
	}
	
	/**
	 * @see api.mds.core.MDSRepository#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel)
		throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#removeModel(String)
	 */
	public void removeModel(String href) throws MDSCoreException {
	}

	/**
	 * @see api.mds.core.MDSRepository#moveModel(String, String)
	 */
	public String moveModel(String from, String to) throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label)
		throws MDSCoreException {
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