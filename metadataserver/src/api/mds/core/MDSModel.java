package api.mds.core;

import java.io.File;

import api.mds.persistence.PersistenceHandler;
import api.mds.xmi.XMIHandler;

import mds.core.MDSCoreException;

/**
 * Abbildung eines UML- o. MOF- Models, besteht aus seiner 
 * XMI-Repräsentation, DTD, Schema und Files
 * 
 * @author Thomas Chille
 */
public interface MDSModel extends MDSObject {

	/**
	 * umbenennen des MDSModel
	 *
	 * @param label neuer Name
	 */
	public void renameModel(String label) throws MDSCoreException;

	/**
	 * gibt Liste aller vorhandenen Versionen des MDSModel zurück
	 *
	 * @return Liste aller Versionen
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String[] getModelVersions(String href) throws MDSCoreException;

	/**
	 * macht Änderungen an einem MDSModel rückgängig
	 *
	 * @param version Versionsnummer
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void restoreModel(String version) throws MDSCoreException;

	/**
	 * fügt ein MDSElement in MDSModel ein
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return Pfad zum neu eingefügten MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertElement(String href, MDSElement mdsElement)
		throws MDSCoreException;

	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeElement(String href) throws MDSCoreException;

	/**
	 * verschiebt ein MDSElement innerhalb des MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an diesee spezifierten Stelle 
	 * @return Pfad zur neuen MDSElement-Position
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String moveElement(String from, String to) throws MDSCoreException;

	/**
	 * kopiert ein MDSElement innerhalb eines MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @param label des neuen MDSElement, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String copyElement(String from, String to, String label)
		throws MDSCoreException;

	/**
	 * validiert MDSModel
	 *
	 * @param href Pfad zum zu validierenden MDSModel
	 * @param validateType Art der Validierung(dtd, schema, strikt, ...)
	 * @return Messages der Validierung
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String[] validateModel(int validateType) throws MDSCoreException;
	
	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler();

	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler);

	/**
	 * Gets the xmiFile
	 * @return Returns a File
	 */
	public File getXmiFile();

	/**
	 * Sets the xmiFile
	 * @param xmiFile The xmiFile to set
	 */
	public void setXmiFile(File xmiFile);
	
	/**
	 * Gets the dtdFile
	 * @return Returns a File
	 */
	public File getDtdFile();

	/**
	 * Sets the dtdFile
	 * @param dtdFile The dtdFile to set
	 */
	public void setDtdFile(File dtdFile);

	/**
	 * Gets the schemaFile
	 * @return Returns a File
	 */
	public File getSchemaFile();

	/**
	 * Sets the schemaFile
	 * @param schemaFile The schemaFile to set
	 */
	public void setSchemaFile(File schemaFile);
	
	/**
	 * Gets the metamodel
	 * @return Returns a MDSModel
	 */
	public MDSModel getMetamodel();

	/**
	 * Sets the metamodel
	 * @param metamodel The metamodel to set
	 */
	public void setMetamodel(MDSModel metamodel);
	
	/**
	 * Gets the additionalFiles
	 * @return Returns a File[]
	 */
	public File[] getAdditionalFiles();

	/**
	 * Sets the additionalFiles
	 * @param additionalFiles The additionalFiles to set
	 */
	public void setAdditionalFiles(File[] additionalFiles);
	
	/**
	 * Gets the xmiHandler
	 * @return Returns a XMIHandler
	 */
	public XMIHandler getXmiHandler();

	/**
	 * Sets the xmiHandler
	 * @param xmiHandler The xmiHandler to set
	 */
	public void setXmiHandler(XMIHandler xmiHandler);
	
	/**
	 * Gets the elements
	 * @return Returns a MDSElement[]
	 */
	public MDSElement[] getElements();
	
	/**
	 * Sets the elements
	 * @param elements The elements to set
	 */
	public void setElements(MDSElement[] elements);


}