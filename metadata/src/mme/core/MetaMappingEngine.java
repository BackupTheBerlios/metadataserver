package mme.core;

/**
 * bindet die metamapping.engine
 * an den metadata.server an
 * 
 * @author Thomas Chille
 */
public interface MetaMappingEngine {
	
	/**
	 * meldet neues Mapping bei Mapper an
	 *
	 * @param mappingResource beinhaltet MapperType, MappingTyp u. die benötigten Files
	 * @return des neu entstandene Mapping
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public Mapping registerMapping(MappingResource mappingResource) 
   	throws MetaMappingEngineException;
   	
	/**
	 * meldet Mapping ab
	 *
	 * @param mapping das abzumeldende Mapping
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public void unregisterMapping(Mapping mapping) 
   	throws MetaMappingEngineException;
   	
	/**
	 * mappt ein Modell bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes Modell anderen Typs
	 *
	 * @param model das Ausgangs-Modell mit seinen Ressoucen 
	 * @param mapping das Mapping
	 * @return des entstandene Modell mit seinen Ressoucen 
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public Model map(Model model, Mapping mapping) 
   	throws MetaMappingEngineException;
   	
	/**
	 * gibt eine Liste aller angemeldeten Mappings zurück
	 *
	 * @param from mit diesem Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public ArrayList getMappings(String from, String to) 
   	throws MetaMappingEngineException;
}

