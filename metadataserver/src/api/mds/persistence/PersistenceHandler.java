package api.mds.persistence;

import java.util.ArrayList;

import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSPersistentObject;
import api.mds.core.MetaDataServer;
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
	 * @param mdsObject das zu sichernde MDSPersistentObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void save(MDSPersistentObject mdsObject) throws PersistenceHandlerException;

	/**
	 * l�dt komplettes MDSPersistentObject
	 * 
	 * @param href das MDSPersistentObject wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene MDSPersistentObject
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public MDSPersistentObject load(MDSHref href, String version)
		throws PersistenceHandlerException;

	/**
	 * l�scht komplettes MDSPersistentObject 
	 * 
	 * @param mdsObject das zu l�schende MDSPersistentObject
	 * @param version Versionsnummer, wenn null dann alle Versionen l�schen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public void delete(MDSPersistentObject mdsObject, String version)
		throws PersistenceHandlerException;

	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zur�ck
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return Liste aller Versionen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public ArrayList getModelVersions(MDSModel mdsModel)
		throws PersistenceHandlerException;

	/**
	 * l�dt alle Mapper
	 * 
	 * @return Liste aller geladenen Mapper
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
	public MDSMapper[] loadMapper() throws PersistenceHandlerException;
	
	public void load(MetaDataServer server);
	
	public void save(MetaDataServer server) throws PersistenceHandlerException;
}