package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

public class MDSRepositoryImpl
	extends MDSPersistentObjectImpl
	implements MDSRepository {

	private int counter = 0;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList models = new ArrayList();

	public MDSRepositoryImpl() {
	}

	/**
	 * @see MDSRepository#delete()
	 */
	public void delete() throws MDSCoreException {
		try {
			delete(null);
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
	public String insertModel(MDSModel mdsModel)
		throws MDSHrefFormatException, MDSCoreException {

		/*
		try {
			mdsModel.save();
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}*/
		if (models.add(mdsModel)) {
			if (mdsModel.getId() == null) {
				mdsModel.setId(this.getId() + "_" + this.counter++);
			}
			mdsModel.setHref(
				new MDSHrefImpl(
					this.getHref().getRepositoryHref()
						+ "/model_"
						+ mdsModel.getId()));
			return mdsModel.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSRepository#insertModel()");
		}
	}

	/**
	 * @see MDSRepository#removeModel(MDSHref)
	 */
	public void removeModel(MDSHref href) throws MDSCoreException {
		MDSModel mdsModel = new MDSModelImpl();
		mdsModel.setHref(href);
		try {
			mdsModel.delete(null);
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
		MDSModel mdsModel = new MDSModelImpl();
		mdsModel.setHref(from);
		MDSRepository mdsRepository = new MDSRepositoryImpl();
		mdsRepository.setHref(to);
		try {
			mdsModel.load(null);
			removeModel(from);
			mdsRepository.load(null);
			String id = mdsRepository.insertModel(mdsModel);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		} catch (MDSHrefFormatException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	/**
	 * @see MDSRepository#copyModel(MDSHref, MDSHref, String)
	 */
	public String copyModel(MDSHref from, MDSHref to, String label)
		throws MDSCoreException {
		MDSModel mdsModel = new MDSModelImpl();
		mdsModel.setHref(from);
		MDSRepository mdsRepository = new MDSRepositoryImpl();
		mdsRepository.setHref(to);
		try {
			mdsModel.load(null);
			MDSModel copyModel = new MDSModelImpl();
			copyModel.setAdditionalFiles(mdsModel.getAdditionalFiles());
			copyModel.setElements(mdsModel.getElements());
			copyModel.setMetamodel(mdsModel.getMetamodel());
			copyModel.setXmiHandler(mdsModel.getXmiHandler());
			copyModel.setPersistenceHandler(mdsModel.getPersistenceHandler());
			copyModel.setLabel(label);
			mdsRepository.load(null);
			String id = mdsRepository.insertModel(copyModel);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		} catch (MDSHrefFormatException e) {
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

	/**
	 * Returns the models.
	 * @return ArrayList
	 */
	public ArrayList getModels() {
		return models;
	}

	/**
	 * Sets the models.
	 * @param models The models to set
	 */
	public void setModels(ArrayList models) {
		this.models = models;
	}

}