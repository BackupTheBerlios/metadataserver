package mds.core;

import java.io.File;

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
	private MDSElement[] elements = null;

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
	private File[] additionalFiles = null;

	/**
	 * zum speichern des models
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * zum manipulieren des models
	 */
	private XMIHandler xmiHandler = null;

	/**
	 * @see MDSModel#insertModel(String)
	 */
	public String insertModel(String href) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#removeModel()
	 */
	public void removeModel() throws MDSCoreException {
	}

	/**
	 * @see MDSModel#moveModel(String)
	 */
	public String moveModel(String to) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#copyModel(String, String)
	 */
	public String copyModel(String to, String label) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#renameModel(String)
	 */
	public void renameModel(String label) throws MDSCoreException {
	}

	/**
	 * @see MDSModel#getModelVersions(String)
	 */
	public String[] getModelVersions(String href) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#restoreModel(String)
	 */
	public void restoreModel(String version) throws MDSCoreException {
	}

	/**
	 * @see MDSModel#insertElement(String, MDSElement)
	 */
	public String insertElement(String href, MDSElement mdsElement)
		throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#removeElement(String)
	 */
	public void removeElement(String href) throws MDSCoreException {
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
	 * @see MDSModel#validateModel(String)
	 */
	public String[] validateModel(String validateType) throws MDSCoreException {
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

	/**
	 * Gets the xmiFile
	 * @return Returns a File
	 */
	public File getXmiFile() {
		return xmiFile;
	}

	/**
	 * Sets the xmiFile
	 * @param xmiFile The xmiFile to set
	 */
	public void setXmiFile(File xmiFile) {
		this.xmiFile = xmiFile;
	}
	/**
	 * Gets the dtdFile
	 * @return Returns a File
	 */
	public File getDtdFile() {
		return dtdFile;
	}

	/**
	 * Sets the dtdFile
	 * @param dtdFile The dtdFile to set
	 */
	public void setDtdFile(File dtdFile) {
		this.dtdFile = dtdFile;
	}

	/**
	 * Gets the schemaFile
	 * @return Returns a File
	 */
	public File getSchemaFile() {
		return schemaFile;
	}

	/**
	 * Sets the schemaFile
	 * @param schemaFile The schemaFile to set
	 */
	public void setSchemaFile(File schemaFile) {
		this.schemaFile = schemaFile;
	}
	/**
	 * Gets the metamodel
	 * @return Returns a MDSModel
	 */
	public MDSModel getMetamodel() {
		return metamodel;
	}

	/**
	 * Sets the metamodel
	 * @param metamodel The metamodel to set
	 */
	public void setMetamodel(MDSModel metamodel) {
		this.metamodel = metamodel;
	}
	/**
	 * Gets the repository
	 * @return Returns a MDSRepository
	 */
	public MDSRepository getRepository() {
		return repository;
	}

	/**
	 * Sets the repository
	 * @param repository The repository to set
	 */
	public void setRepository(MDSRepository repository) {
		this.repository = repository;
	}
	/**
	 * Gets the additionalFiles
	 * @return Returns a File[]
	 */
	public File[] getAdditionalFiles() {
		return additionalFiles;
	}

	/**
	 * Sets the additionalFiles
	 * @param additionalFiles The additionalFiles to set
	 */
	public void setAdditionalFiles(File[] additionalFiles) {
		this.additionalFiles = additionalFiles;
	}
	/**
	 * Gets the xmiHandler
	 * @return Returns a XMIHandler
	 */
	public XMIHandler getXmiHandler() {
		return xmiHandler;
	}

	/**
	 * Sets the xmiHandler
	 * @param xmiHandler The xmiHandler to set
	 */
	public void setXmiHandler(XMIHandler xmiHandler) {
		this.xmiHandler = xmiHandler;
	}
	
	/**
	 * Gets the elements
	 * @return Returns a MDSElement[]
	 */
	public MDSElement[] getElements() {
		return elements;
	}
	
	/**
	 * Sets the elements
	 * @param elements The elements to set
	 */
	public void setElements(MDSElement[] elements) {
		this.elements = elements;
	}

}