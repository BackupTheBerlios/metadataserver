package mds.xmi;

import java.util.*;
import mds.core.*;

/**
 * Stellt Methoden zum Erzeugen und Manipulieren 
 * von MDSModels mittels XMI zur Verfügung
 * 
 * wird von MDSModel implementiert
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
	 * fügt ein MDSElement in MDSModel ein
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @param id an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public void insertElement(MDSModel mdsModel, String id, MDSElement mdsElement)
		throws XMIHandlerException;

	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param mdsModel das betreffende MDSModel
	 * @param id an dieser spezifierten Stelle 
	 * @throws XMIHandlerException im Fehlerfall
	 */
	public void removeElement(MDSModel mdsModel, String id)
		throws XMIHandlerException;

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