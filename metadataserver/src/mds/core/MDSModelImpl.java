package mds.core;

import java.util.ArrayList;
import java.util.Iterator;

import mds.persistence.PersistenceHandlerException;
import mds.xmi.XMIHandlerException;
import mds.xmi.XMIHandlerImpl;

import api.mds.core.MDSElement;
import api.mds.core.MDSFile;
import api.mds.core.MDSHref;
import api.mds.core.MDSModel;
import api.mds.core.MDSRepository;
import api.mds.persistence.PersistenceHandler;
import api.mds.xmi.XMIHandler;

/**
 * @see MDSModel
 * 
 * @author Thomas Chille
 */
public class MDSModelImpl extends MDSObjectImpl implements MDSModel {
	
	private static int counter = 0;
	
	public MDSModelImpl() {
		this.setId("model_" + counter++);
	}
	
	/**
	 * MDSElement-Repräsenation des Models
	 */
	private ArrayList elements = new ArrayList();

	/**
	 * wenn nicht null dann ist Model Instance dieses metamodel
	 */
	private MDSModel metamodel = null;

	/**
	 * Zusätzliche Dateien, welche das model formen (Quelltexte)
	 */
	private ArrayList additionalFiles = new ArrayList();

	/**
	 * zum manipulieren des models
	 */
	private XMIHandler xmiHandler = new XMIHandlerImpl();

	/**
	 * @see MDSModel#renameModel(String)
	 */
	public void renameModel(String label) throws MDSCoreException {
		this.setLabel(label);
	}

	/**
	 * @see MDSModel#getModelVersions(String)
	 */
	public ArrayList getModelVersions() throws MDSCoreException {
		try {
			return this.getPersistenceHandler().getModelVersions(this);
		} catch (PersistenceHandlerException e) {
			throw new MDSCoreException("Fehler: MDSModel#getModelVersions");
		}
	}

	/**
	 * @see MDSModel#restoreModel(String)
	 */
	public void restoreModel(String version) throws MDSCoreException {
	}

	/**
	 * @see MDSModel#insertElement(MDSElement)
	 */
	public String insertElement(MDSElement mdsElement)
		throws MDSCoreException {

		if (elements.add(mdsElement)) {
			return mdsElement.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSModel#insertElement()");
		}
	}

	/**
	 * @see MDSModel#removeElement(MDSHref)
	 */
	public void removeElement(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		MDSElement mdsElement;
		try {
			mdsElement = getById(href);
		} catch (MDSCoreException e) {
			throw new MDSCoreException("Fehler: MDSModels#removeElement()");
		}
		if (!elements.remove(mdsElement)) {
			throw new MDSCoreException("Fehler: MDSModel#removeElement()");
		}
	}

	/**
	 * @see MDSModel#validateModel(int)
	 */
	public ArrayList validateModel(int validateType) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#query(String)
	 */
	public ArrayList query(String query) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#getXmiFile()
	 */
	public MDSFile getXmiFile() {
		try {
			return xmiHandler.mapMDS2XMI(this);
		} catch (XMIHandlerException e) {
		}
		return null;
	}

	/**
	 * @see MDSModel#getDtdFile()
	 */
	public MDSFile getDtdFile() {
		try {
			return xmiHandler.mapMDS2DTD(this);
		} catch (XMIHandlerException e) {
		}
		return null;
	}

	/**
	 * @see MDSModel#getSchemaFile()
	 */
	public MDSFile getSchemaFile() {
		try {
			return xmiHandler.mapMDS2Schema(this);
		} catch (XMIHandlerException e) {
		}
		return null;
	}

	/**
	 * @see MDSModel#getMetamodel()
	 */
	public MDSModel getMetamodel() {
		return metamodel;
	}

	/**
	 * @see MDSModel#setMetamodel(MDSModel)
	 */
	public void setMetamodel(MDSModel metamodel) {
		this.metamodel = metamodel;
	}

	/**
	 * @see MDSModel#getAdditionalFiles()
	 */
	public ArrayList getAdditionalFiles() {
		return additionalFiles;
	}

	/**
	 * @see MDSModel#setAdditionalFiles(ArrayList)
	 */
	public void setAdditionalFiles(ArrayList additionalFiles) {
		this.additionalFiles = additionalFiles;
	}

	/**
	 * @see MDSModel#getXmiHandler()
	 */
	public XMIHandler getXmiHandler() {
		return xmiHandler;
	}

	/**
	 * @see MDSModel#setXmiHandler(XMIHandler)
	 */
	public void setXmiHandler(XMIHandler xmiHandler) {
		this.xmiHandler = xmiHandler;
	}

	/**
	 * @see MDSModel#getElements()
	 */
	public ArrayList getElements() {
		return elements;
	}

	/**
	 * @see MDSModel#setElements(ArrayList)
	 */
	public void setElements(ArrayList elements) {
		this.elements = elements;
	}

	private MDSElement getById(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException {

		String id = href.getElementId();
		MDSElement element;
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element.getId().equals(id)) {
				return element;
			}
		}
		throw new MDSCoreException("Fehler: MDSModels#getById()");
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retString = "\t\tmodel:" + this.getId() + "\n";
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			retString += ((MDSElement)i.next()).toString();
		}
		return retString;
	}

}