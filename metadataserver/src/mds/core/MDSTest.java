package mds.core;

import api.mds.core.*;

public class MDSTest {

	public static void main(String[] args) throws MDSHrefFormatException{
		MetaDataServer server = new MetaDataServerImpl();
		MDSRepository rep = new MDSRepositoryImpl();
		MDSModel model = new MDSModelImpl();
		MDSClass myclass = new MDSClassImpl();
		System.out.println(
			server.insertElement(
				server.insertModel(
					server.insertReposititory(rep), 
					model), 
				myclass).getHref()
			);
	}
}
