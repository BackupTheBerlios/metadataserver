package de.chille.api.mds.core;

import java.util.ArrayList;

import de.chille.mds.core.MDSCoreException;
import de.chille.mds.soap.MDSAssociationBean;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Association zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public interface MDSAssociation extends MDSElement {
	
	/**
	 * fügt ein MDSAssociationEnd hinzu
	 * 
	 * @param associationEnd das MDSAssociationEnd
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void addAssociationEnd(MDSAssociationEnd associationEnd)
		throws MDSCoreException;

	/**
	 * entfernt ein MDSAssociationEnd
	 * 
	 * @param associationEnd das MDSAssociationEnd
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeAssociationEnd(MDSAssociationEnd associationEnd)
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
	
	public MDSAssociationBean exportBean();
	
	public void importBean(MDSAssociationBean bean);
}