package de.chille.api.mme.core;

import java.util.ArrayList;
import java.util.Vector;

import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * bindet die metamapping.engine
 * an den metadata.server an
 * 
 * @author Christain Sterr
 */
public interface MetaMappingEngine {

	/**
	 * meldet neuen Mapper bei MME an
	 *
	 * @param mapper der Mapper
	 */
	public void registerMapper(MDSMapper mapper) throws MetaMappingEngineException;

	/**
	 * meldet Mapper ab
	 *
	 * @param mapper der abzumeldende Mapper
	 */
	public void unregisterMapper(MDSMapper mapper)
		throws MetaMappingEngineException;

	/**
	 * mappt ein MDSModel bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes MDSModel anderen Typs
	 *
	 * @param model das Ausgangs-MDSModel mit seinen Ressoucen 
	 * @param mapping das Mapping
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public void map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException;

	/**
	 * gibt eine Liste aller angemeldeten Mappings zurück
	 *
	 * @param from mit diesem Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @return die Mappings
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public ArrayList getMappings(String from, String to)
		throws MetaMappingEngineException;
		
	/**
	 * gibt einen zum Mapping passenden Mapper zurück
	 * Method getMDSMapperForMapping.
	 * @param mapping
	 * @return MDSMapper
	 */
	public MDSMapper getMDSMapperForMapping(Mapping mapping);	
	
}