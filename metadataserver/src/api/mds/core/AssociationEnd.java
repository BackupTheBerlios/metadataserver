package api.mds.core;

/**
 * Modelliert MOF-AssociationEnd
 * 
 * @author Thomas Chille
 */
public interface AssociationEnd {

	/**
	 * Multiplicity des AssociationEnd von 0
	 */
	public static final String MULTIPLICITY_0 = "0";

	/**
	 * Multiplicity des AssociationEnd von 1
	 */
	public static final String MULTIPLICITY_1 = "1";

	/**
	 * Multiplicity des AssociationEnd von 0 oder mehr
	 */
	public static final String MULTIPLICITY_0_OR_MORE = "0..*";

	/**
	 * Multiplicity des AssociationEnd von 1 oder mehr
	 */
	public static final String MULTIPLICITY_1_OR_MORE = "..*";

	/**
	 * Multiplicity des AssociationEnd von 0 oder 1
	 */
	public static final String MULTIPLICITY_0_OR_1 = "0..1";

	/**
	 * Multiplicity des AssociationEnd von n
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
}