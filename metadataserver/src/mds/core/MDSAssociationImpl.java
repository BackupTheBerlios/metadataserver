package mds.core;

import java.util.ArrayList;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSAssociation;

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