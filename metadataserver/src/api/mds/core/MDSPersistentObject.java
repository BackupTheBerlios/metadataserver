package api.mds.core;

import mds.persistence.PersistenceHandlerException;

import api.mds.persistence.PersistenceHandler;

/**
 * Superinterface aller persistenten MDS-Datatypes
 * 
 * @author Thomas Chille
 */
public interface MDSPersistentObject extends MDSObject {

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
	 * legt object im persistenten speicher ab bzw.
	 * aktualsiert es
	 * 
	 * @throws PersistenceHandlerException
	 */
	public void save() throws PersistenceHandlerException;

	/**
	 * lädt object aus dem persistenten speicher bzw.
	 * aktualisiert es von dort
	 * 
	 * @param version gleich null: lade aktuellstes, sonst diese version
	 * @return MDSPersistentObject
	 * @throws PersistenceHandlerException
	 */
	public MDSPersistentObject load(String version)
		throws PersistenceHandlerException;

	/**
	 * löscht object aus dem persistenten speicher
	 * 
	 * @param version gleich null: lösche komplett, sonst nur diese version
	 * @throws PersistenceHandlerException
	 */
	public void delete(String version) throws PersistenceHandlerException;

}