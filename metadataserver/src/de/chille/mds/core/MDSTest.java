package de.chille.mds.core;

import java.io.*;
import java.util.ArrayList;

import de.chille.mds.MDSGlobals;
import de.chille.mds.xmi.XMIHandlerImpl;

import de.chille.api.mds.core.*;

public class MDSTest {

	public static void main(String[] args)
		throws MDSHrefFormatException, MDSCoreException {
		
		MetaDataServer server = MetaDataServerImpl.getInstance();

		MDSRepository rep = new MDSRepositoryImpl();
		MDSHref rhref = server.insertReposititory(rep);
		
		MDSFile file1 = new MDSFileImpl();
		BufferedReader f;
		String line = null;
		String content = "";

		try {

			// sample laden
			f =
				new BufferedReader(
					new FileReader("sample2.xmi"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			f.close();
			file1.setContent(content);
			// sample-inhalt nach de.chille.mds konvertieren
			MDSModel model2 = new XMIHandlerImpl().mapUML2MDS(file1);
			
			// dabei neu entstandenes model auf server ablegen
			server.insertModel(rhref, model2);
			
			// und xmi-repräsentation ausprinten
			System.out.println(model2.getUmlFile().getContent());
			
			// und dtd-repräsentation ausprinten
			System.out.println(model2.getDtdFile().getContent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(server);

	}

}
