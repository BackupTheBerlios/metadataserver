package api.mme.mapper;

import mme.core.MetaMappingEngineException;

/**
 * Spezialisierung des Mappers für Unicode-Quellen
 * 
 * @author Thomas Chille
 */
public interface UnicodeMapper extends MDSMapper {

	/**
	 * erzeugt mit Hilfe des Parsergeneratos javaCC einen
	 * Parser für das entsprehcnede Mapping
	 *
	 * @throws MetaMappingEngineException im Fehlerfall
	 */
	public void generateParser() throws MetaMappingEngineException;
}