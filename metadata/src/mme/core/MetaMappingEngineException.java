package mme.core;

/**
 * wird im Fehlerfall von MetaMappingEngine-Implementierung
 * geworfen
 * 
 * @author Thomas Chille
 */
public class MetaMappingEngineException extends Exception {
	
	/**
	 * ohne Nachricht
	 */
	public MetaMappingEngineException() {
	}

	/**
	 * mit Nachricht
	 * 
	 * @param msg die Nachricht
	 */
	public MetaMappingEngineException(String msg) {
		super(msg);
	}
}