package mds.core;

import api.mds.core.MDSHref;


public class MDSHrefImpl implements MDSHref {
	
	public String href = null;

	/**
	 * Constructor for MDSHrefImpl.
	 */
	public MDSHrefImpl(String href) throws MDSHrefFormatException {
		setHref(href);
	}

	/**
	 * @see api.mds.core.MDSHref#getHref()
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @see api.mds.core.MDSHref#setHref(String)
	 */
	public void setHref(String href) throws MDSHrefFormatException {
		if (href.matches("^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*$")) {
			this.href = href;
		} else {
			throw new MDSHrefFormatException("Fehler: MDSHref#setHref");
		}
	}
	
	/**
	 * @see api.mds.core.MDSHref#getServerId()
	 */
	public String getServerId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		return hrefParts[0];
	}

	/**
	 * @see api.mds.core.MDSHref#getRepositoryId()
	 */
	public String getRepositoryId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[1] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[1];
		}
	}

	/**
	 * @see api.mds.core.MDSHref#getRepositoryHref()
	 */
	public String getRepositoryHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[1] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[0] + "." + hrefParts[1];
		}
	}

	/**
	 * @see api.mds.core.MDSHref#getModelId()
	 */
	public String getModelId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[2] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[2];
		}
	}

	/**
	 * @see api.mds.core.MDSHref#getModelHref()
	 */
	public String getModelHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[2] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[0] + "." + hrefParts[1] + "." + hrefParts[2];
		}
	}

	/**
	 * @see api.mds.core.MDSHref#getElementId()
	 */
	public String getElementId() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[1] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[1];
		}
	}

	/**
	 * @see api.mds.core.MDSHref#getElementHref()
	 */
	public String getElementHref() throws MDSHrefFormatException {
		String[] hrefParts = href.split("[.]");
		if (hrefParts[1] == null) {
			throw new MDSHrefFormatException("Fehler: MDSHref#getRepositoryId");
		} else {
			return hrefParts[1];
		}
	}
}
