package mme.core;

import java.util.ArrayList;

import mds.core.MDSObjectImpl;
import mds.persistence.PersistenceHandlerException;

import api.mds.core.MDSModel;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;
import api.mme.core.MetaMappingEngine;
import api.mme.mapper.MDSMapper;

/**
 * @see MetaMappingEngine
 * 
 * @author Thomas Chille
 */
public class MetaMappingEngineImpl
	extends MDSObjectImpl
	implements MetaMappingEngine {

	/**
	 *alle auf dem Server vorhandenen Mapper
	 */
	private MDSMapper[] mapper = null;

	/**
	 * zum speichern der mapper
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * Constructor for MetaMappingEngineImpl.
	 */
	public MetaMappingEngineImpl() {
		try {
			mapper = this.getPersistenceHandler().loadMapper();
		} catch (PersistenceHandlerException e) {
		}
	}

	/**
	 * @see MetaMappingEngine#registerMapper(MDSMapper)
	 */
	public void registerMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
	}

	/**
	 * @see MetaMappingEngine#unregisterMapper(MDSMapper)
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
	public ArrayList getMappings(String from, String to)
		throws MetaMappingEngineException {
		return null;
	}

}