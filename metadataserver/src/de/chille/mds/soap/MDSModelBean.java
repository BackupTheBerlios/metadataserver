package de.chille.mds.soap;

import java.util.Vector;

import org.apache.soap.util.Bean;

public class MDSModelBean extends MDSObjectBean {

	/**
	 * Zusätzliche Dateien, welche das model formen (Quelltexte)
	 */
	private Vector additionalFiles = new Vector();

	private int counter = 0;
	
	/**
	 * MDSElement-Repräsenation des Models
	 */
	private Vector elements = new Vector();

	/**
	 * wenn nicht null dann ist Model Instance dieses metamodel
	 */
	private String metamodelHref = null;
	
	private String metamodelName = null;
	/**
	 * @see MDSModel#getMetamodel()
	 */
	public String getMetamodelHref() {
		return metamodelHref;
	}

	/**
	 * @see MDSModel#setMetamodel(MDSModelBean)
	 */
	public void setMetamodelHref(String metamodel) {
		this.metamodelHref = metamodel;
	}

	/**
	 * @see MDSModel#getAdditionalFiles()
	 */
	public Vector getAdditionalFiles() {
		return additionalFiles;
	}

	/**
	 * @see MDSModel#setAdditionalFiles(Vector)
	 */
	public void setAdditionalFiles(Vector additionalFiles) {
		this.additionalFiles = additionalFiles;
	}

	/**
	 * @see MDSModel#getElements()
	 */
	public Vector getElements() {
		return elements;
	}

	/**
	 * @see MDSModel#setElements(Vector)
	 */
	public void setElements(Vector elements) {
		this.elements = elements;
	}

	/**
	 * @see de.chille.api.mds.core.MDSModel#getCounter()
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @see de.chille.api.mds.core.MDSModel#setCounter(int)
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
	/**
	 * Returns the metamodelname.
	 * @return String
	 */
	public String getMetamodelName() {
		return metamodelName;
	}

	/**
	 * Sets the metamodelname.
	 * @param metamodelname The metamodelname to set
	 */
	public void setMetamodelName(String metamodelname) {
		this.metamodelName = metamodelname;
	}

}
