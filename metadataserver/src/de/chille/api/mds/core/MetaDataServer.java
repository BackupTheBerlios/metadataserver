package de.chille.api.mds.core;

import java.util.ArrayList;
import java.util.Vector;

import de.chille.api.mme.core.Mapping;
import de.chille.api.mme.core.MetaMappingEngine;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mds.core.MDSCoreException;
import de.chille.mds.core.MDSHrefFormatException;

/**
 * stellt die Schnittstelle zu den Clients dar
 * und muss vom MDSCore implementiert werden 
 * 
 * @author Thomas Chille
 */
public interface MetaDataServer extends MDSPersistentObject {

	/**
	 * nicht validieren
	 */
	public static final int VALIDATE_NO = 1;

	/**
	 * strikt mit dtd validieren
	 */
	public static final int VALIDATE_DTD_STRIKT = 2;

	/**
	 * strikt mit schema validieren
	 */
	public static final int VALIDATE_SCHEMA_STRIKT = 3;

	/**
	 * erzeugt neues MDSRepository
	 *
	 * @param mdsRepository das einzufügende MDSRepository, id automatisch
	 * @return Pfad zum MDSRepository
	 */
	public MDSHref insertReposititory(MDSRepository mdsRepository);

	/**
	 * löscht ein MDSRepository
	 *
	 * @param href Pfad zum MDSRepository
	 * @return true bei Erfolg
	 */
	public Vector removeRepository(MDSHref href, boolean confirm);

	/**
	 * umbenennen eines MDSRepository
	 *
	 * @param Pfad zum MDSRepository
	 * @param label neuer Name
	 * @return true bei Erfolg
	 */
	public boolean renameRepository(MDSHref href, String label);

	/**
	 * führt eine Abfrage auf einem MDSRepository aus
	 *
	 * @param href Pfad zum MDSRepository
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 */
	public ArrayList queryRepository(MDSHref href, String query);

	/**
	 * fügt ein MDSModel in MDSRepository ein
	 *
	 * @param href an dieser spezifizierten Stelle 
	 * @param mdsModel das einzufügende MDSModel
	 * @return Pfad zum neu eingefügten MDSModel
	 */
	public MDSHref insertModel(MDSHref href, MDSModel mdsModel);

	public Vector removeModel(MDSHref href, boolean conffirm);

	/**
	 * verschiebt ein MDSModel in ein anderes MDSRepository
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return Pfad zur neuen MDSModel-Position
	 */
	public MDSHref moveModel(MDSHref from, MDSHref to);

	/**
	 * kopiert ein MDSModel
	 *
	 * @param from href spezifizierten Stelle 
	 * @param to an diese spezifizierten Stelle 
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSModel
	 */
	public MDSHref copyModel(MDSHref from, MDSHref to, String label);

	/**
	 * umbenennen eines MDSModel
	 *
	 * @param href Pfad zum MDSModel
	 * @param label neuer Name
	 * @return true bei Erfolg
	 */
	public boolean renameModel(MDSHref href, String label);

	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zurück
	 *
	 * @param href Pfad zum MDSModel
	 * @return Liste aller Versionen
	 */
	public ArrayList getModelVersions(MDSHref href);

	/**
	 * macht Änderungen an einem MDSModel rückgängig
	 *
	 * @param version Versionsnummer
	 * @return true bei Erfolg
	 */
	public boolean restoreModel(MDSHref href, String version);

	/**
	 * fügt ein MDSElement in MDSModel ein
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return Pfad zum neu eingefügten MDSElement
	 */
	public MDSHref insertElement(MDSHref href, MDSElement mdsElement);

	/**
	 * entfernt ein MDSElement aus einem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @return true bei Erfolg
	 */
	public Vector removeElement(MDSHref href, boolean conffirm);

	/**
	 * validiert MDSModel
	 *
	 * @param href Pfad zum zu validierenden MDSModel
	 * @param validateType Art der Validierung(dtd, schema, strikt, ...)
	 * @return Messages der Validierung
	 */
	public Vector validateModel(MDSHref href, int validateType);

	/**
	 * importiert MDSModel, wenn ein Mapping angeben wird
	 * wirkt dieses als Importfilter -  
	 * das MDSModel wird dabei im MDSRepository abgelegt
	 *
	 * @param href des Zielrepositories
	 * @param mdsModel das zu importierende MDSModel
	 * @param mapping das evtl. zu verwendende Mapping
	 * @return Pfad zum neu entstandenen MDSModel
	 */
	//public MDSHref importModel(MDSHref href, MDSModel mdsModel, Mapping mapping);

	/**
	 * exportiert MDSModel, wenn ein Mapping angeben wird
	 * wirkt dieses als Exportfilter - das evtl. neue MDSModel 
	 * wird dabei nicht im MDSRepository abgelegt
	 *
	 * @param href Pfad zum zu exportierenden MDSModel
	 * @param mapping das evtl. zu verwendende Mapping
	 * @return des exportierte MDSModel
	 */
	//public MDSModel exportModel(MDSHref href, Mapping mapping);

	/**
	 * meldet neuen Mapper bei MME an
	 *
	 * @param mapper der Mapper
	 * @return true bei Erfolg
	 */
	public boolean registerMapper(MDSMapper mapper);

	/**
	 * meldet Mapper ab
	 *
	 * @param mapper der abzumeldende Mapper
	 * @return true bei Erfolg
	 */
	public boolean unregisterMapper(MDSMapper mapper);

	/**
	 * gibt eine Liste aller angemeldeten Mappings zurück
	 *
	 * @param from mit diesem Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @return die Mappings
	 */
	public ArrayList getMappings(String from, String to);

	/**
	 * konvertiert ein MDSModel bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes MDSModel anderen Typs,
	 * evtl. sind dabei mehrere Mapping-Vorgänge notwendig -
	 *
	 * @param href Pfad zum Ausgangs-MDSModel
	 * @param mapping das Mapping
	 */
	public void convertModel(MDSHref href, Mapping mapping);

	/**
	 * Gets the metaMappingEngine
	 * @return Returns a MetaMappingEngine
	 */
	public MetaMappingEngine getMetaMappingEngine();

	/**
	 * Sets the metaMappingEngine
	 * @param metaMappingEngine The metaMappingEngine to set
	 */
	public void setMetaMappingEngine(MetaMappingEngine metaMappingEngine);
	
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter();

	/**
	 * Returns the repositories.
	 * @return ArrayList
	 */
	public ArrayList getRepositories();

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int counter);

	/**
	 * Method setRepositories.
	 * @param repositories
	 */
	public void setRepositories(ArrayList repositories);

	
	public void startup();
	
	public void shutdown();
	
	public MDSRepository getRepositoryByHref(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException;
	
}