package mds.core;

import java.util.ArrayList;

import api.mds.core.MDSAssociationEnd;
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
	 * Constructor for MDSAssociationImpl.
	 */
	public MDSAssociationImpl() {
		super("association");
	}

	/**
	 * @see MDSAssociationImpl#addAssociationEnd(MDSAssociationEnd)
	 */
	public void addAssociationEnd(MDSAssociationEnd associationEnd)
		throws MDSCoreException {

		if (associationEnds.size() == 2) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		} else if (!associationEnds.add(associationEnd)) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		}
	}

	/**
	 * @see MDSAssociationImpl#removeAssociationEnd(MDSAssociationEnd)
	 */
	public void removeAssociationEnd(MDSAssociationEnd associationEnd)
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
	 * @see MDSAssociationImpl#setAssociationEnds(ArrayList)
	 */
	public void setAssociationEnds(ArrayList associationEnds) {
		this.associationEnds = associationEnds;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tassociation:"
			+ this.getId()
			+ " - "
			+ this.getLabel()
			+ "\n";

	}
}