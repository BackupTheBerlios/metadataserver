package mds.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;
import mds.xmi.XMIHandlerImpl;

import api.mds.core.MDSElement;
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
	private ArrayList elements = null;

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
	 * in diesem Repository liegt das Model
	 */
	private MDSRepository repository = null;

	/**
	 * Zusätzliche Dateien, welche das model formen (Quelltexte)
	 */
	private ArrayList additionalFiles = null;

	/**
	 * zum speichern des models
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * zum manipulieren des models
	 */
	private XMIHandler xmiHandler = null;

	/**
	 * @see MDSModel#renameModel(String)
	 */
	public void renameModel(String label) throws MDSCoreException {
	}

	/**
	 * @see MDSModel#getModelVersions(String)
	 */
	public ArrayList getModelVersions() throws MDSCoreException {
		return null;
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
		
		mdsElement.setId("neue_unique_id");
		if (elements.add(mdsElement)) {
			return this.getId() + "." + mdsElement.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSModel#insertElement()");
		}
	}

	/**
	 * @see MDSModel#removeElement(String)
	 */
	public void removeElement(String href) throws MDSCoreException {
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
	 * @see MDSModel#moveElement(String, String)
	 */
	public String moveElement(String from, String to) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#copyElement(String, String, String)
	 */
	public String copyElement(String from, String to, String label)
		throws MDSCoreException {
		return null;
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
	 * @see MDSModel#getPersistenceHandler()
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * @see MDSModel#setPersistenceHandler(PersistenceHandler)
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
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
	 * @see MDSModel#getRepository()
	 */
	public MDSRepository getRepository() {
		return repository;
	}

	/**
	 * @see MDSModel#setRepository(MDSRepository)
	 */
	public void setRepository(MDSRepository repository) {
		this.repository = repository;
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
	
	private MDSElement getById(String id) throws MDSCoreException{
		MDSElement element;
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			element = (MDSElement)i.next();
			if (element.getId().equals(id)) {
				return element;
			}
		}
		throw new MDSCoreException("Fehler: MDSModels#getById()");
	}
		
}