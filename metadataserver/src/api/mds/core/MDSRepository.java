package api.mds.core;

import java.util.ArrayList;

import api.mds.persistence.PersistenceHandler;

import mds.core.MDSCoreException;

/**
 * beinhaltet die MDSModels
 * 
 * @author Thomas Chille
 */
public interface MDSRepository extends MDSObject {

	/**
	 * legt MDSRepository auf Server ab
	 *
	 * @return Pfad zum MDSRepository
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insert() throws MDSCoreException;

	/**
	 * entfernt MDSRepository und alle beinhaltenden MDSModels
	 * 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void delete() throws MDSCoreException;

	/**
	 * führt eine Abfrage auf MDSRepository und seinen MDSModels aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList query(String query) throws MDSCoreException;

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
	 * fügt in MDSModel in MDSRepository ein
	 *
	 * @param mdsModel das einzufügende Model
	 * @return Pfad zum neu MDSModel
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertModel(MDSModel mdsModel)
		throws MDSCoreException;

	/**
	 * löscht MDSModel
	 * 
	 * @param href Pfad des zu löschenden Model
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeModel(String href) throws MDSCoreException;

	/**
	 * verschiebt MDSModel in ein anderes MDSRepository
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return Pfad zur neuen MDSModel-Position
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String moveModel(String from, String to) throws MDSCoreException;

	/**
	 * kopiert MDSModel
	 *
	 * @param from href spezifizierten Stelle 
	 * @param to an diese spezifizierten Stelle 
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSModel
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String copyModel(String from, String to, String label)
		throws MDSCoreException;

}