package api.mme.mapper;

import java.io.File;
import java.util.ArrayList;

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
	 * @return ArrayList
	 */
	public ArrayList doMapping(String xmi)throws MetaMappingEngineException;
}