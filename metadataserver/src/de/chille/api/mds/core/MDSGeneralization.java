package de.chille.api.mds.core;

import de.chille.mds.core.MDSCoreException;
import de.chille.mds.soap.MDSGeneralizationBean;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Vererbungs-relation zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public interface MDSGeneralization extends MDSElement {

	/**
	 * Returns the subClass.
	 * @return MDSClass
	 */
	public MDSClass getSubClass();

	/**
	 * Returns the superClass.
	 * @return MDSClass
	 */
	public MDSClass getSuperClass();

	/**
	 * Sets the subClass.
	 * @param subClass The subClass to set
	 */
	public void setSubClass(MDSClass subClass);

	/**
	 * Sets the superClass.
	 * @param superClass The superClass to set
	 */
	public void setSuperClass(MDSClass superClass);
	
	public MDSGeneralizationBean exportBean();
	
	public void importBean(MDSGeneralizationBean bean);
}