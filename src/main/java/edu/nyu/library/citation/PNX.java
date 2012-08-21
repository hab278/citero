package edu.nyu.library.citation;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Splitter;


public class PNX extends Format{
	
	private CSF item;
	private String input;

	public PNX(String input) {
		super(input);
		this.input = input;
		item = new CSF();
		doImport();
	}
	
	public void doImport(){
		XMLStringParser xml = new XMLStringParser(input);
		String itemType = xml.xpath("//display/type");
		
		
		if(itemType.equals("book") || item.equals("Books"))
			item.setItemType("book");
		else if (itemType.equals("audio"))
			item.setItemType("audioRecording");
		else if (itemType.equals("video"))
			item.setItemType("videoRecording");
		else if (itemType.equals("report"))
			item.setItemType("report");
		else if (itemType.equals("webpage"))
			item.setItemType("webpage");
		else if (itemType.equals("article"))
			item.setItemType("article");
		else if (itemType.equals("thesis"))
			item.setItemType("thesis");
		else if (itemType.equals("map"))
			item.setItemType("map");
		else
			item.setItemType("document");
		
		item.getFields().put("title", xml.xpath("//display/title"));
		
		String creators =  xml.xpath("//display/creator");
		String contributors = xml.xpath("//display/contributor");
		
		if (creators.isEmpty() && !contributors.isEmpty()) { // <creator> not available using <contributor> as author instead
			creators = contributors;
			contributors = "";
		}

		if (creators.isEmpty() && contributors.isEmpty()){
			creators = xml.xpath("//addata/addau");
		}
		
		for(String str: Splitter.on("; ").trimResults().split(creators))
			if(item.getCreator().containsKey("author"))
				item.getCreator().put("author", item.getCreator().get("author") + "<br />" +str);
			else
				item.getCreator().put("author", str);
		
		for(String str: Splitter.on("; ").trimResults().split(contributors))
			if(item.getCreator().containsKey("contributor"))
				item.getCreator().put("contributor", item.getCreator().get("contributor") + "<br />" +str);
			else
				item.getCreator().put("contributor", str);
		
		if(xml.xpath("//display/publisher").isEmpty()){
			if(xml.xpath("//display/publisher").contains(" : "))
				for(String str : Splitter.on(" : ").split(xml.xpath("//display/publisher")))
					if(item.getFields().containsKey("place"))
						item.getFields().put("publisher", str);
					else
						item.getFields().put("place", str);
		}
		
	}
	
	public PNX(CSF item) {
		super(item);
	}

	@Override
	public String toCSF() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public String export() {
		// TODO Auto-generated method stub
		return "";
	}
}
