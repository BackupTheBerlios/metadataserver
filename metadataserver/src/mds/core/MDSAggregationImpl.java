package mds.core;

import api.mds.core.AssociationEnd;
import api.mds.core.MDSAggregation;

/**
 * Spezialisierung des MDSElements, modelliert eine 
 * Aggregation zwischen MDSClasses
 * 
 * @author Thomas Chille
 */
public class MDSAggregationImpl
	extends MDSElementImpl
	implements MDSAggregation {

	/**
	 * Die beinhaltende Klasse
	 */
	private AssociationEnd containerEnd;

	/**
	 * Die enthaltene Klasse
	 */
	private AssociationEnd containedEnd;

	
	/**
	 * wenn true dann is aggregation eine composition
	 */
	private boolean composition = false;
	
	/**
	 * @see MDSAggregation#getContainedEnd()
	 */
	public AssociationEnd getContainedEnd() {
		return containedEnd;
	}

	/**
	 * @see MDSAggregation#getContainedEnd()
	 */
	public AssociationEnd getContainerEnd() {
		return containerEnd;
	}

	/**
	 * @see MDSAggregation#setContainedEnd(AssociationEnd)
	 */
	public void setContainedEnd(AssociationEnd containedEnd) {
		this.containedEnd = containedEnd;
	}

	/**
	 * @see MDSAggregation#setContainerEnd(AssociationEnd)
	 */
	public void setContainerEnd(AssociationEnd containerEnd) {
		this.containerEnd = containerEnd;
	}

	/**
	 * @see MDSAggregation#isComposition()
	 */
	public boolean isComposition() {
		return composition;
	}

	/**
	 * @see MDSAggregation#setComposition(boolean)
	 */
	public void setComposition(boolean composition) {
		this.composition = composition;
	}

}