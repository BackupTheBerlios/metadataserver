package api.mds.core;

import java.util.ArrayList;

import mds.core.MDSCoreException;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Association zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public interface MDSAssociation extends MDSElement {
	
	public static final int NONE_AGGREGATION = 0;
	
	public static final int SHARED_AGGREGATION = 1;
	
	public static final int COMPOSITE_AGGREGATION = 2;
	
	/**
	 * fügt ein AssociationEnd hinzu
	 * 
	 * @param associationEnd das AssociationEnd
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void addAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException;

	/**
	 * entfernt ein AssociationEnd
	 * 
	 * @param associationEnd das AssociationEnd
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeAssociationEnd(AssociationEnd associationEnd)
		throws MDSCoreException;

	/**
	 * Gets the associationEnds
	 * @return Returns a ArrayList
	 */
	public ArrayList getAssociationEnds();
	
	/**
	 * Sets the associationEnds
	 * @param associationEnds The associationEnds to set
	 */
	public void setAssociationEnds(ArrayList associationEnds);
	
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