package de.chille.mds.core;

import de.chille.api.mds.core.*;
import de.chille.mds.soap.MDSGeneralizationBean;

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

	public MDSGeneralizationBean exportBean() {
		MDSGeneralizationBean bean = new MDSGeneralizationBean();
		bean.setHref(this.getHref().getHrefString());
		bean.setId(this.getId());
		bean.setLabel(this.getLabel());
		bean.setSubClass(this.getSubClass().exportBean());
		bean.setSuperClass(this.getSuperClass().exportBean());
		return bean;
	}

	/**
	 * @see de.chille.api.mds.core.MDSGeneralization#importBean(MDSGeneralizationBean)
	 */
	public void importBean(MDSGeneralizationBean bean) {
		MetaDataServer server = MetaDataServerImpl.getInstance();
		this.setId(bean.getId());
		this.setLabel(bean.getLabel());
		try {
			if (bean.getHref() != null)
				this.setHref(new MDSHrefImpl(bean.getHref()));
			MDSHref href = new MDSHrefImpl(bean.getSuperClass().getHref());
			this.setSuperClass(
				((MDSClassImpl)) server
					.getRepositoryByHref(href)
					.getModelByHref(href)
					.getElementById(href));
			href = new MDSHrefImpl(bean.getSubClass().getHref());
			this.setSubClass(
				((MDSClassImpl)) server
					.getRepositoryByHref(href)
					.getModelByHref(href)
					.getElementById(href));
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		} catch (MDSCoreException e) {
			e.printStackTrace();
		}
	}

}
