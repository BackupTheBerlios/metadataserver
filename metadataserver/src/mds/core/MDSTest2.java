package mds.core;

import java.io.*;
import java.util.ArrayList;

import mds.MDSGlobals;
import mds.persistence.PersistenceHandlerException;
import mds.xmi.XMIHandlerImpl;

import api.mds.core.*;

public class MDSTest2 {

	public static void main(String[] args)
		throws MDSHrefFormatException, MDSCoreException {

		MetaDataServer server = new MetaDataServerImpl();

		MDSRepository rep = new MDSRepositoryImpl();
		rep.setLabel("myRepppp");

		// testmodel erstellen
		MDSModel model = new MDSModelImpl();
		model.setLabel("erstes");

		MDSClass myclass1 = new MDSClassImpl();
		myclass1.setLabel("MyClass0");

		MDSClass myclass2 = new MDSClassImpl();
		myclass2.setLabel("MyClass1");

		MDSClass myclass3 = new MDSClassImpl();
		myclass3.setLabel("MyClass2");

		MDSClass myclass4 = new MDSClassImpl();
		myclass4.setLabel("MyClass3");

		MDSClass myclass5 = new MDSClassImpl();
		myclass5.setLabel("MyClass4");

		MDSClass myclass6 = new MDSClassImpl();
		myclass6.setLabel("MyClass5");

		MDSClass myclass7 = new MDSClassImpl();
		myclass7.setLabel("MyClass6");

		MDSClass myclass8 = new MDSClassImpl();
		myclass8.setLabel("MyClass7");

		MDSAssociationEnd end1 = new MDSAssociationEndImpl();
		//end1.setMultiplicity(MDSAssociationEnd.MULTIPLICITY_0_OR_1);
		end1.setMdsClass(myclass1);

		MDSAssociationEnd end2 = new MDSAssociationEndImpl();
		//end2.setMultiplicity(MDSAssociationEnd.MULTIPLICITY_0_OR_MORE);
		end2.setMdsClass(myclass2);

		MDSAssociationEnd end3 = new MDSAssociationEndImpl();
		end3.setMdsClass(myclass3);

		MDSAssociationEnd end4 = new MDSAssociationEndImpl();
		end4.setMdsClass(myclass4);

		MDSAssociationEnd end5 = new MDSAssociationEndImpl();
		end5.setMdsClass(myclass7);

		MDSAssociationEnd end6 = new MDSAssociationEndImpl();
		end6.setMdsClass(myclass8);

		MDSAssociation compo = new MDSAssociationImpl();
		compo.setLabel("Compo");
		end5.setAggregation(MDSAssociationEnd.COMPOSITE_AGGREGATION);
		compo.addAssociationEnd(end5);
		compo.addAssociationEnd(end6);

		MDSAssociation asso = new MDSAssociationImpl();
		asso.setLabel("Asso");
		asso.addAssociationEnd(end1);
		asso.addAssociationEnd(end2);

		MDSAssociation aggi = new MDSAssociationImpl();
		aggi.setLabel("Aggi");
		end3.setAggregation(MDSAssociationEnd.SHARED_AGGREGATION);
		aggi.addAssociationEnd(end3);
		aggi.addAssociationEnd(end4);

		MDSGeneralization geni = new MDSGeneralizationImpl();
		geni.setLabel("Geni");
		geni.setSuperClass(myclass5);
		geni.setSubClass(myclass6);

		MDSHref rhref = server.insertReposititory(rep);

		MDSHref href = server.insertModel(rhref, model);

		server.insertElement(href, myclass1);
		server.insertElement(href, myclass2);
		server.insertElement(href, myclass3);
		server.insertElement(href, myclass4);
		server.insertElement(href, myclass5);
		server.insertElement(href, myclass6);
		server.insertElement(href, myclass7);
		server.insertElement(href, myclass8);
		server.insertElement(href, asso);
		server.insertElement(href, aggi);
		server.insertElement(href, geni);
		server.insertElement(href, compo);

		ArrayList files = new ArrayList();
		MDSFile file = new MDSFileImpl();
		file.setName("test");
		file.setPath(".");
		file.setType("plain_text");
		files.add(file);
		model.setAdditionalFiles(files);

		// zweites testmodel mit erstem als metamodel
		MDSModel model1 = new MDSModelImpl();
		model1.setLabel("zweites");
		model1.setMetamodel(model);

		href = server.insertModel(rhref, model1);
		server.insertElement(href, myclass1);
		
		try {
			model.save();
			model1.save();
		} catch (PersistenceHandlerException e) {
		}
		
		// vom ersten die xmi-repräsentation ausprinten
		String xmiContent = model.getXmiFile().getContent();
		System.out.println(xmiContent);

		// vom zweiten die xmi-repräsemtation ausprinten
		xmiContent = model1.getXmiFile().getContent();
		System.out.println(xmiContent);

		// erstes model als sample speichern
		FileWriter f1;
		MDSFile file1 = new MDSFileImpl();
		BufferedReader f;
		String line = null;
		String content = "";

		try {

			f1 = new FileWriter("sample.xmi");
			f1.write(model.getXmiFile().getContent());
			f1.close();

			// sample laden
			f = new BufferedReader(new FileReader("sample.xmi"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			f.close();
			file1.setContent(content);
			// sample-inhalt nach mds konvertieren
			//MDSModel model2 = new XMIHandlerImpl().mapXMI2MDS(file1);

			// dabei neu entstandenes model auf server ablegen
			//server.insertModel(rhref, model2);

			// und xmi-repräsentation ausprinten
			//System.out.println(model2.getXmiFile().getContent());

			// und dtd-repräsentation ausprinten
			//System.out.println(model2.getDtdFile().getContent());

		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println(server);

		//System.out.println(model.getSchemaFile().getContent());
	}

}
