package de.chille.mds.core;

import de.chille.api.mds.core.MDSClass;
import de.chille.api.mds.core.MDSHref;
import de.chille.mds.soap.MDSClassBean;

/**
 * @see MDSClass
 * 
 * @author Thomas Chille
 */
public class MDSClassImpl extends MDSElementImpl implements MDSClass {

	/**
	 * Constructor for MDSClassImpl.
	 */
	public MDSClassImpl() {
		super("class");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tclass:" + this.getId() + " - " + this.getLabel() + "\n";

	}

	public MDSClassBean exportBean() {
		MDSClassBean bean = new MDSClassBean();
		bean.setHref(this.getHref().getHrefString());
		bean.setId(this.getId());
		bean.setLabel(this.getLabel());
		return bean;
	}

	/**
	 * @see de.chille.api.mds.core.MDSClass#importBean(MDSClassBean)
	 */
	public void importBean(MDSClassBean bean) {
		try {
			if (bean.getHref() != null)
				this.setHref(new MDSHrefImpl(bean.getHref()));
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		this.setId(bean.getId());
		this.setLabel(bean.getLabel());
	}

}