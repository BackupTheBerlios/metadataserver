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
	
	/**
	 * Returns the path.
	 * @return String
	 */
	public String getPath();

	/**
	 * Sets the path.
	 * @param name The path to set
	 */
	public void setPath(String path);
	
	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType();

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type);
}
