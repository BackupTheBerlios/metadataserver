package de.chille.mme.core;

import java.util.List;
import java.util.Vector;
import java.io.File;
import de.chille.mme.MmeGlobals;
import de.chille.mme.mapper.*;
import de.chille.api.mme.mapper.*;
import de.chille.api.mme.core.*;

/**
 * @author Christian Sterr
 *
 * helper Class die alle im xml Mapper Pfad
 * befindlichen Configs für Mapper sucht
 * und diese bei der de.chille.mme anmeldet
 */
public class MDSXmlMapperFactory {



	/**
	 * Method srearchPath.
	 */
	private static List srearchPath(){
		List xmlMapperList = new Vector();
		File parent = new File(MmeGlobals.XML_MAPPER_PFAD);
		System.out.println("Factory path abso:" + parent.getAbsolutePath());
		File[] mapperPath = parent.listFiles();
		for(int i=0; i<mapperPath.length; i++){	
			File xmlMapperConfigFile = new File(mapperPath[i], MmeGlobals.XML_MAPPER_CONFIG_FILE_NAME);
			if(xmlMapperConfigFile.exists()){
				System.out.println("Factory sub path: " + xmlMapperConfigFile.getAbsolutePath());
				XMLMapperImpl mapper = new XMLMapperImpl(xmlMapperConfigFile);
				xmlMapperList.add(mapper);
			}
		}
		System.out.println("länge: " + xmlMapperList.size());
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
			System.out.println("register: " + ((MDSMapper)xmlMapperList.get(i)).getClass());
			mmeengine.registerMapper((MDSMapper)xmlMapperList.get(i));
		}
	}
}
