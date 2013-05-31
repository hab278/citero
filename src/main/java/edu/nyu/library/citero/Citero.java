package edu.nyu.library.citero;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * The Citero class is the tool required to start the data interchange process.
 * Usage example:
 *     Citero.map("some string").from(Formats.someSourceFormat).to(Formats.someDestinationFormat);
 * Heres a working example:
 *     Citero.map("itemType: journalArticle).from(Formats.CSF).to(Formats.RIS);
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
     * This is the only way to get an instance of a Citero object.
     * 
     * @param datum
     *            Input data represented as a string
     * @return A Citero object with the loaded data.
     */
    public static Citero map(final String datum) {
        return new Citero(datum);
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
     * Loads data into Citero after converting it to a common format.
     * 
     * @param fmt
     *            The input format of the data.
     * @return A Citero object with the loaded data and format.
     * @throws IllegalArgumentException
     *             thrown when input is not known or if data is not valid
     */
    public Citero from(final Formats fmt)
            throws IllegalArgumentException {
        logger.debug("MAIN CITERO TOOL.");
        if (data.isEmpty() || !fmt.isSourceFormat())
            throw new IllegalArgumentException("Data is inconsistent with format.");
        format = fmt;
        setFields(data, format.getInstance(data).toCSF());
        return this;
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
    public String to(final Formats output) throws IllegalArgumentException {
        if (format == null)
            throw new IllegalStateException("Must call from() first.");
        if (!output.isDestinationFormat())
            throw new IllegalArgumentException("Not a valid output format.");
        if (output.equals(format))
            return data;
        return ((DestinationFormat) output.getInstance(item)).doExport();
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
    public String to(final CitationStyles output) throws IllegalArgumentException {
        if (format == null)
            throw new IllegalStateException("Must call from() first.");
        return CiteprocAdapter.dataAndStyle( ((DestinationFormat) Formats.CSL.getInstance(item)).doExport(),output.STYLE_DEF).result();
    }
}
