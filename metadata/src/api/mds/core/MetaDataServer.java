package api.mds.core;

import java.util.ArrayList;

import api.mme.core.Mapping;
import api.mme.mapper.MDSMapper;

/**
 * stellt die Schnittstelle zu den Clients dar
 * und muss vom MDSCore implementiert werden 
 * 
 * @author Thomas Chille
 */
public interface MetaDataServer {

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
	public static final int VALIDATE_SCHEMA_STRKT = 3;

	/**
	 * erzeugt neues MDSRepository
	 *
	 * @param mdsRepository das einzufügende MDSRepository, id automatisch
	 * @return Pfad zum MDSRepository
	 */
	public String insertReposititory(MDSRepository mdsRepository);

	/**
	 * löscht ein MDSRepository
	 *
	 * @param href Pfad zum MDSRepository
	 * @return true bei Erfolg
	 */
	public boolean deleteRepository(String href);

	/**
	 * umbenennen eines MDSRepository
	 *
	 * @param Pfad zum MDSRepository
	 * @param label neuer Name
	 * @return true bei Erfolg
	 */
	public boolean renameRepository(String href, String label);

	/**
	 * führt eine Abfrage auf einem MDSRepository aus
	 *
	 * @param href Pfad zum MDSRepository
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 */
	public String[] queryRepository(String href, String query);

	/**
	 * fügt ein MDSModel in MDSRepository ein
	 *
	 * @param href an dieser spezifizierten Stelle 
	 * @param mdsModel das einzufügende MDSModel
	 * @return Pfad zum neu eingefügten MDSModel
	 */
	public String insertModel(String href, MDSModel mdsModel);

	/**
	 * entfernt ein MDSModel aus einem MDSRepository
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @return true bei Erfolg
	 */
	public boolean removeModel(String href);

	/**
	 * verschiebt ein MDSModel in ein anderes MDSRepository
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return Pfad zur neuen MDSModel-Position
	 */
	public String moveModel(String from, String to);

	/**
	 * kopiert ein MDSModel
	 *
	 * @param from href spezifizierten Stelle 
	 * @param to an diese spezifizierten Stelle 
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSModel
	 */
	public String copyModel(String from, String to, String label);

	/**
	 * umbenennen eines MDSModel
	 *
	 * @param href Pfad zum MDSModel
	 * @param label neuer Name
	 * @return true bei Erfolg
	 */
	public boolean renameModel(String href, String label);

	/**
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zurück
	 *
	 * @param href Pfad zum MDSModel
	 * @return Liste aller Versionen
	 */
	public String[] getModelVersions(String href);

	/**
	 * macht Änderungen an einem MDSModel rückgängig
	 *
	 * @param version Versionsnummer
	 * @return true bei Erfolg
	 */
	public boolean restoreModel(String href, String version);

	/**
	 * fügt ein MDSElement in MDSModel ein
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return Pfad zum neu eingefügten MDSElement
	 */
	public String insertElement(String href, MDSElement mdsElement);

	/**
	 * entfernt ein MDSElement aus einem MDSModel
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @return true bei Erfolg
	 */
	public boolean removeElement(String href);

	/**
	 * verschiebt ein MDSElement innerhalb eines MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return Pfad zur neuen MDSElement-Position
	 */
	public String moveElement(String from, String to);

	/**
	 * kopiert ein MDSElement innerhalb eines MDSModel
	 * oder in ein anderes MDSModel
	 *
	 * @param from href spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @param label des neuen MDSElement, id wird automatisch vergeben
	 * @return Pfad zur Kopie des MDSElement
	 */
	public String copyElement(String from, String to, String label);

	/**
	 * validiert MDSModel
	 *
	 * @param href Pfad zum zu validierenden MDSModel
	 * @param validateType Art der Validierung(dtd, schema, strikt, ...)
	 * @return Messages der Validierung
	 */
	public String[] validateModel(String href, int validateType);

	/**
	 * importiert MDSModel, wenn ein Mapping angeben wird
	 * wirkt dieses als Importfilter -  
	 * das MDSModel wird dabei im MDSRepository abgelegt
	 *
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @param mdsModel das zu importierende MDSModel
	 * @param mapping das evtl. zu verwendende Mapping
	 * @return Pfad zum neu entstandenen MDSModel
	 */
	public String importModel(String label, MDSModel mdsModel, Mapping mapping);

	/**
	 * exportiert MDSModel, wenn ein Mapping angeben wird
	 * wirkt dieses als Exportfilter - das evtl. neue MDSModel 
	 * wird dabei nicht im MDSRepository abgelegt
	 *
	 * @param href Pfad zum zu exportierenden MDSModel
	 * @param mapping das evtl. zu verwendende Mapping
	 * @return des exportierte MDSModel
	 */
	public MDSModel exportModel(String href, Mapping mapping);

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
	 * das neue MDSModel wird automatisch im selben MDSRepository abgelegt
	 *
	 * @param href Pfad zum Ausgangs-MDSModel
	 * @param mapping das Mapping
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zum neu entstandenen MDSModel
	 */
	public String convertModel(String href, Mapping mapping, String label);
}