package api.mds.core;

/**
 * Strukturelement eines MDSModels
 * 
 * @author Thomas Chille
 */
public interface MDSElement extends MDSObject {
	
	/**
	 * Returns the prefix.
	 * @return String
	 */
	public String getPrefix();

	/**
	 * Sets the prefix.
	 * @param prefix The prefix to set
	 */
	public void setPrefix(String prefix);
}