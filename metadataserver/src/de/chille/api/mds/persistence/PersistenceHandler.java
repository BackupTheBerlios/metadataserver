package de.chille.api.mds.persistence;

import java.util.ArrayList;

import de.chille.mds.persistence.PersistenceHandlerException;

import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mds.core.MDSPersistentObject;
import de.chille.api.mds.core.MetaDataServer;
import de.chille.api.mme.mapper.MDSMapper;

/**
 * stellt Methoden zur dauerhaften Speicherung der MDSObjecte mit einer
 * einfachen Versionierung für MDSModels zur Verfügung
 * 
 * für jede Speicherart muß ein PersistenceHandler implementiert werden
 * 
 * @author Thomas Chille
 */
public interface PersistenceHandler {

	/**
	 * sichert komplettes MDSObject
	 * 
	 * aktualisiert automatisch die Versionsnummer in einem MDSModel
	 *
	 * @param mdsObject das zu sichernde MDSPersistentObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void save(MDSPersistentObject mdsObject) throws PersistenceHandlerException;

	/**
	 * lädt komplettes MDSPersistentObject
	 * 
	 * @param href das MDSPersistentObject wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene MDSPersistentObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public MDSPersistentObject load(MDSHref href, String version)
		throws PersistenceHandlerException;

	/**
	 * löscht komplettes MDSPersistentObject 
	 * 
	 * @param mdsObject das zu löschende MDSPersistentObject
	 * @param version Versionsnummer, wenn null dann alle Versionen löschen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void delete(MDSPersistentObject mdsObject, String version)
		throws PersistenceHandlerException;

	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zurück
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return Liste aller Versionen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public ArrayList getModelVersions(MDSModel mdsModel)
		throws PersistenceHandlerException;

	public void load(MetaDataServer server);
	
}