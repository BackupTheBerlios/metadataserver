package mds.core;

import api.mds.core.MDSFile;

/**
 * @see api.mds.core.MDSFile#getContent()
 * 
 * @author Thomas Chille
 */
public class MDSFileImpl implements MDSFile {

	private String content = null;
	
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

}
