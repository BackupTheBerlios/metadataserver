package mds.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;
import mds.xmi.XMIHandlerImpl;

import api.mds.core.MDSElement;
import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;
import api.mds.xmi.XMIHandler;

/**
 * @see MDSModel
 * 
 * @author Thomas Chille
 */
public class MDSModelImpl extends MDSObjectImpl implements MDSModel {

	/**
	 * MDSElement-Repräsenation des Models
	 */
	private ArrayList elements = new ArrayList();

	/**
	 * xmi-repräsenation des Models
	 */
	private File xmiFile = null;

	/**
	 * dtd-Repräsenation des Models
	 */
	private File dtdFile = null;

	/**
	 * schema-repräsenation des Models
	 */
	private File schemaFile = null;

	/**
	 * wenn nicht null dann ist Model Instance dieses metamodel
	 */
	private MDSModel metamodel = null;

	/**
	 * Zusätzliche Dateien, welche das model formen (Quelltexte)
	 */
	private ArrayList additionalFiles = new ArrayList();

	/**
	 * zum manipulieren des models
	 */
	private XMIHandler xmiHandler = new XMIHandlerImpl();

	/**
	 * @see MDSModel#renameModel(String)
	 */
	public void renameModel(String label) throws MDSCoreException {
		this.setLabel(label);
	}

	/**
	 * @see MDSModel#getModelVersions(String)
	 */
	public ArrayList getModelVersions() throws MDSCoreException {
		try {
			return this.getPersistenceHandler().getModelVersions(this);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSModel#getModelVersions");
		}
	}

	/**
	 * @see MDSModel#restoreModel(String)
	 */
	public void restoreModel(String version) throws MDSCoreException {
	}

	/**
	 * @see MDSModel#insertElement(MDSElement)
	 */
	public String insertElement(MDSElement mdsElement)
		throws MDSCoreException {

		mdsElement.setId("element_id");
		if (elements.add(mdsElement)) {
			return mdsElement.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSModel#insertElement()");
		}
	}

	/**
	 * @see MDSModel#removeElement(MDSHref)
	 */
	public void removeElement(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		MDSElement mdsElement;
		try {
			mdsElement = getById(href);
		} catch (MDSCoreException e) {
			throw new MDSCoreException("Fehler: MDSModels#removeElement()");
		}
		if (!elements.remove(mdsElement)) {
			throw new MDSCoreException("Fehler: MDSModel#removeElement()");
		}
	}

	/**
	 * @see MDSModel#moveElement(MDSHref, MDSHref)
	 */
	public String moveElement(MDSHref from, MDSHref to)
		throws MDSCoreException, MDSHrefFormatException {
			
		try {
			MDSElement mdsElement =
				(MDSElement)this.getPersistenceHandler().load(from, null);
			this.removeElement(from);
			MDSModel mdsModel =
				(MDSModel)this.getPersistenceHandler().load(to, null);
			String id = mdsModel.insertElement(mdsElement);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	/**
	 * @see MDSModel#copyElement(MDSHref, MDSHref, String)
	 */
	public String copyElement(MDSHref from, MDSHref to, String label)
		throws MDSCoreException {
			
		try {
			MDSElement mdsElement =
				(MDSElement)this.getPersistenceHandler().load(from, null);
			MDSElement copyElement = new MDSElementImpl();
			copyElement.setPersistenceHandler(mdsElement.getPersistenceHandler());
			copyElement.setLabel(label);
			
			MDSModel mdsModel =
				(MDSModel)this.getPersistenceHandler().load(to, null);
			String id = mdsModel.insertElement(mdsElement);
			return id;
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSRepository#moveModel()");
		}
	}

	/**
	 * @see MDSModel#validateModel(int)
	 */
	public ArrayList validateModel(int validateType) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#query(String)
	 */
	public ArrayList query(String query) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#getXmiFile()
	 */
	public File getXmiFile() {
		return xmiFile;
	}

	/**
	 * @see MDSModel#setXmiFile(File)
	 */
	public void setXmiFile(File xmiFile) {
		this.xmiFile = xmiFile;
	}

	/**
	 * @see MDSModel#getDtdFile()
	 */
	public File getDtdFile() {
		return dtdFile;
	}

	/**
	 * @see MDSModel#setDtdFile(File)
	 */
	public void setDtdFile(File dtdFile) {
		this.dtdFile = dtdFile;
	}

	/**
	 * @see MDSModel#getSchemaFile()
	 */
	public File getSchemaFile() {
		return schemaFile;
	}

	/**
	 * @see MDSModel#setSchemaFile(File)
	 */
	public void setSchemaFile(File schemaFile) {
		this.schemaFile = schemaFile;
	}

	/**
	 * @see MDSModel#getMetamodel()
	 */
	public MDSModel getMetamodel() {
		return metamodel;
	}

	/**
	 * @see MDSModel#setMetamodel(MDSModel)
	 */
	public void setMetamodel(MDSModel metamodel) {
		this.metamodel = metamodel;
	}

	/**
	 * @see MDSModel#getAdditionalFiles()
	 */
	public ArrayList getAdditionalFiles() {
		return additionalFiles;
	}

	/**
	 * @see MDSModel#setAdditionalFiles(File[])
	 */
	public void setAdditionalFiles(ArrayList additionalFiles) {
		this.additionalFiles = additionalFiles;
	}

	/**
	 * @see MDSModel#getXmiHandler()
	 */
	public XMIHandler getXmiHandler() {
		return xmiHandler;
	}

	/**
	 * @see MDSModel#setXmiHandler(XMIHandler)
	 */
	public void setXmiHandler(XMIHandler xmiHandler) {
		this.xmiHandler = xmiHandler;
	}

	/**
	 * @see MDSModel#getElements()
	 */
	public ArrayList getElements() {
		return elements;
	}

	/**
	 * @see MDSModel#setElements(ArrayList)
	 */
	public void setElements(ArrayList elements) {
		this.elements = elements;
	}

	private MDSElement getById(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		String id = href.getElementId();
		MDSElement element;
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element.getId().equals(id)) {
				return element;
			}
		}
		throw new MDSCoreException("Fehler: MDSModels#getById()");
	}

}