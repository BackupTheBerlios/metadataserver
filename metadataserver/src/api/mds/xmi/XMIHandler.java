package api.mds.xmi;

import java.util.ArrayList;

import mds.xmi.XMIHandlerException;

import api.mds.core.MDSElement;
import api.mds.core.MDSFile;
import api.mds.core.MDSModel;

/**
 * Stellt Methoden zum Erzeugen und Manipulieren 
 * von MDSModels mittels XMI zur Verf�gung
 * 
 * wird von MDSModel benutzt
 * 
 * @author Thomas Chille
 */
public interface XMIHandler {

	/**
	 * erzeugt XMI-Repr�sentation eines MDSModel
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2XMI(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt oder aktualsiert die zum MDSModel geh�rende DTD
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2DTD(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt oder aktualsiert das zum MDSModel geh�rende Schema
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @return MDSFile
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public MDSFile mapMDS2Schema(MDSModel mdsModel) throws XMIHandlerException;

}