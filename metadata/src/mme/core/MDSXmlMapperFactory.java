package mme.core;

import java.util.List;
import java.util.Vector;
import java.io.File;
import mme.MmeGlobals;
import mme.mapper.*;
import api.mme.mapper.*;
import api.mme.core.*;

/**
 * @author Christian Sterr
 *
 * helper Class die alle im xml Mapper Pfad
 * befindlichen Configs für Mapper sucht
 * und diese bei der mme anmeldet
 */
public class MDSXmlMapperFactory {



	/**
	 * Method srearchPath.
	 */
	private static List srearchPath(){
		List xmlMapperList = new Vector();
		File parent = new File(MmeGlobals.XML_MAPPER_PFAD);
		File[] mapperPath = parent.listFiles();
		for(int i=0; i<mapperPath.length; i++){
			File xmlMapperConfigFile = new File(mapperPath[i], MmeGlobals.XML_MAPPER_CONFIG_FILE_NAME);
			XMLMapperImpl mapper = new XMLMapperImpl(xmlMapperConfigFile);
			xmlMapperList.add(mapper);
		}
		return xmlMapperList;
	}
	
	public static void setMappings(List mapperList){
		
	}
	
	/**
	 * Method registerAllXmlMapper.
	 * @param mmeengine
	 * @throws MetaMappingEngineException
	 */
	public static void registerAllXmlMapper(MetaMappingEngine mmeengine)throws MetaMappingEngineException{
		List xmlMapperList = srearchPath();
		for(int i=0; i<xmlMapperList.size(); i++){
			mmeengine.registerMapper((MDSMapper)xmlMapperList.get(i));
		}
	}
}
