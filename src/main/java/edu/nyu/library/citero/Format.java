package edu.nyu.library.citero;

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
     * @param in
     *            string representation of the data.
     */
    public Format(final String in) {
    }

    /**
     * Constructor that takes in a CSF input.
     * 
     * @param file
     *            CSF representation of the data.
     */
    public Format(final CSF file) {
    }

    /**
     * Function that returns the CSF object that gets loaded into this object.
     * 
     * @return The CSF object.
     */
    public abstract CSF toCSF();

}
