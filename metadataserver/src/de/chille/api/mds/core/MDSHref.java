package de.chille.api.mds.core;

import de.chille.mds.core.MDSHrefFormatException;

/**
 * modelliert eine Referenz im MDS-Format
 * über diese können alle Objekte des Servers referenziert werden
 * Format serverId[.repositoryId][.modelId][.elementId]
 * 
 * @author Thomas Chille
 */
public interface MDSHref {

	/**
	 * Returns the href.
	 * @return String
	 */
	public String getHrefString();

	/**
	 * Sets the href.
	 * @param href The href to set
	 */
	public void setHref(String href) throws MDSHrefFormatException;

	/**
	 * extrahiert die ServerId=ServerHref aus href
	 * 
	 * @return String
	 */
	public String getServerId() throws MDSHrefFormatException;

	/**
	 * extrahiert die ServerId aus href
	 * 
	 * @return String
	 */
	public String getServerHref() throws MDSHrefFormatException;
	
	/**
	 * extrahiert die RepositoryId aus href
	 * 
	 * @return String
	 */
	public String getRepositoryId() throws MDSHrefFormatException;

	/**
	 * extrahiert die RepositoryHref aus href
	 * 
	 * @return String
	 */
	public String getRepositoryHref() throws MDSHrefFormatException;

	/**
	 * extrahiert die ModelId aus href
	 * 
	 * @return String
	 */
	public String getModelId() throws MDSHrefFormatException;

	/**
	 * extrahiert die ModelHref aus href
	 * 
	 * @return String
	 */
	public String getModelHref() throws MDSHrefFormatException;

	/**
	 * extrahiert die ElementId aus href
	 * 
	 * @return String
	 */
	public String getElementId() throws MDSHrefFormatException;

	/**
	 * extrahiert die ElementHref aus href
	 * 
	 * @return String
	 */
	public String getElementHref() throws MDSHrefFormatException;
}
