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
 * @author Thomas Chille
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
	 * @see MDSMapper#map(MDSModel, Mapping)
	 */
	public MDSModel map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException {
		return null;
	}

	/**
	 * @see MDSMapper#getMapping()
	 */
	public Mapping getMapping() {
		return mapping;
	}

	/**
	 * @see MDSMapper#setMapping(Mapping)
	 */
	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	/**
	 * @see MDSMapper#getMappingFile()
	 */
	public File getMappingFile() {
		return mappingFile;
	}

	/**
	 * @see MDSMapper#setMappingFile(File)
	 */
	public void setMappingFile(File mappingFile) {
		this.mappingFile = mappingFile;
	}

	/**
	 * @see MDSMapper#getPersistenceHandler()
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}

	/**
	 * @see MDSMapper#setPersistenceHandler(PersistenceHandler)
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}
}