package mme.core;

import java.util.*;
import mds.core.*;
import mme.mapper.*;

/**
 * bindet die metamapping.engine
 * an den metadata.server an
 * 
 * @author Thomas Chille
 */
public interface MetaMappingEngine
{
	/**
	 * meldet neuen Mapper bei MME an
	 *
	 * @param mapper der Mapper
	 * @return true bei Erfolg
	 */
   	public void registerMapper(Mapper mapper) 
   	throws MetaMappingEngineException;
   	
	/**
	 * meldet Mapper ab
	 *
	 * @param mapper der abzumeldende Mapper
	 */
   	public void unregisterMapper(Mapper mapper)
	throws MetaMappingEngineException;
   	
	/**
	 * mappt ein MDSModel bestimmten Typs auf ein 
	 * durchs Mapping spezifziertes MDSModel anderen Typs
	 *
	 * @param model das Ausgangs-MDSModel mit seinen Ressoucen 
	 * @param mapping das Mapping
	 * @return des entstandene MDSModel mit seinen Ressoucen 
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public MDSModel map(MDSModel mdsModel, Mapping mapping) 
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
}

