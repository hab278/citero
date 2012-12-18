package edu.nyu.library.citation;

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
 * The CSF object stores all the data scrapped from various other formats. All
 * export methods will have their data come from CSF files.
 * 
 * @author hab278
 * 
 */

public class CSF {

	/** A logger for debugging */
	public final char SEPARATOR = ':'; 
	private final Log logger = LogFactory.getLog(CSF.class);
	/** A Configuration file that stores the data. */
	private Configuration config;
	/** A string representing the properties */
	private String data;

	/**
	 * The default constructor. Creates a new Configuration file.
	 */
	public CSF() {
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		data = "";
	}
	
	public CSF(String in) throws ConfigurationException{
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		load(in);
	}
	
	public CSF(Reader in) throws ConfigurationException{
		logger.debug("CSF FORMAT");
		config = new PropertiesConfiguration();
		load(in);
	}

	/**
	 * This method loads the properties for the configuration from an outside
	 * source.
	 * 
	 * @param in
	 *            A string representation of the configuration, this is passed
	 *            to {@link CSF#load(Reader)} as a StringReader object.
	 * @throws ConfigurationException
	 *             Inherited from {@link CSF#load(Reader)}
	 */
	public void load(String in) throws ConfigurationException {
		data = in;
		load(new StringReader(in));
	}

	/**
	 * This method loads the properties for the configuration from an outside
	 * source.
	 * 
	 * @param in
	 *            A reader representation of the configuration.
	 * @throws ConfigurationException
	 */
	public void load(Reader in) throws ConfigurationException {

		logger.info("Loading into CSF");

		try {
			((PropertiesConfiguration) config).load(in);
		} catch (NoSuchMethodError e) {
			// For primo we have to manually load the properties
			Scanner scan = new Scanner(data);
			while (scan.hasNextLine()) {
				StringBuffer rawLine = new StringBuffer(scan.nextLine());
				
				while( rawLine.toString().trim().endsWith("\\") && scan.hasNextLine() )
					rawLine.append(scan.nextLine());
					
				if (!rawLine.toString().replaceAll("\\\\" + SEPARATOR, "").contains(String.valueOf(SEPARATOR)))
					continue;
				String[] keyval = rawLine.toString().split(String.valueOf(SEPARATOR), 2);
				for (int i = 0; i < keyval.length; ++i)
					keyval[i] = keyval[i].trim();
				config.addProperty(keyval[0], keyval[1].replace("\\.", "."));
			}
		}

	}

	/**
	 * Accessor for the configuration properties.
	 * 
	 * @return The configuration properties.
	 */
	public Configuration config() {
		return config;
	}

	/**
	 * Human readable output of the data.
	 * 
	 * @return A human readable format of properties.
	 */
	public String getData() {
		if( data.isEmpty() )
		{
			StringBuffer out = new StringBuffer();
			Iterator<?> itr = config().getKeys();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				String[] value = config().getStringArray(key);
				out.append(key + " : ");
				for (int i = 0; i < value.length; ++i)
					if (i == value.length - 1)
						out.append(value[i] + '\n');
					else
						out.append(value[i] + ", ");
			}
			data = out.toString();
		}
		return data;
	}

}
