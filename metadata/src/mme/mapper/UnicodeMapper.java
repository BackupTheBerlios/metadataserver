package mme.mapper;

import mme.core.*;

/**
 * Spezialisierung des Mappers f�r Unicode-Quellen
 * 
 * @author Thomas Chille
 */
public interface UnicodeMapper extends Mapper {

	/**
	 * erzeugt mit Hilfe des Parsergeneratos javaCC einen
	 * Parser f�r das entsprehcnede Mapping
	 *
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public void generateParser() throws MetaMappingEngineException;
}