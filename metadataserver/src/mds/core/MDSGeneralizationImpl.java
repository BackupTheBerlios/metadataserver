package mds.core;

import api.mds.core.MDSClass;
import api.mds.core.MDSGeneralization;

/**
 * @author Thomas Chille
 *
 * @see api.mds.core.MDSGeneralization
 */
public class MDSGeneralizationImpl
	extends MDSElementImpl
	implements MDSGeneralization {
	
	/**
	 * die vererbende Klasse
	 */
	private MDSClass superClass;
	
	/**
	 * die erbende Klasse
	 */
	private MDSClass subClass;
	
	/**
	 * Constructor for MDSGeneralizationImpl.
	 */
	public MDSGeneralizationImpl() {
		super("generalization");
	}

	/**
	 * @see api.mds.core.MDSGeneralization#getSubClass()
	 */
	public MDSClass getSubClass() {
		return subClass;
	}

	/**
	 * @see api.mds.core.MDSGeneralization#getSuperClass()
	 */
	public MDSClass getSuperClass() {
		return superClass;
	}

	/**
	 * @see api.mds.core.MDSGeneralization#setSubClass(MDSClass)
	 */
	public void setSubClass(MDSClass subClass) {
		this.subClass = subClass;
	}

	/**
	 * @see api.mds.core.MDSGeneralization#setSuperClass(MDSClass)
	 */
	public void setSuperClass(MDSClass superClass) {
		this.superClass = superClass;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tgeneralization:" + this.getId() + "\n";

	}

}
