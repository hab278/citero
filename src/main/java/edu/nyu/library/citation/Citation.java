package edu.nyu.library.citation;

import org.apache.commons.configuration.ConfigurationException;
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
	private static Formats format;
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
		return new Citation(Citation.data, format);
	}

	/**
	 * Returns a citation object with the data loaded from a CSF object.
	 * 
	 * @param file
	 *            A CSF object containing bibliographic data.
	 * @return A Citation object with data loaded from a CSF object.
	 */
	public static Citation from(CSF file) {
		return new Citation(file);
	}

	/**
	 * Converts data to the specified output format in string representation.
	 * 
	 * @param output
	 *            The format the data should be converted to
	 * @return A string representation of the converted data.
	 */
	public String to(Formats output) {
		if( format == null)
			throw new IllegalArgumentException();
		return output(output);
	}

	/**
	 * Creates a Citation instance and loads the provided data and format.
	 * 
	 * @param data
	 *            Input data represented as a string
	 * @param input
	 *            format specified via string
	 * @throws IllegalArgumentException
	 *             derived from {@link Citation#loadData(String, Formats)}
	 */
	private Citation(String data, Formats input)
			throws IllegalArgumentException {
		logger.debug("MAIN CITATION TOOL");
		format = input;
		Citation.data = data;
		loadData(data, input);
	}

	/**
	 * Creates a citation instance and loads the provided CSF object.
	 * 
	 * @param file
	 *            A CSF object that contains the data payload.
	 */
	private Citation(CSF file) {
		data = file.data();
		item = file;
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
		if (input.getClass() != Formats.class || data.isEmpty())
			throw new IllegalArgumentException();
		switch (input) {
		case RIS:
			item = new RIS(data).CSF();
			break;
		case CSF:
			// loadCSF();
			try {
				item = new CSF();
				item.load(data);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case OPENURL:
			item = new OPENURL(data).CSF();
			break;
		case PNX:
			item = new PNX(data).CSF();
			break;
		case BIBTEX:
			item = new BIBTEX(data).CSF();
			break;
		case EASYBIB:
			item = new EASYBIB(data).CSF();
			break;
		default:
			throw new IllegalArgumentException();

		}
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
		if (output == format)
			return data;
		switch (output) {
		case CSF:
			return item.props;
		case RIS:
			RIS ris = new RIS(item);
			return ris.export();
		case OPENURL:
			OPENURL openurl = new OPENURL(item);
			return openurl.export();
		case BIBTEX:
			BIBTEX bibtex = new BIBTEX(item);
			return bibtex.export();
		case PNX:
			PNX pnx = new PNX(item);
			return pnx.export();
		case EASYBIB:
			EASYBIB easybib = new EASYBIB(item);
			return easybib.export();
		default:
			throw new IllegalArgumentException();
		}
	}
}