package de.chille.mds.soap;

import java.util.Vector;


public class MDSRepositoryBean extends MDSObjectBean {
	
	private int counter = 0;

	/**
	 * alle auf dem Server vorhandenen Reposititories
	 */
	private Vector models = new Vector();
	
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Returns the models.
	 * @return Vector
	 */
	public Vector getModels() {
		return models;
	}

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Sets the models.
	 * @param models The models to set
	 */
	public void setModels(Vector models) {
		this.models = models;
	}

}
