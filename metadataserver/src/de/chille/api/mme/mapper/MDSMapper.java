package de.chille.api.mme.mapper;

import de.chille.api.mds.core.*;
import de.chille.api.mme.core.Mapping;
import de.chille.mds.soap.MDSMapperBean;
import de.chille.mme.core.MetaMappingEngineException;


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
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public void map(MDSModel mdsModel)
		throws MetaMappingEngineException;
		
	public Mapping getMapping();
	
	public void setMapping(Mapping mapping);
	
	/**
	 * Gets the mappingFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getMappingFile();

	/**
	 * Sets the mappingFile
	 * @param mappingFile The mappingFile to set
	 */
	public void setMappingFile(MDSFile mappingFile);
	
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter();

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int newCounter);
	
	public void importBean(MDSMapperBean bean);
}