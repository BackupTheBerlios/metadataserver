package mme.core;

import api.mme.core.Mapping;

/**
 * @see Mapping
 * 
 * @author Thomas Chille
 */
public class MappingImpl implements Mapping {

	/**
	 * Quelle
	 */
	private String from = null;
	
	private String to = null;
	
	/**
	 * Ziel
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * Sets the from
	 * @param from The from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * Gets the to
	 * @return Returns a String
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * Sets the to
	 * @param to The to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
}

