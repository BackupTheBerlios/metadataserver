package de.chille.mme.mapper;

import java.io.File;

import de.chille.mme.core.MetaMappingEngineException;

import de.chille.api.mme.mapper.UnicodeMapper;

/**
 * @see UnicodeMapper
 * 
 * @author Christian Sterr
 */
public class UnicodeMapperImpl extends MDSMapperImpl implements UnicodeMapper {


	/**
	 * soll die Packetstrucktur nachgebildet werden.
	 */
	private boolean buildPackage = true;

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
	
	/**
	 * @see de.chille.api.de.chille.de.chille.mme.mapper.UnicodeMapper#doMapping(String)
	 */
	public String doMapping(String xmi)throws MetaMappingEngineException{
		return null;
	}
	
	/**
	 * @see de.chille.api.de.chille.de.chille.mme.mapper.MDSMapper#setBuildPackage(boolean)
	 */
	public void setBuildPackage(boolean buildPackage){
		this.buildPackage = buildPackage;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mme.mapper.MDSMapper#getBuildPackage()
	 */
	public boolean getBuildPackage(){
		return buildPackage;
	}

}