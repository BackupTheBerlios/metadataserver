package api.mds.core;

/**
 * Repräsentation der in den Models genutzten Files
 * 
 * @author Thomas Chille
 */
public interface MDSFile {

	/**
	 * Returns the content.
	 * @return String
	 */
	public String getContent();

	/**
	 * Sets the href.
	 * @param href The href to set
	 */
	public void setContent(String content);
	
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName();

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name);

}
