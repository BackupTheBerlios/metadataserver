package de.chille.mds.soap;

public class MDSMapperBean extends MDSObjectBean {
	
	/**
	 * Quelle
	 */
	private String from = null;
	
	/**
	 * Ziel
	 */
	private String to = null;
	
	/**
	 * grammarfile oder stylesheet
	 */
	private MDSFileBean file = null;
	
	/**
	 * xml- oder unicode mapper
	 */
	private String type = null;
	
	/**
	 * Constructor for MDSObjectBean.
	 */
	public MDSMapperBean() {
		super();
	}
	
	/**
	 * Returns the file.
	 * @return MDSFileBean
	 */
	public MDSFileBean getFile() {
		return file;
	}

	/**
	 * Returns the from.
	 * @return String
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Returns the to.
	 * @return String
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the file.
	 * @param file The file to set
	 */
	public void setFile(MDSFileBean file) {
		this.file = file;
	}

	/**
	 * Sets the from.
	 * @param from The from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Sets the to.
	 * @param to The to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
