package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

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
	private ArrayList repositories = new ArrayList();

	/**
	 * Constructor for MetaDataServerImpl.
	 */
	public MetaDataServerImpl() {
		this.setId("server");
		MetaMappingEngine metaMappingEngine = new MetaMappingEngineImpl();
	}

	/**
	 * @see MetaDataServer#insertReposititory(MDSRepository)
	 */
	public String insertReposititory(MDSRepository mdsRepository) {
		try {
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
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.delete();
			if (repositories.remove(mdsRepository)) {
				return true;
			} else {
				return false;
			}
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#renameRepository(String, String)
	 */
	public boolean renameRepository(String href, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.setLabel(label);
			return true;
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#queryRepository(String, String)
	 */
	public ArrayList queryRepository(String href, String query) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			ArrayList result = mdsRepository.query(query);
			return result;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			String modelHref = mdsRepository.insertModel(mdsModel);
			return this.getId() + "." + modelHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeModel(String)
	 */
	public boolean removeModel(String href) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			mdsRepository.removeModel(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#moveModel(String, String)
	 */
	public String moveModel(String from, String to) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			String newHref = mdsRepository.moveModel(from, to);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			String newHref = mdsRepository.copyModel(from, to, label);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#renameModel(String, String)
	 */
	public boolean renameModel(String href, String label) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			mdsModel.renameModel(label);
			return true;
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#getModelVersions(String)
	 */
	public ArrayList getModelVersions(String href) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			ArrayList versions = mdsModel.getModelVersions();
			return versions;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#restoreModel(String, String)
	 */
	public boolean restoreModel(String href, String version) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			mdsModel.restoreModel(version);
			return true;
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#insertElement(String, MDSElement)
	 */
	public String insertElement(String href, MDSElement mdsElement) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			String newHref = mdsModel.insertElement(mdsElement);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeElement(String)
	 */
	public boolean removeElement(String href) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(mHref);
			mdsModel.removeElement(href);
			return true;
		} catch (MDSCoreException e) {
			return false;
		}
	}

	/**
	 * @see MetaDataServer#moveElement(String, String)
	 */
	public String moveElement(String from, String to) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(mHref);
			String newHref = mdsModel.moveElement(from, to);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#copyElement(String, String, String)
	 */
	public String copyElement(String from, String to, String label) {
		try {
			String[] hrefParts = from.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			String mHref =
				hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(mHref);
			String newHref = mdsModel.copyElement(from, to, label);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#validateModel(String, int)
	 */
	public ArrayList validateModel(String href, int validateType) {
		try {
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			ArrayList result = mdsModel.validateModel(validateType);
			return result;
		} catch (MDSCoreException e) {
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
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			return mdsModel;
		} catch (MDSCoreException e) {
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
			String[] hrefParts = href.split(".");
			String rHref = hrefParts[0] + "." + hrefParts[1];
			MDSRepository mdsRepository = getRepositoryByHref(rHref);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			MDSModel newModel = metaMappingEngine.map(mdsModel, mapping);
			newModel.setLabel(label);
			String newHref = this.insertModel(href, mdsModel);
			return newHref;
		} catch (MDSCoreException e) {
			return null;
		} catch (MetaMappingEngineException e) {
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

	private MDSRepository getRepositoryByHref(String href)
		throws MDSCoreException {

		String[] hrefParts = href.split("[.]");
		String id = hrefParts[1];
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
}
