package de.chille.mds.core;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import de.chille.mds.MDSGlobals;
import de.chille.mds.persistence.FilesystemHandlerImpl;
import de.chille.mds.persistence.PersistenceHandlerException;
import de.chille.mme.core.*;
import de.chille.mme.core.MetaMappingEngineException;
import de.chille.mme.core.MetaMappingEngineImpl;

import de.chille.api.mds.core.MDSElement;
import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mds.core.MDSRepository;
import de.chille.api.mds.core.MetaDataServer;
import de.chille.api.mds.persistence.PersistenceHandler;
import de.chille.api.mme.core.Mapping;
import de.chille.api.mme.core.MetaMappingEngine;
import de.chille.api.mme.mapper.MDSMapper;

/**
 * @see MetaDataServer
 * 
 * @author Thomas Chille
 */
public final class MetaDataServerImpl
	extends MDSPersistentObjectImpl
	implements MetaDataServer {

	private static int counter = 0;

	/**
	 * der Server kennt seine MME
	 */
	private static MetaMappingEngine metaMappingEngine = null;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private ArrayList repositories = new ArrayList();

	public void startup() {
		this.getPersistenceHandler().load(this);
		metaMappingEngine = new MetaMappingEngineImpl();
	}

	/**
	 * @see MetaDataServer#insertReposititory(MDSRepository)
	 */
	public MDSHref insertReposititory(MDSRepository mdsRepository) {
		try {
			boolean doSave = false;
			if (repositories.add(mdsRepository)) {
				if (mdsRepository.getId() == null) {
					mdsRepository.setId(this.getId() + "_" + this.counter++);
					doSave = true;
				}
				MDSHref href =
					new MDSHrefImpl(
						this.getHref().getServerHref()
							+ "/repository_"
							+ mdsRepository.getId());
				mdsRepository.setHref(href);
				if (doSave) {
					save();
				}
				return href;
			} else {
				return null;
			}
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see MetaDataServer#deleteRepository(String)
	 */
	public Vector removeRepository(MDSHref href, boolean confirm) {
		Vector result = new Vector();
		Vector temp = new Vector();
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			int i = mdsRepository.getModels().size();
			for (--i; i >= 0; i--) {
				result.addAll(
					mdsRepository.removeModel(
						((MDSModelImpl) mdsRepository.getModels().get(i))
							.getHref(),
						confirm));
			}
			if (!confirm) {
				mdsRepository.delete(null);
				if (repositories.remove(mdsRepository)) {
					save();
				}
			} else {
				// alle confirm-msg's die andere repositories betreffen herausfiltern
				Iterator j = result.iterator();
				while (j.hasNext()) {
					String msg = (String) j.next();
					if (!msg
						.startsWith(
							mdsRepository.getHref().getRepositoryHref())) {
						temp.add(msg);
					}
				}
				result = temp;
			}
		} catch (MDSCoreException e) {
			e.printStackTrace();
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		} catch (PersistenceHandlerException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @see MetaDataServer#renameRepository(MDSHref, String)
	 */
	public boolean renameRepository(MDSHref href, String label) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			mdsRepository.setLabel(label);
			save();
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
			save();
			return mdsModel.getHref();
		} catch (MDSCoreException e) {
			return null;
		} catch (MDSHrefFormatException e) {
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeModel(MDSHref)
	 */
	public Vector removeModel(MDSHref href, boolean confirm) {
		Vector result = new Vector();
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			result = mdsRepository.removeModel(href, confirm);
			save();
		} catch (MDSCoreException e) {
			e.printStackTrace();
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @see MetaDataServer#moveModel(MDSHref, MDSHref)
	 */
	public MDSHref moveModel(MDSHref from, MDSHref to) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(from);
			String id = mdsRepository.moveModel(from, to);
			save();
			return new MDSHrefImpl(to.getHrefString() + "/" + id);
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
			String id = getRepositoryByHref(from).copyModel(from, to, label);
			;
			save();
			return new MDSHrefImpl(to.getHrefString() + "/model_" + id);
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
			save();
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
			save();
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
			save();
			return mdsElement.getHref();
		} catch (MDSCoreException e) {
			e.printStackTrace();
			return null;
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see MetaDataServer#removeElement(MDSHref)
	 */
	public Vector removeElement(MDSHref href, boolean confirm) {
		Vector result = new Vector();
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			result = mdsModel.removeElement(href, confirm);
			save();
		} catch (MDSCoreException e) {
			e.printStackTrace();
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @see MetaDataServer#validateModel(MDSHref, int)
	 */
	public Vector validateModel(MDSHref href, int validateType) {
		try {
			MDSRepository mdsRepository = getRepositoryByHref(href);
			MDSModel mdsModel = mdsRepository.getModelByHref(href);
			Vector result = mdsModel.validateModel(validateType);
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
	/*public MDSHref importModel(
		MDSHref href,
		MDSModel mdsModel,
		Mapping mapping) {
	
		try {
			if (mapping != null) {
				mdsModel = metaMappingEngine.map(mdsModel, mapping);
			}
			MDSHref newHref = this.insertModel(href, mdsModel);
			save();
			return newHref;
		} catch (MetaMappingEngineException e) {
			return null;
		}
	}*/

	/**
	 * @see MetaDataServer#exportModel(MDSHref, Mapping)
	 */
	/*public MDSModel exportModel(MDSHref href, Mapping mapping) {
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
	}*/

	/**
	 * @see MetaDataServer#registerMapper(MDSMapper)
	 */
	public String registerMapper(MDSMapper mapper) {
		try {
			metaMappingEngine.registerMapper(mapper);
			return null;
		} catch (MetaMappingEngineException e) {
			return e.getMessage();
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
	public MDSModel convertModel(MDSHref href, Mapping mapping)
		throws MetaMappingEngineException {
		MDSModel mdsModel = null;
		try {
			mdsModel = getRepositoryByHref(href).getModelByHref(href);
			metaMappingEngine.map(mdsModel, mapping);
			save();
		} catch (MDSCoreException e) {
			e.printStackTrace();
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return mdsModel;
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

	public MDSRepository getRepositoryByHref(MDSHref href)
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
		String retString =
			"server:" + this.getId() + " - " + this.getLabel() + "\n";
		Iterator i = repositories.iterator();
		while (i.hasNext()) {
			retString += ((MDSRepository) i.next()).toString();
		}
		return retString;
	}

	/**
	 * @see de.chille.mds.core.MetaDataServer#getCounter()
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @see de.chille.mds.core.MetaDataServer#getRepositories()
	 */
	public ArrayList getRepositories() {
		return repositories;
	}

	/**
	 * @see de.chille.mds.core.MetaDataServer#setCounter(int)
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Method setRepositories.
	 * @param repositories
	 */
	public void setRepositories(ArrayList repositories) {
		this.repositories = repositories;
	}

	public static void main(String[] args) throws Exception {
		MetaDataServer server = MetaDataServerImpl.getInstance();
		System.out.println(server);
		server.convertModel(
			new MDSHrefImpl("mds://server_0/repository_0_12/model_0_12_3"),
			new MappingImpl("mds", "java"));
		server.save();
		System.out.println(server);
	}

	public void shutdown() {
		save();
		System.exit(0);
	}

	public void reset() {
		MetaDataServerImpl.defaultInstance = null;
		MetaDataServerImpl.getInstance();
	}

	/** This is the default instance used for this singleton. */
	private static MetaDataServerImpl defaultInstance = null;

	/**
	 * Gets the unique instance of this class. <br>
	 *
	 * @return the unique instance of this class
	 */
	public synchronized static MetaDataServerImpl getInstance() {
		if (MetaDataServerImpl.defaultInstance == null) {
			MetaDataServerImpl.defaultInstance = new MetaDataServerImpl();
			defaultInstance.startup();
		}
		return MetaDataServerImpl.defaultInstance;
	}

	public void save() {
		try {
			super.save();
		} catch (PersistenceHandlerException e) {
			e.printStackTrace();
		}
	}

}
