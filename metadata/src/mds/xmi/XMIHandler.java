package mds.xmi;

import java.util.*;
import mds.core.*;

/**
 * Stellt Methoden zum Erzeugen und Manipulieren 
 * von MDSModels mittels XMI zur Verf�gung
 * 
 * wird von MDSModel implementiert
 * 
 * @author Thomas Chille
 */
public interface XMIHandler 
{
	/**
	 * erzeugt ein MDSModel, inklusive seiner XMI-Repr�sentation
	 *
	 * @param label die ID wird atomatisch vergeben
	 * @param metamodel wenn nicht null wird neues MDSModel eine Instance dieses MDSModels
	 * @param type f�rs Mapping(java, xmi2.0, text, ...)
	 * @return des neu entstandene MDSModel
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public MDSModel createModel(String label, MDSModel metamodel, String type)
   	throws XMIHandlerException;
   	
	/**
	 * f�gt ein MDSElement in MDSModel ein
	 *
	 * @param id an dieser spezifierten Stelle 
	 * @param mdsElement das MDSElement (Klasse, Assoziation,...)
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public void insert(String id, MDSElement mdsElement)
   	throws XMIHandlerException;
   	
	/**
	 * entfernt ein MDSElement aus dem MDSModel
	 *
	 * @param id an dieser spezifierten Stelle 
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public void remove(MDSModel mdsModel, String id)
   	throws XMIHandlerException;
	
	/**
	 * erzeugt oder aktualsiert die zum MDSModel geh�rende DTD
	 *
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public void generateDTD()
   	throws XMIHandlerException;
	
	/**
	 * erzeugt oder aktualsiert das zum MDSModel geh�rende Schema
	 *
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public void generateSchema()
   	throws XMIHandlerException;
   	
	/**
	 * f�hrt eine Abfrage auf dem MDSModel aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws XMIHandlerException im Fehlerfall
	 */
   	public ArrayList query(String query)
   	throws XMIHandlerException;
}

