package de.chille.mds.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.chille.mds.MDSGlobals;
import de.chille.mds.core.MDSFileImpl;
import de.chille.mds.core.MDSHrefFormatException;
import de.chille.mds.core.MDSHrefImpl;
import de.chille.mds.core.MDSModelImpl;
import de.chille.mds.core.MDSRepositoryImpl;
import de.chille.mds.core.MetaDataServerImpl;
import de.chille.mds.xmi.XMIHandlerImpl;

import de.chille.api.mds.core.MDSFile;
import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mds.core.MDSPersistentObject;
import de.chille.api.mds.core.MDSRepository;
import de.chille.api.mds.core.MetaDataServer;
import de.chille.api.mds.persistence.PersistenceHandler;
import de.chille.api.mme.mapper.MDSMapper;

/**
 * @see PersistenceHandler
 * 
 * @author Thomas Chille
 */
public class FilesystemHandlerImpl implements PersistenceHandler {

	/**
	 * @see PersistenceHandler#save(MDSPersistentObject)
	 */
	public void save(MDSPersistentObject mdsObject)
		throws PersistenceHandlerException {

		if (mdsObject instanceof MDSRepositoryImpl) {
			try {
				File dir =
					new File(
						MDSGlobals.REPOSITORIES_PATH
							+ "repository_"
							+ mdsObject.getId());
				File file =
					new File(
						MDSGlobals.REPOSITORIES_PATH
							+ "repository_"
							+ mdsObject.getId()
							+ "/repository.xml");
				if (!dir.exists() && !dir.mkdir()) {
					throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
				} else {
					// if (!file.exists()) 
					if (!file.exists() && !file.createNewFile()) {
						throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
					}
					FileWriter fw;
					fw = new FileWriter(file);
					String doc =
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
					doc += "<repository>\r\n";
					doc += "\t<name>" + mdsObject.getLabel() + "</name>\r\n";
					doc += "\t<counter>"
						+ ((MDSRepositoryImpl) mdsObject).getCounter()
						+ "</counter>\r\n";
					// TODO: set datetime
					doc += "\t<datetime>"
						+ "0000-00-00 00:00:00:000"
						+ "</datetime>\r\n";
					doc += "</repository>";
					fw.write(doc);
					fw.close();
				}
				Iterator i =
					((MDSRepositoryImpl) mdsObject).getModels().iterator();
				MDSModel model;
				while (i.hasNext()) {
					model = (MDSModel) i.next();
					model.save();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
			}
		} else if (mdsObject instanceof MDSModelImpl) {
			MDSModel mdsModel = (MDSModelImpl) mdsObject;
			try {
				String path =
					MDSGlobals.REPOSITORIES_PATH
						+ "repository_"
						+ mdsModel.getHref().getRepositoryId()
						+ "/model_"
						+ mdsModel.getId();
				File dir = new File(path);
				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(mod)");
					}
					path += "/version_0";
					dir = new File(path);
					if (!dir.mkdir()) {
						throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(mod)");
					}
				} else {
					int max = getMaxVersion(path);
					path += "/version_" + ++max;
					dir = new File(path);
					if (!dir.mkdir()) {
						throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(mod)");
					}
				}
				path += "/";
				FileWriter fw;
				fw = new FileWriter(path + "model.xmi");
				fw.write(mdsModel.getUmlFile().getContent());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(mod)");
			} catch (MDSHrefFormatException e) {
				e.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(mod)");
			}
		}
	}

	private int getMaxVersion(String path) throws PersistenceHandlerException {
		ArrayList versions = getModelVersions(path);
		int max = 0, tmp = 0;
		Iterator i = versions.iterator();
		while (i.hasNext()) {
			tmp = Integer.parseInt((String) i.next());
			if (tmp > max) {
				max = tmp;
			}
		}
		return max;
	}

	/**
	 * @see PersistenceHandler#load(MDSHref, String)
	 */
	public MDSPersistentObject load(MDSHref href, String version)
		throws PersistenceHandlerException {
	
		String modelID = null;
		String repositoryID = null;
		MDSFile xmiFile = new MDSFileImpl();
		BufferedReader f;
		String line = null;
		String content = "";
		MDSModel model;
		try {
			repositoryID = href.getRepositoryId();
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
			throw new PersistenceHandlerException("Fehler: FilesystemHandler#load()");
		}
		File dir =
			new File(
				MDSGlobals.REPOSITORIES_PATH + "repository_" + repositoryID);
		try {
			modelID = href.getModelId();
			// hier weiter wenn model geladen werden soll
			f =
				new BufferedReader(
					new FileReader(
						dir
							+ "/model_"
							+ modelID
							+ "/version_"
							+ getMaxVersion(dir + "/model_" + modelID)
							+ "/model.xmi"));
			while ((line = f.readLine()) != null) {
				content += line + "\n";
			}
			f.close();
			xmiFile.setContent(content);
			model = new XMIHandlerImpl().mapUML2MDS(xmiFile);
			model.setId(modelID);
			model.setHref(href);
	
			return model;
	
		} catch (MDSHrefFormatException e) {
			try {
				// komplettes repository laden
				File file =
					new File(
						MDSGlobals.REPOSITORIES_PATH
							+ "repository_"
							+ repositoryID
							+ "/repository.xml");
				MDSRepository repository = new MDSRepositoryImpl();
				repository.setHref(new MDSHrefImpl(href.getRepositoryHref()));
				repository.setId(repositoryID);
				// repository-name aus repository.xml parsen
				DocumentBuilderFactory dFactory =
					DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();
				for (int i = 0; i < nList.getLength(); i++) {
					if (nList.item(i).getNodeName().equals("name")) {
						repository.setLabel(
							nList.item(i).getFirstChild().getNodeValue());
					} else if (nList.item(i).getNodeName().equals("counter")) {
						repository.setCounter(
							Integer.parseInt(
								nList.item(i).getFirstChild().getNodeValue()));
					}
	
				}
				// alle enthaltenen models laden
				File[] entries = dir.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return true;
					}
				});
				for (int i = 0; i < entries.length; i++) {
					content = "";
					modelID = entries[i].getName();
					if (!modelID.startsWith("model_")) {
						continue;
					}
					f =
						new BufferedReader(
							new FileReader(
								dir
									+ "/"
									+ modelID
									+ "/version_"
									+ getMaxVersion(dir + "/" + modelID)
									+ "/model.xmi"));
					while ((line = f.readLine()) != null) {
						content += line + "\n";
					}
					f.close();
					xmiFile.setContent(content);
					model = new XMIHandlerImpl().mapUML2MDS(xmiFile);
					model.setId(modelID.substring(6));
					repository.insertModel(model);
				}
	
				return repository;
	
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#load(rep)");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceHandlerException("Fehler: FilesystemHandler#load(mod)");
		}
	}

	/**
	 * @see PersistenceHandler#delete(MDSPersistentObject, String)
	 */
	public void delete(MDSPersistentObject mdsObject, String version)
		throws PersistenceHandlerException {
	}

	/**
	 * @see PersistenceHandler#getModelVersions(MDSModel)
	 */
	public ArrayList getModelVersions(MDSModel mdsModel)
		throws PersistenceHandlerException {

		ArrayList versions = new ArrayList();
		File dir;
		try {
			dir =
				new File(
					MDSGlobals.REPOSITORIES_PATH
						+ mdsModel.getHref().getRepositoryId()
						+ "/"
						+ mdsModel.getId());
		} catch (MDSHrefFormatException e) {
			throw new PersistenceHandlerException("Fehler: FilesystemHandler#getModelVersions");
		}
		File[] entries = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		});
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getName().startsWith("version_")) {
				versions.add(entries[i].getName().substring(8));
			}
		}
		return versions;
	}

	/**
	 * @see PersistenceHandler#getModelVersions(MDSModel)
	 */
	private ArrayList getModelVersions(String path)
		throws PersistenceHandlerException {

		ArrayList versions = new ArrayList();
		File dir = new File(path);
		File[] entries = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		});
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getName().startsWith("version_")) {
				versions.add(entries[i].getName().substring(8));
			}
		}
		return versions;
	}

	/**
	 * @see PersistenceHandler#loadMapper()
	 */
	public MDSMapper[] loadMapper() throws PersistenceHandlerException {
		return null;
	}

	public void load(MetaDataServer server) {
		// die servereigenschaften aus server.xml parsen
		File file = new File(MDSGlobals.REPOSITORIES_PATH + "/server.xml");
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		//server.setLabel("blala");
		try {
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			Element root = doc.getDocumentElement();
			NodeList nList = root.getChildNodes();
			for (int i = 0; i < nList.getLength(); i++) {
				if (nList.item(i).getNodeName().equals("name")) {
					server.setLabel(
						nList.item(i).getFirstChild().getNodeValue());
				} else if (nList.item(i).getNodeName().equals("counter")) {
					server.setCounter(
						Integer.parseInt(
							nList.item(i).getFirstChild().getNodeValue()));
				} else if (nList.item(i).getNodeName().equals("id")) {
					server.setId(
						nList.item(i).getFirstChild().getNodeValue());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

			
		// alle enthaltenen repositories laden
		File dir = new File(MDSGlobals.REPOSITORIES_PATH);
		File[] entries = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		});
		
		String repID = null;
		MDSRepository rep = null;
		
		
		for (int i = 0; i < entries.length; i++) {
			
			repID = entries[i].getName();
			if (!repID.startsWith("repository_")) {
				continue;
			}
			try {
				rep =
					(MDSRepositoryImpl) new FilesystemHandlerImpl().load(
						new MDSHrefImpl(
							"mds://server_" + server.getId() + "/" + repID),
						null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			server.insertReposititory(rep);
			
		}
	}

	public void save(MetaDataServer server)
		throws PersistenceHandlerException {
		Iterator i = server.getRepositories().iterator();
		while (i.hasNext()) {
			try {
				((MDSRepository) i.next()).save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File file = new File(MDSGlobals.REPOSITORIES_PATH + "/server.xml");
		try {
			if (!file.exists() && !file.createNewFile()) {
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(server)");
			}
			FileWriter fw;
			fw = new FileWriter(file);
			String doc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
			doc += "<server>\r\n";
			doc += "\t<id>" + server.getId() + "</id>\r\n";
			doc += "\t<name>" + server.getLabel() + "</name>\r\n";
			doc += "\t<counter>" + server.getCounter() + "</counter>\r\n";
			// TODO: set datetime
			doc += "\t<datetime>"
				+ "0000-00-00 00:00:00:000"
				+ "</datetime>\r\n";
			doc += "</server>";
			fw.write(doc);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
