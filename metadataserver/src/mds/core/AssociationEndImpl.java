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
	private int multiplicity = 0;

	/**
	 * die Klasse dieses AssociationEndes
	 */
	private MDSClass mdsClass = null;

	/**
	 * @see AssociationEnd#getMultiplicity()
	 */
	public int getMultiplicity() {
		return multiplicity;
	}

	/**
	 * @see AssociationEnd#setMultiplicity(int)
	 */
	public void setMultiplicity(int multiplicity) {
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