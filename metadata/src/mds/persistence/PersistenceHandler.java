package mds.persistence;

import java.util.*;
import mds.core.*;

/**
 * stellt Methoden zur dauerhaften Speicherung der MDSModels mit einer
 * einfachen Versionierung zur Verf�gung
 * 
 * f�r jede Speicherart mu� ein PersistenceHandler implementiert werden
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
	 * l�dt komplettes MDSModel (xmi, [dtd], [xsd]),
	 * 
	 * @param mdsPfad das MDSModel wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene MDSModel
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public MDSModel load(String mdsPfad, String version)
   	throws PersistenceHandlerException;
	
	/**
	 * l�scht komplettes MDSModel (xmi, [dtd], [xsd]),
	 * 
	 * @param mdsModel das zu l�schende MDSModel
	 * @param version Versionsnummer, wenn null dann alle Versionen l�schen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public void delete(MDSModel mdsModel, String version)
   	throws PersistenceHandlerException;
	
	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zur�ck
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return Liste aller Versionen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public ArrayList getVersions(MDSModel mdsModel)
   	throws PersistenceHandlerException;
}

