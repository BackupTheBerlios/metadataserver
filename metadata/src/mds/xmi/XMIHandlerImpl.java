package mds.xmi;

import java.util.*;
import mds.core.*;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMIHandlerImpl implements XMIHandler 
{
	/**
	 * @see XMIHandler#createModel(String, MDSModel, String)
	 */
	public MDSModel createModel(String label, MDSModel metamodel, String type)
	throws XMIHandlerException 
	{
		return null;
	}

	/**
	 * @see XMIHandler#insert(String, MDSElement)
	 */
	public void insert(String id, MDSElement mdsElement) 
	throws XMIHandlerException 
	{
	}

	/**
	 * @see XMIHandler#remove(MDSModel, String)
	 */
	public void remove(MDSModel mdsModel, String id) 
	throws XMIHandlerException 
	{
	}

	/**
	 * @see XMIHandler#generateDTD()
	 */
	public void generateDTD() 
	throws XMIHandlerException 
	{
	}

	/**
	 * @see XMIHandler#generateSchema()
	 */
	public void generateSchema() 
	throws XMIHandlerException 
	{
	}

	/**
	 * @see XMIHandler#query(String)
	 */
	public ArrayList query(String query) 
	throws XMIHandlerException 
	{
		return null;
	}
}

