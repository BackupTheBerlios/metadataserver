package de.chille.mds.core;

import org.apache.soap.util.Bean;

import de.chille.api.mds.core.*;
import de.chille.api.mds.core.MDSAssociationEnd;
import de.chille.api.mds.core.MDSClass;
import de.chille.mds.soap.MDSAssociationEndBean;

/**
 * @see MDSAssociationEnd
 * 
 * @author Thomas Chille
 */
public class MDSAssociationEndImpl implements MDSAssociationEnd {

	/**
	 * die multiplicity dieses AssociationEndes
	 */
	private String multiplicity = null;

	/**
	 * type der Aggregation
	 */
	private int aggregation = NONE_AGGREGATION;

	/**
	 * die Klasse dieses AssociationEndes
	 */
	private MDSClass mdsClass = null;

	/**
	 * Constructor for MDSAssociationEndImpl.
	 */
	public MDSAssociationEndImpl(int aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * Constructor for MDSAssociationEndImpl.
	 */
	public MDSAssociationEndImpl() {
	}

	/**
	 * @see MDSAssociationEnd#getMultiplicity()
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	/**
	 * @see MDSAssociationEnd#setMultiplicity(String)
	 */
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}

	/**
	 * @see MDSAssociationEnd#getMdsClass()
	 */
	public MDSClass getMdsClass() {
		return mdsClass;
	}

	/**
	 * @see MDSAssociationEnd#setMdsClass(MDSClass)
	 */
	public void setMdsClass(MDSClass mdsClass) {
		this.mdsClass = mdsClass;
	}

	/**
	 * @see MDSAssociationEnd#getAggregation()
	 */
	public int getAggregation() {
		return aggregation;
	}

	/**
	 * @see MDSAssociationEnd#setAggregation(int)
	 */
	public void setAggregation(int aggregation) {
		this.aggregation = aggregation;
	}

	public MDSAssociationEndBean exportBean() {
		MDSAssociationEndBean bean = new MDSAssociationEndBean();
		bean.setAggregation(this.getAggregation());
		bean.setMultiplicity(this.getMultiplicity());
		bean.setMdsClass(this.getMdsClass().exportBean());
		return bean;
	}

	public void importBean(MDSAssociationEndBean bean) {
		this.setAggregation(bean.getAggregation());
		this.setMultiplicity(bean.getMultiplicity());
		MetaDataServer server = MetaDataServerImpl.getInstance();
		try {
			MDSHref href = new MDSHrefImpl(bean.getMdsClass().getHref());
			this.setMdsClass(
				(MDSClassImpl) server
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