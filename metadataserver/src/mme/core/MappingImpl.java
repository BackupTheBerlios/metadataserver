package mme.core;

import api.mme.core.Mapping;
import api.mme.mapper.MDSMapper;

/**
 *	@see Mapping
 *  @author Christian Sterr
 *
 */
public class MappingImpl implements Mapping {

	/**
	 * Quelle
	 */
	private String from = null;
	
	/**
	 * Ziel
	 */
	private String to = null;
	
	/**
	 * Gets the from
	 * @return Returns a String
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
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o){
		System.out.println("-> equals Test!");
		Mapping mapping = (Mapping)o;
		if( mapping.getFrom().compareToIgnoreCase(this.getFrom()) == 0 &&
			mapping.getTo().compareToIgnoreCase(this.getTo()) == 0 ){
			return true;
		}
		return false;
	}
	
}

