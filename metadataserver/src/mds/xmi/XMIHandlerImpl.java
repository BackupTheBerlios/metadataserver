package mds.xmi;

import java.util.ArrayList;
import java.util.Iterator;

import mds.core.MDSAggregationImpl;
import mds.core.MDSAssociationImpl;
import mds.core.MDSClassImpl;
import mds.core.MDSGeneralizationImpl;

import api.mds.core.MDSClass;
import api.mds.core.MDSElement;
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
		XMIFile file = new XMIFile("test.xmi");
		try {
			file.write(map2XMIFramework(mdsModel).iterator(), XMIFile.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * @see XMIHandler#generateDTD(MDSModel)
	 */
	public void generateDTD(MDSModel mdsModel) throws XMIHandlerException {
	}

	/**
	 * @see XMIHandler#generateSchema(MDSModel)
	 */
	public void generateSchema(MDSModel mdsModel) throws XMIHandlerException {
	}

	private ArrayList map2XMIFramework(MDSModel mdsModel)
		throws XMIHandlerException {
			
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
			if (element instanceof MDSGeneralizationImpl) {
				parent =
					getClassById(
						((MDSGeneralizationImpl) element)
							.getSuperClass()
							.getId(), classes);
				child =
					getClassById(
						((MDSGeneralizationImpl) element)
							.getSubClass()
							.getId(), classes);
			} else if (element instanceof MDSAssociationImpl) {
				ends = ((MDSAssociationImpl) element).getAssociationEnds();
				end1 = (api.mds.core.AssociationEnd) ends.get(0);
				end2 = (api.mds.core.AssociationEnd) ends.get(1);
				getClassById(end1.getMdsClass().getId(), classes).addXMIValue(
					element.getLabel(),
					getClassById(end2.getMdsClass().getId(), classes),
					Value.REFERENCE);
			} else if (element instanceof MDSAggregationImpl) {
				end1 = ((MDSAggregationImpl) element).getContainerEnd();
				end2 = ((MDSAggregationImpl) element).getContainedEnd();
				container = getClassById(end1.getMdsClass().getId(), classes);
				part = getClassById(end2.getMdsClass().getId(), classes);
				container.addXMIValue(
					element.getLabel(),
					part,
					Value.CONTAINED);
				classes.remove(part);
			}
		}
		return classes;
	}

	private XMIObject getClassById(String id, ArrayList classes) throws XMIHandlerException {
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
}
