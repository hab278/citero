package edu.nyu.library.citero;

/**
 * This interface forces the format to have an export() method and is used to
 * mark the format as a DestinationFormat. This means that Citero can export to
 * this format.
 *
 * @author hab278
 *
 */
public interface DestinationFormat {

    /**
     * This method should be public and outputs data.
     *
     * @return A string value of the data in the format of the implementing
     *         class.
     */
    String export();
}
