package edu.nyu.library.citation;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.io.Reader;
import java.io.StringReader;

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
	/** @deprecated A temporary boolean to tell if this object is using a configuration file or maps to store data */
	private boolean isConf;
	/** @deprecated A string representation of the configuration properties. */
	private String props;
	/** @deprecated The item's type. */
	private String itemType;
	/** @deprecated A map of the fields.  */
	private Map<String,String> fields;
	/** @deprecated A map of the creators. */
	private Map<String,String> creator;
	
	/**
	 * The default constructor. Creates a new Configuration file.
	 */
	public CSF()
	{
		itemType = "";
		fields = new HashMap<String, String>();
		creator = new HashMap<String, String>();
		config = new PropertiesConfiguration();
		isConf = false;
		props = "";
	}
	
	/**
	 * This method loads the properties for the configuration from an outside source.
	 * @param in A string representation of the configuration, this is passed to {@link CSF#load(Reader)} as a StringReader object.
	 * @throws ConfigurationException Inherited from {@link CSF:load(Reader)}
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
		conf();
		isConf = true;
	}
	
	/**
	 * Checks whether or not there is a configuration file in this object.
	 * @deprecated All CSF objects will use Configuration properties.
	 * @return Returns true if and only if a configuration file exists in this object.
	 */
	public boolean isConf()			{ return isConf; }
	
	/**
	 * Accessor for the configuration properties.
	 * @return The configuration properties.
	 */
	public Configuration config()	{ return config; }
	
	/**
	 * @deprecated Converts configuration file to maps for legacy use.
	 */
	private void conf(){
		Iterator<?> itr = config.getKeys();
		while(itr.hasNext()){
			String key = (String) itr.next();
			if(key.equals("itemType"))
				itemType = config.getString(key);
			else if(key.equals("author") || key.equals("editor") || key.equals("contributor") || key.equals("translator") ){
				if(config.getStringArray(key).length > 1){
					String values = "";
					for(String str: config.getStringArray(key))
						values += "; " + str;
					values = values.replaceFirst("; ", "");
					creator.put(key, values);
				}
				else
				creator.put(key, config.getString(key));
			}
			else
				if(config.getStringArray(key).length > 1){
					String values = "";
					for(String str: config.getStringArray(key))
						values += "; " + str;
					values = values.replaceFirst("; ", "");
					fields.put(key, values);
				}
				else
					fields.put(key, config.getString(key));
			
		}
		System.out.println(toCSF());
	}
	
	/**
	 * @deprecated Converts maps to configuration files for legacy use.
	 */
	public void prop(){
		config.addProperty("itemType", itemType);
		Set<Map.Entry<String,String>> entries = creator.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				config.addProperty(entry.getKey(), entry.getValue());
		entries = fields.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				config.addProperty(entry.getKey(), entry.getValue());
	}
	

	/**
	 * Accessor for the Item's type. Mutator for the Item's fields.
	 * @deprecated Use {@link CSF#config()} instead
	 * @return item's type
	 */
	public String getItemType(){
		return itemType;
	}
	/**
	 * Mutator for Item's type
	 * @deprecated Use {@link CSF#config()} instead
	 * @param itemType
	 */
	public void setItemType(String itemType){
		this.itemType = itemType;
	}
	/**
	 * Accessor for the Item's fields.
	 * @deprecated Use {@link CSF#config()} instead
	 * @return A map of fields
	 */
	public Map<String,String> getFields(){
		return fields;
	}
	
	/**
	 * Mutator for the Item's fields.
	 * @deprecated Use {@link CSF#config()} instead
	 * @param fields A map or fields.
	 */
	public void setFields(Map<String,String> fields){
		this.fields = fields;
	}
	
	
	/**
	 * Accessor for the Item's creators.
	 * @deprecated Use {@link CSF#config()} instead
	 * @return A map of creators.
	 */
	public Map<String,String> getCreator(){
		return creator;
	}
	
	/**
	 * Mutator for the Item's creators.
	 * @deprecated Use {@link CSF#config()} instead
	 * @param creator A map of creators.
	 */
	public void setCreator(Map<String,String> creator){
		this.creator = creator;
	}
	
	/**
	 * Converts the data in this object into a human readable format as a String representation.
	 */
	public String toString()
	{
		//return "itemType: " + itemType + "\nfields: " + fields.toString();
		String output = "itemType: " + itemType + "\ncreator: {";
		Set<Map.Entry<String,String>> entries = creator.entrySet();
		if(!entries.isEmpty())
		{
			for(Map.Entry<String, String> entry: entries)
				output += entry.getKey()+ ": " + entry.getValue() + ",";
			output = output.substring(0, output.length()-1) + "}\nfields: {";
		}
		else
			output += "}\nfields: {";
		entries = fields.entrySet();
		if(!entries.isEmpty())
		{
			for(Map.Entry<String, String> entry: entries)
				 output += entry.getKey()+ ": " + entry.getValue() + ",";
			output = output.substring(0, output.length()-1) + "}";
		}
		else
			output += "}";
		return output;
//		return "itemType: " + itemType +"\ncreator: " + creator.toString() + "\nfields: " + fields.toString();
		
	}
	
	/**
	 * This method returns a Citation Standard Format normalization of the data as a string.
	 * @deprecated Use {@link CSF#config()} instead
	 * @return A string containing the data in Citation Standard Format.
	 */
	public String toCSF(){
		if(fields.isEmpty() && creator.isEmpty())
			return "";
		String yaml = "---\nitemType: " + itemType + "\ncreator:\n";
		Set<Map.Entry<String,String>> entries = creator.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				yaml += "  ? " + entry.getKey()+ "\n  : " + entry.getValue() + "\n";
		yaml += "fields:\n";
		entries = fields.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				yaml += "  ? " + entry.getKey() + "\n  : " + entry.getValue() + "\n";
		yaml = yaml.substring(0,yaml.length()-1);
		return yaml;
	}
	
}
