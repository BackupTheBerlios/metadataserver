package mme.core;
import java.util.*;
import api.mme.mapper.*;
import java.io.*;
import api.mme.core.*;
import java.util.List;
import mme.mapper.*;
import mds.core.*;
import api.mds.core.*;


/**
 * @author Christian Sterr
 *
 */
public class TestMDSXmlMApperFactory {
	
	public static void main(String args[]){
		MetaMappingEngine mappingEngine = null;
		try{
			System.out.println("Test start");
			mappingEngine = new MetaMappingEngineImpl();
			MDSXmlMapperFactory.registerAllXmlMapper(mappingEngine);
			List mappList = mappingEngine.getMappings(null, null);
			System.out.println("länge: !!!!!!!!!!!!! " + mappList.size());
			for(int i=0; i<mappList.size(); i++){
				Mapping mapp = (Mapping)mappList.get(i);
				System.out.println("From: " + mapp.getFrom());
				System.out.println("To: " + mapp.getTo());
			}
		}catch(Exception e){
			System.out.println("error: debug TestMDS");
			e.printStackTrace();
		}
//			File file = new File("C:/1Anwendung/Studium/diplom/butterflyTest/test2/eltern.xmi");
//			FileInputStream inStream = new FileInputStream(file);			
			BufferedReader f;
			StringBuffer line = new StringBuffer();

		MDSModel testModel = new MDSModelImpl();
		MDSFileImpl mdsFile = new MDSFileImpl();
		try {
			f = new BufferedReader(
			new FileReader("C:/1Anwendung/Studium/diplom/butterflyTest/test2/eltern.xmi"));
			String app = f.readLine();
			while (app != null) {
				line.append(app);
				app = f.readLine();
				System.out.println(app);
			}
			f.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen der Datei");
		}
//		System.out.println("toSring()" + line.toString());
		mdsFile.setContent(line.toString());
		//testModel.setXmiFile(mdsFile);
//		System.out.println("mdsFile: " + mdsFile.getContent());		
		try{
			MDSMapper mdsMapper = mappingEngine.getMDSMapperForMapping((Mapping)mappingEngine.getMappings("xmi-argo", "java").get(0));
			System.out.println("testMain: " + testModel.getXmiFile().getContent());
			mdsMapper.map(testModel);
			ArrayList aList = testModel.getAdditionalFiles();
			for(int i=0; i<aList.size(); i++){
				System.out.println("aList: " + ((File)aList.get(i)).getAbsolutePath());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
