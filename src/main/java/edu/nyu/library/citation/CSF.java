package edu.nyu.library.citation;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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
	private final Log logger = LogFactory.getLog(CSF.class);
	/** A Configuration file that stores the data. */
	//private Configuration config;
	/** A string representing the properties */
	protected String props;
	private Class<?> clazz;
	
	private Object ficker;

	/**
	 * The default constructor. Creates a new Configuration file.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public CSF() {
		logger.info("CSF FORMAT");
		
		//config = new PropertiesConfiguration();
		URL configurations, lang;
		try {
			configurations = new URL("jar","","/Users/hannan/.m2/repository/commons-configuration/commons-configuration/1.9/commons-configuration-1.9.jar");
			lang = new URL("jar","","/Users/hannan/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar");
			URLClassLoader cl = URLClassLoader.newInstance(new URL [] {lang,configurations});
			clazz = cl.loadClass("org.apache.commons.configuration.PropertiesConfiguration");
			Thread.currentThread().setContextClassLoader(cl);
			ficker = clazz.newInstance();
			logger.info("This is working");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		props = "";
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
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public void load(String in) throws ConfigurationException{
		load(new StringReader(in));
		props = in;
	}

	/**
	 * This method loads the properties for the configuration from an outside
	 * source.
	 * 
	 * @param in
	 *            A reader representation of the configuration.
	 * @throws ConfigurationException
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void load(Reader in) throws ConfigurationException {
		//((PropertiesConfiguration) config).load(in);
		Method load;
		try {
			load = clazz.getMethod("load", String.class);
			load.invoke(ficker, in);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Accessor for the configuration properties.
	 * 
	 * @return The configuration properties.
	 */
	public Configuration config() {
		return (Configuration) ficker;
		//return config;
	}

	/**
	 * Human readable output of the data.
	 * 
	 * @return A human readable format of properties.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public String data() {
		StringWriter out = new StringWriter();
		Method save;
		try {
			save = clazz.getMethod("save", Writer.class);
			save.invoke(ficker, out);
			//((PropertiesConfiguration) config).save(out);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}

}
