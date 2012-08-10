package edu.nyu.library.citation;


import java.util.Map;
import java.util.Set;

public class CSF {

	private String itemType;
	private Map<String,String> fields;
	private Map<String,String> creator;
	
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
		for(Map.Entry<String, String> entry: entries)
			 output += entry.getKey()+ ": " + entry.getValue() + ",";
		output = output.substring(0, output.length()-1) + "}\nfields: {";
		entries = fields.entrySet();
		for(Map.Entry<String, String> entry: entries)
			 output += entry.getKey()+ ": " + entry.getValue() + ",";
		output = output.substring(0, output.length()-1) + "}";
		return output;
		//return "itemType: " + itemType +"\ncreator: " + creator.toString() + "\nfields: " + fields.toString();
		
	}
	
	public String toYaml(){
		String yaml = "---\nitemType: " + itemType + "\ncreator:\n";
		Set<Map.Entry<String,String>> entries = creator.entrySet();
		for(Map.Entry<String, String> entry: entries)
			 yaml += "  ? " + entry.getKey()+ "\n  : " + entry.getValue() + "\n";
		yaml = yaml.substring(0, yaml.length()-1) + "fields:\n";
		entries = fields.entrySet();
		for(Map.Entry<String, String> entry: entries)
			 yaml += "  ? " + entry.getKey() + "\n  : " + entry.getValue() + "\n";
		return yaml;
	}
	
}
