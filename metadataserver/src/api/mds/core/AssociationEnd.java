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
	public static final int MULTIPLICITY_0 = 1;

	/**
	 * Multiplicity des AssociationEnd von 1
	 */
	public static final int MULTIPLICITY_1 = 2;

	/**
	 * Multiplicity des AssociationEnd von 0 oder mehr
	 */
	public static final int MULTIPLICITY_0_OR_MORE = 3;

	/**
	 * Multiplicity des AssociationEnd von 1 oder mehr
	 */
	public static final int MULTIPLICITY_1_OR_MORE = 4;

	/**
	 * Multiplicity des AssociationEnd von 0 oder 1
	 */
	public static final int MULTIPLICITY_0_OR_1 = 5;

	/**
	 * Multiplicity des AssociationEnd von n
	 */
	public static final int MULTIPLICITY_n = 6;
	
	/**
	 * Gets the multiplicity
	 * @return Returns a int
	 */
	public int getMultiplicity();

	/**
	 * Sets the multiplicity
	 * @param multiplicity The multiplicity to set
	 */
	public void setMultiplicity(int multiplicity);

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