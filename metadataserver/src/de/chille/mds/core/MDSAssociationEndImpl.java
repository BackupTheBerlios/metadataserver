package de.chille.mds.core;

import de.chille.api.mds.core.MDSAssociationEnd;
import de.chille.api.mds.core.MDSClass;

/**
 * @see MDSAssociationEnd
 * 
 * @author Thomas Chille
 */
public class MDSAssociationEndImpl implements MDSAssociationEnd {

	/**
	 * die multiplicity dieses AssociationEndes
	 */
	private String multiplicity = null;

	/**
	 * type der Aggregation
	 */
	private int aggregation = NONE_AGGREGATION;

	/**
	 * die Klasse dieses AssociationEndes
	 */
	private MDSClass mdsClass = null;

	/**
	 * Constructor for MDSAssociationEndImpl.
	 */
	public MDSAssociationEndImpl(int aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * Constructor for MDSAssociationEndImpl.
	 */
	public MDSAssociationEndImpl() {
	}

	/**
	 * @see MDSAssociationEnd#getMultiplicity()
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	/**
	 * @see MDSAssociationEnd#setMultiplicity(String)
	 */
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}

	/**
	 * @see MDSAssociationEnd#getMdsClass()
	 */
	public MDSClass getMdsClass() {
		return mdsClass;
	}

	/**
	 * @see MDSAssociationEnd#setMdsClass(MDSClass)
	 */
	public void setMdsClass(MDSClass mdsClass) {
		this.mdsClass = mdsClass;
	}
	
	/**
	 * @see MDSAssociationEnd#getAggregation()
	 */
	public int getAggregation() {
		return aggregation;
	}

	/**
	 * @see MDSAssociationEnd#setAggregation(int)
	 */
	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

}