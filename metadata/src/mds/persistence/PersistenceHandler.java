package mds.persistence;

import java.util.*;
import mds.core.*;

/**
 * stellt Methoden zur dauerhaften Speicherung der MDSModels mit einer
 * einfachen Versionierung zur Verfügung
 * 
 * für jede Speicherart muß ein PersistenceHandler implementiert werden
 * 
 * @author Thomas Chille
 */
public interface PersistenceHandler {
	
	/**
	 * sichert komplettes MDSModel (xmi, [dtd], [xsd]),
	 * 
	 * aktualisiert automatisch die Versionsnummer im MDSModel
	 *
	 * @param mdsModel das zu sichernde MDSModel
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public void save(MDSModel mdsModel)
   	throws PersistenceHandlerException;
	
	/**
	 * lädt komplettes MDSModel (xmi, [dtd], [xsd]),
	 * 
	 * @param mdsPfad das MDSModel wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene MDSModel
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public MDSModel load(String mdsPfad, String version)
   	throws PersistenceHandlerException;
	
	/**
	 * löscht komplettes MDSModel (xmi, [dtd], [xsd]),
	 * 
	 * @param mdsModel das zu löschende MDSModel
	 * @param version Versionsnummer, wenn null dann alle Versionen löschen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public void delete(MDSModel mdsModel, String version)
   	throws PersistenceHandlerException;
	
	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zurück
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return Liste aller Versionen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public ArrayList getVersions(MDSModel mdsModel)
   	throws PersistenceHandlerException;
}

