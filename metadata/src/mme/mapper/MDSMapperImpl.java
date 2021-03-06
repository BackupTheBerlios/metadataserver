package mme.mapper;

import java.io.File;

import mds.core.MDSObjectImpl;
import mme.core.MetaMappingEngineException;

import api.mds.core.MDSModel;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;
import api.mme.mapper.MDSMapper;

/**
 * @see MDSMapper
 * 
 * @author Christian Sterr
 */
public class MDSMapperImpl extends MDSObjectImpl implements MDSMapper {

	/**
	 * das von diesem Mapper durchführbare Mapping
	 */
	private Mapping mapping = null;

	/**
	 * Datei mit den globalen Mappinginstruktionen
	 */
	private File mappingFile = null;

	/**
	 * zum speichern des models
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * @see Mapper#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException {
		return null;
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
}