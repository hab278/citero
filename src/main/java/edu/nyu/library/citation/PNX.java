package edu.nyu.library.citation;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		if(input.equals("test"))
			return;
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
			item.setItemType("journalArticle");
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
		if(!contributors.isEmpty())
			for(String str: Splitter.on("; ").trimResults().split(contributors))
				if(item.getCreator().containsKey("contributor"))
					item.getCreator().put("contributor", item.getCreator().get("contributor") + "<br />" +str);
				else
					item.getCreator().put("contributor", str);
		
		if(!xml.xpath("//display/publisher").isEmpty()){
			if(xml.xpath("//display/publisher").contains(" : "))
				for(String str : Splitter.on(" : ").split(xml.xpath("//display/publisher")))
					if(item.getFields().containsKey("place"))
						item.getFields().put("publisher", str.replaceAll(",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", ""));
					else
						item.getFields().put("place", str.replaceAll(",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", ""));
			else
				item.getFields().put("publisher", xml.xpath("//display/publisher").replaceAll(",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", ""));
		}
		
		if(!xml.xpath("//display/creationdate|//search/creationdate").isEmpty())
			item.getFields().put("date", xml.xpath("//display/creationdate|//search/creationdate"));
		
		if(!xml.xpath("//display/language").isEmpty())
			item.getFields().put("language", xml.xpath("//display/language"));
		

		String pages;
		pages = xml.xpath("//display/format");
		if(!pages.isEmpty())
			if(pages.matches(".*[0-9]+.*")){
				pages = pages.replaceAll("[\\(\\)\\[\\]]", "").replaceAll("\\D", " ").trim().split(" ")[0];
				System.out.println(pages);
				item.getFields().put("pages", pages);
				item.getFields().put("numPages", pages);
			}
		
		if(!xml.xpath("//display/identifier").isEmpty())
		{
			String [] identifiers = xml.xpath("//display/identifier").split(";");
			for( String str: identifiers){
				String key = str.contains("isbn")? "ISBN" : "ISSN";
				if(item.getFields().containsKey(key))
					item.getFields().put(key, item.getFields().get(key) +" ; " + str.trim().replaceAll("\\D", ""));
				else
					item.getFields().put(key, str.trim().replaceAll("\\D", ""));
			}
		}
		
		if(!xml.xpath("//display/edition").isEmpty())
			item.getFields().put("edition", xml.xpath("//display/edition"));
		
		if(!xml.xpath("//search/subject").isEmpty())
			item.getFields().put("tags", xml.xpath("//search/subject"));
		
		if(!xml.xpath("//enrichment/classificationlcc").isEmpty())
			item.getFields().put("callNumber", xml.xpath("//enrichment/classificationlcc"));
		
	}
	
	public PNX(CSF item) {
		super(item);
		this.item = item;
	}

	@Override
	public String toCSF() {
		// TODO Auto-generated method stub
		return item.toCSF();
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public String export() {
		XMLStringParser xml = new XMLStringParser();
		if(item.getItemType().equals("audioRecording"))
			xml.build("//display/type", "audio");
		else if(item.getItemType().equals("videoRecording"))
			xml.build("//display/type", "video");
		else if(item.getItemType().equals("journalArticle"))
			xml.build("//display/type", "article");
		else
			xml.build("//display/type", item.getItemType());
		Set<Map.Entry<String, String>> entries = item.getCreator().entrySet();
		for(Entry<String,String> entry: entries)
			if(entry.getKey().equals("author"))
				for(String str: Splitter.on("<br />").omitEmptyStrings().trimResults().split(entry.getValue()))
					xml.build("//display//creator", str);
			else if(entry.getKey().equals("contributor"))
				for(String str: Splitter.on("<br />").omitEmptyStrings().trimResults().split(entry.getValue()))
					xml.build("//display//contributor", str);
			else if(entry.getKey().equals("publisher"))
				if(item.getFields().containsKey("place"))
					xml.build("//display/publisher", entry.getValue() + " : " + item.getFields().get("place"));
				else
					xml.build("//display/publisher", entry.getValue());
			else if(entry.getKey().equals("date"))
				xml.build("//display/creationdate", entry.getValue());
			else if(entry.getKey().equals("language"))
				xml.build("//display/language", entry.getValue());
			else if(entry.getKey().equals("pages"))
				xml.build("//display/format", entry.getValue());
			else if(entry.getKey().equals("ISBN"))
				xml.build("//display/identifier", "$$Cisbn$$V"+entry.getValue() );
			else if(entry.getKey().equals("ISSN"))
				xml.build("//display/identifier", "$$Cissn$$V"+entry.getValue() );
			else if(entry.getKey().equals("edition"))
				xml.build("//display/edition", entry.getValue());
			else if(entry.getKey().equals("tags"))
				xml.build("//search/subject", entry.getValue());
			else if(entry.getKey().equals("callNumber"))
				xml.build("//enrichment/classificationlcc", entry.getValue());
		return xml.out();
	}
}
