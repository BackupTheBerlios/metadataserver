package de.chille.api.mme.mapper;

import de.chille.mme.core.MetaMappingEngineException;

/**
 * Spezialisierung des Mappers f�r Unicode-Quellen
 * 
 * @author Thomas Chille
 */
public interface UnicodeMapper extends MDSMapper {

	/**
	 * erzeugt mit Hilfe des Parsergeneratos javaCC einen
	 * Parser f�r das entsprehcnede Mapping
	 *
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public void generateParser() throws MetaMappingEngineException;
	
}