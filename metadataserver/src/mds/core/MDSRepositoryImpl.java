package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

public class MDSRepositoryImpl extends MDSObjectImpl implements MDSRepository {

	private static int counter = 0;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList models = new ArrayList();

	public MDSRepositoryImpl() {
		this.setId("repository_" + counter++);
	}
	
	/**
	 * @see MDSRepository#insert()
	 */
	public String insert() throws MDSCoreException {

		try {
			this.getPersistenceHandler().save(this);
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
			this.getPersistenceHandler().delete(this, null);
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

		try {
			this.getPersistenceHandler().save(mdsModel);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}
		if (models.add(mdsModel)) {
			return mdsModel.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}
	}

	/**
	 * @see MDSRepository#removeModel(MDSHref)
	 */
	public void removeModel(MDSHref href) throws MDSCoreException {
		MDSModel mdsModel;
		try {
			mdsModel = (MDSModel) this.getPersistenceHandler().load(href, null);
			this.getPersistenceHandler().delete(mdsModel, null);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
		}
		if (!models.remove(mdsModel)) {
			throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
		}
	}

	/**
	 * @see MDSRepository#moveModel(MDSHref, MDSHref)
	 */
	public String moveModel(MDSHref from, MDSHref to) throws MDSCoreException {

		try {
			MDSModel mdsModel =
				(MDSModel) this.getPersistenceHandler().load(from, null);
			this.removeModel(from);
			MDSRepository mdsRepository =
				(MDSRepository) this.getPersistenceHandler().load(to, null);
			String id = mdsRepository.insertModel(mdsModel);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	/**
	 * @see MDSRepository#copyModel(MDSHref, MDSHref, String)
	 */
	public String copyModel(MDSHref from, MDSHref to, String label)
		throws MDSCoreException {
		try {
			MDSModel mdsModel =
				(MDSModel) this.getPersistenceHandler().load(from, null);
			MDSModel copyModel = new MDSModelImpl();
			copyModel.setAdditionalFiles(mdsModel.getAdditionalFiles());
			copyModel.setDtdFile(mdsModel.getDtdFile());
			copyModel.setElements(mdsModel.getElements());
			copyModel.setMetamodel(mdsModel.getMetamodel());
			copyModel.setSchemaFile(mdsModel.getSchemaFile());
			copyModel.setXmiFile(mdsModel.getXmiFile());
			copyModel.setXmiHandler(mdsModel.getXmiHandler());
			copyModel.setPersistenceHandler(mdsModel.getPersistenceHandler());
			copyModel.setLabel(label);
			MDSRepository mdsRepository =
				(MDSRepository) this.getPersistenceHandler().load(to, null);
			String id = mdsRepository.insertModel(mdsModel);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	public MDSModel getModelByHref(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		String id = href.getModelId();
		MDSModel mdsModel;
		Iterator i = models.iterator();
		while (i.hasNext()) {
			mdsModel = (MDSModel) i.next();
			if (mdsModel.getId().equals(id)) {
				return mdsModel;
			}
		}
		throw new MDSCoreException("Fehler: MDSRepository#getModelByHref()");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retString = "\trepository:" + this.getId() + "\n";
		Iterator i = models.iterator();
		while (i.hasNext()) {
			retString += ((MDSModel) i.next()).toString();
		}
		return retString;
	}

}