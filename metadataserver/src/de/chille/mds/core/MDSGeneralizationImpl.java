package de.chille.mds.core;

import de.chille.api.mds.core.MDSClass;
import de.chille.api.mds.core.MDSGeneralization;
import de.chille.api.mds.core.MDSHref;

/**
 * @author Thomas Chille
 *
 * @see de.chille.api.de.chille.de.chille.mds.core.MDSGeneralization
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
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSGeneralization#getSubClass()
	 */
	public MDSClass getSubClass() {
		return subClass;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSGeneralization#getSuperClass()
	 */
	public MDSClass getSuperClass() {
		return superClass;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSGeneralization#setSubClass(MDSClass)
	 */
	public void setSubClass(MDSClass subClass) {
		this.subClass = subClass;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSGeneralization#setSuperClass(MDSClass)
	 */
	public void setSuperClass(MDSClass superClass) {
		this.superClass = superClass;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tgeneralization:"
			+ this.getId()
			+ " - "
			+ this.getLabel()
			+ "\n";

	}

}
