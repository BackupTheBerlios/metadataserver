package de.chille.mds.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.soap.util.Bean;

import de.chille.api.mds.core.*;
import de.chille.api.mds.core.MDSAssociationEnd;
import de.chille.api.mds.core.MDSAssociation;
import de.chille.api.mds.core.MDSHref;
import de.chille.mds.soap.*;
import de.chille.mds.soap.MDSAssociationBean;
import de.chille.mds.soap.MDSObjectBean;

/**
 * @see MDSAssociation
 * 
 * @author Thomas Chille
 */
public class MDSAssociationImpl
	extends MDSElementImpl
	implements MDSAssociation {

	/**
	 * die beiden associationEnds einer association
	 */
	private ArrayList associationEnds = new ArrayList();

	/**
	 * Constructor for MDSAssociationImpl.
	 */
	public MDSAssociationImpl() {
		super("association");
	}

	/**
	 * @see MDSAssociationImpl#addAssociationEnd(MDSAssociationEnd)
	 */
	public void addAssociationEnd(MDSAssociationEnd associationEnd)
		throws MDSCoreException {

		if (associationEnds.size() == 2) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		} else if (!associationEnds.add(associationEnd)) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#addAssociationEnd()");
		}
	}

	/**
	 * @see MDSAssociationImpl#removeAssociationEnd(MDSAssociationEnd)
	 */
	public void removeAssociationEnd(MDSAssociationEnd associationEnd)
		throws MDSCoreException {

		if (!associationEnds.remove(associationEnd)) {
			throw new MDSCoreException("Fehler: MDSAssociationImpl#removeAssociationEnd()");
		}
	}

	/**
	 * @see MDSAssociationImpl#getAssociationEnds()
	 */
	public ArrayList getAssociationEnds() {
		return associationEnds;
	}

	/**
	 * @see MDSAssociationImpl#setAssociationEnds(ArrayList)
	 */
	public void setAssociationEnds(ArrayList associationEnds) {
		this.associationEnds = associationEnds;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tassociation:"
			+ this.getId()
			+ " - "
			+ this.getLabel()
			+ "\n";

	}

	public MDSAssociationBean exportBean() {
		MDSAssociationBean bean = new MDSAssociationBean();
		bean.setHref(this.getHref().getHrefString());
		bean.setId(this.getId());
		bean.setLabel(this.getLabel());
		Vector ends = new Vector();
		Iterator i = this.getAssociationEnds().iterator();
		while (i.hasNext()) {
			ends.add(((MDSAssociationEndImpl) i.next()).exportBean());
		}
		bean.setAssociationEnds(ends);
		return bean;
	}

	/**
	 * @see de.chille.api.mds.core.MDSAssociation#importBean(MDSAssociationBean)
	 */
	public void importBean(MDSAssociationBean bean) {
		try {
			if (bean.getHref() != null)
				this.setHref(new MDSHrefImpl(bean.getHref()));

		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		this.setId(bean.getId());
		this.setLabel(bean.getLabel());
		ArrayList ends = new ArrayList();
		MDSAssociationEnd end1 = new MDSAssociationEndImpl();
		MDSAssociationEnd end2 = new MDSAssociationEndImpl();
		end1.importBean(
			(MDSAssociationEndBean) bean.getAssociationEnds().get(0));
		ends.add(end1);
		end2.importBean(
			(MDSAssociationEndBean) bean.getAssociationEnds().get(1));
		ends.add(end2);
		this.setAssociationEnds(ends);
	}

}