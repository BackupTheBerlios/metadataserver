package mds.core;

import java.util.ArrayList;

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
	private ArrayList superClasses = null;

	/**
	 * Lister aller Unterklassen dieser klasse
	 */
	private ArrayList subClasses = null;

	/**
	 * @see MDSClass#addSuperClass(MDSClass)
	 */
	public void addSuperClass(MDSClass superClass) throws MDSCoreException {
		if (!superClasses.add(superClass)) {
			throw new MDSCoreException("Fehler: MDSClass#addSuperClass()");
		}
	}

	/**
	 * @see MDSClass#removeSuperClass(MDSClass)
	 */
	public void removeSuperClass(MDSClass superClass) throws MDSCoreException {
		if (!superClasses.remove(superClass)) {
			throw new MDSCoreException("Fehler: MDSClass#removeSuperClass()");
		}
	}

	/**
	 * @see MDSClass#addSubClass(MDSClass)
	 */
	public void addSubClass(MDSClass subClass) throws MDSCoreException {
		if (!subClasses.add(subClass)) {
			throw new MDSCoreException("Fehler: MDSClass#addSubClass()");
		}
	}

	/**
	 * @see MDSClass#removeSubClass(MDSClass)
	 */
	public void removeSubClass(MDSClass subClass) throws MDSCoreException {
		if (!subClasses.remove(subClass)) {
			throw new MDSCoreException("Fehler: MDSClass#removeSubClass()");
		}
	}

	/**
	 * @see MDSClass#getSuperClasses()
	 */
	public ArrayList getSuperClasses() throws MDSCoreException {
		return superClasses;
	}

	/**
	 * @see MDSClass#setSuperClasses(ArrayList)
	 */
	public void setSuperClasses(ArrayList superClasses) throws MDSCoreException {
		this.superClasses = superClasses;
	}

	/**
	 * @see MDSClass#getSubClasses()
	 */
	public ArrayList getSubClasses() throws MDSCoreException {
		return subClasses;
	}

	/**
	 * @see MDSClass#setSubClasses(ArrayList)
	 */
	public void setSubClasses(ArrayList subClasses) throws MDSCoreException {
		this.subClasses = subClasses;
	}
}