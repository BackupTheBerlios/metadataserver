package mds.core;

import java.util.ArrayList;

import api.mds.core.MDSElement;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.core.MetaDataServer;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;
import api.mme.mapper.MDSMapper;

/**
 * @see MetaDataServer
 * 
 * @author Thomas Chille
 */
public class MetaDataServerImpl implements MetaDataServer {

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private MDSRepository[] repositories = null;
	
	/**
	 * muﬂ beim deployen hier gesetzt werden
	 */
	private PersistenceHandler persistenceHandler = null;
	
	/**
	 * @see MetaDataServer#insertReposititory(MDSRepository)
	 */
	public String insertReposititory(MDSRepository mdsRepository) {
		return null;
	}

	/**
	 * @see MetaDataServer#deleteRepository(String)
	 */
	public boolean deleteRepository(String href) {
		return false;
	}

	/**
	 * @see MetaDataServer#renameRepository(String, String)
	 */
	public boolean renameRepository(String href, String label) {
		return false;
	}

	/**
	 * @see MetaDataServer#queryRepository(String, String)
	 */
	public String[] queryRepository(String href, String query) {
		return null;
	}

	/**
	 * @see MetaDataServer#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel) {
		return null;
	}

	/**
	 * @see MetaDataServer#removeModel(String)
	 */
	public boolean removeModel(String href) {
		return false;
	}

	/**
	 * @see MetaDataServer#moveModel(String, String)
	 */
	public String moveModel(String from, String to) {
		return null;
	}

	/**
	 * @see MetaDataServer#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label) {
		return null;
	}

	/**
	 * @see MetaDataServer#renameModel(String, String)
	 */
	public boolean renameModel(String href, String label) {
		return false;
	}

	/**
	 * @see MetaDataServer#getModelVersions(String)
	 */
	public String[] getModelVersions(String href) {
		return null;
	}

	/**
	 * @see MetaDataServer#restoreModel(String, String)
	 */
	public boolean restoreModel(String href, String version) {
		return false;
	}

	/**
	 * @see MetaDataServer#insertElement(String, MDSElement)
	 */
	public String insertElement(String href, MDSElement mdsElement) {
		return null;
	}

	/**
	 * @see MetaDataServer#removeElement(String)
	 */
	public boolean removeElement(String href) {
		return false;
	}

	/**
	 * @see MetaDataServer#moveElement(String, String)
	 */
	public String moveElement(String from, String to) {
		return null;
	}

	/**
	 * @see MetaDataServer#copyElement(String, String, String)
	 */
	public String copyElement(String from, String to, String label) {
		return null;
	}

	/**
	 * @see MetaDataServer#validateModel(String, String)
	 */
	public String[] validateModel(String href, int validateType) {
		return null;
	}

	/**
	 * @see MetaDataServer#importModel(String, MDSModel, Mapping)
	 */
	public String importModel(String label, MDSModel mdsModel, Mapping mapping) {
		return null;
	}

	/**
	 * @see MetaDataServer#exportModel(String, Mapping)
	 */
	public MDSModel exportModel(String href, Mapping mapping) {
		return null;
	}

	/**
	 * @see MetaDataServer#registerMapper(MDSMapper)
	 */
	public boolean registerMapper(MDSMapper mapper) {
		return false;
	}

	/**
	 * @see MetaDataServer#unregisterMapper(MDSMapper)
	 */
	public boolean unregisterMapper(MDSMapper mapper) {
		return false;
	}

	/**
	 * @see MetaDataServer#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to) {
		return null;
	}

	/**
	 * @see MetaDataServer#convertModel(String, Mapping, String)
	 */
	public String convertModel(String href, Mapping mapping, String label) {
		return null;
	}

	/**
	 * Gets the persistenceHandler
	 * @return Returns a PersistenceHandler
	 */
	public PersistenceHandler getPersistenceHandler() {
		return persistenceHandler;
	}
	
	/**
	 * Sets the persistenceHandler
	 * @param persistenceHandler The persistenceHandler to set
	 */
	public void setPersistenceHandler(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}
}

