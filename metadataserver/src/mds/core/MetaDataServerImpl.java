package mds.core;

import java.util.ArrayList;

import mds.persistence.FilesystemHandlerImpl;
import mds.persistence.PersistenceHandlerException;
import mme.core.MetaMappingEngineException;
import mme.core.MetaMappingEngineImpl;

import api.mds.core.MDSElement;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.core.MetaDataServer;
import api.mds.persistence.PersistenceHandler;
import api.mme.core.Mapping;
import api.mme.core.MetaMappingEngine;
import api.mme.mapper.MDSMapper;

/**
 * @see MetaDataServer
 * 
 * @author Thomas Chille
 */
public class MetaDataServerImpl
	extends MDSObjectImpl
	implements MetaDataServer {

	/**
	 * der Server kennt seine MME
	 */
	private MetaMappingEngine metaMappingEngine = null;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList repositories = null;

	/**
	 * muﬂ beim deployen hier gesetzt werden
	 */
	private PersistenceHandler persistenceHandler = null;

	/**
	 * Constructor for MetaDataServerImpl.
	 */
	public MetaDataServerImpl() {
		PersistenceHandler persistenceHandler = new FilesystemHandlerImpl();
		MetaMappingEngine metaMappingEngine =
			new MetaMappingEngineImpl(persistenceHandler);
	}

	/**
	 * @see MetaDataServer#insertReposititory(MDSRepository)
	 */
	public String insertReposititory(MDSRepository mdsRepository) {
		try {
			mdsRepository.setPersistenceHandler(persistenceHandler);
			String href = mdsRepository.insert();
			if (repositories.add(mdsRepository)) {
				return this.getId() + "." + href;
			} else {
				return null;
			}
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#deleteRepository(String)
	 */
	public boolean deleteRepository(String href) {
		try {
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(href, null);
			mdsRepository.delete();
			if (repositories.remove(mdsRepository)) {
				return true;
			} else {
				return false;
			}
		} catch (MDSCoreException e) {
			return false;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#renameRepository(String, String)
	 */
	public boolean renameRepository(String href, String label) {
		try {
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(href, null);
			mdsRepository.setLabel(label);
			return true;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#queryRepository(String, String)
	 */
	public ArrayList queryRepository(String href, String query) {
		try {
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(href, null);
			ArrayList result = mdsRepository.query(query);
			return result;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel) {
		try {
			mdsModel.setPersistenceHandler(persistenceHandler);
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(href, null);
			String modelHref = mdsRepository.insertModel(mdsModel);
			return this.getId() + "." + modelHref;
		} catch (MDSCoreException e) {
			return null;
		}catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeModel(String)
	 */
	public boolean removeModel(String href) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1] ;
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(rHref, null);
			mdsRepository.removeModel(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#moveModel(String, String)
	 */
	public String moveModel(String from, String to) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1] ;
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(rHref, null);
			String newHref = mdsRepository.moveModel(from, to);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1] ;
			MDSRepository mdsRepository =
				(MDSRepository) persistenceHandler.load(rHref, null);
			String newHref = mdsRepository.copyModel(from, to, label);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#renameModel(String, String)
	 */
	public boolean renameModel(String href, String label) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			mdsModel.renameModel(label);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#getModelVersions(String)
	 */
	public String[] getModelVersions(String href) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			String[] versions = mdsModel.getModelVersions(href);
			return versions;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#restoreModel(String, String)
	 */
	public boolean restoreModel(String href, String version) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			mdsModel.restoreModel(version);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#insertElement(String, MDSElement)
	 */
	public String insertElement(String href, MDSElement mdsElement) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			String newHref = mdsModel.insertElement(href, mdsElement);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeElement(String)
	 */
	public boolean removeElement(String href) {
		try {
			String[] hrefParts = href.split(".");
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(mHref, null);
			mdsModel.removeElement(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (PersistenceHandlerException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#moveElement(String, String)
	 */
	public String moveElement(String from, String to) {
		try {
			String[] hrefParts = from.split(".");
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(mHref, null);
			String newHref = mdsModel.moveElement(from, to);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#copyElement(String, String, String)
	 */
	public String copyElement(String from, String to, String label) {
		try {
			String[] hrefParts = from.split(".");
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(mHref, null);
			String newHref = mdsModel.copyElement(from, to, label);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#validateModel(String, int)
	 */
	public String[] validateModel(String href, int validateType) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			String[] result = mdsModel.validateModel(validateType);
			return result;
		} catch (MDSCoreException e) {
			return null;
		} catch (PersistenceHandlerException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#importModel(String, MDSModel, Mapping)
	 */
	public String importModel(
		String href,
		MDSModel mdsModel,
		Mapping mapping) {

		try {
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			String newHref = this.insertModel(href, mdsModel);
			return newHref;
		} catch (MetaMappingEngineException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#exportModel(String, Mapping)
	 */
	public MDSModel exportModel(String href, Mapping mapping) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			return mdsModel;
		} catch (PersistenceHandlerException e) {
			return null;
		} catch (MetaMappingEngineException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#registerMapper(MDSMapper)
	 */
	public boolean registerMapper(MDSMapper mapper) {
		try {
			metaMappingEngine.registerMapper(mapper);
			return true;
		} catch (MetaMappingEngineException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#unregisterMapper(MDSMapper)
	 */
	public boolean unregisterMapper(MDSMapper mapper) {
		try {
			metaMappingEngine.unregisterMapper(mapper);
			return true;
		} catch (MetaMappingEngineException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to) {
		try {
			ArrayList mappings = metaMappingEngine.getMappings(from, to);
			return mappings;
		} catch (MetaMappingEngineException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#convertModel(String, Mapping, String)
	 */
	public String convertModel(String href, Mapping mapping, String label) {
		try {
			MDSModel mdsModel = (MDSModel) persistenceHandler.load(href, null);
			MDSModel newModel = metaMappingEngine.map(mdsModel, mapping);
			newModel.setLabel(label);
			String newHref = this.insertModel(href, mdsModel);
			return newHref;
		} catch (PersistenceHandlerException e) {
			return null;
		} catch (MetaMappingEngineException e) {
			return null;
		}
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

	/**
	 * Gets the metaMappingEngine
	 * @return Returns a MetaMappingEngine
	 */
	public MetaMappingEngine getMetaMappingEngine() {
		return metaMappingEngine;
	}

	/**
	 * Sets the metaMappingEngine
	 * @param metaMappingEngine The metaMappingEngine to set
	 */
	public void setMetaMappingEngine(MetaMappingEngine metaMappingEngine) {
		this.metaMappingEngine = metaMappingEngine;
	}
}
