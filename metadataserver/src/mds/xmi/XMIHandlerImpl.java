package mds.xmi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

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

	/**
	 * @see XMIHandler#generateXMI(MDSModel)
	 */
	public void generateXMI(MDSModel mdsModel) throws XMIHandlerException {
		ArrayList classes = new ArrayList();
		ArrayList relations = new ArrayList();
		MDSElement element = null;
		XMIObject object = null;

		Iterator i = mdsModel.getElements().iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSClassImpl) {
				object = new XMIObjectImpl(element.getLabel());
				object.setXMIId(element.getId());
				classes.add(object);
			} else {
				relations.add(element);
			}
		}

		ArrayList ends;
		api.mds.core.AssociationEnd end1, end2;
		XMIObject container, part, parent, child;

		i = relations.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element instanceof MDSAssociationImpl) {
				ends = ((MDSAssociationImpl) element).getAssociationEnds();
				end1 = (api.mds.core.AssociationEnd) ends.get(0);
				end2 = (api.mds.core.AssociationEnd) ends.get(1);
				getObjectById(end1.getMdsClass().getId(), classes).addXMIValue(
					element.getLabel(),
					getObjectById(end2.getMdsClass().getId(), classes),
					Value.REFERENCE);
			} else if (element instanceof MDSAggregationImpl) {
				end1 = ((MDSAggregationImpl) element).getContainerEnd();
				end2 = ((MDSAggregationImpl) element).getContainedEnd();
				container = getObjectById(end1.getMdsClass().getId(), classes);
				part = getObjectById(end2.getMdsClass().getId(), classes);
				container.addXMIValue(
					element.getLabel(),
					part,
					Value.CONTAINED);
				classes.remove(part);
			}
		}
		XMIFile file = new XMIFile("test.xmi");
		try {
			//file.setOutputStream(System.out);
			file.write(classes.iterator(), XMIFile.DEFAULT);
			String line, content = null;
			BufferedReader f = new BufferedReader(new FileReader("test.xmi"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			MDSFile mdsFile = new MDSFileImpl();
			mdsFile.setContent(content);
			mdsModel.setXmiFile(mdsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see XMIHandler#generateDTD(MDSModel)
	 */
	public void generateDTD(MDSModel mdsModel) throws XMIHandlerException {
		XMIDTD dtd = new XMIDTD("test.dtd");
		try {
			dtd.write(map2XMIClasses(mdsModel).iterator());
			String line, content = null;
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
			String line, content = null;
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
