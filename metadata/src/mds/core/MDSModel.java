package mds.core;

import java.util.*;

/**
 * Abbildung eines UML- o. MOF- Models, besteht aus seiner 
 * XMI-Repr�sentation, DTD, Schema und Files
 * 
 * @author Thomas Chille
 */
public interface MDSModel extends MDSObject {

	/**
	 * f�gt in MDSModel in MDSRepository ein
	 *
	 * @param href Pfad des MDSRepository
	 * @return Pfad zum neu MDSModel
	 */
	public String insertModel(String href) throws MDSCoreException;

	/**
	 * l�scht MDSModel
	 */
	public void removeModel() throws MDSCoreException;

	/**
	 * verschiebt MDSModel in ein anderes MDSRepository
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return Pfad zur neuen MDSModel-Position
	 */
	public String moveModel(String to) throws MDSCoreException;

	/**
	 * kopiert MDSModel
	 *
	 * @param from href spezifizierten Stelle 
	 * @param to an diese spezifizierten Stelle 
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSModel
	 */
	public String copyModel(String to, String label) throws MDSCoreException;

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
	 */
	public ArrayList getModelVersions(String href) throws MDSCoreException;

	/**
	 * macht �nderungen an einem MDSModel r�ckg�ngig
	 *
	 * @param version Versionsnummer
	 */
	public void restoreModel(String version) throws MDSCoreException;

	/**
	 * f�gt ein MDSElement in MDSModel ein
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return Pfad zum neu eingef�gten MDSElement
	 */
	public String insertElement(String href, MDSElement mdsElement) throws MDSCoreException;

	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 */
	public void removeElement(String href) throws MDSCoreException;

	/**
	 * verschiebt ein MDSElement innerhalb des MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an diesee spezifierten Stelle 
	 * @return Pfad zur neuen MDSElement-Position
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
	 */
	public String copyElement(String from, String to, String label) throws MDSCoreException;

	/**
	 * validiert MDSModel
	 *
	 * @param href Pfad zum zu validierenden MDSModel
	 * @param validateType Art der Validierung(dtd, schema, strikt, ...)
	 * @return Messages der Validierung
	 */
	public ArrayList validateModel(String validateType) throws MDSCoreException;

}