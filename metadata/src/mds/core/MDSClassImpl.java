package mds.core;

import api.mds.core.MDSClass;

/**
 * @see MDSClass
 * 
 * @author Thomas Chille
 */
public class MDSClassImpl extends MDSElementImpl implements MDSClass {

	/**
	 * Lister aller Superklassen dieser klasse
	 */
	private MDSClass[] superClasses = null;

	/**
	 * Lister aller Unterklassen dieser klasse
	 */
	private MDSClass[] subClasses = null;

	/**
	 * @see MDSClass#addSuperClass(MDSClass)
	 */
	public void addSuperClass(MDSClass superClass) throws MDSCoreException {
	}

	/**
	 * @see MDSClass#removeSuperClass(MDSClass)
	 */
	public void removeSuperClass(MDSClass superClass) throws MDSCoreException {
	}

	/**
	 * @see MDSClass#addSubClass(MDSClass)
	 */
	public void addSubClass(MDSClass subClass) throws MDSCoreException {
	}

	/**
	 * @see MDSClass#removeSubClass(MDSClass)
	 */
	public void removeSubClass(MDSClass subClass) throws MDSCoreException {
	}

	/**
	 * Gets the superClasses
	 * @return Returns a MDSClass[]
	 */
	public MDSClass[] getSuperClasses() throws MDSCoreException {
		return superClasses;
		
		
	}

	/**
	 * Sets the superClasses
	 * @param superClasses The superClasses to set
	 */
	public void setSuperClasses(MDSClass[] superClasses) throws MDSCoreException {
		this.superClasses = superClasses;
	}

	/**
	 * Gets the subClasses
	 * @return Returns a MDSClass[]
	 */
	public MDSClass[] getSubClasses() throws MDSCoreException {
		return subClasses;
	}

	/**
	 * Sets the subClasses
	 * @param subClasses The subClasses to set
	 */
	public void setSubClasses(MDSClass[] subClasses) throws MDSCoreException {
		this.subClasses = subClasses;
	}
}