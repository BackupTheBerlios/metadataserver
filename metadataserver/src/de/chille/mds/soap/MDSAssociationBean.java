package de.chille.mds.soap;

import java.util.Vector;

public class MDSAssociationBean extends MDSObjectBean {
	
	/**
	 * die beiden associationEnds einer association
	 */
	private Vector associationEnds = new Vector();
	
	/**
	 * Constructor for MDSObjectBean.
	 */
	public MDSAssociationBean() {
		super();
	}
	
	/**
	 * Returns the associationEnds.
	 * @return Vector
	 */
	public Vector getAssociationEnds() {
		return associationEnds;
	}

	/**
	 * Sets the associationEnds.
	 * @param associationEnds The associationEnds to set
	 */
	public void setAssociationEnds(Vector associationEnds) {
		this.associationEnds = associationEnds;
	}

}
