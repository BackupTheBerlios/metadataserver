package mme.mapper;

import mme.mapper.XMLMapperProzessor;
import java.util.ArrayList;
import java.io.File;
/**
 * @author Christian Sterr
 *
 */
public class TestXMLMapper {

	public static void main(String[] args) throws Exception{
		XMLMapperProzessor trans = new XMLMapperProzessor ();
		
		ArrayList list = trans.startMapping("C:/1Anwendung/Studium/diplom/butterflyTest/test2/eltern.xmi", "C:/1Anwendung/Studium/diplom/butterflyTest/test2/eltern");
		for(int i=0; i<list.size(); i++){
			File file = (File)list.get(i);
			System.out.println("File Path: " + file.getAbsolutePath());
		}
	}
}
