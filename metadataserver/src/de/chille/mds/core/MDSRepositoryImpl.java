package de.chille.mds.core;

import java.util.*;

import de.chille.api.mds.core.*;
import de.chille.mds.MDSGlobals;
import de.chille.mds.persistence.PersistenceHandlerException;
import de.chille.mds.soap.MDSModelBean;
import de.chille.mds.soap.MDSRepositoryBean;

public class MDSRepositoryImpl
	extends MDSPersistentObjectImpl
	implements MDSRepository {

	private static int counter = 0;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList models = new ArrayList();

	public MDSRepositoryImpl() {
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

	public Vector removeModel(MDSHref href, boolean confirm)
		throws MDSCoreException {
		MetaDataServer server = MetaDataServerImpl.getInstance();
		Vector result = new Vector();
		MDSModel mdsModel = null;
		try {
			mdsModel = getModelByHref(href);
			if (!confirm) {
				mdsModel.delete(null);
			}
		} catch (Exception e) {
			throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
		}
		if (!confirm) {
			if (!models.remove(mdsModel)) {
				throw new MDSCoreException("Fehler: MDSRepository#removeModel()");
			}
		}
		// wenn in anderen model als metamodel, dann dort entfernen
		Iterator j = null;
		Iterator i = server.getRepositories().iterator();
		while (i.hasNext()) {
			j = ((MDSRepositoryImpl) i.next()).getModels().iterator();
			while (j.hasNext()) {
				MDSModel model = (MDSModelImpl) j.next();
				if (model.getMetamodel() != null
					&& model.getMetamodel().equals(mdsModel)) {
					if (confirm) {
						result.add(model.getHref().getHrefString());
					} else {
						model.setMetamodel(null);
						model.touch();
					}
				}
			}
		}
		return result;
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
			//removeModel(from);
			mdsRepository.load(null);
			String id = mdsRepository.insertModel(mdsModel);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		} catch (MDSHrefFormatException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	} /**
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
	} /**
							 * @see java.lang.Object#toString()
							 */
	public String toString() {
		String retString =
			"\trepository:" + this.getId() + " - " + this.getLabel() + "\n";
		Iterator i = models.iterator();
		while (i.hasNext()) {
			retString += ((MDSModel) i.next()).toString();
		}
		return retString;
	} /**
							 * @see de.chille.api.de.chille.de.chille.mds.core.MDSRepository#getModels()
							 */
	public ArrayList getModels() {
		return models;
	} /**
							 * @see de.chille.api.de.chille.de.chille.mds.core.MDSRepository#setModels(ArrayList)
							 */
	public void setModels(ArrayList models) {
		this.models = models;
	}

	public void update() throws PersistenceHandlerException {
		MDSRepository repository = (MDSRepositoryImpl) load(null);
		this.setLabel(repository.getLabel());
		this.setModels(repository.getModels());
	} /**
							 * @see de.chille.api.de.chille.de.chille.mds.core.MDSRepository#getCounter()
							 */
	public int getCounter() {
		return counter;
	} /**
							 * @see de.chille.api.de.chille.de.chille.mds.core.MDSRepository#setCounter(int)
							 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	public MDSRepositoryBean exportBean() {
		MDSRepositoryBean bean = new MDSRepositoryBean();
		bean.setHref(this.getHref().getHrefString());
		bean.setId(this.getId());
		bean.setLabel(this.getLabel());
		bean.setCounter(this.getCounter());
		Vector modelBeans = new Vector();
		Iterator i = this.getModels().iterator();
		while (i.hasNext()) {
			modelBeans.add(((MDSModelImpl) i.next()).exportBean());
		}
		bean.setModels(modelBeans);
		return bean;
	}

	public void importBean(MDSRepositoryBean bean) {
		try {
			if (bean.getHref() != null)
				this.setHref(new MDSHrefImpl(bean.getHref()));
		} catch (MDSHrefFormatException e) {
		}
		if (bean.getId() != null)
			this.setId(bean.getId());
		if (bean.getLabel() != null)
			this.setLabel(bean.getLabel());
		this.setCounter(bean.getCounter());
		ArrayList models = new ArrayList();
		MDSModelImpl model = null;
		Iterator i = bean.getModels().iterator();
		while (i.hasNext()) {
			model = new MDSModelImpl();
			model.importBean((MDSModelBean) i.next());
			models.add(model);
		}
	}
}