package api.mds.core;

import java.util.ArrayList;

import api.mds.persistence.PersistenceHandler;

import mds.core.MDSCoreException;
import mds.core.MDSHrefFormatException;

/**
 * beinhaltet die MDSModels
 * 
 * @author Thomas Chille
 */
public interface MDSRepository extends MDSObject {

	/**
	 * legt MDSRepository auf Server ab
	 *
	 * @return id des MDSRepository
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insert() throws MDSCoreException;

	/**
	 * entfernt MDSRepository und alle beinhaltenden MDSModels
	 * 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void delete() throws MDSCoreException;

	/**
	 * f�hrt eine Abfrage auf MDSRepository und seinen MDSModels aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList query(String query) throws MDSCoreException;

	/**
	 * f�gt in MDSModel in MDSRepository ein
	 *
	 * @param mdsModel das einzuf�gende Model
	 * @return id des neuen MDSModel
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertModel(MDSModel mdsModel)
		throws MDSCoreException;

	/**
	 * l�scht MDSModel
	 * 
	 * @param href Pfad des zu l�schenden Model
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeModel(MDSHref href) throws MDSCoreException;

	/**
	 * verschiebt MDSModel in ein anderes MDSRepository
	 *
	 * @param from von spezifierten Stelle 
	 * @param to an dieser spezifierten Stelle 
	 * @return id des neuen MDSModel 
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String moveModel(MDSHref from, MDSHref to)
		throws MDSCoreException;

	/**
	 * kopiert MDSModel
	 *
	 * @param from von spezifizierten Stelle 
	 * @param to an diese spezifizierten Stelle 
	 * @param label des neuen MDSModels, id wird automatisch vergeben
	 * @return id der Kopie des MDSModel
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String copyModel(MDSHref from, MDSHref to, String label)
		throws MDSCoreException;

	public MDSModel getModelByHref(MDSHref href)
		throws MDSCoreException, MDSHrefFormatException;

}