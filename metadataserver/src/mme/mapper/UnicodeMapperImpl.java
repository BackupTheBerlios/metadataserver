package mme.mapper;

import java.io.File;

import mme.core.MetaMappingEngineException;

import api.mme.mapper.UnicodeMapper;

/**
 * @see UnicodeMapper
 * 
 * @author Thomas Chille
 */
public class UnicodeMapperImpl extends MDSMapperImpl implements UnicodeMapper {

	/**
	 * *.jj - Datei um parserFile zu generieren
	 */
	private File grammarFile = null;

	/**
	 * der generierte Parser für diesen Mapper
	 */
	private File parserFile = null;

	/**
	 * @see UnicodeMapper#generateParser()
	 */
	public void generateParser() throws MetaMappingEngineException {
	}

	/**
	 * Gets the grammarFile
	 * @return Returns a File
	 */
	public File getGrammarFile() {
		return grammarFile;
	}

	/**
	 * Sets the grammarFile
	 * @param grammarFile The grammarFile to set
	 */
	public void setGrammarFile(File grammarFile) {
		this.grammarFile = grammarFile;
	}
	
	/**
	 * Gets the parserFile
	 * @return Returns a File
	 */
	public File getParserFile() {
		return parserFile;
	}

	/**
	 * Sets the parserFile
	 * @param parserFile The parserFile to set
	 */
	public void setParserFile(File parserFile) {
		this.parserFile = parserFile;
	}
}