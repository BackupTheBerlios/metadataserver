package mme.core;

import java.util.*;
import mds.core.*;

/**
 * @see MetaMappingEngine
 */
public class MetaMappingEngineImpl implements MetaMappingEngine 
{
	/**
	 * @see MetaMappingEngine#registerMapping(MappingResource)
	 */
	public Mapping registerMapping(MappingResource mappingResource) 
	throws MetaMappingEngineException 
	{
		return null;
	}

	/**
	 * @see MetaMappingEngine#unregisterMapping(Mapping)
	 */
	public void unregisterMapping(Mapping mapping) 
	throws MetaMappingEngineException 
	{
	}

	/**
	 * @see MetaMappingEngine#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel, Mapping mapping) 
	throws MetaMappingEngineException 
	{
		return null;
	}

	/**
	 * @see MetaMappingEngine#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to) 
	throws MetaMappingEngineException {
		return null;
	}
}

