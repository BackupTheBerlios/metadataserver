package mme.core;

import api.mds.core.MDSModel;
import api.mme.core.Mapping;
import api.mme.core.MetaMappingEngine;
import api.mme.mapper.MDSMapper;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

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
			System.out.println("register mapper!!" + " size: " + mdsMapperVec.size());
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
			return mdsMapper.map(mdsModel);
		return mdsModel;
	}

	/**
	 * @see MetaMappingEngine#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to)
		throws MetaMappingEngineException {

		ArrayList mappingList = new ArrayList();

		if(from == null && to == null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				mappingList.add( currentMapping );
			}
			return mappingList;			
		}

		if(from == null && to != null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				if(to.compareToIgnoreCase(mappingTo) == 0){
					mappingList.add( currentMapping);
				}
			}
			return mappingList;
		}
			
		if(from != null && to == null){
			for(int i=0; i<mdsMapperVec.size(); i++){
			Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingFrom = currentMapping.getFrom();
				if(from.compareToIgnoreCase(mappingFrom) == 0){
					mappingList.add( currentMapping);
				}
			}
			return mappingList;
		}

		if(from != null && to != null){
			for(int i=0; i<mdsMapperVec.size(); i++){
				Mapping currentMapping = ((MDSMapper)mdsMapperVec.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				String mappingFrom = currentMapping.getFrom();
				if(from.compareToIgnoreCase(mappingFrom) == 0){
					mappingList.add( currentMapping);
				}
			}
			return mappingList;
		}
		return mappingList;
	}
	

	/**
	 * Method getMDSMapperForMapping.
	 * @param mapping
	 * @return MDSMapper
	 */
	public MDSMapper getMDSMapperForMapping(Mapping mapping){
		for(int i=0; i<mdsMapperVec.size(); i++){			
			MDSMapper  mdsMapper = (MDSMapper)mdsMapperVec.get(i);
			Mapping mapp = mdsMapper.getMapping();
			if(mapp == mapping)
				return mdsMapper;
		}
		return null;
	}
	
}