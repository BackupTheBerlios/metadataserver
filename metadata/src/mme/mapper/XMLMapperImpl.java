package mme.mapper;

import java.io.File;

import api.mme.mapper.XMLMapper;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMLMapperImpl extends MDSMapperImpl implements XMLMapper {

	/**
	 * der für diesen mapper verwendeten XSL-Stylesheets
	 */
	private File[] stylesheets = null;

	/**
	 * Gets the stylesheets
	 * @return Returns a File[]
	 */
	public File[] getStylesheets() {
		return stylesheets;
	}

	/**
	 * Sets the stylesheets
	 * @param stylesheets The stylesheets to set
	 */
	public void setStylesheets(File[] stylesheets) {
		this.stylesheets = stylesheets;
	}
}