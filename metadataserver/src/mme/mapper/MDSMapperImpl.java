package mme.mapper;

import java.io.File;

import mds.core.MDSObjectImpl;
import mme.core.MetaMappingEngineException;

import api.mds.core.MDSModel;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;
import api.mme.mapper.MDSMapper;
import api.mme.mapper.UnicodeMapper;
import api.mme.mapper.XMLMapper;

import java.util.ArrayList;

/**
 * @see MDSMapper
 * 
 * @author Christian Sterr
 */
public class MDSMapperImpl extends MDSObjectImpl implements MDSMapper {

	/**
	 * soll dieser Mapper die Packetstrucktur nachbilden
	 */
	private boolean buildPackage = true;

	/**
	 * das von diesem Mapper durchführbare Mapping
	 */
	private Mapping mapping = null;

	/**
	 * Datei mit den globalen Mappinginstruktionen
	 */
	private File mappingFile = null;
	
	/**
	 * der eigendliche Mapper
	 */
	public XMLMapper xmlMapper = null;

	/**
	 * 
	 */
	public UnicodeMapper unicodeMapper = null;

	/**
	 * zum speichern des models
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * @see Mapper#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel)
		throws MetaMappingEngineException {
		String xmlContend = mdsModel.getUmlFile().getContent();
		System.out.println("map: " + xmlContend);
		ArrayList fileList = null;
		if(xmlMapper != null){
			this.xmlMapper.setBuildPackage(this.getBuildPackage());
			fileList = this.xmlMapper.doMapping(xmlContend);
			// dann handelt es sich um einen XmlMapper
		}else{
			if(unicodeMapper != null)
			// dann handelt es sich um einen unicodeMapper
			this.unicodeMapper.setBuildPackage(this.getBuildPackage());
		}
		mdsModel.setAdditionalFiles(fileList);
		return mdsModel;
	}

	/**
	 * Gets the mapping
	 * @return Returns a Mapping
	 */
	public Mapping getMapping() {
		return mapping;
	}

	/**
	 * Sets the mapping
	 * @param mapping The mapping to set
	 */
	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	/**
	 * Gets the mappingFile
	 * @return Returns a File
	 */
	public File getMappingFile() {
		return mappingFile;
	}

	/**
	 * Sets the mappingFile
	 * @param mappingFile The mappingFile to set
	 */
	public void setMappingFile(File mappingFile) {
		this.mappingFile = mappingFile;
	}
	
	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}
	
	/**
	 * @see api.mme.mapper.MDSMapper#setBuildPackage(boolean)
	 */
	public void setBuildPackage(boolean buildPackage){
		this.buildPackage = buildPackage;
	}


	/**
	 * @see api.mme.mapper.MDSMapper#getBuildPackage()
	 */
	public boolean getBuildPackage(){
		return buildPackage;
	}	
}