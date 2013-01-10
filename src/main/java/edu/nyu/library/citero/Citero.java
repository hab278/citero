package edu.nyu.library.citero;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Citero class is the tool required to start the data interchange process.
 * 
 * @author hab278
 */
public final class Citero {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(Citero.class);
    /**
     * data variable is the string representation of the data in Citeros own
     * common format.
     */
    private static String data;
    /** format variable is the original format the data was in. */
    private Formats format;
    /** item variable is a CSF object that contains the data. */
    private static CSF item;

    /**
     * Creates a Citero instance and loads the provided data.
     * 
     * @param datum
     *            Input data represented as a string
     */
    private Citero(final String datum) {
        Citero.data = datum;
    }

    /**
     * Returns a Citero object with the loaded data.
     * 
     * @param datum
     *            Input data represented as a string
     * @return A Citero object with the loaded data.
     */
    public static Citero map(final String datum) {
        return new Citero(datum);
    }

    /**
     * Returns a Citero object with the loaded data and format.
     * 
     * @param fmt
     *            The format the input data came in.
     * @return A Citero object with the loaded data and format.
     */
    public Citero from(final Formats fmt) {
        format = fmt;
        logger.debug("MAIN CITATION TOOL");
        loadData(data, fmt);
        return this;
    }

    /**
     * Sets this objects data and CSF variables.
     * @param datum
     *             The string representation of the data.
     * @param file
     *             The CSF object to be stored.
     */
    private static void setFields(final String datum, final CSF file) {
        data = datum;
        item = file;
    }

    /**
     * Converts data to the specified output format in string representation.
     * 
     * @param output
     *            The format the data should be converted to
     * @return A string representation of the converted data.
     */
    public String to(final Formats output) {
        return output(output);
    }

    /**
     * Loads data into Citero after converting it to a common format.
     * 
     * @param datum
     *            A string representation of the data.
     * @param input
     *            The input format of the data.
     * @throws IllegalArgumentException
     *             thrown when input is not known or if data is not valid
     */
    private void loadData(final String datum, final Formats input)
            throws IllegalArgumentException {
        if (datum.isEmpty() || !input.isSourceFormat())
            throw new IllegalArgumentException();
        setFields(datum, (CSF) input.getInstance(datum).toCSF());
    }

    /**
     * Converts data to the specified output format in string representation.
     * 
     * @param output
     *            The format the data should be converted to
     * @return A string representation of the converted data.
     * @throws IllegalArgumentException
     *             thrown when data has not been loaded or outputFormat is not
     *             known.
     */
    private String output(final Formats output) throws IllegalArgumentException {
        if (format == null || !output.isDestinationFormat())
            throw new IllegalArgumentException();
        if (output.equals(format))
            return data;
        return ((DestinationFormat) output.getInstance(item)).export();
    }
}
