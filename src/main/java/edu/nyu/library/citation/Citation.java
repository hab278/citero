package edu.nyu.library.citation;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The citation class is the tool required to start the data interchange
 * process.
 * 
 * @author hab278
 */
public class Citation {

	private final Log logger = LogFactory.getLog(Citation.class);
	/**
	 * data variable is the string representation of the data in citations own
	 * common format.
	 */
	private static String data;
	/** format variable is the original format the data was in. */
	private Formats format;
	/** item variable is a CSF object that contains the data. */
	private static CSF item;

	/**
	 * Creates a Citation instance and loads the provided data
	 * 
	 * @param data
	 *            Input data represented as a string
	 */
	private Citation(String data) {
		Citation.data = data;
	}

	/**
	 * Returns a citation object with the loaded data.
	 * 
	 * @param data
	 *            Input data represented as a string
	 * @return A Citation object with the loaded data.
	 */
	public static Citation map(String data) {
		return new Citation(data);
	}

	/**
	 * Returns a citation object with the loaded data and format.
	 * 
	 * @param format
	 *            The format the input data came in.
	 * @return A Citation object with the loaded data and format.
	 */
	public Citation from(Formats format) {
		this.format = format;
		logger.debug("MAIN CITATION TOOL");
		loadData(data, format);
		return this;
	}

	/**
	 * Returns a citation object with the data loaded from a CSF object.
	 * 
	 * @param file
	 *            A CSF object containing bibliographic data.
	 * @return A Citation object with data loaded from a CSF object.
	 */
	public Citation from(CSF file) {
		setFields(file);
		return this;
	}
	
	private static void setFields(CSF file)
	{
		data = item.export();
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
	 * Loads data into Citation after converting it to a common format.
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
		if (data.isEmpty())
			throw new IllegalArgumentException();
		setFields(input.getInstance(data).toCSF());
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
		if (format == null)
			throw new IllegalArgumentException();
		if (output == format)
			return data;
		if(output.getInstance(item).getClass().isAnnotationPresent(SourceFormat.class))
			throw new IllegalArgumentException();
		return ((DestinationFormat) output.getInstance(item)).export();
	}
}