package mds.persistence;

import java.util.*;
import mds.core.*;

/**
 * @see PersistenceHandler
 * 
 * @author Thomas Chille
 */
public class FilesystemHandlerImpl implements PersistenceHandler 
{
	/**
	 * @see PersistenceHandler#save(MDSModel)
	 */
	public void save(MDSModel mdsModel)
	throws PersistenceHandlerException 
	{
	}

	/**
	 * @see PersistenceHandler#load(String, String)
	 */
	public MDSModel load(String mdsPfad, String version) 
	throws PersistenceHandlerException 
	{
		return null;
	}

	/**
	 * @see PersistenceHandler#delete(MDSModel, String)
	 */
	public void delete(MDSModel mdsModel, String version) 
	throws PersistenceHandlerException 
	{
	}

	/**
	 * @see PersistenceHandler#getVersions(MDSModel)
	 */
	public ArrayList getVersions(MDSModel mdsModel) 
	throws PersistenceHandlerException 
	{
		return null;
	}
}

