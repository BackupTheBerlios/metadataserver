package mds.core;

import api.mds.core.*;

public class MDSTest {

	public static void main(String[] args) throws MDSHrefFormatException{
		MetaDataServer server = new MetaDataServerImpl();
		MDSRepository rep = new MDSRepositoryImpl();
		MDSModel model = new MDSModelImpl();
		MDSClass myclass = new MDSClassImpl();
		MDSClass myclass2 = new MDSClassImpl();
		MDSHref href = server.insertModel(server.insertReposititory(rep), model);
		server.insertElement(href, 	myclass);
		server.insertElement(href, myclass2);
		System.out.println(server);
		model.getXmiFile();
	}
	
}
