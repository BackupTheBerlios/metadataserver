package mds.core;

import java.util.ArrayList;

import api.mds.core.MDSClass;
import api.mds.core.MDSHref;

/**
 * @see MDSClass
 * 
 * @author Thomas Chille
 */
public class MDSClassImpl extends MDSElementImpl implements MDSClass {

	/**
	 * Constructor for MDSClassImpl.
	 */
	public MDSClassImpl() {
		super("class");
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tclass:" + this.getId() + "\n";

	}
}