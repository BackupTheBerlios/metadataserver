package mme.core;
import java.util.List;
import api.mme.mapper.*;
import java.io.File;
import api.mme.core.*;
import java.util.List;
import mme.mapper.*;


/**
 * @author Christian Sterr
 *
 */
public class TestMDSXmlMApperFactory {
	
	public static void main(String args[]){
		try{
			System.out.println("Test start");
			MetaMappingEngine mappingEngine = new MetaMappingEngineImpl();
			MDSXmlMapperFactory.registerAllXmlMapper(mappingEngine);
/*			List mapperList = mappingEngine.getMappings()
			for(int i=0; i<mapperList.size(); i++){
				XMLMapperImpl mapper = (XMLMapperImpl)mapperList.get(i);				
				File help = mapper.getConfigXml();
				mappingEngine.registerMapper(mapper);
				System.out.println("xml Config File Path: " + help);
				System.out.println("xml Config File Path exists: " + help.exists());
				System.out.println("xml Config File Path: " + help.getName());
			}*/
			List mappList = mappingEngine.getMappings(null, null);
			System.out.println("länge: " + mappList.size());

			for(int i=0; i<mappList.size(); i++){
				Mapping mapp = (Mapping)mappList.get(i);
				System.out.println("From: " + mapp.getFrom());
				System.out.println("To: " + mapp.getTo());
			}
		}catch(Exception e){
			System.out.println("error: debug TestMDS");
			e.printStackTrace();
		}
	}
}
