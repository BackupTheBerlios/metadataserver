package mds.core;

import java.util.ArrayList;

import api.mds.core.MDSClass;

/**
 * @see MDSClass
 * 
 * @author Thomas Chille
 */
public class MDSClassImpl extends MDSElementImpl implements MDSClass {
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "\t\t\tclass:" + this.getId() + "\n";

	}
}