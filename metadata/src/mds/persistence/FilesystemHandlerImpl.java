package mds.persistence;

import api.mds.core.MDSModel;
import api.mds.core.MDSObject;
import api.mds.persistence.PersistenceHandler;
import api.mme.mapper.MDSMapper;

/**
 * @see PersistenceHandler
 * 
 * @author Thomas Chille
 */
public class FilesystemHandlerImpl implements PersistenceHandler {

	/**
	 * @see PersistenceHandler#save(MDSObject)
	 */
	public void save(MDSObject mdsObject) throws PersistenceHandlerException {
	}

	/**
	 * @see PersistenceHandler#load(String, String)
	 */
	public MDSObject load(String href, String version)
		throws PersistenceHandlerException {
		return null;
	}

	/**
	 * @see PersistenceHandler#delete(MDSObject, String)
	 */
	public void delete(MDSObject mdsObject, String version)
		throws PersistenceHandlerException {
	}

	/**
	 * @see PersistenceHandler#getModelVersions(MDSModel)
	 */
	public String[] getModelVersions(MDSModel mdsModel)
		throws PersistenceHandlerException {
		return null;
	}

	/**
	 * @see PersistenceHandler#loadMapper()
	 */
	public MDSMapper[] loadMapper() throws PersistenceHandlerException {
		return null;
	}

}

