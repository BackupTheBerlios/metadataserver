package api.mds.core;

import java.io.File;
import java.util.ArrayList;

import api.mds.persistence.PersistenceHandler;
import api.mds.xmi.XMIHandler;

import mds.core.MDSCoreException;
import mds.core.MDSHrefFormatException;

/**
 * Abbildung eines UML- o. MOF- Models, besteht aus seiner 
 * XMI-Repr�sentation, DTD, Schema und Files
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
	 * gibt Liste aller vorhandenen Versionen des MDSModel zur�ck
	 *
	 * @return Liste aller Versionen
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList getModelVersions() throws MDSCoreException;

	/**
	 * macht �nderungen an einem MDSModel r�ckg�ngig
	 *
	 * @param version Versionsnummer
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void restoreModel(String version) throws MDSCoreException;

	/**
	 * f�gt ein MDSElement in MDSModel ein
	 *
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return id des eingef�gten MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertElement(MDSElement mdsElement)
		throws MDSCoreException;

	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeElement(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException;

	/**
	 * verschiebt ein MDSElement in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an diese spezifierten Stelle 
	 * @return id des MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String moveElement(MDSHref from, MDSHref to)
		throws MDSCoreException, MDSHrefFormatException;

	/**
	 * kopiert ein MDSElement innerhalb eines MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from von spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @param label des neuen MDSElement, id wird automatisch vergeben
	 * @return id der Kopie des MDSElement
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String copyElement(MDSHref from, MDSHref to, String label)
		throws MDSCoreException;

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
	 * f�hrt eine Abfrage auf MDSModel aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList query(String query) throws MDSCoreException;

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

}