package de.chille.mds.soap;

public class MDSFileBean extends MDSObjectBean {
	
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
	 * Constructor for MDSFileBean.
	 */
	public MDSFileBean() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getPath();
	}

}
