package edu.nyu.library.citero;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Citero class is the tool required to start the data interchange process.
 * 
 * @author hab278
 */
public class Citero {

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
     * Creates a Citero instance and loads the provided data
     * 
     * @param data
     *            Input data represented as a string
     */
    private Citero(String data) {
        Citero.data = data;
    }

    /**
     * Returns a Citero object with the loaded data.
     * 
     * @param data
     *            Input data represented as a string
     * @return A Citero object with the loaded data.
     */
    public static Citero map(String data) {
        return new Citero(data);
    }

    /**
     * Returns a Citero object with the loaded data and format.
     * 
     * @param format
     *            The format the input data came in.
     * @return A Citero object with the loaded data and format.
     */
    public Citero from(Formats format) {
        this.format = format;
        logger.debug("MAIN CITATION TOOL");
        loadData(data, format);
        return this;
    }

    /**
     * Returns a Citero object with the data loaded from a CSF object.
     * 
     * @param file
     *            A CSF object containing bibliographic data.
     * @return A Citero object with data loaded from a CSF object.
     */
    public Citero from(CSF file) {
        setFields(file);
        return this;
    }

    private static void setFields(CSF file) {
        setFields(item.export(), file);
    }

    private static void setFields(String datum, CSF file) {
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
    public String to(Formats output) {
        return output(output);
    }

    /**
     * Loads data into Citero after converting it to a common format.
     * 
     * @param data
     *            A string representation of the data.
     * @param input
     *            The input format of the data.
     * @throws IllegalArgumentException
     *             thrown when input is not known or if data is not valid
     */
    private void loadData(String data, Formats input)
            throws IllegalArgumentException {
        if (data.isEmpty() || !input.isSourceFormat())
            throw new IllegalArgumentException();
        setFields(data, input.getInstance(data).toCSF());
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
    private String output(Formats output) throws IllegalArgumentException {
        if (format == null || !output.isDestinationFormat())
            throw new IllegalArgumentException();
        if (output.equals(format))
            return data;
        return ((DestinationFormat) output.getInstance(item)).export();
    }
}