package api.mds.core;

import java.util.ArrayList;

import api.mds.persistence.PersistenceHandler;
import api.mds.xmi.XMIHandler;

import mds.core.MDSCoreException;
import mds.core.MDSHrefFormatException;
import mds.persistence.PersistenceHandlerException;

/**
 * Abbildung eines UML- o. MOF- Models, besteht aus seiner 
 * XMI-Repräsentation, DTD, Schema und Files
 * 
 * @author Thomas Chille
 */
public interface MDSModel extends MDSPersistentObject {

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
	public ArrayList getModelVersions() throws MDSCoreException;

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
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return id des eingefügten MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertElement(MDSElement mdsElement)
		throws MDSCoreException, MDSHrefFormatException;

	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeElement(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException;

	/**
	 * validiert MDSModel
	 *
	 * @param href Pfad zum zu validierenden MDSModel
	 * @param validateType Art der Validierung(dtd, schema, strikt, ...)
	 * @return Messages der Validierung
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList validateModel(int validateType) throws MDSCoreException;

	/**
	 * führt eine Abfrage auf MDSModel aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList query(String query) throws MDSCoreException;

	/**
	 * Gets the umlFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getUmlFile();

	/**
	 * Gets the xmiFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getXmiFile();

	/**
	 * Gets the dtdFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getDtdFile();

	/**
	 * Gets the schemaFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getSchemaFile();

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
	 * @return Returns a ArrayList
	 */
	public ArrayList getAdditionalFiles();

	/**
	 * Sets the additionalFiles
	 * @param additionalFiles The additionalFiles to set
	 */
	public void setAdditionalFiles(ArrayList additionalFiles);

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
	 * @return Returns a ArrayList
	 */
	public ArrayList getElements();

	/**
	 * Sets the elements
	 * @param elements The elements to set
	 */
	public void setElements(ArrayList elements);
	
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter();

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int counter);
	
	public void update() throws PersistenceHandlerException;

}