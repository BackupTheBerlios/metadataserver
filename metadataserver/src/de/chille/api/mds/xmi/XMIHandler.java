package de.chille.api.mds.xmi;

import java.util.ArrayList;

import de.chille.mds.xmi.XMIHandlerException;

import de.chille.api.mds.core.MDSElement;
import de.chille.api.mds.core.MDSFile;
import de.chille.api.mds.core.MDSModel;

/**
 * Stellt Methoden zum Erzeugen und Manipulieren 
 * von MDSModels mittels XMI zur Verfügung
 * 
 * wird von MDSModel benutzt
 * 
 * @author Thomas Chille
 */
public interface XMIHandler {

	/**
	 * erzeugt UML-XMI-Repräsentation eines MDSModel
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2UML(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt XMI-Repräsentation eines MDSModel
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @param dtdPath wenn not null pfad zur dtd, doctype wird erzeugt
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2XMI(MDSModel mdsModel, String dtdPath) throws XMIHandlerException;

	/**
	 * erzeugt die zum MDSModel gehörende DTD
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2DTD(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt das zum MDSModel gehörende Schema
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2Schema(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt aus xmi ein de.chille.mds-model
	 *
	 * @param mdsFile das betreffende MDSFile
	 * @return MDSModel
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSModel mapUML2MDS(MDSFile mdsFile) throws XMIHandlerException;

}