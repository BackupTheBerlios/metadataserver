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
	 * type der Aggregation
	 */
	private int aggregation = NONE_AGGREGATION;

	/**
	 * die Klasse dieses AssociationEndes
	 */
	private MDSClass mdsClass = null;

	/**
	 * Constructor for AssociationEndImpl.
	 */
	public AssociationEndImpl(int aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * Constructor for AssociationEndImpl.
	 */
	public AssociationEndImpl() {
	}

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
	
	/**
	 * @see AssociationEnd#getAggregation()
	 */
	public int getAggregation() {
		return aggregation;
	}

	/**
	 * @see AssociationEnd#setAggregation(int)
	 */
	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

}