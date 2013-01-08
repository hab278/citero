package edu.nyu.library.citero;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The CSF object stores all the data scrapped from
 * various other formats. All export methods will
 * have their data come from CSF files.
 *
 * @author hab278
 *
 */
@SourceFormat
public class CSF extends Format implements DestinationFormat {

	/** A logger for debugging */
	public static final char SEPARATOR = ':';
	private final Log logger = LogFactory.getLog(CSF.class);
	/** A Configuration file that stores the data. */
	private Configuration config;
	/** A string representing the properties */
	private String data;

	/**
	 * The default constructor. Creates a new Configuration file.
	 */
	public CSF() {
		super("");
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		data = "";
	}

	/**
	 * Constructor that also creates a configuration file.
	 *
	 * @param in
	 *		String representation of configuration.
	 * @throws ConfigurationException
	 *		Thrown when there is an error in syntax.
	 */
	public CSF(final String in) throws ConfigurationException {
		super(in);
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		doImport(in);
	}

	/**
	 * Constructor that also creates a configuration file.
	 *
	 * @param in
	 *            Reader representation of configuration.
	 * @throws ConfigurationException
	 *             Thrown when there is an error in syntax.
	 */
	public CSF(final Reader in) throws ConfigurationException {
		super(in.toString());
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		doImport(in);
	}

	/**
	 * This method loads the properties for
	 * the configuration from an outside source.
	 *
	 * @param in
	 *		A string representation of the configuration,
	 *		this is passed to {@link CSF#doImport(Reader)}
	 *		as a StringReader object.
	 * @throws ConfigurationException
	 *             Inherited from {@link CSF#doImport(Reader)}
	 */
	private void doImport(final String in) throws ConfigurationException {
		data = in;
		doImport(new StringReader(in));
	}

	/**
	 * This method loads the properties for
	 * the configuration from an outside source.
	 *
	 * @param in
	 *		A reader representation of the configuration.
	 * @throws ConfigurationException
	 *		Thrown by bad CSF syntax
	 */
	private void doImport(final Reader in) throws ConfigurationException {

		logger.info("Loading into CSF");

		try {
			((PropertiesConfiguration) config).load(in);
		} catch (NoSuchMethodError e) {
			// For primo we have to manually load the properties
			Scanner scan = new Scanner(data);
			while (scan.hasNextLine()) {
				StringBuffer rawLine = 
						new StringBuffer(scan.nextLine());
				while (rawLine.toString().
						trim().endsWith("\\")
						&& scan.hasNextLine()) {
					rawLine.append(scan.nextLine());
				}
				if (!rawLine.toString().replaceAll("\\\\" + SEPARATOR, "")
						.contains(String.valueOf(SEPARATOR))) {
					continue;
				}
				String[] keyval = rawLine.toString().split(
						String.valueOf(SEPARATOR), 2);
				for (int i = 0; i < keyval.length; ++i) {
					keyval[i] = keyval[i].trim();
				}
				config.addProperty(keyval[0],
						keyval[1].replace("\\.", "."));
			}
		}

	}

	/**
	 * Accessor for the configuration properties.
	 *
	 * @return The configuration properties.
	 */
	public final Configuration config() {
		return config;
	}

	/**
	 * Human readable output of the data.
	 *
	 * @return A human readable format of properties.
	 */
	public final  String export() {
		if (data.isEmpty()) {
			StringBuffer out = new StringBuffer();
			Iterator<?> itr = config().getKeys();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				String[] value = config().getStringArray(key);
				out.append(key + " : ");
				for (int i = 0; i < value.length; ++i) {
					if (i == value.length - 1) {
						out.append(value[i] + '\n');
					} else {
						out.append(value[i] + ", ");
					}
				}
			}
			data = out.toString();
		}
		return data;
	}

	@Override
	public final  CSF toCSF() {
		return this;
	}

}
