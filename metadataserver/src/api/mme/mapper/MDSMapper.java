package api.mme.mapper;

import java.io.File;

import mme.core.MetaMappingEngineException;

import api.mds.core.MDSModel;
import api.mds.core.MDSObject;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;

/**
 * stellt Methoden zur Transformation der MDSModels
 * bereit.
 * Wird vom XML- und UnicodeMapper erweitert
 * 
 * @author Thomas Chille
 */
public interface MDSMapper extends MDSObject {

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
	 * Gets the mapping
	 * @return Returns a Mapping
	 */
	public Mapping getMapping();

	/**
	 * Sets the mapping
	 * @param mapping The mapping to set
	 */
	public void setMapping(Mapping mapping);

	/**
	 * Gets the mappingFile
	 * @return Returns a File
	 */
	public File getMappingFile();

	/**
	 * Sets the mappingFile
	 * @param mappingFile The mappingFile to set
	 */
	public void setMappingFile(File mappingFile);

	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler();

	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler);
}