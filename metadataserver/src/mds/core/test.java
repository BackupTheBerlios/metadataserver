/**
 * Created on 06.10.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package mds.core;

import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;

/**
 * @author Thomas Chille
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class test implements MDSRepository {

	/**
	 * @see api.mds.core.MDSRepository#insert()
	 */
	public String insert() throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#delete()
	 */
	public void delete() throws MDSCoreException {
	}

	/**
	 * @see api.mds.core.MDSRepository#query(String)
	 */
	public String[] query(String query) throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#getPersistenceHandler()
	 */
	public PersistenceHandler getPersistenceHandler() {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#setPersistenceHandler(PersistenceHandler)
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
	}

	/**
	 * @see api.mds.core.MDSRepository#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel)
		throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#removeModel(String)
	 */
	public void removeModel(String href) throws MDSCoreException {
	}

	/**
	 * @see api.mds.core.MDSRepository#moveModel(String, String)
	 */
	public String moveModel(String from, String to) throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSRepository#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label)
		throws MDSCoreException {
		return null;
	}

	/**
	 * @see api.mds.core.MDSObject#getId()
	 */
	public String getId() {
		return null;
	}

	/**
	 * @see api.mds.core.MDSObject#setId(String)
	 */
	public void setId(String id) {
	}

	/**
	 * @see api.mds.core.MDSObject#getLabel()
	 */
	public String getLabel() {
		return null;
	}

	/**
	 * @see api.mds.core.MDSObject#setLabel(String)
	 */
	public void setLabel(String label) {
	}

}
