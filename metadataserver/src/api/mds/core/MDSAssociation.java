package api.mds.core;

import java.util.ArrayList;

import mds.core.MDSCoreException;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Relation zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public interface MDSAssociation extends MDSElement {
	
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
}