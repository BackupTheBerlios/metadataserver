package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.FilesystemHandlerImpl;
import mds.persistence.PersistenceHandlerException;
import mme.core.MetaMappingEngineException;
import mme.core.MetaMappingEngineImpl;

import api.mds.core.MDSElement;
import api.mds.core.MDSHref;
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
	private ArrayList repositories = new ArrayList();

	/**
	 * Constructor for MetaDataServerImpl.
	 */
	public MetaDataServerImpl() {
		this.setId("server_0");
		MetaMappingEngine metaMappingEngine = new MetaMappingEngineImpl();
	}

	/**
	 * @see MetaDataServer#insertReposititory(MDSRepository)
	 */
	public MDSHref insertReposititory(MDSRepository mdsRepository) {
		try {
			String id = mdsRepository.insert();
			if (repositories.add(mdsRepository)) {
				return new MDSHrefImpl(this.getId() + "." + id);
			} else {
				return null;
			}
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#deleteRepository(String)
	 */
	public boolean deleteRepository(MDSHref href) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.delete();
			if (repositories.remove(mdsRepository)) {
				return true;
			} else {
				return false;
			}
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#renameRepository(MDSHref, String)
	 */
	public boolean renameRepository(MDSHref href, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.setLabel(label);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#queryRepository(MDSHref, String)
	 */
	public ArrayList queryRepository(MDSHref href, String query) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			ArrayList result = mdsRepository.query(query);
			return result;
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#insertModel(MDSHref, MDSModel)
	 */
	public MDSHref insertModel(MDSHref href, MDSModel mdsModel) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			String id = mdsRepository.insertModel(mdsModel);
			return new MDSHrefImpl(href.getHref() + "." + id);
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeModel(MDSHref)
	 */
	public boolean removeModel(MDSHref href) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.removeModel(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#moveModel(MDSHref, MDSHref)
	 */
	public MDSHref moveModel(MDSHref from, MDSHref to) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(from);
			String id = mdsRepository.moveModel(from, to);
			return new MDSHrefImpl(to.getHref() + "." + id);
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#copyModel(MDSHref, MDSHref, String)
	 */
	public MDSHref copyModel(MDSHref from, MDSHref to, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(from);
			String id = mdsRepository.copyModel(from, to, label);
			return new MDSHrefImpl(to.getHref() + "." + id);
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#renameModel(MDSHref, String)
	 */
	public boolean renameModel(MDSHref href, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			mdsModel.renameModel(label);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#getModelVersions(MDSHref)
	 */
	public ArrayList getModelVersions(MDSHref href) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			ArrayList versions = mdsModel.getModelVersions();
			return versions;
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#restoreModel(MDSHref, String)
	 */
	public boolean restoreModel(MDSHref href, String version) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			mdsModel.restoreModel(version);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#insertElement(MDSHref, MDSElement)
	 */
	public MDSHref insertElement(MDSHref href, MDSElement mdsElement) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			String id = mdsModel.insertElement(mdsElement);
			return new MDSHrefImpl(href.getHref() + "." + id);
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeElement(MDSHref)
	 */
	public boolean removeElement(MDSHref href) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			mdsModel.removeElement(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		} catch (MDSHrefFormatException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#validateModel(MDSHref, int)
	 */
	public ArrayList validateModel(MDSHref href, int validateType) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			ArrayList result = mdsModel.validateModel(validateType);
			return result;
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#importModel(MDSHref, MDSModel, Mapping)
	 */
	public MDSHref importModel(
		MDSHref href,
		MDSModel mdsModel,
		Mapping mapping) {

		try {
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			MDSHref newHref = this.insertModel(href, mdsModel);
			return newHref;
		} catch (MetaMappingEngineException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#exportModel(MDSHref, Mapping)
	 */
	public MDSModel exportModel(MDSHref href, Mapping mapping) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			return mdsModel;
		} catch (MDSCoreException e) {
			return null;
		} catch (MetaMappingEngineException e) {
			return null;
		} catch (MDSHrefFormatException e) {
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
	 * @see MetaDataServer#convertModel(MDSHref, Mapping, String)
	 */
	public MDSHref convertModel(MDSHref href, Mapping mapping, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			MDSModel newModel = metaMappingEngine.map(mdsModel, mapping);
			newModel.setLabel(label);
			MDSHref newHref = this.insertModel(href, mdsModel);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (MetaMappingEngineException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#getMetaMappingEngine()
	 */
	public MetaMappingEngine getMetaMappingEngine() {
		return metaMappingEngine;
	}

	/**
	 * @see MetaDataServer#setMetaMappingEngine(MetaMappingEngine)
	 */
	public void setMetaMappingEngine(MetaMappingEngine metaMappingEngine) {
		this.metaMappingEngine = metaMappingEngine;
	}

	private MDSRepository getRepositoryByHref(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		String id = href.getRepositoryId();
		MDSRepository mdsRepository;
		Iterator i = repositories.iterator();
		while (i.hasNext()) {
			mdsRepository = (MDSRepository) i.next();
			if (mdsRepository.getId().equals(id)) {
				return mdsRepository;
			}
		}
		throw new MDSCoreException("Fehler: MetaDataServer#getRepositoryByHref()");
	}
		
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retString = "server:" + this.getId() + "\n";
		Iterator i = repositories.iterator();
		while (i.hasNext()) {
			retString += ((MDSRepository)i.next()).toString();
		}
		return retString;
	}

}
