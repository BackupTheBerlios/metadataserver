package api.mme.mapper;

import mme.core.MetaMappingEngineException;

import api.mds.core.MDSModel;
import api.mds.core.MDSObject;
import api.mme.core.Mapping;
import java.io.File;

/**
 * stellt Methoden zur Transformation der MDSModels
 * bereit.
 * Wird vom XML- und UnicodeMapper erweitert
 * 
 * @author Christian Sterr
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
	 * Method getMapping.
	 * @return Mapping
	 */
	public Mapping getMapping();
	
	/**
	 * Method setMappingFile.
	 * @param mappingFile
	 */
	public void setMappingFile(File mappingFile);
	
	/**
	 * Method getMappingFile.
	 * @return File
	 */
	public File getMappingFile();
		
}