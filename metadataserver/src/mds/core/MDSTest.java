package mds.core;

import api.mds.core.*;

public class MDSTest {

	public static void main(String[] args)
		throws MDSHrefFormatException, MDSCoreException {

		MetaDataServer server = new MetaDataServerImpl();

		MDSRepository rep = new MDSRepositoryImpl();

		MDSModel model = new MDSModelImpl();

		MDSClass myclass1 = new MDSClassImpl();
		myclass1.setLabel("myclass0");

		MDSClass myclass2 = new MDSClassImpl();
		myclass2.setLabel("myclass1");

		MDSClass myclass3 = new MDSClassImpl();
		myclass3.setLabel("myclass2");

		MDSClass myclass4 = new MDSClassImpl();
		myclass4.setLabel("myclass3");

		MDSClass myclass5 = new MDSClassImpl();
		myclass5.setLabel("myclass4");

		MDSClass myclass6 = new MDSClassImpl();
		myclass6.setLabel("myclass5");

		MDSClass myclass7 = new MDSClassImpl();
		myclass5.setLabel("myclass6");

		MDSClass myclass8 = new MDSClassImpl();
		myclass6.setLabel("myclass7");


		AssociationEnd end1 = new AssociationEndImpl();
		end1.setMultiplicity(AssociationEnd.MULTIPLICITY_0_OR_1);
		end1.setMdsClass(myclass1);

		AssociationEnd end2 = new AssociationEndImpl();
		end2.setMultiplicity(AssociationEnd.MULTIPLICITY_0_OR_MORE);
		end2.setMdsClass(myclass2);

		AssociationEnd end3 = new AssociationEndImpl();
		end3.setMdsClass(myclass3);

		AssociationEnd end4 = new AssociationEndImpl();
		end4.setMdsClass(myclass4);

		AssociationEnd end5 = new AssociationEndImpl();
		end5.setMdsClass(myclass7);

		AssociationEnd end6 = new AssociationEndImpl();
		end6.setMdsClass(myclass8);

		MDSAssociation compo = new MDSAssociationImpl(MDSAssociation.COMPOSITE_AGGREGATION);
		compo.setLabel("compo");
		compo.addAssociationEnd(end5);
		compo.addAssociationEnd(end6);

		MDSAssociation asso = new MDSAssociationImpl();
		asso.setLabel("asso");
		asso.addAssociationEnd(end1);
		asso.addAssociationEnd(end2);

		MDSAssociation aggi = new MDSAssociationImpl(MDSAssociation.SHARED_AGGREGATION);
		aggi.setLabel("aggi");
		aggi.addAssociationEnd(end3);
		aggi.addAssociationEnd(end4);

		MDSGeneralization geni = new MDSGeneralizationImpl();
		geni.setLabel("geni");
		geni.setSuperClass(myclass5);
		geni.setSubClass(myclass6);

		MDSHref href =
			server.insertModel(server.insertReposititory(rep), model);

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

		//MDSModel model1 = new MDSModelImpl();
		//model1.setMetamodel(model);
		
		//href = server.insertModel(erver.insertReposititory(rep), model1);
		//server.insertElement(href, myclass1);
		
		System.out.println(server);

		System.out.println(model.getXmiFile().getContent());
		//System.out.println(model.getDtdFile().getContent());
		//System.out.println(model.getSchemaFile().getContent());
	}

}
