package api.mds.persistence;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSModel;
import api.mds.core.MDSObject;
import api.mme.mapper.MDSMapper;

/**
 * stellt Methoden zur dauerhaften Speicherung der MDSObjecte mit einer
 * einfachen Versionierung f�r MDSModels zur Verf�gung
 * 
 * f�r jede Speicherart mu� ein PersistenceHandler implementiert werden
 * 
 * @author Thomas Chille
 */
public interface PersistenceHandler {

	/**
	 * sichert komplettes MDSObject
	 * 
	 * aktualisiert automatisch die Versionsnummer in einem MDSModel
	 *
	 * @param mdsObject das zu sichernde MDSObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void save(MDSObject mdsObject) throws PersistenceHandlerException;

	/**
	 * l�dt komplettes MDSObject
	 * 
	 * @param href das MDSObject wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene MDSObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public MDSObject load(String href, String version)
		throws PersistenceHandlerException;

	/**
	 * l�scht komplettes MDSObject 
	 * 
	 * @param mdsObject das zu l�schende MDSObject
	 * @param version Versionsnummer, wenn null dann alle Versionen l�schen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void delete(MDSObject mdsObject, String version)
		throws PersistenceHandlerException;

	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zur�ck
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return Liste aller Versionen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public String[] getModelVersions(MDSModel mdsModel)
		throws PersistenceHandlerException;

	/**
	 * l�dt alle Mapper
	 * 
	 * @return Liste aller geladenen Mapper
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public MDSMapper[] loadMapper() throws PersistenceHandlerException;
}