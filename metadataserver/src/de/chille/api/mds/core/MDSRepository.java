package de.chille.api.mds.core;

import java.util.ArrayList;
import java.util.Vector;

import de.chille.mds.core.MDSCoreException;
import de.chille.mds.core.MDSHrefFormatException;
import de.chille.mds.persistence.PersistenceHandlerException;
import de.chille.mds.soap.MDSRepositoryBean;

/**
 * beinhaltet die MDSModels
 * 
 * @author Thomas Chille
 */
public interface MDSRepository extends MDSPersistentObject {

	/**
	 * führt eine Abfrage auf MDSRepository und seinen MDSModels aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 * @throws MDSCoreException im Fehlerfall
	 */
	public ArrayList query(String query) throws MDSCoreException;

	/**
	 * fügt in MDSModel in MDSRepository ein
	 *
	 * @param mdsModel das einzufügende Model
	 * @return id des neuen MDSModel
	 * @throws MDSCoreException im Fehlerfall
	 */
	public String insertModel(MDSModel mdsModel)
		throws MDSHrefFormatException, MDSCoreException;

	public Vector removeModel(MDSHref href, boolean confirm) throws MDSCoreException;

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
	
	/**
	 * Returns the models.
	 * @return ArrayList
	 */
	public ArrayList getModels();

	/**
	 * Sets the models.
	 * @param models The models to set
	 */
	public void setModels(ArrayList models);
	
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter();

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int counter);
	
	public void update() throws PersistenceHandlerException;
	
	public MDSRepositoryBean exportBean();
	
	public void importBean(MDSRepositoryBean bean);
}