package mds.xmi;

/**
 * wird im Fehlerfall von XMIHandler-Implementierungen
 * geworfen
 * 
 * @author Thomas Chille
 */
public class XMIHandlerException extends Exception {
	
	/**
	 * ohne Nachricht
	 */
	public XMIHandlerException() {
	}

	/**
	 * mit Nachricht
	 * 
	 * @param msg die Nachricht
	 */
	public XMIHandlerException(String msg) {
		super(msg);
	}
}