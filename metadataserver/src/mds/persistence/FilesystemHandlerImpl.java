package mds.persistence;

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

import mds.MDSGlobals;
import mds.core.MDSFileImpl;
import mds.core.MDSHrefFormatException;
import mds.core.MDSHrefImpl;
import mds.core.MDSModelImpl;
import mds.core.MDSRepositoryImpl;
import mds.xmi.XMIHandlerImpl;

import api.mds.core.MDSFile;
import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSPersistentObject;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;
import api.mme.mapper.MDSMapper;

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
				} else if (!file.exists()) {
					if (!file.createNewFile()) {
						throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
					}
					FileWriter fw;
					fw = new FileWriter(file);
					String doc =
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
					doc += "<repository>\r\n";
					doc += "\t<name>" + mdsObject.getLabel() + "</name>\r\n";
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
		try {
			modelID = href.getModelId();
		} catch (MDSHrefFormatException e) {
			try {
				repositoryID = href.getRepositoryId();
				File dir =
					new File(MDSGlobals.REPOSITORIES_PATH + repositoryID);
				if (modelID == null) {
					// komplettes repository laden
					File file =
						new File(
							MDSGlobals.REPOSITORIES_PATH
								+ repositoryID
								+ "/repository.xml");
					MDSRepository repository = new MDSRepositoryImpl();
					// repository-name aus repository.xml parsen
					DocumentBuilderFactory dFactory =
						DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					Element root = doc.getDocumentElement();
					NodeList nList = root.getChildNodes();
					for (int i = 0; i < nList.getLength(); i++) {
						String name = nList.item(i).getNodeName();
						if (nList.item(i).getAttributes() != null
							&& nList.item(i).getAttributes().getNamedItem("name")
								!= null)
							repository.setLabel(
								nList
									.item(i)
									.getAttributes()
									.getNamedItem("name")
									.getNodeValue());
					}
					// alle enthaltenen models laden
					File[] entries = dir.listFiles(new FileFilter() {
						public boolean accept(File pathname) {
							return true;
						}
					});
					MDSFile xmiFile = new MDSFileImpl();
					BufferedReader f;
					String line = null;
					String content = "";
					for (int i = 0; i < entries.length; i++) {
						modelID = entries[i].getName();
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
						MDSModel model =
							new XMIHandlerImpl().mapUML2MDS(xmiFile);
						model.setId(modelID);
						repository.insertModel(model);
					}
					return repository;
				} else {
					// nur ein model laden

				}

			} catch (Exception e1) {
				e1.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#load(mod)");
			}
		}
		return null;
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

}
