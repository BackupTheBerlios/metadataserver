package de.chille.mds.xmi;

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

import de.chille.mds.MDSGlobals;
import de.chille.mds.core.MDSAssociationEndImpl;
import de.chille.mds.core.MDSAssociationImpl;
import de.chille.mds.core.MDSClassImpl;
import de.chille.mds.core.MDSCoreException;
import de.chille.mds.core.MDSFileImpl;
import de.chille.mds.core.MDSGeneralizationImpl;
import de.chille.mds.core.MDSHrefFormatException;
import de.chille.mds.core.MDSHrefImpl;
import de.chille.mds.core.MDSModelImpl;
import de.chille.mds.persistence.PersistenceHandlerException;

import de.chille.api.mds.core.MDSAssociationEnd;
import de.chille.api.mds.core.MDSAssociation;
import de.chille.api.mds.core.MDSClass;
import de.chille.api.mds.core.MDSElement;
import de.chille.api.mds.core.MDSFile;
import de.chille.api.mds.core.MDSGeneralization;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mds.xmi.XMIHandler;

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
	 * @see de.chille.api.de.chille.de.chille.mds.xmi.XMIHandler#mapMDS2XMI(MDSModel)
	 */
	public MDSFile mapMDS2UML(MDSModel mdsModel) throws XMIHandlerException {

		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

		String xdoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xdoc += "<!DOCTYPE XMI SYSTEM \""
			+ MDSGlobals.RESOURCES_PATH
			+ "UMLX13-11.dtd\" [\n";
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

		String xclass =
			"\t\t<UML:Class xmi.id=\"mdsID_#id#\" name=\"#name#\"/>\n";

		String xgeneralization =
			"\t\t<UML:Class xmi.id=\"mdsID_#subId#\" name=\"#name#\" generalization=\"mdsID_#superId#\">\n";
		xgeneralization += "\t\t\t<UML:Namespace.ownedElement>\n";
		xgeneralization
			+= "\t\t\t\t<UML:Generalization xmi.id=\"mdsID_#id#\" name=\"#genName#\" child=\"mdsID_#subId#\" parent=\"mdsID_#superId#\"/>\n";
		xgeneralization += "\t\t\t</UML:Namespace.ownedElement>\n";
		xgeneralization += "\t\t</UML:Class>\n";

		String xassociation1 =
			"\t\t<UML:Association xmi.id=\"mdsID_#id#\" name=\"#name#\">\n";
		xassociation1 += "\t\t\t<UML:Association.connection>\n";
		xassociation1
			+= "\t\t\t\t<UML:AssociationEnd aggregation=\"#aggregation#\" type=\"mdsID_#endId#\"/>\n";

		String xassociation2 =
			"\t\t\t\t<UML:AssociationEnd aggregation=\"#aggregation#\" type=\"mdsID_#endId#\"/>\n";
		xassociation2 += "\t\t\t</UML:Association.connection>\n";
		xassociation2 += "\t\t</UML:Association>\n";

		String xextensions1 =
			"\t\t<XMI.extensions xmi.extender=\"metadata.server\">\n";

		String xcounter = "\t\t\t<counter value=\"#counter#\"/>\n";

		String xfile1 = "\t\t\t<additionalFiles>\n";
		String xfile2 =
			"\t\t\t\t<file name=\"#name#\" path=\"#path#\" type=\"#type#\"/>\n";
		String xfile3 = "\t\t\t</additionalFiles>\n";

		String xextensions2 = "\t\t</XMI.extensions>\n";

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
		MDSAssociationEnd end1, end2;
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
			end1 = (MDSAssociationEnd) ends.get(0);
			end2 = (MDSAssociationEnd) ends.get(1);
			String aggregation = null;
			switch (end1.getAggregation()) {
				case MDSAssociationEnd.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case MDSAssociationEnd.SHARED_AGGREGATION :
					aggregation = "aggregate";
					break;
				case MDSAssociationEnd.COMPOSITE_AGGREGATION :
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
				case MDSAssociationEnd.NONE_AGGREGATION :
					aggregation = "none";
					break;
				case MDSAssociationEnd.SHARED_AGGREGATION :
					aggregation = "aggregate";
					break;
				case MDSAssociationEnd.COMPOSITE_AGGREGATION :
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
		xdoc += xextensions1;
		xdoc += xcounter.replaceAll("#counter#", "" + mdsModel.getCounter());
		i = mdsModel.getAdditionalFiles().iterator();
		while (i.hasNext()) {
			MDSFile file = (MDSFileImpl) i.next();
			xdoc += xfile1;
			xdoc
				+= xfile2
					.replaceAll("#name#", file.getName())
					.replaceAll("#path#", file.getPath())
					.replaceAll("#type#", file.getType());
			xdoc += xfile3;
		}
		xdoc += xextensions2;
		xdoc += xfooter;
		MDSFile mdsFile = new MDSFileImpl();
		mdsFile.setContent(xdoc);

		return mdsFile;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.xmi.XMIHandler#mapMDS2XMI(MDSModel)
	 */
	public MDSFile mapMDS2XMI(MDSModel mdsModel, String dtdPath)
		throws XMIHandlerException {

		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

		String xdoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xdoc
			+= "\n<!-- ################################################### -->\n";
		xdoc
			+= "<!-- #          generated by metadata.server           # -->\n";
		xdoc
			+= "<!-- ################################################### -->\n\n";
		if (dtdPath != null) {
			xdoc += "<!DOCTYPE XMI SYSTEM \"" + dtdPath + "\">\n";
		}
		xdoc += "<XMI xmi.version=\"1.1\">\n";
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
				generalizations.add(element);
			} else {
				associations.add(element);
			}
		}

		i = classes.iterator();
		while (i.hasNext()) {
			elementList = new ArrayList();
			attributeList = new ArrayList();
			mdsClass = (MDSClassImpl) i.next();

			xdoc += "\t\t<"
				+ mdsClass.getLabel()
				+ " xmi.id=\"mdsID_"
				+ mdsClass.getId()
				+ "\"";

			j = getXMIClassAssociations(mdsClass, "all").iterator();
			while (j.hasNext()) {
				xdoc += (String) j.next();
			}
			xdoc += "/>\n";
		}
		xdoc += "\t</XMI.content>\n";
		xdoc += "</XMI>";

		MDSFile mdsFile = new MDSFileImpl();

		mdsFile.setContent(xdoc);
		return mdsFile;

	}

	private ArrayList elementList = null;

	/**
	 * @see XMIHandler#generateDTD(MDSModel)
	 */
	public MDSFile mapMDS2DTD(MDSModel mdsModel) throws XMIHandlerException {

		String dtd = "";

		BufferedReader f;
		String line = null;
		try {
			// fixed content laden
			f =
				new BufferedReader(
					new FileReader(
						MDSGlobals.RESOURCES_PATH + "fixedContent.dtd"));
			while ((line = f.readLine()) != null) {
				dtd += line + "\n";
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
				generalizations.add(element);
			} else {
				associations.add(element);
			}
		}
		String intro =
			"\n<!-- ################################################### -->\n";
		intro
			+= "<!-- # additional content generated by metadata.server # -->\n";
		intro
			+= "<!-- ################################################### -->\n\n";
		i = classes.iterator();
		while (i.hasNext()) {
			elementList = new ArrayList();
			attributeList = new ArrayList();
			mdsClass = (MDSClassImpl) i.next();
			// rules 6,6a,6b
			dtd += intro + "<!ELEMENT " + mdsClass.getLabel() + " (";
			intro = "\n";
			// rules 6e,6f
			j = getClassAssociations(mdsClass, "all").iterator();
			while (j.hasNext()) {
				dtd += (String) j.next() + " | ";
			}
			// rule 6c
			dtd += "XMI.extension)*>\n";
			dtd += "<!ATTLIST " + mdsClass.getLabel() + " ";
			if (attributeList.size() > 0) {
				j = attributeList.iterator();
				while (j.hasNext()) {
					dtd += "\n\t\t\t" + (String) j.next() + " IDREF #IMPLIED";
				}
			}
			dtd += "\n\t\t\t%XMI.element.att; %XMI.link.att; >\n";
			j = elementList.iterator();
			while (j.hasNext()) {
				dtd += (String) j.next();
			}
		}

		MDSFile mdsFile = new MDSFileImpl();

		mdsFile.setContent(dtd);
		return mdsFile;
	}

	private ArrayList attributeList = null;

	private ArrayList getXMIClassAssociations(MDSClass mdsClass, String mode) {

		MDSAssociation association = null;
		MDSAssociationEnd end1 = null, end2 = null;
		MDSClass end1Class = null, end2Class = null;
		ArrayList ends = null, classAssociations = new ArrayList();
		Iterator i = associations.iterator();
		String newElement = "";
		while (i.hasNext()) {
			association = (MDSAssociationImpl) i.next();
			ends = association.getAssociationEnds();
			for (int j = 0; j < 2; j++) {
				end1 = (MDSAssociationEndImpl) ends.get(j);
				end1Class = end1.getMdsClass();
				end2 = (MDSAssociationEndImpl) ends.get(j == 0 ? 1 : 0);
				end2Class = end2.getMdsClass();
				// wenn end==mdsClass
				if (end1Class.getId().equals(mdsClass.getId())) {
					// wenn anderes ende ==composite		
					if (end2.getAggregation()
						== MDSAssociationEnd.COMPOSITE_AGGREGATION) {
						// alles subclasses des anderen endes hinzufügen
						// bei rekursion nur non-composite-refs
						// in regel nicht klar beschrieben ???
						if (mode.equals("all")) {
							// anderes ende hinzufügen
							classAssociations.add(
								" "
									+ end2Class.getLabel().toLowerCase()
									+ "=\"mdsID_"
									+ end2Class.getId()
									+ "\"");
							classAssociations.addAll(
								getXMISubClasses(end2Class));
						}
					} else {
						// anderes ende hinzufügen mit mdsClass.-prefix
						newElement =
							" "
								+ end2Class.getLabel().toLowerCase()
								+ "=\""
								+ end2Class.getId()
								+ "\"";
						classAssociations.add(newElement);
						// wenn non-composite alle non-composite-refs der
						// superclasses hinzufügen
						classAssociations.addAll(
							getXMISuperClassAssociations(mdsClass));
					}
				}
			}
		}
		return classAssociations;
	}

	private ArrayList getClassAssociations(MDSClass mdsClass, String mode) {

		MDSAssociation association = null;
		MDSAssociationEnd end1 = null, end2 = null;
		MDSClass end1Class = null, end2Class = null;
		ArrayList ends = null, classAssociations = new ArrayList();
		Iterator i = associations.iterator();
		String newElement = "";
		while (i.hasNext()) {
			association = (MDSAssociationImpl) i.next();
			ends = association.getAssociationEnds();
			for (int j = 0; j < 2; j++) {
				end1 = (MDSAssociationEndImpl) ends.get(j);
				end1Class = end1.getMdsClass();
				end2 = (MDSAssociationEndImpl) ends.get(j == 0 ? 1 : 0);
				end2Class = end2.getMdsClass();
				// wenn end==mdsClass
				if (end1Class.getId().equals(mdsClass.getId())) {
					// wenn anderes ende ==composite		
					if (end2.getAggregation()
						== MDSAssociationEnd.COMPOSITE_AGGREGATION) {
						// alles subclasses des anderen endes hinzufügen
						// bei rekursion nur non-composite-refs
						// in regel nicht klar beschrieben ???
						if (mode.equals("all")) {
							// anderes ende hinzufügen
							classAssociations.add(end2Class.getLabel());
							attributeList.add(
								end2Class.getLabel().toLowerCase());
							// rule 6e
							classAssociations.addAll(getSubClasses(end2Class));
						}
					} else {
						// anderes ende hinzufügen mit mdsClass.-prefix
						newElement =
							mdsClass.getLabel()
								+ "."
								+ end2Class.getLabel().toLowerCase();
						classAssociations.add(newElement);
						attributeList.add(end2Class.getLabel().toLowerCase());
						if (mode.equals("all")) {
							elementList.add(
								"<!ELEMENT "
									+ newElement
									+ " ("
									+ end2Class.getLabel()
									+ " | XMI.extension)*>\n");
						}

						// rule 6f
						// wenn non-composite alle non-composite-refs der
						// superclasses hinzufügen
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
				subClasses.add(subClass.getLabel());
				attributeList.add(subClass.getLabel().toLowerCase());
				subClasses.addAll(getSubClasses(subClass));
			}
		}
		return subClasses;
	}

	private ArrayList getXMISubClasses(MDSClass superClass) {
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
				subClasses.add(
					" "
						+ subClass.getLabel().toLowerCase()
						+ "=\"mdsID_"
						+ subClass.getId()
						+ "\"");
				//attributeList.add(subClass.getLabel().toLowerCase());
				subClasses.addAll(getXMISubClasses(subClass));
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
					getClassAssociations(
						generalization.getSuperClass(),
						"refs only"));
			}
		}
		return superClassAssociations;
	}

	private ArrayList getXMISuperClassAssociations(MDSClass subClass) {
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
					getXMIClassAssociations(
						generalization.getSuperClass(),
						"refs only"));
			}
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
	 * @see de.chille.api.de.chille.de.chille.mds.xmi.XMIHandler#mapXMI2MDS(MDSFile)
	 */
	public MDSModel mapUML2MDS(MDSFile mdsFile) throws XMIHandlerException {
		
		classes = new ArrayList();
		associations = new ArrayList();
		generalizations = new ArrayList();

		MDSModel model = new MDSModelImpl();
		MDSElement element = null;
		
		try {
			DOMParser parser = new DOMParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(mdsFile.getContent().getBytes());
			InputSource is = new InputSource(bais);
			parser.parse(is);
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
						newClass.setId(
							((String) nodeAttribs.get("xmi.id")).substring(6));
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
							e.printStackTrace();
							throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#XMI.metamodel");
						} catch (PersistenceHandlerException e) {
							e.printStackTrace();
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
				} else if (nodeName.equals("counter")) {
					if (nodeAttribs.containsKey("value")) {
						model.setCounter(
							Integer.parseInt(
								(String) nodeAttribs.get("value")));
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#counter");
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
			MDSAssociationEnd newAssociationEnd = null;
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
							((String) nodeAttribs.get("xmi.id")).substring(6));
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
							((String) nodeAttribs.get("xmi.id")).substring(6));
						newGeneralization.setSuperClass(
							getClassById(
								((String) nodeAttribs.get("parent")).substring(
									6)));
						newGeneralization.setSubClass(
							getClassById(
								((String) nodeAttribs.get("child")).substring(
									6)));
						generalizations.add(newGeneralization);
					} else {
						throw new XMIHandlerException("Fehler: XMIHandler#mapXMI2MD#UML:Generalization");
					}
				} else if (nodeName.equals("UML:AssociationEnd")) {
					if (nodeAttribs.containsKey("aggregation")
						&& nodeAttribs.containsKey("type")) {
						newAssociationEnd = new MDSAssociationEndImpl();
						newAssociationEnd.setMdsClass(
							getClassById(
								((String) nodeAttribs.get("type")).substring(
									6)));
						aggregation = (String) nodeAttribs.get("aggregation");
						if (aggregation.equals("none")) {
							newAssociationEnd.setAggregation(
								MDSAssociationEnd.NONE_AGGREGATION);
						} else if (aggregation.equals("aggregate")) {
							newAssociationEnd.setAggregation(
								MDSAssociationEnd.SHARED_AGGREGATION);
						} else if (aggregation.equals("composite")) {
							newAssociationEnd.setAggregation(
								MDSAssociationEnd.COMPOSITE_AGGREGATION);
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