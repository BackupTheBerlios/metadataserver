package mme.mapper;

import mds.core.*;
import mme.core.*;

/**
 * stellt Methoden zur Transformation der MDSModels
 * bereit.
 * Wird vom XML- und UnicodeMapper erweitert
 * 
 * @author Thomas Chille
 */
public interface Mapper extends MDSObject
{	
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
}



