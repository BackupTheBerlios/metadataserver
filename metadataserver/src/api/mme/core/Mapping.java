package api.mme.core;

/**
 * modelliert eine Mappingmöglichkeit(Ziel, Quelle)
 * @author Christian Sterr
 *
 */
public interface Mapping {

	/**
	 * Method getFrom.
	 * @return String
	 */
	public String getFrom();
	
	/**
	 * Sets the from
	 * @param from The from to set
	 */
	public void setFrom(String from);

	/**
	 * Gets the to
	 * @return Returns a String
	 */
	public String getTo();
	
	/**
	 * Sets the to
	 * @param to The to to set
	 */
	public void setTo(String to);
	
}