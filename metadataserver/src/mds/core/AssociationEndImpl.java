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
	 * Gets the multiplicity
	 * @return Returns a int
	 */
	public int getMultiplicity() {
		return multiplicity;
	}

	/**
	 * Sets the multiplicity
	 * @param multiplicity The multiplicity to set
	 */
	public void setMultiplicity(int multiplicity) {
		this.multiplicity = multiplicity;
	}

	/**
	 * Gets the mdsClass
	 * @return Returns a MDSClass
	 */
	public MDSClass getMdsClass() {
		return mdsClass;
	}

	/**
	 * Sets the mdsClass
	 * @param mdsClass The mdsClass to set
	 */
	public void setMdsClass(MDSClass mdsClass) {
		this.mdsClass = mdsClass;
	}

}