package api.mds.core;

import java.util.ArrayList;

import mds.core.MDSCoreException;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Aggregation zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public interface MDSAggregation extends MDSElement {
	
	/**
	 * Returns the containedEnd.
	 * @return AssociationEnd
	 */
	public AssociationEnd getContainedEnd();

	/**
	 * Returns the containerEnd.
	 * @return AssociationEnd
	 */
	public AssociationEnd getContainerEnd();

	/**
	 * Sets the containedEnd.
	 * @param containedEnd The containedEnd to set
	 */
	public void setContainedEnd(AssociationEnd containedEnd);

	/**
	 * Sets the containerEnd.
	 * @param containerEnd The containerEnd to set
	 */
	public void setContainerEnd(AssociationEnd containerEnd);
	
	/**
	 * Returns the composition.
	 * @return boolean
	 */
	public boolean isComposition();

	/**
	 * Sets the composition.
	 * @param composition The composition to set
	 */
	public void setComposition(boolean composition);
}