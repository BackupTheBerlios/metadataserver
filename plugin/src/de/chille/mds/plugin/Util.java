package de.chille.mds.plugin;

import java.io.*;
import java.util.Random;
import java.util.Vector;

import de.chille.mds.plugin.tree.*;
import de.chille.mds.soap.*;

public class Util {

	private static Util defaultInstance = null;

	private static Random random = null;

	public synchronized static Util getInstance() {
		if (Util.defaultInstance == null) {
			Util.defaultInstance = new Util();
		}
		return Util.defaultInstance;
	}

	public static void addModel(
		TreeRepository repository,
		MDSModelBean model) {
		MDSObjectBean element = null;
		TreeObject obj = null;
		TreeParent elem = null;
		TreeModel mod = new TreeModel(model);
		for (int k = 0; k < model.getElements().size(); k++) {
			try {
				element = (MDSObjectBean) model.getElements().get(k);
				if (element instanceof MDSClassBean) {
					obj = new TreeClass(element);
					mod.addChild(obj);
				} else if (element instanceof MDSAssociationBean) {
					elem = new TreeAssociation(element);
					obj =
						new TreeClass(
							(
								(MDSAssociationEndBean)
									((MDSAssociationBean) element)
								.getAssociationEnds()
								.get(0))
								.getMdsClass());
					elem.addChild(obj);
					obj =
						new TreeClass(
							(
								(MDSAssociationEndBean)
									((MDSAssociationBean) element)
								.getAssociationEnds()
								.get(1))
								.getMdsClass());
					elem.addChild(obj);
					mod.addChild(elem);
				} else if (element instanceof MDSGeneralizationBean) {
					elem = new TreeGeneralization(element);
					obj =
						new TreeClass(
							((MDSGeneralizationBean) element).getSuperClass());
					elem.addChild(obj);
					obj =
						new TreeClass(
							((MDSGeneralizationBean) element).getSubClass());
					elem.addChild(obj);

					mod.addChild(elem);
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		setReferences(mod);
		repository.addChild(mod);
	}

	public static void setReferences(TreeModel model) {
		TreeObject[] children = model.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof TreeAssociation
				|| children[i] instanceof TreeGeneralization) {
				TreeObject[] classes = ((TreeParent) children[i]).getChildren();
				setReference(model, (TreeClass) classes[0]);
				setReference(model, (TreeClass) classes[1]);
			}
		}
	}

	public static void setReference(TreeModel model, TreeClass tclass) {
		TreeObject[] children = model.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof TreeClass) {
				if (tclass
					.getBean()
					.getHref()
					.equals(((TreeClass) children[i]).getBean().getHref())) {
					tclass.setBean(((TreeClass) children[i]).getBean());
					break;
				}
			}
		}
	}

	public static void addFiles(MDSModelBean model, String path) {
		model.setAdditionalFiles(new Vector());
		addFiles(model, path, "");
	}

	private static void addFiles(MDSModelBean model, String path, String rel) {
		String fullpath;
		if (rel.equals("")) {
			fullpath = path;
		} else {
			fullpath = path + "/" + rel;
		}
		File[] entries = new File(fullpath).listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		});
		if (!rel.equals("")) {
			rel += "/";
		}
		MDSFileBean mdsFile;
		BufferedReader f;
		String name = "", content, line = "";
		for (int i = 0; i < entries.length; i++) {
			name = entries[i].getName();
			if (entries[i].isDirectory()) {
				addFiles(model, path, rel + name);
				continue;
			}
			content = "";
			mdsFile = new MDSFileBean();
			mdsFile.setName(Util.getUniqueID());
			mdsFile.setPath(rel + name);
			try {
				f = new BufferedReader(new FileReader(path + "/" + rel + name));
				while ((line = f.readLine()) != null) {
					content += line + "\n";
				}
				f.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mdsFile.setContent(content);
			model.getAdditionalFiles().add(mdsFile);
		}
	}
	
	public static String getUniqueID() {
		if (Util.random == null) {
			Util.random = new Random();
		}
		return "uid_"
			+ System.currentTimeMillis()
			+ "_"
			+ Math.abs(random.nextInt());
	}
}
