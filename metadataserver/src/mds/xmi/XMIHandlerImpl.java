package mds.xmi;

import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import mds.core.MDSAssociationImpl;
import mds.core.MDSClassImpl;
import mds.core.MDSFileImpl;
import mds.core.MDSGeneralizationImpl;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSAssociation;
import api.mds.core.MDSClass;
import api.mds.core.MDSElement;
import api.mds.core.MDSFile;
import api.mds.core.MDSModel;
import api.mds.xmi.XMIHandler;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMIHandlerImpl implements XMIHandler {

	/**
	 * @see api.mds.xmi.XMIHandler#mapMDS2XMI(MDSModel)
	 */
	public MDSFile mapMDS2XMI(MDSModel mdsModel) throws XMIHandlerException {

		String xdoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		//xdoc += "<!DOCTYPE XMI SYSTEM \"file:///D:/Eigene Dateien/diplom/metadataserver/uml1998.dtd\">\n";
		xdoc += "<XMI xmi.version=\"1.1\" xmlns:UML=\"org.omg/UML1.3\">\n";
		xdoc += "\t<XMI.header>\n";
		xdoc += "\t\t<XMI.model xmi.name=\"testmodel\" href=\"test.xmi\"/>\n";
		xdoc += "\t\t<XMI.metamodel xmi.name=\"UML\" href=\"uml.xml\"/>\n";
		xdoc += "\t</XMI.header>\n";
		xdoc += "\t<XMI.content>\n";

		String xclass = "\t<UML:Class xmi:id=\"#id#\" name=\"#id#\"/>\n";

		String xgeneralization =
			"\t<UML:Class xmi:id=\"#id#\" name=\"#id#\" generalization=\"#superId#\"/>\n";

		String xassociation1 = "\t<UML:Association>\n";
		xassociation1 += "\t\t<UML:Association.connection>\n";
		xassociation1
			+= "\t\t\t<UML:AssociationEnd name=\"#id#\" aggregation=\"#aggregation#\" type=\"#endId#\"/>\n";

		String xassociation2 =
			"\t\t\t<UML:AssociationEnd name=\"#id#\" aggregation=\"#aggregation#\" type=\"#endId#\"/>\n";
		xassociation2 += "\t\t</UML:Association.connection>\n";
		xassociation2 += "\t</UML:Association>\n";

		String xfooter = "\t</XMI.content>\n";
		xfooter += "</XMI>";

		ArrayList classes = new ArrayList();
		ArrayList associations = new ArrayList();
		ArrayList generalizations = new ArrayList();
		MDSElement element = null;
		Iterator i = mdsModel.getElements().iterator();
		Iterator j;
		MDSClass mdsClass = null, subClass = null;

		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSClassImpl) {
				classes.add(element);
			} else if (element instanceof MDSGeneralizationImpl) {
				subClass = ((MDSGeneralizationImpl) element).getSubClass();
				j = classes.iterator();
				while (j.hasNext()) {
					mdsClass = (MDSClassImpl) j.next();
					if (mdsClass.getId().equals(subClass.getId())) {
						classes.remove(subClass);
					}
					generalizations.add(element);
					break;
				}
			} else {
				associations.add(element);
			}
		}

		ArrayList ends;
		api.mds.core.AssociationEnd end1, end2;
		i = classes.iterator();
		while (i.hasNext()) {
			xdoc += xclass.replaceAll("#id#", ((MDSElement) i.next()).getId());
		}
		i = generalizations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			xdoc
				+= xgeneralization
					.replaceAll(
						"#id#",
						((MDSGeneralizationImpl) element)
							.getSubClass()
							.getId())
					.replaceAll(
						"#superId#",
						((MDSGeneralizationImpl) element)
							.getSuperClass()
							.getId());
		}
		i = associations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			ends = ((MDSAssociationImpl) element).getAssociationEnds();
			end1 = (AssociationEnd) ends.get(0);
			end2 = (AssociationEnd) ends.get(1);
			String aggregation = null;
			switch (((MDSAssociationImpl) element).getAggregation()) {
				case MDSAssociation.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case MDSAssociation.SHARED_AGGREGATION :
					aggregation = "shared";
					break;
				case MDSAssociation.COMPOSITE_AGGREGATION :
					aggregation = "composite";
					break;
			}
			xdoc
				+= xassociation1
					.replaceAll("#id#", element.getId())
					.replaceAll("#endId#", end1.getMdsClass().getId())
					.replaceAll("#aggregation#", aggregation);
			xdoc
				+= xassociation2
					.replaceAll("#id#", element.getId())
					.replaceAll("#endId#", end2.getMdsClass().getId())
					.replaceAll("#aggregation#", aggregation);
		}

		xdoc += xfooter;
		MDSFile mdsFile = new MDSFileImpl();
		mdsFile.setContent(xdoc);

		return mdsFile;
	}

	/**
	 * @see XMIHandler#generateDTD(MDSModel)
	 */
	public MDSFile mapMDS2DTD(MDSModel mdsModel) throws XMIHandlerException {
		MDSFile mdsFile = null;

		return mdsFile;
	}

	/**
	 * @see XMIHandler#mapMDS2Schema(MDSModel)
	 */
	public MDSFile mapMDS2Schema(MDSModel mdsModel)
		throws XMIHandlerException {
		MDSFile mdsFile = null;

		return mdsFile;
	}

	/**
	 * @see api.mds.xmi.XMIHandler#mapXMI2MDS(MDSModel)
	 */
	public MDSFile mapXMI2MDS(MDSModel mdsModel, MDSFile mdsFile)
		throws XMIHandlerException {

		try {
			DOMParser parser = new DOMParser();
			parser.parse(mdsFile.getContent());

			Document d = parser.getDocument();
			DocumentTraversal dt = (DocumentTraversal) d;

			NodeIterator it =
				dt.createNodeIterator(
					d.getDocumentElement(),
					NodeFilter.SHOW_ALL,
					new ObjectFilter(),
					true);

			Node n = it.nextNode();
			ArrayList names, vals;
			NamedNodeMap attribs;

			while (n != null) {

				System.out.println(n.getNodeName() + ":");
				System.out.println("  attributes:");

				attribs = n.getAttributes();
				names = new ArrayList();
				vals = new ArrayList();

				for (int j = 0; j < attribs.getLength(); ++j) {

					System.out.println(
						"    "
							+ attribs.item(j).getNodeName()
							+ ": '"
							+ attribs.item(j).getNodeValue()
							+ "'");

					names.add(attribs.item(j).getNodeName());
					vals.add(attribs.item(j).getNodeValue());
				}
				/*
				if (n.getNodeName().equals("Class") {
					xmiObject = new XMIObjectImpl(*/
				n = it.nextNode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class ObjectFilter implements NodeFilter {

		public short acceptNode(Node n) {
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				//if (e.getAttributeNode("xmi:id") != null)
				return NodeFilter.FILTER_ACCEPT;
			}
			return NodeFilter.FILTER_REJECT;
		}
	}
}