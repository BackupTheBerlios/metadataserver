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
import org.xml.sax.InputSource;

import mds.core.MDSAssociationImpl;
import mds.core.MDSClassImpl;
import mds.core.MDSFileImpl;
import mds.core.MDSGeneralizationImpl;
import mds.core.MDSModelImpl;

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
		xdoc += "<!DOCTYPE XMI SYSTEM \"uml13_xmi11.dtd\" [\n";
		xdoc += "\t<!ELEMENT additionalFiles (file+)>\n";
		xdoc += "\t<!ELEMENT file EMPTY>\n";
		xdoc += "\t<!ATTLIST file\n";
		xdoc += "\t\tname CDATA #REQUIRED\n";
		xdoc += "\t\tpath CDATA #REQUIRED\n";
		xdoc += "\t\ttype CDATA #REQUIRED\n";
		xdoc += "\t>\n";
		xdoc += "]>\n";
		xdoc += "<XMI xmi.version=\"1.1\" xmlns:UML=\"org.omg/UML1.3\">\n";
		xdoc += "\t<XMI.header>\n";
		xdoc += "\t\t<XMI.documentation>\n";
		xdoc += "\t\t\t<XMI.exporter>metadata.server</XMI.exporter>\n";
		xdoc += "\t\t\t<XMI.exporterVersion>0.1</XMI.exporterVersion\n";
		xdoc += "\t\t</XMI.documentation>\n";
		xdoc += "\t\t<XMI.model xmi.name=\""
			+ mdsModel.getId()
			+ "\" href=\""
			+ mdsModel.getHref().getHrefString()
			+ "\"/>\n";
		xdoc
			+= "\t\t<XMI.metamodel xmi.name=\"UML1.3\" href=\"mds://server_0/repository_0/model_1\"/>\n";
		xdoc += "\t</XMI.header>\n";
		xdoc += "\t<XMI.content>\n";

		String xclass = "\t\t<UML:Class xmi.id=\"#id#\" name=\"#id#\"/>\n";

		String xgeneralization =
			"\t\t<UML:Class xmi.id=\"#id#\" name=\"#id#\" generalization=\"#superId#\"/>\n";

		String xassociation1 = "\t\t<UML:Association name=\"#id#\">\n";
		xassociation1 += "\t\t\t<UML:Association.connection>\n";
		xassociation1
			+= "\t\t\t\t<UML:AssociationEnd aggregation=\"#aggregation#\" type=\"#endId#\"/>\n";

		String xassociation2 =
			"\t\t\t\t<UML:AssociationEnd aggregation=\"#aggregation#\" type=\"#endId#\"/>\n";
		xassociation2 += "\t\t\t</UML:Association.connection>\n";
		xassociation2 += "\t\t</UML:Association>\n";

		String xextensions1 =
			"\t\t<XMI.extensions xmi.extender=\"metadata.server\">\n";
		xextensions1 += "\t\t\t<additionalFiles>\n";

		String xextensions2 =
			"\t\t\t\t<file name=\"#name#\" path=\"#path#\" type=\"#type#\"/>\n";

		String xextensions3 = "\t\t\t</additionalFiles>\n";
		xextensions3 += "\t\t</XMI.extensions>\n";

		String xfooter = "\t</XMI.content>\n";
		xfooter += "</XMI>";

		ArrayList classes = new ArrayList();
		ArrayList associations = new ArrayList();
		ArrayList generalizations = new ArrayList();
		MDSElement element = null;
		Iterator i, j;
		MDSClass mdsClass = null, subClass = null;

		i = mdsModel.getElements().iterator();
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
						break;
					}

				}
				classes.remove(mdsClass);
				generalizations.add(element);
			} else {
				associations.add(element);
			}
		}

		ArrayList ends;
		AssociationEnd end1, end2;
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
			switch (end1.getAggregation()) {
				case AssociationEnd.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case AssociationEnd.SHARED_AGGREGATION :
					aggregation = "shared";
					break;
				case AssociationEnd.COMPOSITE_AGGREGATION :
					aggregation = "composite";
					break;
			}
			xdoc
				+= xassociation1
					.replaceAll("#id#", element.getId())
					.replaceAll("#endId#", end1.getMdsClass().getId())
					.replaceAll("#aggregation#", aggregation);
			switch (end2.getAggregation()) {
				case AssociationEnd.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case AssociationEnd.SHARED_AGGREGATION :
					aggregation = "shared";
					break;
				case AssociationEnd.COMPOSITE_AGGREGATION :
					aggregation = "composite";
					break;
			}
			xdoc
				+= xassociation2
					.replaceAll("#id#", element.getId())
					.replaceAll("#endId#", end2.getMdsClass().getId())
					.replaceAll("#aggregation#", aggregation);
		}
		i = mdsModel.getAdditionalFiles().iterator();
		while (i.hasNext()) {
			MDSFile file = (MDSFileImpl) i.next();
			xdoc += xextensions1;
			xdoc
				+= xextensions2
					.replaceAll("#name#", file.getName())
					.replaceAll("#path#", file.getPath())
					.replaceAll("#type#", file.getType());
			xdoc += xextensions3;
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
	 * @see api.mds.xmi.XMIHandler#mapXMI2MDS(MDSFile)
	 */
	public MDSModel mapXMI2MDS(MDSFile mdsFile) throws XMIHandlerException {

		MDSModel model = new MDSModelImpl();
		MDSElement element = null;
		
		try {
			DOMParser parser = new DOMParser();
			parser.parse(
				new InputSource(
					new ByteArrayInputStream(mdsFile.getContent().getBytes())));

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
					element = new MDSClassImpl();
				
				element.setId(id)*/
				n = it.nextNode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
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