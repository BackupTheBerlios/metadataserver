package de.chille.api.mds.core;

import de.chille.mds.soap.MDSClassBean;

/**
 * Spezialisierung des MDSElements, modelliert eine Klasse 
 * 
 * @author Thomas Chille
 */
public interface MDSClass extends MDSElement {

	public MDSClassBean exportBean();
	
	public void importBean(MDSClassBean bean);
}