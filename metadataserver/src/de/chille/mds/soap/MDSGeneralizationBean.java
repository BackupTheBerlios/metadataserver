package de.chille.mds.soap;

public class MDSGeneralizationBean extends MDSObjectBean {
	
	/**
	 * die vererbende Klasse
	 */
	private MDSClassBean superClass;

	/**
	 * die erbende Klasse
	 */
	private MDSClassBean subClass;

	/**
	 * Constructor for MDSObjectBean.
	 */
	public MDSGeneralizationBean() {
		super();
	}
	
	/**
	 * Returns the subClass.
	 * @return MDSClass
	 */
	public MDSClassBean getSubClass() {
		return subClass;
	}

	/**
	 * Returns the superClass.
	 * @return MDSClassBean
	 */
	public MDSClassBean getSuperClass() {
		return superClass;
	}

	/**
	 * Sets the subClass.
	 * @param subClass The subClass to set
	 */
	public void setSubClass(MDSClassBean subClass) {
		this.subClass = subClass;
	}

	/**
	 * Sets the superClass.
	 * @param superClass The superClass to set
	 */
	public void setSuperClass(MDSClassBean superClass) {
		this.superClass = superClass;
	}

}
