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

import mds.core.MDSAggregationImpl;
import mds.core.MDSAssociationImpl;
import mds.core.MDSClassImpl;
import mds.core.MDSFileImpl;
import mds.core.MDSGeneralizationImpl;

import api.mds.core.MDSClass;
import api.mds.core.MDSElement;
import api.mds.core.MDSFile;
import api.mds.core.MDSModel;
import api.mds.xmi.XMIHandler;

import com.ibm.xmi.framework.*;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMIHandlerImpl implements XMIHandler {

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

	Namespace namespace = new Namespace("UML", "org.omg/UML1.3");

	/**
	 * @see api.mds.xmi.XMIHandler#generateXMI(MDSModel)
	 */
	public void generateXMI(MDSModel mdsModel) throws XMIHandlerException {

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
		ArrayList relations = new ArrayList();
		MDSElement element = null;
		XMIObject xmiObject = null;
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
				while (i.hasNext()) {
					mdsClass = (MDSClassImpl) i.next();
					if (mdsClass.getId().equals(subClass.getId())) {
						classes.remove(subClass);
					}
					relations.add(element);
				}
			} else {
				relations.add(element);
			}
		}

		ArrayList ends;
		api.mds.core.AssociationEnd end1, end2;
		XMIObject container, part, parent, child, oend1, oend2;

		i = classes.iterator();
		while (i.hasNext()) {
			xdoc += xclass.replaceAll("#id#", ((MDSElement) i.next()).getId());
		}
		i = relations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSGeneralizationImpl) {
				xdoc
					+= xgeneralization.replaceAll(
						"#id#",
						element.getId()).replaceAll(
						"#superId#",
						((MDSGeneralizationImpl) element)
							.getSuperClass()
							.getId());
			} else if (element instanceof MDSAssociationImpl) {
				ends = ((MDSAssociationImpl) element).getAssociationEnds();
				end1 = (api.mds.core.AssociationEnd) ends.get(0);
				end2 = (api.mds.core.AssociationEnd) ends.get(1);
				xdoc
					+= xassociation1
						.replaceAll("#id#", element.getId())
						.replaceAll("#endId#", end1.getMdsClass().getId())
						.replaceAll("#aggregation#", "none");
				;
				xdoc
					+= xassociation2
						.replaceAll("#id#", element.getId())
						.replaceAll("#endId#", end2.getMdsClass().getId())
						.replaceAll("#aggregation#", "none");
				;
			} else if (element instanceof MDSAggregationImpl) {
				end1 = ((MDSAggregationImpl) element).getContainedEnd();
				end2 = ((MDSAggregationImpl) element).getContainerEnd();
				xdoc
					+= xassociation1
						.replaceAll("#id#", element.getId())
						.replaceAll("#endId#", end1.getMdsClass().getId())
						.replaceAll("#aggregation#", "shared");
				;
				xdoc
					+= xassociation2
						.replaceAll("#id#", element.getId())
						.replaceAll("#endId#", end2.getMdsClass().getId())
						.replaceAll("#aggregation#", "shared");
				;
			}
		}
		xdoc += xfooter;
		MDSFile mdsFile = new MDSFileImpl();
		mdsFile.setContent(xdoc);
		mdsModel.setXmiFile(mdsFile);

		FileWriter f1;
		try {
			f1 = new FileWriter("test.xmi");
			f1.write(xdoc);
			f1.close();

			DOMParser parser = new DOMParser();
			parser.parse("test.xmi");

			Document d = parser.getDocument();
			DocumentTraversal dt = (DocumentTraversal) d;

			NodeIterator it =
				dt.createNodeIterator(
					d.getDocumentElement(),
					NodeFilter.SHOW_ALL,
					new ObjectFilter(),
					true);

			Node n = it.nextNode();

			while (n != null) {
				writeObject(n);
				n = it.nextNode();
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Erstellen der Datei");
		}

	}

	public static void writeObject(Node object) {
		System.out.println(object.getNodeName() + ":");
		System.out.println("  attributes:");
		NamedNodeMap attribs = object.getAttributes();
		String[] names, vals;
		
		for (int j = 0; j < attribs.getLength(); ++j) {
			System.out.println(
				"    "
					+ attribs.item(j).getNodeName()
					+ ": '"
					+ attribs.item(j).getNodeValue()
					+ "'");
			names[j] = attribs.item(j).getNodeName();
			vals[j] = attribs.item(j).getNodeValue();
		}
		
		if (object.getNodeName().equals("Class") {
			xmiObject = new XMIObjectImpl(
		
		
	}

	/**
	 * @see XMIHandler#generateDTD(MDSModel)
	 */
	public void generateDTD(MDSModel mdsModel) throws XMIHandlerException {
		XMIDTD dtd = new XMIDTD("test.dtd");
		try {
			dtd.write(map2XMIClasses(mdsModel).iterator());
			String line, content = "";
			BufferedReader f = new BufferedReader(new FileReader("test.dtd"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			MDSFile mdsFile = new MDSFileImpl();
			mdsFile.setContent(content);
			mdsModel.setDtdFile(mdsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see XMIHandler#generateSchema(MDSModel)
	 */
	public void generateSchema(MDSModel mdsModel) throws XMIHandlerException {
		XMISchema schema = new XMISchema("test.xsd");
		//schema.setTargetNamespace(
		//	new Namespace("test", "http://test.com/test.xsd"));
		try {
			schema.write(map2XMIClasses(mdsModel).iterator());
			String line, content = "";
			BufferedReader f = new BufferedReader(new FileReader("test.xsd"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			MDSFile mdsFile = new MDSFileImpl();
			mdsFile.setContent(content);
			mdsModel.setSchemaFile(mdsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList map2XMIClasses(MDSModel mdsModel)
		throws XMIHandlerException {

		ArrayList classes = new ArrayList();
		ArrayList relations = new ArrayList();
		MDSElement element = null;
		XMIClass xmiClass = null;

		Iterator i = mdsModel.getElements().iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSClassImpl) {
				xmiClass = new XMIClassImpl(element.getLabel());
				xmiClass.setXMIUserData(element.getId());
				classes.add(xmiClass);
			} else {
				relations.add(element);
			}
		}

		ArrayList ends;
		api.mds.core.AssociationEnd end1, end2;
		XMIClass container, part, parent, child, xmiEnd1, xmiEnd2;
		Feature feature;

		i = relations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSGeneralizationImpl) {
				parent =
					getClassById(
						((MDSGeneralizationImpl) element)
							.getSuperClass()
							.getId(),
						classes);
				child =
					getClassById(
						((MDSGeneralizationImpl) element).getSubClass().getId(),
						classes);
				parent.addSubclass(child);
				child.addSuperclass(parent);
			} else if (element instanceof MDSAssociationImpl) {
				ends = ((MDSAssociationImpl) element).getAssociationEnds();
				end1 = (api.mds.core.AssociationEnd) ends.get(0);
				end2 = (api.mds.core.AssociationEnd) ends.get(1);
				xmiEnd1 = getClassById(end1.getMdsClass().getId(), classes);
				xmiEnd2 = getClassById(end2.getMdsClass().getId(), classes);
				feature = new FeatureImpl(element.getLabel());
				feature.setXMIValueType(Value.REFERENCE);
				feature.setXMIType(xmiEnd2);
				feature.setXMIMultiplicity(end1.getMultiplicity());
				xmiEnd1.add(feature);
			} else if (element instanceof MDSAggregationImpl) {
				end1 = ((MDSAggregationImpl) element).getContainerEnd();
				end2 = ((MDSAggregationImpl) element).getContainedEnd();
				container = getClassById(end1.getMdsClass().getId(), classes);
				part = getClassById(end2.getMdsClass().getId(), classes);
				feature = new FeatureImpl(element.getLabel());
				feature.setXMIValueType(Value.CONTAINED);
				feature.setXMIType(part);
				feature.setXMIMultiplicity(end1.getMultiplicity());
				container.add(feature);
			}
		}
		return classes;
	}

	private XMIObject getObjectById(String id, ArrayList classes)
		throws XMIHandlerException {
		XMIObject object = null;
		Iterator i = classes.iterator();
		while (i.hasNext()) {
			object = (XMIObjectImpl) i.next();
			if (object.getXMIId().equals(id)) {
				return object;
			}
		}
		throw new XMIHandlerException("Fehler: XMIHandler#getClassById()");
	}

	private XMIClass getClassById(String id, ArrayList classes)
		throws XMIHandlerException {
		XMIClass xmiClass = null;
		Iterator i = classes.iterator();
		while (i.hasNext()) {
			xmiClass = (XMIClassImpl) i.next();
			if (xmiClass.getXMIUserData().equals(id)) {
				return xmiClass;
			}
		}
		throw new XMIHandlerException("Fehler: XMIHandler#getClassById()");
	}
}
