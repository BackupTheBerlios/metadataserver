package mds.core;

import api.mds.core.*;

public class MDSTest {

	public static void main(String[] args) {
		MetaDataServer server = new MetaDataServerImpl();
		MDSRepository rep = new MDSRepositoryImpl();
		MDSModel model = new MDSModelImpl();
		String href = server.insertReposititory(rep);
		System.out.println(server.insertModel(href, model));
	}
}
