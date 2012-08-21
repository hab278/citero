package edu.nyu.library.citation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


public class CSF {

	private String itemType;
	private Map<String,ArrayList<String>> fields;
	private Map<String,ArrayList<String>> creator;
	
	public CSF()
	{
		itemType = "";
		fields = new HashMap<String, ArrayList<String>>();
		creator = new HashMap<String, ArrayList<String>>();
		
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
		Map<String, String> fields = new HashMap<String, String>();
		Joiner joiner = Joiner.on("; ");
		Set<Map.Entry<String, ArrayList<String>>> entries = this.fields.entrySet();
		for(Map.Entry<String, ArrayList<String>> entry: entries)
			fields.put(entry.getKey(), joiner.join(entry.getValue().iterator()));
		return fields;
	}
	
	public void setFields(Map<String,String> fields){
		Map<String, ArrayList<String>> fieldz = new HashMap<String, ArrayList<String>>();
		Set<Map.Entry<String, String>> entries = fields.entrySet();
		for(Map.Entry<String, String> entry: entries)
			fieldz.put(entry.getKey(), Lists.newArrayList(Splitter.on("; ").split(entry.getValue())));
		this.fields = fieldz;
	}
	
	
	public Map<String,String> getCreator(){
		Map<String, String> creator = new HashMap<String, String>();
		Joiner joiner = Joiner.on("; ");
		Set<Map.Entry<String, ArrayList<String>>> entries = this.creator.entrySet();
		for(Map.Entry<String, ArrayList<String>> entry: entries)
			creator.put(entry.getKey(), joiner.join(entry.getValue().iterator()));
		return creator;
	}
	
	public void setCreator(Map<String,String> creator){
		Map<String, ArrayList<String>> creators = new HashMap<String, ArrayList<String>>();
		Set<Map.Entry<String, String>> entries = creator.entrySet();
		for(Map.Entry<String, String> entry: entries)
			creators.put(entry.getKey(), Lists.newArrayList(Splitter.on("; ").split(entry.getValue())));
		this.creator = creators;
	}
	
	public String toString()
	{
		//return "itemType: " + itemType + "\nfields: " + fields.toString();
		String output = "itemType: " + itemType + "\ncreator: {";
		Set<Entry<String, ArrayList<String>>> entries = creator.entrySet();
		if(!entries.isEmpty())
		{
			for(Map.Entry<String, ArrayList<String>> entry: entries)
				output += entry.getKey()+ ": " + entry.getValue() + ",";
			output = output.substring(0, output.length()-1) + "}\nfields: {";
		}
		else
			output += "}\nfields: {";
		entries = fields.entrySet();
		if(!entries.isEmpty())
		{
			for(Map.Entry<String, ArrayList<String>> entry: entries)
				 output += entry.getKey()+ ": " + entry.getValue() + ",";
			output = output.substring(0, output.length()-1) + "}";
		}
		else
			output += "}";
		return output;
		//return "itemType: " + itemType +"\ncreator: " + creator.toString() + "\nfields: " + fields.toString();
		
	}
	
	public String toCSF(){
		String yaml = "---\nitemType: " + itemType + "\ncreator:\n";
		Set<Map.Entry<String,ArrayList<String>>> entries = creator.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, ArrayList<String>> entry: entries)
				yaml += "  ? " + entry.getKey()+ "\n  : " + entry.getValue() + "\n";
		yaml += "fields:\n";
		entries = fields.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, ArrayList<String>> entry: entries)
				yaml += "  ? " + entry.getKey() + "\n  : " + entry.getValue() + "\n";
		yaml = yaml.substring(0,yaml.length()-1);
		return yaml;
	}
	
}
