package api.mds.core;

import java.util.ArrayList;

import mds.core.MDSCoreException;

/**
 * Spezialisierung des MDSElements, modelliert eine Klasse 
 * 
 * @author Thomas Chille
 */
public interface MDSClass extends MDSElement {

	/**
	 * fügt eine Superklasse hinzu
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
	 * fügt eine Unterklasse hinzu
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
	
	/**
	 * Gets the superClasses
	 * @return Returns a ArrayList
	 */
	public ArrayList getSuperClasses() throws MDSCoreException;

	/**
	 * Sets the superClasses
	 * @param superClasses The superClasses to set
	 */
	public void setSuperClasses(ArrayList superClasses) throws MDSCoreException;

	/**
	 * Gets the subClasses
	 * @return Returns a ArrayList
	 */
	public ArrayList getSubClasses() throws MDSCoreException;

	/**
	 * Sets the subClasses
	 * @param subClasses The subClasses to set
	 */
	public void setSubClasses(ArrayList subClasses) throws MDSCoreException;
}