package api.mds.core;

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
	public String[] query(String query) throws MDSCoreException;
	
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
}