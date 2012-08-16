package edu.nyu.library.citation;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class CSF {

	private String itemType;
	private Map<String,String> fields;
	private Map<String,String> creator;
	private Map<String,String> attachments;
	
	public CSF()
	{
		itemType = "";
		fields = new HashMap<String, String>();
		creator = new HashMap<String, String>();
		attachments = new HashMap<String, String>();
		
	}
	
	public CSF(String input)
	{
		Constructor constructor = new Constructor(CSF.class);
		TypeDescription itemDescription = new TypeDescription(CSF.class);
		
		itemDescription.putMapPropertyType("creator", String.class, String.class);
		itemDescription.putMapPropertyType("fields", String.class, String.class);
		itemDescription.putMapPropertyType("attachments", String.class, String.class);
		
		constructor.addTypeDescription(itemDescription);
		
		Yaml yaml = new Yaml(constructor);
		
		CSF file  = (CSF)yaml.load(input);
		
		this.itemType = file.getItemType();
		this.fields = file.getFields();
		this.attachments = file.getAttachments();
		this.creator = file.getCreator();
	}
	
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
	
	public Map<String,String> getAttachments(){
		return attachments;
	}
	
	public void setAttachments(Map<String,String> attachments){
		this.attachments = attachments;
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
			output = output.substring(0, output.length()-1) + "}\nattachments: {";
		}
		else
			output += "}\nattachments: {";
		entries = attachments.entrySet();
		if(!entries.isEmpty())
		{
			for(Map.Entry<String, String> entry: entries)
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
		Set<Map.Entry<String,String>> entries = creator.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				yaml += "  ? " + entry.getKey()+ "\n  : " + entry.getValue() + "\n";
		yaml += "fields:\n";
		entries = fields.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				yaml += "  ? " + entry.getKey() + "\n  : " + entry.getValue() + "\n";
		yaml += "attachments:\n";
		entries = attachments.entrySet();
		if(!entries.isEmpty())
			for(Map.Entry<String, String> entry: entries)
				yaml += "  ? " + entry.getKey() + "\n  : " + entry.getValue() + "\n";
		yaml = yaml.substring(0,yaml.length()-1);
		return yaml;
	}
	
}
