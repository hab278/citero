package edu.nyu.library.citation;


import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * The CSF object stores all the data scrapped from various other formats.
 * All export methods will have their data come from CSF files.
 * @author hab278
 *
 */

public class CSF {

	/** A Configuration file that stores the data. */
	private Configuration config;
	protected String props;
	
	/**
	 * The default constructor. Creates a new Configuration file.
	 */
	public CSF()
	{
		config = new PropertiesConfiguration();
		props = "";
	}
	
	/**
	 * This method loads the properties for the configuration from an outside source.
	 * @param in A string representation of the configuration, this is passed to {@link CSF#load(Reader)} as a StringReader object.
	 * @throws ConfigurationException Inherited from {@link CSF#load(Reader)}
	 */
	public void load(String in) throws ConfigurationException{
		load(new StringReader(in));
		props = in;
	}

	/**
	 * This method loads the properties for the configuration from an outside source.
	 * @param in A reader representation of the configuration.
	 * @throws ConfigurationException
	 */
	public void load(Reader in) throws ConfigurationException
	{
		((PropertiesConfiguration)config).load(in);
	}
	

	
	/**
	 * Accessor for the configuration properties.
	 * @return The configuration properties.
	 */
	public Configuration config()	{ return config; }
	

	public String data(){
		StringWriter out = new StringWriter();
		try {
			((PropertiesConfiguration)config).save(out);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}
	
}
