package mds.xmi;

import api.mds.core.MDSElement;
import api.mds.core.MDSModel;
import api.mds.xmi.XMIHandler;

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
	}

	/**
	 * @see XMIHandler#insertElement(MDSModel, String, MDSElement)
	 */
	public void insertElement(MDSModel mdsModel, String id, MDSElement mdsElement)
		throws XMIHandlerException {
	}

	/**
	 * @see XMIHandler#removeElement(MDSModel, String)
	 */
	public void removeElement(MDSModel mdsModel, String id)
		throws XMIHandlerException {
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
	public String[] query(MDSModel mdsModel, String query)
		throws XMIHandlerException {
		return null;
	}

}

