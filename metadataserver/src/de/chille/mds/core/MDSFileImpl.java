package de.chille.mds.core;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import de.chille.api.mds.core.MDSFile;
import de.chille.mds.soap.MDSFileBean;

/**
 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#getContent()
 * 
 * @author Thomas Chille
 */
public class MDSFileImpl implements MDSFile {

	private String content = "";
	
	private String name = "";
	
	private String path = "";
	
	private String type = "";
	
	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#getContent()
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#setContent(String)
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#getName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#setName(String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#getType()
	 */
	public String getType() {
		return type;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#setType(String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#getPath()
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#setPath(String)
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSFile#save(String)
	 */
	public boolean save(String path) {
		try {
			FileWriter f1 = new FileWriter(path);
			f1.write(getContent());
			f1.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public MDSFileBean exportBean() {
		MDSFileBean bean = new MDSFileBean();
		bean.setContent(this.getContent() == null ? "" : this.getContent());
		bean.setName(this.getName() == null ? "" : this.getName());
		bean.setPath(this.getPath() == null ? "" : this.getPath());
		bean.setType(this.getType() == null ? "" : this.getType());
		return bean;
	}
	
	
	/**
	 * @see de.chille.api.mds.core.MDSFile#importBean(MDSFileBean)
	 */
	public void importBean(MDSFileBean bean) {
		this.setContent(bean.getContent() == null ? "" : bean.getContent());
		this.setName(bean.getName() == null ? "" : bean.getName());
		this.setPath(bean.getPath() == null ? "" : bean.getPath());
		this.setType(bean.getType() == null ? "" : bean.getType());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}

}
