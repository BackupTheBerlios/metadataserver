package de.chille.mds.soap;

import org.apache.soap.util.Bean;

public class MDSAssociationEndBean extends MDSObjectBean {
	
	public static final int NONE_AGGREGATION = 0;
	
	public static final int SHARED_AGGREGATION = 1;
	
	public static final int COMPOSITE_AGGREGATION = 2;
	
	/**
	 * Multiplicity des MDSAssociationEnd von 0
	 */
	public static final String MULTIPLICITY_0 = "0";

	/**
	 * Multiplicity des MDSAssociationEnd von 1
	 */
	public static final String MULTIPLICITY_1 = "1";

	/**
	 * Multiplicity des MDSAssociationEnd von 0 oder mehr
	 */
	public static final String MULTIPLICITY_0_OR_MORE = "0..*";

	/**
	 * Multiplicity des MDSAssociationEnd von 1 oder mehr
	 */
	public static final String MULTIPLICITY_1_OR_MORE = "..*";

	/**
	 * Multiplicity des MDSAssociationEnd von 0 oder 1
	 */
	public static final String MULTIPLICITY_0_OR_1 = "0..1";

	/**
	 * Multiplicity des MDSAssociationEnd von n
	 */
	public static final String MULTIPLICITY_n = "*";
	
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
	private MDSClassBean mdsClass = null;

	/**
	 * Returns the aggregation.
	 * @return int
	 */
	public int getAggregation() {
		return aggregation;
	}

	/**
	 * Returns the mdsClass.
	 * @return MDSClass
	 */
	public MDSClassBean getMdsClass() {
		return mdsClass;
	}

	/**
	 * Returns the multiplicity.
	 * @return String
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	/**
	 * Sets the aggregation.
	 * @param aggregation The aggregation to set
	 */
	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * Sets the mdsClass.
	 * @param mdsClass The mdsClass to set
	 */
	public void setMdsClass(MDSClassBean mdsClass) {
		this.mdsClass = mdsClass;
	}

	/**
	 * Sets the multiplicity.
	 * @param multiplicity The multiplicity to set
	 */
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}

}
