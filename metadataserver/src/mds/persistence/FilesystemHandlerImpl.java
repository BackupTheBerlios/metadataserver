package mds.persistence;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import mds.MDSGlobals;
import mds.core.MDSHrefFormatException;
import mds.core.MDSModelImpl;
import mds.core.MDSRepositoryImpl;

import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSPersistentObject;
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
					new File(MDSGlobals.REPOSITORIES_PATH + mdsObject.getId());
				File file =
					new File(
						MDSGlobals.REPOSITORIES_PATH
							+ mdsObject.getId()
							+ "/repositorie.xml");
				if (!dir.exists() && !dir.mkdir()) {
					throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
				} else if (!file.exists() && !file.createNewFile()) {
					throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
				}
				FileWriter fw;
				fw = new FileWriter(file);
				String doc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
				doc += "<repository>\r\n";
				doc += "\t<name>" + mdsObject.getLabel() + "</name>\r\n";
				// TODO: set datetime
				doc += "\t<datetime>"
					+ "0000-00-00 00:00:00:000"
					+ "</datetime>\r\n";
				doc += "</repository>";
				fw.write(doc);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new PersistenceHandlerException("Fehler: FilesystemHandler#save(rep)");
			}
		} else if (mdsObject instanceof MDSModelImpl) {
			MDSModel mdsModel = (MDSModelImpl) mdsObject;
			try {
				String path =
					MDSGlobals.REPOSITORIES_PATH
						+ mdsObject.getHref().getRepositoryId()
						+ "/"
						+ mdsObject.getId();
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
					ArrayList versions =
						getModelVersions(mdsModel);
					int max = 0, tmp = 0;
					Iterator i = versions.iterator();
					while (i.hasNext()) {
						tmp = Integer.parseInt((String) i.next());
						if (tmp > max) {
							max = tmp;
						}
					}
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

	/**
	 * @see PersistenceHandler#load(MDSHref, String)
	 */
	public MDSPersistentObject load(MDSHref href, String version)
		throws PersistenceHandlerException {
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
	 * @see PersistenceHandler#loadMapper()
	 */
	public MDSMapper[] loadMapper() throws PersistenceHandlerException {
		return null;
	}

}
