package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

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
	public ArrayList query(String query) throws MDSCoreException {
		ArrayList result = null;
		Iterator i = models.iterator();
		while (i.hasNext()) {
			MDSModel model = (MDSModel) i.next();
			result.addAll(model.query(query));
		}
		return result;
	}

	/**
	 * @see MDSRepository#insertModel(MDSModel)
	 */
	public String insertModel(MDSModel mdsModel) throws MDSCoreException {
		mdsModel.setId("neue_unique_id");
		try {
			persistenceHandler.save(mdsModel);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}
		if (models.add(mdsModel)) {
			return this.getId() + "." + mdsModel.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}
	}

	/**
	 * @see MDSRepository#removeModel(String)
	 */
	public void removeModel(String href) throws MDSCoreException {
		MDSModel mdsModel;
		try {
			mdsModel = (MDSModel) persistenceHandler.load(href, null);
			persistenceHandler.delete(this, null);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
		}
		if (!models.remove(mdsModel)) {
			throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
		}
	}

	/**
	 * @see MDSRepository#moveModel(String, String)
	 */
	public String moveModel(String from, String to) throws MDSCoreException {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(from, null);
			this.removeModel(from);
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(to, null);
			String newHref = mdsRepository.insertModel(mdsModel);
			return newHref;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	/**
	 * @see MDSRepository#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label)
		throws MDSCoreException {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(from, null);
			MDSModel copyModel = new MDSModelImpl();
			copyModel.setAdditionalFiles(mdsModel.getAdditionalFiles());
			copyModel.setDtdFile(mdsModel.getDtdFile());
			copyModel.setElements(mdsModel.getElements());
			copyModel.setMetamodel(mdsModel.getMetamodel());
			copyModel.setSchemaFile(mdsModel.getSchemaFile());
			copyModel.setXmiFile(mdsModel.getXmiFile());
			copyModel.setXmiHandler(mdsModel.getXmiHandler());
			copyModel.setPersistenceHandler(persistenceHandler);
			copyModel.setLabel(label);
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(to, null);
			String newHref = mdsRepository.insertModel(mdsModel);
			return newHref;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
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