package api.mme.mapper;

import java.io.File;
import mme.core.MetaMappingEngineException;

/**
 * Spezialisierung des Mappers für XML-Quellen
 * 
 * @author Christian Sterr
 */
public interface XMLMapper extends MDSMapper {

	/**
	 * Method setConfig.
	 * @param configFile
	 */
//	public void setConfig(File configFile);

	/**
	 * Method doMapping.
	 * @param xmi
	 * @return String
	 */
	public String doMapping(String xmi)throws MetaMappingEngineException;
}