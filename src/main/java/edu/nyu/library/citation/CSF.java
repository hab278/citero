package edu.nyu.library.citation;


import java.util.Map;

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
		return "itemType: " + itemType +"\ncreator: " + creator.toString() + "\nfields: " + fields.toString();
		
	}
	
}
