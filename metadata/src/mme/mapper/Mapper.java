package mme.mapper;

import java.util.*;
import mds.core.*;
import mme.core.*;

/**
 * stellt Methoden zur Transformation der MDSModels
 * bereit.
 * Wird vom XML- und UnicodeMapper implementiert
 * 
 * @author Thomas Chille
 */
public interface Mapper 
{	
	/**
	 * meldet neues Mapping bei Mapper an
	 *
	 * @param mappingResource beinhaltet MapperType, MappingTyp u. die ben�tigten Files
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
	 * gibt eine Liste aller angemeldeten Mappings zur�ck
	 *
	 * @param from mit diesem Quelltyp, wenn null mit allen Quelltypen
	 * @param to mit diesem Zieltyp, wenn null mit allen Zieltypen
	 * @return die Mappings
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
   	public ArrayList getMappings(String from, String to) 
   	throws MetaMappingEngineException;
}



