package mds.persistence;

/**
 * stellt Methoden zum dauerhaften Speicherung der Modelle mit einer
 * einfachen Versionierung zur Verfügung
 * 
 * für jede Speicherart muß ein PersistenceHandler implementiert werden
 * 
 * @author Thomas Chille
 */
public interface PersistenceHandler {
	
	/**
	 * sichert komplettes Model (xmi, [dtd], [xsd]),
	 * 
	 * aktualisiert automatisch die Versionsnummer im Modell
	 *
	 * @param model das zu sichernde Modell
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public void save(Model model) throws PersistenceHandlerException;
	
	/**
	 * lädt komplettes Model (xmi, [dtd], [xsd]),
	 * 
	 * @param mdsPfad das Modell wird anhand diesen Pfades identifiziert
	 * @param version Versionsnummer, wenn null dann die aktuellste Version laden
	 * @return das geladene Modell
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public Model load(String mdsPfad, String version) throws PersistenceHandlerException;
	
	/**
	 * löscht komplettes Model (xmi, [dtd], [xsd]),
	 * 
	 * @param model das zu löschende Modell
	 * @param version Versionsnummer, wenn null dann alle Versionen löschen
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public void delete(Model model, String version) throws PersistenceHandlerException;
	
	/**
	 * gibt Liste aller vorhandenen Versionen eines Modells zurück
	 *
	 * @param model das betreffende Modell
	 * @throws PersistenceHandlerException im Fehlerfall
	 */
   	public ArrayList getVersions(Model model) throws PersistenceHandlerException;
}

