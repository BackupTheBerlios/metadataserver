package mme.core;

/**
 * bindet die metamapping.engine
 * an den metadata.server an
 * 
 * @author Thomas Chille
 */
public interface MetaMappingEngine {
	
	/**
	 * meldet neuen Mapper an
	 *
	 * @param mappingResource beinhaltet den Type und die ben�tigten Files
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public void registerMapper(MappingResource mappingResource) 
   	throws MetaMappingEngineException;
   	
	/**
	 * meldet Mapper ab
	 *
	 * @param mappingResource beinhaltet den Type und die ben�tigten Files
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public void unregisterMapper(Mapper mapper) 
   	throws MetaMappingEngineException;
   	
	/**
	 * mappt ein Modell bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes Modell anderen Typs
	 *
	 * @param model das Ausgangs-Modell
	 * @param mapping das Mapping
	 * @return des entstandene Modell
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public Model map(Model model, Mapping mapping) 
   	throws MetaMappingEngineException;
   	
	/**
	 * gibt eine Liste aller angemeldeten Mapper zur�ck
	 *
	 * @param type kann 'XML','Unicode' oder null sein
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public ArrayList getMapper(String type) 
   	throws MetaMappingEngineException;
   	
	/**
	 * gibt eine Liste aller durch die angemeldeten Mapper 
	 * m�glichen Mappings zur�ck,
	 *
	 * @param from mit dieser Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public ArrayList getMappings(String from, String to) 
   	throws MetaMappingEngineException;
}

