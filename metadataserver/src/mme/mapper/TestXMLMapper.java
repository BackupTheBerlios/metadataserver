package mme.mapper;

import mme.mapper.XmlMapperProzessor;
/**
 * @author Christian Sterr
 *
 */
public class TestXMLMapper {

	public static void main(String[] args) throws Exception{
		XmlMapperProzessor trans = new XmlMapperProzessor ();
		
		trans.startMapping("C:/1Anwendung/Studium/diplom/butterflyTest/test2/test.xmi", "C:/1Anwendung/Studium/diplom/butterflyTest/test2/testXmi");
	}
}
