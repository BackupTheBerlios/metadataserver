package api.mds.core;

import mds.core.MDSCoreException;

/**
 * Spezialisierung des MDSElements, modelliert eine Klasse 
 * 
 * @author Thomas Chille
 */
public interface MDSClass extends MDSElement {

	/**
	 * f�gt eine Superklasse hinzu
	 * 
	 * @param superClass die Superklasse
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void addSuperClass(MDSClass superClass) throws MDSCoreException;

	/**
	 * entfernt eine Superklasse
	 * 
	 * @param superClass die Superklasse
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeSuperClass(MDSClass superClass) throws MDSCoreException;

	/**
	 * f�gt eine Unterklasse hinzu
	 * 
	 * @param subClass die Unterklasse
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void addSubClass(MDSClass subClass) throws MDSCoreException;

	/**
	 * entfernt eine Unterklasse
	 * 
	 * @param subClass die Unterklasse
	 * @throws MDSCoreException im Fehlerfall
	 */
	public void removeSubClass(MDSClass subClass) throws MDSCoreException;
}