package mds.core;

import java.util.*;
import mme.core.*;

/**
 * stellt die Schnittstelle zu den Clients dar
 * und muss vom MDSCore implementiert werden 
 * 
 * @author Thomas Chille
 */
public interface MetaDataServer 
{
	/**
	 * erzeugt neues MDSRepository
	 *
	 * @param label des neuen MDSRepository, id wird automatisch vergeben
	 * @return Pfad zum MDSRepository
	 */
   	public String createReposititory(String label);
	
	/**
	 * l�scht ein MDSRepository
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
	 * f�hrt eine Abfrage auf einem MDSRepository aus
	 *
	 * @param href Pfad zum MDSRepository
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 */
   	public ArrayList queryRepository(String href, String query);
	
	/**
	 * f�gt ein MDSModel in MDSRepository ein
	 *
	 * @param href an dieser spezifizierten Stelle 
	 * @param mdsModel das einzuf�gende MDSModel
	 * @return Pfad zum neu eingef�gten MDSModel
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
	 * gibt Liste aller vorhandenen Versionen eines MDSModel zur�ck
	 *
	 * @param href Pfad zum MDSModel
	 * @return Liste aller Versionen
	 */
   	public ArrayList getModelVersions(String href);
	
	/**
	 * macht �nderungen an einem MDSModel r�ckg�ngig
	 *
	 * @param version Versionsnummer
	 * @return true bei Erfolg
	 */
   	public boolean restoreModel(String href, String version);
	
	/**
	 * f�gt ein MDSElement in MDSModel ein
	 *
	 * @param href an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @return Pfad zum neu eingef�gten MDSElement
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
   	public ArrayList validateModel(String href, String validateType);
	
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
	 * meldet neues Mapping bei Mapper an
	 *
	 * @param mappingResource beinhaltet MapperType, MappingTyp u. die ben�tigten Files
	 * @return des neu entstandene Mapping
	 */
   	public Mapping registerMapping(MappingResource mappingResource);
	
	/**
	 * meldet Mapping ab
	 *
	 * @param mapping das abzumeldende Mapping
	 */
   	public boolean unregisterMapping(Mapping mapping);
	
	/**
	 * gibt eine Liste aller angemeldeten Mappings zur�ck
	 *
	 * @param from mit diesem Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @return die Mappings
	 */
   	public ArrayList getMappings(String from, String to);
   		
	/**
	 * konvertiert ein MDSModel bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes MDSModel anderen Typs,
	 * evtl. sind dabei mehrere Mapping-Vorg�nge notwendig -
	 * das neue MDSModel wird automatisch im selben MDSRepository abgelegt
	 *
	 * @param href Pfad zum Ausgangs-MDSModel
	 * @param mapping das Mapping
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return Pfad zum neu entstandenen MDSModel
	 */
   	public String convertModel(String href, Mapping mapping, String label);
}

