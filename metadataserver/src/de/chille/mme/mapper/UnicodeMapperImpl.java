package de.chille.mme.mapper;

import java.io.File;

import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.mapper.UnicodeMapper;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * @see UnicodeMapper
 * 
 * @author Christian Sterr
 */
public class UnicodeMapperImpl extends MDSMapperImpl implements UnicodeMapper {


	/**
	 * *.jj - Datei um parserFile zu generieren
	 */
	private File grammarFile = null;

	/**
	 * der generierte Parser f�r diesen Mapper
	 */
	private File parserFile = null;

	/**
	 * Constructor for UnicodeMapperImpl.
	 * @param id
	 */
	public UnicodeMapperImpl(String id) {
		super(id);
	}

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
	
	/**
	 * @see Mapper#map(MDSModel, Mapping)
	 */
	public void map(MDSModel mdsModel) throws MetaMappingEngineException {
	}


}