package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

public class MDSRepositoryImpl extends MDSObjectImpl implements MDSRepository {

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList models = new ArrayList();
	
	/**
	 * @see MDSRepository#insert()
	 */
	public String insert() throws MDSCoreException {
		this.setId("neue_unique_id");
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
		mdsModel.setId("neue_unique_id");
		try {
			this.getPersistenceHandler().save(mdsModel);
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
			mdsModel = (MDSModel) this.getPersistenceHandler().load(href, null);
			this.getPersistenceHandler().delete(this, null);
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
			MDSModel mdsModel = (MDSModel) this.getPersistenceHandler().load(from, null);
			this.removeModel(from);
			MDSRepository mdsRepository =
				(MDSRepository) this.getPersistenceHandler().load(to, null);
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
			MDSModel mdsModel = (MDSModel) this.getPersistenceHandler().load(from, null);
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
			String newHref = mdsRepository.insertModel(mdsModel);
			return newHref;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}
	
	public MDSModel getModelByHref(String href)
		throws MDSCoreException {
		
		String[] hrefParts = href.split(".");
		String id = hrefParts[2];
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

}