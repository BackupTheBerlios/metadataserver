package de.chille.mds.core;

/**
 * wenn Href nicht dem geforderten Format entspricht
 * 
 * @author Thomas Chille
 */
public class MDSHrefFormatException extends Exception {
	
	/**
	 * ohne Nachricht
	 */
	public MDSHrefFormatException() {
	}

	/**
	 * mit Nachricht
	 * 
	 * @param msg die Nachricht
	 */
	public MDSHrefFormatException(String msg) {
		super(msg);
	}
}