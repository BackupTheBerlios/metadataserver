package de.chille.mds.core;

import de.chille.api.mds.core.MDSHref;

public class MDSHrefImpl implements MDSHref {

	public String href = null;

	/**
	 * Constructor for MDSHrefImpl.
	 */
	public MDSHrefImpl(String href) throws MDSHrefFormatException {
		setHref(href);
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getHref()
	 */
	public String getHrefString() {
		return href;
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#setHref(String)
	 */
	public void setHref(String href) throws MDSHrefFormatException {
		if (href.matches("^mds://[a-zA-Z0-9_]+([/][a-zA-Z0-9_]+)*$")) {
			this.href = href;
		} else {
			throw new MDSHrefFormatException("Fehler: MDSHref#setHref");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getServerId()
	 */
	public String getServerId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return hrefParts[2].substring(7);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getServerId");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getServerHref()
	 */
	public String getServerHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return  "mds://" + hrefParts[2];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getServerHref");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getRepositoryId()
	 */
	public String getRepositoryId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return hrefParts[3].substring(11);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getRepositoryHref()
	 */
	public String getRepositoryHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return "mds://" + hrefParts[2] + "/" + hrefParts[3];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryHref");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getModelId()
	 */
	public String getModelId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return hrefParts[4].substring(6);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getModelId");
		}
	}
	
	/**
	 * @see de.chille.api.mds.core.MDSHref#getModelHref()
	 */
	public String getModelHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return "mds://"
				+ hrefParts[2]
				+ "/"
				+ hrefParts[3]
				+ "/"
				+ hrefParts[4];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getModelHref");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getElementId()
	 */
	public String getElementId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			int pos = 0;
			if (hrefParts[5].startsWith("ass")) {
				pos = 12;
			} else if (hrefParts[5].startsWith("cla")) {
				pos = 6;
			} else if (hrefParts[5].startsWith("gen")) {
				pos = 15;
			} return hrefParts[5].substring(pos);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getElementId");
		}
	}

	/**
	 * @see de.chille.api.mds.core.MDSHref#getElementHref()
	 */
	public String getElementHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[/]");
		try {
			return "mds://"
				+ hrefParts[2]
				+ "/"
				+ hrefParts[3]
				+ "/"
				+ hrefParts[4]
				+ "/"
				+ hrefParts[5];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getElementHref");
		}
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getHrefString();
	}

}
