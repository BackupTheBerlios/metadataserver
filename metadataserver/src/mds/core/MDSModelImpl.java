package mds.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import mds.MDSGlobals;
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
public class MDSModelImpl extends MDSPersistentObjectImpl implements MDSModel {

	private int counter = 0;

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
		// ohne metamodel gibt es nichts zu validieren
		if (getMetamodel() != null) {
			try {
				// dtd des metamodels erzeugen und temporär im filesystem ablegen
				MDSFile dtdFile = xmiHandler.mapMDS2DTD(getMetamodel());
				dtdFile.save(MDSGlobals.TEMP_PATH + "validate.dtd");
				// xmi des models mit doctype-element erzeugen
				MDSFile xmiFile =
					xmiHandler.mapMDS2XMI(
						this,
						MDSGlobals.TEMP_PATH + "validate.dtd");
				// debug
				System.out.println(xmiFile.getContent());

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
			return null;
		} else {
			return null;
		}
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
			out.println("Warning: " + getParseExceptionInfo(spe));
		}
		public void error(SAXParseException spe) throws SAXException {
			String message = "Error: " + getParseExceptionInfo(spe);
			System.out.println(message);
			//throw new SAXException(message);
		}
		public void fatalError(SAXParseException spe) throws SAXException {
			String message = "Fatal Error: " + getParseExceptionInfo(spe);
			//throw new SAXException(message);
		}
	}
}