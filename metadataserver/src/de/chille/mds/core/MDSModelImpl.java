package de.chille.mds.core;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

import de.chille.api.mds.core.*;
import de.chille.api.mds.xmi.XMIHandler;
import de.chille.mds.MDSGlobals;
import de.chille.mds.Util;
import de.chille.mds.persistence.PersistenceHandlerException;
import de.chille.mds.soap.*;
import de.chille.mds.xmi.XMIHandlerException;
import de.chille.mds.xmi.XMIHandlerImpl;

/**
 * @see MDSModel
 * 
 * @author Thomas Chille
 */
public class MDSModelImpl extends MDSPersistentObjectImpl implements MDSModel {

	private int counter = 0;

	private boolean unsavedChanges = false;

	private Vector messages;

	public MDSModelImpl() {
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
		touch();
	}

	/**
	 * @see MDSModel#insertElement(MDSElement)
	 */
	public String insertElement(MDSElement mdsElement)
		throws MDSCoreException, MDSHrefFormatException {

		if (elements.add(mdsElement)) {
			if (mdsElement.getId() == null) {
				mdsElement.setId(this.getId() + "_" + this.counter++);
			}
			mdsElement.setHref(
				new MDSHrefImpl(
					this.getHref().getModelHref()
						+ "/"
						+ mdsElement.getPrefix()
						+ mdsElement.getId()));
			touch();
			return mdsElement.getId();
		} else {
			throw new MDSCoreException("Fehler: MDSModel#insertElement()");
		}
	}

	/**
	 * @see MDSModel#removeElement(MDSHref)
	 */
	public Vector removeElement(MDSHref href, boolean confirm)
		throws MDSCoreException, MDSHrefFormatException {

		Vector result = new Vector();
		Vector collect = new Vector();
		MDSElement mdsElement;
		try {
			mdsElement = getElementById(href);
			if (mdsElement instanceof MDSClassImpl) {
				// mdsElement-beziehungen löschen 
				Iterator iter = getElements().iterator();
				while (iter.hasNext()) {
					MDSElementImpl element = (MDSElementImpl) iter.next();
					if (element instanceof MDSAssociationImpl) {
						if (
							(
								(MDSAssociationEndImpl)
									((MDSAssociationImpl) element)
							.getAssociationEnds()
							.get(0))
							.getMdsClass()
							.equals(mdsElement)
							|| (
								(MDSAssociationEndImpl)
									((MDSAssociationImpl) element)
								.getAssociationEnds()
								.get(1))
								.getMdsClass()
								.equals(mdsElement)) {
							collect.add(element);
						}
					} else if (element instanceof MDSGeneralizationImpl) {
						if (((MDSGeneralizationImpl) element)
							.getSuperClass()
							.equals(mdsElement)
							|| ((MDSGeneralizationImpl) element)
								.getSubClass()
								.equals(
								mdsElement)) {
							collect.add(element);
						}
					}
				}
			}
		} catch (MDSCoreException e) {
			throw new MDSCoreException("Fehler: MDSModels#removeElement()");
		}
		if (!confirm) {
			elements.remove(mdsElement);
			touch();
		}
		Iterator iter = collect.iterator();
		while (iter.hasNext()) {
			if (confirm) {
				result.add(
					((MDSElementImpl) iter.next()).getHref().getHrefString());
			} else {
				elements.remove(iter.next());
			}
		}
		return result;
	}

	/**
	 * @see MDSModel#validateModel(int)
	 */
	public Vector validateModel(int validateType) throws MDSCoreException {

		messages = new Vector();
		// ohne metamodel gibt es nichts zu validieren
		if (getMetamodel() != null) {
			// Vollständigkeitstest
			Iterator i = getMetamodel().getElements().iterator();
			Iterator j = null;
			boolean ok = false;
			while (i.hasNext()) {
				MDSElement element = (MDSElementImpl) i.next();
				j = elements.iterator();
				ok = false;
				while (j.hasNext()) {
					if (((MDSElementImpl) j.next())
						.getLabel()
						.equals(element.getLabel())) {
						ok = true;
						break;
					}
				}
				if (!ok) {
					messages.add(
						"Missing Element: "
							+ element.getPrefix().replaceFirst("_", "::")
							+ element.getLabel());
				}
			}
			try {
				// dtd des metamodels erzeugen und temporär im filesystem ablegen
				MDSFile dtdFile = xmiHandler.mapMDS2DTD(getMetamodel());
				dtdFile.save(MDSGlobals.TEMP_PATH + "validate.dtd");
				// xmi des models mit doctype-element erzeugen
				MDSFile xmiFile =
					xmiHandler.mapMDS2XMI(
						this,
						"file://" + MDSGlobals.TEMP_PATH + "validate.dtd");
				// debug
				//System.out.println(xmiFile.getContent());

				// mittels SAXParser validieren
				SAXParserFactory spf = SAXParserFactory.newInstance();
				spf.setValidating(true);
				XMLReader xmlReader = null;
				SAXParser saxParser = spf.newSAXParser();
				xmlReader = saxParser.getXMLReader();
				//xmlReader.setContentHandler(new validate());
				xmlReader.setErrorHandler(new MyErrorHandler(System.err));
				xmlReader.parse(
					new InputSource(
						new ByteArrayInputStream(
							xmiFile.getContent().getBytes())));

			} catch (XMIHandlerException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return messages;
	}

	/**
	 * @see MDSModel#query(String)
	 */
	public ArrayList query(String query) throws MDSCoreException {
		return null;
	}

	/**
	 * @see MDSModel#getUmlFile()
	 */
	public MDSFile getUmlFile() {
		try {
			return xmiHandler.mapMDS2UML(this);
		} catch (XMIHandlerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @see MDSModel#getXmiFile()
	 */
	public MDSFile getXmiFile() {
		try {
			return xmiHandler.mapMDS2XMI(this, null);
		} catch (XMIHandlerException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
		touch();
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
		touch();
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
		touch();
	}

	public MDSElement getElementById(MDSHref href)
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

	public MDSElement getElementByLabel(String label)
		throws MDSCoreException {

		MDSElement element;
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			element = (MDSElement) i.next();
			if (element.getLabel().equals(label)) {
				return element;
			}
		}
		throw new MDSCoreException("Fehler: MDSModels#getElementByLabel()");
	}

	public void update() throws PersistenceHandlerException {
		MDSModel model = (MDSModelImpl) load(null);
		this.setLabel(model.getLabel());
		this.setAdditionalFiles(model.getAdditionalFiles());
		this.setElements(model.getElements());
		this.setMetamodel(model.getMetamodel());
		untouch();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retString =
			"\t\tmodel:" + this.getId() + " - " + this.getLabel() + "\n";
		Iterator i = elements.iterator();
		while (i.hasNext()) {
			retString += ((MDSElement) i.next()).toString();
		}
		return retString;
	}

	// Error handler to report errors and warnings
	private class MyErrorHandler implements ErrorHandler {
		/** Error handler output goes here */
		private PrintStream out;
		MyErrorHandler(PrintStream out) {
			this.out = out;
		}
		/**
		 * Returns a string describing parse exception details
		 */
		private String getParseExceptionInfo(SAXParseException spe) {
			String systemId = spe.getSystemId();
			if (systemId == null) {
				systemId = "null";
			}
			String info =
				"URI="
					+ systemId
					+ " Line="
					+ spe.getLineNumber()
					+ ": "
					+ spe.getMessage();
			return info;
		}
		// The following methods are standard SAX ErrorHandler methods.
		// See SAX documentation for more info.
		public void warning(SAXParseException spe) throws SAXException {
			messages.add("Warning: " + getParseExceptionInfo(spe));
		}
		public void error(SAXParseException spe) throws SAXException {
			messages.add("Error: " + getParseExceptionInfo(spe));
		}
		public void fatalError(SAXParseException spe) throws SAXException {
			messages.add("Fatal Error: " + getParseExceptionInfo(spe));
		}
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSModel#getCounter()
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mds.core.MDSModel#setCounter(int)
	 */
	public void setCounter(int counter) {
		this.counter = counter;
		touch();
	}

	public MDSModelBean exportBean() {
		MDSModelBean bean = new MDSModelBean();
		bean.setHref(this.getHref().getHrefString());
		bean.setId(this.getId());
		bean.setLabel(this.getLabel());
		bean.setCounter(this.getCounter());
		if (this.getMetamodel() != null) {
			bean.setMetamodelHref(
				this.getMetamodel().getHref().getHrefString());
			bean.setMetamodelName(this.getMetamodel().getLabel());
		}
		MDSElement element = null;

		Vector elementBeans = new Vector();
		Iterator i = this.getElements().iterator();
		while (i.hasNext()) {
			element = (MDSElementImpl) i.next();
			if (element.getPrefix().equals("class_")) {
				elementBeans.add(((MDSClassImpl) element).exportBean());
			} else if (element.getPrefix().equals("association_")) {
				elementBeans.add(((MDSAssociationImpl) element).exportBean());
			} else if (element.getPrefix().equals("generalization_")) {
				elementBeans.add(
					((MDSGeneralizationImpl) element).exportBean());
			}
		}
		bean.setElements(elementBeans);
		Vector files = new Vector();
		i = this.getAdditionalFiles().iterator();
		while (i.hasNext()) {
			files.add(((MDSFileImpl) i.next()).exportBean());
		}
		bean.setAdditionalFiles(files);
		return bean;
	}

	public void importBean(MDSModelBean bean) {
		MDSHref href = null;
		MetaDataServer server = MetaDataServerImpl.getInstance();
		ArrayList elements = new ArrayList();
		Iterator i = bean.getElements().iterator();
		try {
			if (bean.getHref() != null)
				this.setHref(new MDSHrefImpl(bean.getHref()));
			if (bean.getId() != null)
				this.setId(bean.getId());
			if (bean.getLabel() != null)
				this.setLabel(bean.getLabel());
			this.setCounter(bean.getCounter());
			if (bean.getMetamodelHref() != null) {
				href = new MDSHrefImpl(bean.getMetamodelHref());
				this.setMetamodel(
					server.getRepositoryByHref(href).getModelByHref(href));
			}
			while (i.hasNext()) {

				href = new MDSHrefImpl(((MDSObjectBean) i.next()).getHref());
				elements.add(
					server.getRepositoryByHref(href).getModelByHref(
						href).getElementById(
						href));
			}
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		} catch (MDSCoreException e) {
			e.printStackTrace();
		}
		this.setElements(elements);
		ArrayList files = new ArrayList();
		MDSFile file = null;
		i = bean.getAdditionalFiles().iterator();
		while (i.hasNext()) {
			file = new MDSFileImpl();
			file.importBean((MDSFileBean) i.next());
			files.add(file);
		}
		this.setAdditionalFiles(files);
	}

	public void touch() {
		unsavedChanges = true;
	}

	public void untouch() {
		unsavedChanges = false;
	}
	/**
	 * Returns the unsavedChanges.
	 * @return boolean
	 */
	public boolean hasUnsavedChanges() {
		return unsavedChanges;
	}

}