package mds.core;

import java.util.*;

public interface MDSRepository extends MDSObject 
{
	/**
	 * legt MDSRepository auf Server ab
	 *
	 * @return Pfad zum MDSRepository
	 */
   	public String insert()
	throws MDSCoreException;
	
	/**
	 * entfernt MDSRepository und alle beinhaltenden MDSModels
	 *
	 * @return Pfad zum MDSRepository
	 */
   	public void delete()
	throws MDSCoreException;
	
	/**
	 * umbenennen des MDSRepository
	 *
	 * @param label neuer Name
	 */
   	public void rename(String label)
	throws MDSCoreException;
	
	/**
	 * führt eine Abfrage auf MDSRepository und seinen MDSModels aus
	 *
	 * @param query der Querystring
	 * @return das Ergebnis der Abfrage
	 */
   	public ArrayList query(String query)
	throws MDSCoreException;	
}

