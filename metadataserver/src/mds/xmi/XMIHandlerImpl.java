package mds.xmi;

import java.util.ArrayList;
import java.util.Iterator;

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
		MDSElement element = null;
		XMIObject newClass = null;
		ArrayList classes = new ArrayList();
		ArrayList relations = new ArrayList();
		Iterator i = mdsModel.getElements().iterator();
		while (i.hasNext()) {
			element = (MDSElement)i.next();
			if (element.getClass().getName().equals("mds.core.MDSClassImpl")) {
				newClass = new XMIObjectImpl(element.getId());
				//newClass.setXMIValue(arg0, arg1, arg2)
				classes.add(newClass);
			} else {
				relations.add(element);
			}			
		}
		XMIFile file = new XMIFile("test.xmi");
		try {
			file.write(classes.iterator(), XMIFile.DEFAULT);
		} catch (Exception e) {
		}
		/*
		i = relations.iterator();
		while (i.hasNext()) {
			element = (MDSElement)i.next();
			if (element.getClass().getName().equals("mds.core.MDSGeneralizationImpl")) {
				((XMIClassImpl)getClassById(element.getId())).add
			} else if (element.getClass().getName().equals("mds.core.MDSAssociationImpl")) {
				
			} else if (element.getClass().getName().equals("mds.core.MDSAggregationImpl")) {
				
			}
		}*/
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

	/**
	 * @see XMIHandler#query(MDSModel, String)
	 */
	public ArrayList query(MDSModel mdsModel, String query)
		throws XMIHandlerException {
		return null;
	}

}

