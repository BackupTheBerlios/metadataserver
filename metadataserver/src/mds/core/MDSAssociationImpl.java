package mds.core;

import java.util.ArrayList;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSAssociation;
import api.mds.core.MDSHref;

/**
 * @see MDSAssociation
 * 
 * @author Thomas Chille
 */
public class MDSAssociationImpl
	extends MDSElementImpl
	implements MDSAssociation {

	/**
	 * die beiden associationEnds einer association
	 */
	private ArrayList associationEnds = new ArrayList();

	/**
	 * type der Aggregation
	 */
	private int aggregation = NONE_AGGREGATION;

	/**
	 * Constructor for MDSAssociationImpl.
	 */
	public MDSAssociationImpl() {
		super("association");
	}
	
	/**
	 * Constructor for MDSAssociationImpl.
	 */
	public MDSAssociationImpl(int aggregation) {
		super("association");
		this.aggregation = aggregation;
	}
	
	/**
	 * @see MDSAssociationImpl#addAssociationEnd(AssociationEnd)
	 */
	public void addAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException {

		if (associationEnds.size() == 2) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		} else if (!associationEnds.add(associationEnd)) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		}
	}

	/**
	 * @see MDSAssociationImpl#removeAssociationEnd(AssociationEnd)
	 */
	public void removeAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException {

		if (!associationEnds.remove(associationEnd)) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#removeAssociationEnd()");
		}
	}

	/**
	 * @see MDSAssociationImpl#getAssociationEnds()
	 */
	public ArrayList getAssociationEnds() {
		return associationEnds;
	}

	/**
	 * @see MDSAssociationImpl#getAggregation()
	 */
	public int getAggregation() {
		return aggregation;
	}

	/**
	 * @see MDSAssociationImpl#setAggregation(int)
	 */
	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * @see MDSAssociationImpl#setAssociationEnds(ArrayList)
	 */
	public void setAssociationEnds(ArrayList associationEnds) {
		this.associationEnds = associationEnds;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tassociation:" + this.getId() + "\n";

	}
}