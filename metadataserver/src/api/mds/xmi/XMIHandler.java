package api.mds.xmi;

import java.util.ArrayList;

import mds.xmi.XMIHandlerException;

import api.mds.core.MDSElement;
import api.mds.core.MDSModel;

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
	 * erzeugt XMI-Repräsentation eines MDSModel
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public void generateXMI(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt oder aktualsiert die zum MDSModel gehörende DTD
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public void generateDTD(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * erzeugt oder aktualsiert das zum MDSModel gehörende Schema
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public void generateSchema(MDSModel mdsModel) throws XMIHandlerException;

	/**
	 * führt eine Abfrage auf dem MDSModel aus
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public ArrayList query(MDSModel mdsModel, String query)
		throws XMIHandlerException;
}