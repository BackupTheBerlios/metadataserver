package mds.core;

import java.io.FileWriter;

import api.mds.core.MDSFile;

/**
 * @see api.mds.core.MDSFile#getContent()
 * 
 * @author Thomas Chille
 */
public class MDSFileImpl implements MDSFile {

	private String content = null;
	
	private String name = null;
	
	private String path = null;
	
	private String type = null;
	
	/**
	 * @see api.mds.core.MDSFile#getContent()
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @see api.mds.core.MDSFile#setContent(String)
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @see api.mds.core.MDSFile#getName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see api.mds.core.MDSFile#setName(String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see api.mds.core.MDSFile#getType()
	 */
	public String getType() {
		return type;
	}

	/**
	 * @see api.mds.core.MDSFile#setType(String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @see api.mds.core.MDSFile#getPath()
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @see api.mds.core.MDSFile#setPath(String)
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @see api.mds.core.MDSFile#save(String)
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
}
