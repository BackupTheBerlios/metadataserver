package mme.core;

import api.mds.core.MDSModel;
import api.mme.core.Mapping;
import api.mme.core.MetaMappingEngine;
import api.mme.mapper.MDSMapper;
import java.util.Vector;
import java.util.List;

/**
 * @see MetaMappingEngine
 * 
 * @author Christina Sterr
 */
public class MetaMappingEngineImpl implements MetaMappingEngine {

	/**
	 *alle auf dem Server vorhandenen Mapper
	 */
	private List mdsMapperVec = new Vector();

	/**
	 * @see MetaMappingEngine#registerMapper(Mapper)
	 */
	public void registerMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
			mdsMapperVec.add(mapper);
			
	}

	/**
	 * @see MetaMappingEngine#unregisterMapper(Mapper)
	 */
	public void unregisterMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
			mdsMapperVec.remove(mapper);
	}

	/**
	 * @see MetaMappingEngine#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException {
		MDSMapper mdsMapper = getMDSMapperForMapping(mapping);
		if(mdsMapper != null)
			return mdsMapper.map(mdsModel, mapping);
		return null;
	}

	/**
	 * @see MetaMappingEngine#getMappings(String, String)
	 */
	public List getMappings(String from, String to)
		throws MetaMappingEngineException {

		List mappingList = new Vector();

		if(from == null && to == null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Object currentMapping1 = mdsMapperVec.get(i);				
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				String mappingFrom = currentMapping.getFrom();
				mappingList.add( currentMapping );
			}
		}

		if(from == null && to != null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				if(to.compareToIgnoreCase(mappingTo) == 0){
					mappingList.add( currentMapping);
				}
			}
		}

		if(from != null && to == null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingFrom = currentMapping.getFrom();
				if(from.compareToIgnoreCase(mappingFrom) == 0){
					mappingList.add( currentMapping);
				}
			}
		}

		return mappingList;
	}
	

	/**
	 * Method getMDSMapperForMapping.
	 * @param mapping
	 * @return MDSMapper
	 */
	private MDSMapper getMDSMapperForMapping(Mapping mapping){
		for(int i=0; i<mdsMapperVec.size(); i++){			
			MDSMapper  mdsMapper = (MDSMapper)mdsMapperVec.get(i);
			Mapping mapp = mdsMapper.getMapping();
			if(mapp == mapping)
				return mdsMapper;
		}
		return null;
	}
	
}