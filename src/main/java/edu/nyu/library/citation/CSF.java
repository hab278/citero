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


public class CSF {

	private Configuration config;
	
	//Deprecated
	private String itemType;
	private Map<String,String> fields;
	private Map<String,String> creator;
	
	public CSF()
	{
		itemType = "";
		fields = new HashMap<String, String>();
		creator = new HashMap<String, String>();
		config = new PropertiesConfiguration();
	}
	
	public void load(String in) throws ConfigurationException		{ load(new StringReader(in)); }

	public void load(Reader in) throws ConfigurationException
	{
		((PropertiesConfiguration)config).load(in);
		conf();
	}
	
	public Configuration config()	{ return config; }
	
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
	}
	
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
	
//	public CSF(String input)
//	{
//		Constructor constructor = new Constructor(CSF.class);
//		TypeDescription itemDescription = new TypeDescription(CSF.class);
//		
//		itemDescription.putMapPropertyType("creator", String.class, String.class);
//		itemDescription.putMapPropertyType("fields", String.class, String.class);
//		itemDescription.putMapPropertyType("attachments", String.class, String.class);
//		
//		constructor.addTypeDescription(itemDescription);
//		
//		Yaml yaml = new Yaml(constructor);
//		
//		CSF file  = (CSF)yaml.load(input);
//		System.out.println(file.getCreator());
//		this.itemType = file.getItemType();
//		this.fields = file.getFields();
//		this.attachments = file.getAttachments();
//		this.creator = file.getCreator();
//	}
	
	public String getItemType(){
		return itemType;
	}
	
	public void setItemType(String itemType){
		this.itemType = itemType;
	}
	
	public Map<String,String> getFields(){
		return fields;
	}
	
	public void setFields(Map<String,String> fields){
		this.fields = fields;
	}
	
	
	public Map<String,String> getCreator(){
		return creator;
	}
	
	public void setCreator(Map<String,String> creator){
		this.creator = creator;
	}
	
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
