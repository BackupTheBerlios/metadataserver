package mds.persistence;

/**
 * wird im Fehlerfall von PersistenceHandler-Implementierungen
 * geworfen
 * 
 * @author Thomas Chille
 */
public class PersistenceHandlerException extends Exception {
	
	/**
     * ohne Nachricht
     */
    public PersistenceHandlerException() {
    }

    /**
     * mit Nachricht
     * 
     * @param msg die Nachricht
     */
    public PersistenceHandlerException(String msg) {
        super(msg);
    }
}

