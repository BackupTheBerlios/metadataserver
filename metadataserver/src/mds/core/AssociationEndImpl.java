package mds.core;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSClass;

/**
 * @see AssociationEnd
 * 
 * @author Thomas Chille
 */
public class AssociationEndImpl implements AssociationEnd {

	/**
	 * die multiplicity dieses AssociationEndes
	 */
	private String multiplicity = null;

	/**
	 * die Klasse dieses AssociationEndes
	 */
	private MDSClass mdsClass = null;

	/**
	 * @see AssociationEnd#getMultiplicity()
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	/**
	 * @see AssociationEnd#setMultiplicity(String)
	 */
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}

	/**
	 * @see AssociationEnd#getMdsClass()
	 */
	public MDSClass getMdsClass() {
		return mdsClass;
	}

	/**
	 * @see AssociationEnd#setMdsClass(MDSClass)
	 */
	public void setMdsClass(MDSClass mdsClass) {
		this.mdsClass = mdsClass;
	}

}