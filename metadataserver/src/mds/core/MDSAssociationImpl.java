package mds.core;

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

	private AssociationEnd[] associationEnds = null;

	public void addAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException {
	}

	public void removeAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException {
	}

	/**
	 * Gets the associationEnds
	 * @return Returns a AssociationEnd[]
	 */
	public AssociationEnd[] getAssociationEnds() {
		return associationEnds;
	}
	/**
	 * Sets the associationEnds
	 * @param associationEnds The associationEnds to set
	 */
	public void setAssociationEnds(AssociationEnd[] associationEnds) {
		this.associationEnds = associationEnds;
	}

}