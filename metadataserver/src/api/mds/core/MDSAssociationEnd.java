package api.mds.core;

/**
 * Modelliert MOF-MDSAssociationEnd
 * 
 * @author Thomas Chille
 */
public interface MDSAssociationEnd {

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
	 * Gets the multiplicity
	 * @return Returns a String
	 */
	public String getMultiplicity();

	/**
	 * Sets the multiplicity
	 * @param multiplicity The multiplicity to set
	 */
	public void setMultiplicity(String multiplicity);

	/**
	 * Gets the mdsClass
	 * @return Returns a MDSClass
	 */
	public MDSClass getMdsClass();

	/**
	 * Sets the mdsClass
	 * @param mdsClass The mdsClass to set
	 */
	public void setMdsClass(MDSClass mdsClass);
	
	/**
	 * Returns the aggregation.
	 * @return int
	 */
	public int getAggregation();

	/**
	 * Sets the aggregation.
	 * @param aggregation The aggregation to set
	 */
	public void setAggregation(int aggregation);
}