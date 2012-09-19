package edu.nyu.library.citation;

/**
 * Format abstract class, all formats inherit this.
 * 
 * @author hab278
 * 
 */

public abstract class Format {

	/**
	 * Constructor that takes in a string input.
	 * 
	 * @param input
	 *            string representation of the data.
	 */
	public Format(String input) {
	}

	/**
	 * Constructor that takes in a CSF input.
	 * 
	 * @param item
	 *            CSF representation of the data.
	 */
	public Format(CSF item) {
	}

	/**
	 * Function that returns the CSF object that gets loaded into this object.
	 * 
	 * @return The CSF object.
	 */
	public abstract CSF CSF();

	/**
	 * Export converts CSF objects to a string representation of this object's
	 * format.
	 * 
	 * @return A string representation of the inputed data in this object's
	 *         format.
	 */
	public abstract String export();

}