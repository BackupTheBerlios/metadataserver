package mme.core;

import api.mds.core.MDSModel;
import api.mme.core.Mapping;
import api.mme.core.MetaMappingEngine;
import api.mme.mapper.MDSMapper;

/**
 * @see MetaMappingEngine
 * 
 * @author Thomas Chille
 */
public class MetaMappingEngineImpl implements MetaMappingEngine {

	/**
	 *alle auf dem Server vorhandenen Mapper
	 */
	private MDSMapper[] mapper = null;

	/**
	 * @see MetaMappingEngine#registerMapper(Mapper)
	 */
	public void registerMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
	}

	/**
	 * @see MetaMappingEngine#unregisterMapper(Mapper)
	 */
	public void unregisterMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
	}

	/**
	 * @see MetaMappingEngine#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException {
		return null;
	}

	/**
	 * @see MetaMappingEngine#getMappings(String, String)
	 */
	public Mapping[] getMappings(String from, String to)
		throws MetaMappingEngineException {
		return null;
	}

}