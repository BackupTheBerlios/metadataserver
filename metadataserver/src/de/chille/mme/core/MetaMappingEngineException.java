package de.chille.mme.core;

/**
 * wird im Fehlerfall von MetaMappingEngine-Implementierung
 * geworfen
 * 
 * @author Thomas Chille
 */
public class MetaMappingEngineException extends Exception {
	
	private String src = "";
	
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
	
	/**
	 * mit Nachricht und Verursacher
	 * 
	 * @param msg die Nachricht
	 */
	public MetaMappingEngineException(String msg, String src) {
		super(msg);
		this.src = src;
	}
	/**
	 * Returns the src.
	 * @return String
	 */
	public String getSrc() {
		return src;
	}

}