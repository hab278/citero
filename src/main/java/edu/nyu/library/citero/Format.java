package edu.nyu.library.citero;

/**
 * Format abstract class, all formats inherit this.
 * 
 * @author hab278
 * 
 */

public abstract class Format {

    /** String for the data. */
    protected String input;

    /**
     * Constructor that takes in a string input.
     * 
     * @param in
     *            string representation of the data.
     */
    public Format(final String in) {
        input = in;
    }

    /**
     * Constructor that takes in a Format input.
     * 
     * @param file
     *            Format representation of the data.
     */
    public Format(final Format file) {
        input = file.input;
    }

    /**
     * Function that returns the CSF object that gets loaded into this object.
     * 
     * @return The Format object.
     */
    public abstract Format toCSF();

}
