package mds.xmi;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.xml.sax.SAXException;

import mds.core.AssociationEndImpl;
import mds.core.MDSAssociationImpl;
import mds.core.MDSClassImpl;
import mds.core.MDSCoreException;
import mds.core.MDSFileImpl;
import mds.core.MDSGeneralizationImpl;
import mds.core.MDSHrefFormatException;
import mds.core.MDSHrefImpl;
import mds.core.MDSModelImpl;
import mds.persistence.PersistenceHandlerException;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSAssociation;
import api.mds.core.MDSClass;
import api.mds.core.MDSElement;
import api.mds.core.MDSFile;
import api.mds.core.MDSGeneralization;
import api.mds.core.MDSModel;
import api.mds.xmi.XMIHandler;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMIHandlerImpl implements XMIHandler {

	private ArrayList classes;
	private ArrayList associations;
	private ArrayList generalizations;
	/**
	 * @see api.mds.xmi.XMIHandler#mapMDS2XMI(MDSModel)
	 */
	public MDSFile mapMDS2XMI(MDSModel mdsModel) throws XMIHandlerException {

		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

		String xdoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xdoc += "<!DOCTYPE XMI SYSTEM \"resources/UMLX13-11.dtd\" [\n";
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
		xdoc += "\t\t\t<XMI.exporterVersion>0.1</XMI.exporterVersion>\n";
		xdoc += "\t\t</XMI.documentation>\n";

		xdoc += "\t\t<XMI.model xmi.name=\""
			+ mdsModel.getLabel()
			+ "\" href=\""
			+ mdsModel.getHref().getHrefString()
			+ "\"/>\n";

		if (mdsModel.getMetamodel() != null) {
			xdoc += "\t\t<XMI.metamodel xmi.name=\""
				+ mdsModel.getMetamodel().getLabel()
				+ "\" href=\""
				+ mdsModel.getMetamodel().getHref().getHrefString()
				+ "\"/>\n";
		}

		xdoc += "\t</XMI.header>\n";
		xdoc += "\t<XMI.content>\n";

		String xclass = "\t\t<UML:Class xmi.id=\"#id#\" name=\"#name#\"/>\n";

		String xgeneralization =
			"\t\t<UML:Class xmi.id=\"#subId#\" name=\"#name#\" generalization=\"#superId#\">\n";
		xgeneralization += "\t\t\t<UML:Namespace.ownedElement>\n";
		xgeneralization
			+= "\t\t\t\t<UML:Generalization xmi.id=\"#id#\" name=\"#genName#\" child=\"#subId#\" parent=\"#superId#\"/>\n";
		xgeneralization += "\t\t\t</UML:Namespace.ownedElement>\n";
		xgeneralization += "\t\t</UML:Class>\n";

		String xassociation1 =
			"\t\t<UML:Association xmi.id=\"#id#\" name=\"#name#\">\n";
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
			element = (MDSElement) i.next();
			xdoc
				+= (xclass.replaceAll("#id#", element.getId())).replaceAll(
					"#name#",
					element.getLabel());
		}
		i = generalizations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			xdoc
				+= xgeneralization
					.replaceAll(
						"#subId#",
						((MDSGeneralizationImpl) element)
							.getSubClass()
							.getId())
					.replaceAll(
						"#superId#",
						((MDSGeneralizationImpl) element)
							.getSuperClass()
							.getId())
					.replaceAll("#id#", element.getId())
					.replaceAll(
						"#name#",
						((MDSGeneralizationImpl) element)
							.getSubClass()
							.getLabel())
					.replaceAll("#genName#", element.getLabel());
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
					aggregation = "aggregate";
					break;
				case AssociationEnd.COMPOSITE_AGGREGATION :
					aggregation = "composite";
					break;
			}
			xdoc
				+= xassociation1
					.replaceAll("#id#", element.getId())
					.replaceAll("#endId#", end1.getMdsClass().getId())
					.replaceAll("#aggregation#", aggregation)
					.replaceAll("#name#", element.getLabel());
			switch (end2.getAggregation()) {
				case AssociationEnd.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case AssociationEnd.SHARED_AGGREGATION :
					aggregation = "aggregate";
					break;
				case AssociationEnd.COMPOSITE_AGGREGATION :
					aggregation = "composite";
					break;
			}
			xdoc
				+= xassociation2.replaceAll(
					"#endId#",
					end2.getMdsClass().getId()).replaceAll(
					"#aggregation#",
					aggregation);
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

		String dtd = "";

		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

		ArrayList classAssociations = new ArrayList();
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
				//classes.remove(mdsClass);
				generalizations.add(element);
			} else {
				associations.add(element);
			}
		}
		i = classes.iterator();
		while (i.hasNext()) {
			mdsClass = (MDSClassImpl) i.next();
			// rules 6,6a,6b
			dtd += "<!ELEMENT " + mdsClass.getLabel() + " (";
			// rules 6e,6f
			j = getClassAssociations(mdsClass).iterator();
			while (j.hasNext()) {
				dtd += ((MDSClassImpl) j.next()).getLabel() + " | ";
			}
			// rule 6c
			dtd += "XMI.extension)*>\n";
		}

		MDSFile mdsFile = new MDSFileImpl();

		mdsFile.setContent(dtd);
		return mdsFile;
	}

	private ArrayList getClassAssociations(MDSClass mdsClass) {

		MDSAssociation association = null;
		AssociationEnd end = null;
		MDSClass endClass = null;
		ArrayList ends = null, classAssociations = new ArrayList();
		Iterator i = associations.iterator();
		while (i.hasNext()) {
			association = (MDSAssociationImpl) i.next();
			ends = association.getAssociationEnds();
			for (int j = 0; j < 2; j++) {
				end = (AssociationEndImpl) ends.get(j);
				endClass = end.getMdsClass();
				if (endClass.getId().equals(mdsClass.getId())) {
					classAssociations.add(
						((AssociationEndImpl) ends.get(j == 0 ? 1 : 0))
							.getMdsClass());
					if (end.getAggregation()
						== AssociationEnd.COMPOSITE_AGGREGATION) {
						// rule 6e
						classAssociations.addAll(getSubClasses(mdsClass));
					} else {
						// rule 6f
						classAssociations.addAll(
							getSuperClassAssociations(mdsClass));
					}
				}
			}
		}
		return classAssociations;
	}

	/**
	 * gibt rekursiv alle subclasses für eine übergebene superclass zurück
	 * um die xmi-dtd-production-rule 6f zu imlementieren
	 * 
	 * @param superClass
	 * @return ArrayList
	 */
	private ArrayList getSubClasses(MDSClass superClass) {
		ArrayList subClasses = new ArrayList();
		MDSClass subClass = null;
		MDSGeneralization generalization = null;
		Iterator i = generalizations.iterator();
		while (i.hasNext()) {
			generalization = (MDSGeneralizationImpl) i.next();
			if (generalization
				.getSuperClass()
				.getId()
				.equals(superClass.getId())) {
				subClass = generalization.getSubClass();
				subClasses.add(subClass);
				subClasses.addAll(getSubClasses(subClass));
			}
		}
		return subClasses;
	}

	/**
	 * gibt rekursiv alle Associationen der superlasses für eine übergebene 
	 * subclass zurück um die xmi-dtd-production-rule 6e zu imlementieren
	 * 
	 * @param subClass
	 * @return ArrayList
	 */
	private ArrayList getSuperClassAssociations(MDSClass subClass) {
		ArrayList superClassAssociations = new ArrayList();
		MDSGeneralization generalization = null;
		Iterator i = generalizations.iterator();
		while (i.hasNext()) {
			generalization = (MDSGeneralizationImpl) i.next();
			if (generalization
				.getSubClass()
				.getId()
				.equals(subClass.getId())) {
				superClassAssociations.addAll(
					getSuperClassAssociations(generalization.getSuperClass()));
			}//getClassAssociations(generalization.getSuperClass()));
		}
		return superClassAssociations;
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
		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

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
			NamedNodeMap attribs;
			HashMap nodeAttribs = null;
			String nodeName = "", attName = "", attValue = "";
			MDSClass newClass = null;
			ArrayList files = new ArrayList();
			MDSFile file = null;
			while (n != null) {
				nodeName = n.getNodeName();
				attribs = n.getAttributes();
				nodeAttribs = new HashMap();
				for (int j = 0; j < attribs.getLength(); ++j) {
					attName = attribs.item(j).getNodeName();
					attValue = attribs.item(j).getNodeValue();
					nodeAttribs.put(attName, attValue);
				}
				if (nodeName.equals("UML:Class")) {
					if (nodeAttribs.containsKey("xmi.id")
						&& nodeAttribs.containsKey("name")) {
						newClass = new MDSClassImpl();
						newClass.setLabel((String) nodeAttribs.get("name"));
						newClass.setId((String) nodeAttribs.get("xmi.id"));
						classes.add(newClass);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:Class");
					}
				} else if (nodeName.equals("XMI.model")) {
					if (nodeAttribs.containsKey("href")
						&& nodeAttribs.containsKey("xmi.name")) {
						model.setLabel((String) nodeAttribs.get("xmi.name"));
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#XMI.model");
					}
				} else if (nodeName.equals("XMI.metamodel")) {
					if (nodeAttribs.containsKey("href")
						&& nodeAttribs.containsKey("xmi.name")) {
						MDSModel metamodel = null;
						try {
							metamodel =
								(MDSModelImpl) model
									.getPersistenceHandler()
									.load(
									new MDSHrefImpl(
										(String) nodeAttribs.get("href")),
									null);
						} catch (MDSHrefFormatException e) {
							throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#XMI.metamodel");
						} catch (PersistenceHandlerException e) {
							throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#XMI.metamodel");
						}
						model.setMetamodel(metamodel);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#XMI.metamodel");
					}
				} else if (nodeName.equals("file")) {
					if (nodeAttribs.containsKey("name")
						&& nodeAttribs.containsKey("path")
						&& nodeAttribs.containsKey("type")) {
						file = new MDSFileImpl();
						file.setName((String) nodeAttribs.get("name"));
						file.setPath((String) nodeAttribs.get("path"));
						file.setType((String) nodeAttribs.get("type"));
						files.add(file);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#file");
					}
				}
				n = it.nextNode();
			}
			model.setAdditionalFiles(files);
			it =
				dt.createNodeIterator(
					d.getDocumentElement(),
					NodeFilter.SHOW_ALL,
					new ObjectFilter(),
					true);
			n = it.nextNode();
			MDSAssociation newAssociation = null;
			MDSGeneralization newGeneralization = null;
			AssociationEnd newAssociationEnd = null;
			String aggregation = "";
			while (n != null) {
				nodeName = n.getNodeName();
				attribs = n.getAttributes();
				nodeAttribs = new HashMap();
				for (int j = 0; j < attribs.getLength(); ++j) {
					attName = attribs.item(j).getNodeName();
					attValue = attribs.item(j).getNodeValue();
					nodeAttribs.put(attName, attValue);
				}
				if (nodeName.equals("UML:Association")) {
					if (nodeAttribs.containsKey("xmi.id")
						&& nodeAttribs.containsKey("name")) {
						newAssociation = new MDSAssociationImpl();
						newAssociation.setLabel(
							(String) nodeAttribs.get("name"));
						newAssociation.setId(
							(String) nodeAttribs.get("xmi.id"));
						associations.add(newAssociation);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:Association");
					}
				} else if (nodeName.equals("UML:Generalization")) {
					if (nodeAttribs.containsKey("xmi.id")
						&& nodeAttribs.containsKey("name")
						&& nodeAttribs.containsKey("child")
						&& nodeAttribs.containsKey("parent")) {
						newGeneralization = new MDSGeneralizationImpl();
						newGeneralization.setLabel(
							(String) nodeAttribs.get("name"));
						newGeneralization.setId(
							(String) nodeAttribs.get("xmi.id"));
						newGeneralization.setSuperClass(
							getClassById((String) nodeAttribs.get("parent")));
						newGeneralization.setSubClass(
							getClassById((String) nodeAttribs.get("child")));
						generalizations.add(newGeneralization);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:Generalization");
					}
				} else if (nodeName.equals("UML:AssociationEnd")) {
					if (nodeAttribs.containsKey("aggregation")
						&& nodeAttribs.containsKey("type")) {
						newAssociationEnd = new AssociationEndImpl();
						newAssociationEnd.setMdsClass(
							getClassById((String) nodeAttribs.get("type")));
						aggregation = (String) nodeAttribs.get("aggregation");
						if (aggregation.equals("none")) {
							newAssociationEnd.setAggregation(
								AssociationEnd.NONE_AGGREGATION);
						} else if (aggregation.equals("aggregate")) {
							newAssociationEnd.setAggregation(
								AssociationEnd.SHARED_AGGREGATION);
						} else if (aggregation.equals("composite")) {
							newAssociationEnd.setAggregation(
								AssociationEnd.COMPOSITE_AGGREGATION);
						} else {
							throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:AssociationEnd");
						}
						newAssociation.addAssociationEnd(newAssociationEnd);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:AssociationEnd");
					}
				}
				n = it.nextNode();
			}
			classes.addAll(generalizations);
			classes.addAll(associations);
			model.setElements(classes);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MDSCoreException e) {
			throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:AssociationEnd");
		}
		return model;
	}

	private MDSClass getClassById(String id) throws XMIHandlerException {

		MDSClass mdsClass;
		Iterator i = classes.iterator();
		while (i.hasNext()) {
			mdsClass = (MDSClass) i.next();
			if (mdsClass.getId().equals(id)) {
				return mdsClass;
			}
		}
		throw new XMIHandlerException("Fehler: XMIHandler#getClassById()");
	}

	private static class ObjectFilter implements NodeFilter {

		public short acceptNode(Node n) {
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				return NodeFilter.FILTER_ACCEPT;
			}
			return NodeFilter.FILTER_REJECT;
		}
	}
}