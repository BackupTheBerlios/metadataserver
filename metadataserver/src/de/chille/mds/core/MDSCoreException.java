package de.chille.mds.core;

/**
 * wird im Fehlerfall im MDSCore-Implementierungen
 * geworfen
 * 
 * @author Thomas Chille
 */
public class MDSCoreException extends Exception {
	
	/**
	 * ohne Nachricht
	 */
	public MDSCoreException() {
	}

	/**
	 * mit Nachricht
	 * 
	 * @param msg die Nachricht
	 */
	public MDSCoreException(String msg) {
		super(msg);
	}
}