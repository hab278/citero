package edu.nyu.library.citation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RIS extends Format{

	private String input;
	private CSF item;
	private Map<String,String>  dataOutMap, dataInMap;
	
	public RIS(String input) {
		super(input);
		this.input = input;
		
		item = new CSF();
		dataOutMap = new HashMap<String,String>();
		dataInMap = new HashMap<String, String>();
		map();
		
		doImport(input);
	}
	
	public RIS(CSF item) {
		super(item);
	}

	@Override
	public String toCSF() {
		return item.toCSF();
	}
	
	public CSF CSF(){
		return item;
	}
	
	public String export(){
		return input;
	}
	
	private void processTag(String tag, String value){
		
		if( value.isEmpty() || value == null || value.trim().isEmpty())
			return;
		
		//if input type is mapped
		if(dataInMap.containsKey(tag))
			item.getFields().put(dataInMap.get(tag), value);
		//for types
		else if(tag.equals("TY")){
			for(String val:dataOutMap.keySet())
				if(dataOutMap.get(val).equals(value))
					item.setItemType(val);
			if(item.getItemType().isEmpty())
				if(dataInMap.containsKey(value))
					item.setItemType(dataInMap.get(value));
				else
					item.setItemType("document");
		}
		//for journal type
		else if(tag.equals("JO")){
			if (item.getItemType().equals("conferencePaper"))
				item.getFields().put("conferenceName", value);
			else
				item.getFields().put("publicationTitle", value);
		}
		//for booktitle
		else if(tag.equals("BT"))
		{
			if( item.getItemType().equals("book") || item.getItemType().equals("manuscropt")  )
				item.getFields().put("title", value);
			else if( item.getItemType().equals("BT") )
				item.getFields().put("bookTitle", value);
			else
				item.getFields().put("backupPublicationTitle", value);
		}
		//For t2
		else if(tag.equals("T2"))
			item.getFields().put("backupPublicationTitle", value);
		//for authors, add first name last name?
		else if(tag.equals("AU") || tag.equals("A1")){
			String target = "";
			if(item.getItemType().equals("patent"))
				target = "inventor";
			else
				target = "author";
				item.getCreator().put(target, value);
		}
		//for editor
		else if(tag.equals("ED"))
			item.getCreator().put("editor", value);
		//contributors and assignee
		else if(tag.equals("A2")){
			if(item.getItemType().equals("patent")){
				if(item.getCreator().containsKey("assignee"))
					item.getCreator().put("assignee", item.getCreator().get("assignee")+", "+value);
				else
					item.getCreator().put("assignee", value);
			}
			else
				item.getCreator().put("contributor", value);			
		}
		//date TODO split
		else if(tag.equals("Y1") || tag.equals("PY")){
			item.getFields().put("date", value);
		}
		//date 2 TODO split?
		else if(tag.equals("Y2")){
				
			if (item.getItemType().equals("patent"))
					item.getFields().put("filingDate", value);
			else
				item.getFields().put("accessDate", value);
		}
		//note
		else if(tag.equals("N1")){
			if(item.getFields().containsKey("title"))
				if(!value.equals(item.getFields().get("title")))
					if(value.contains("<br>") || value.contains("<p>"))
						item.getFields().put("note", value);
					else
						item.getFields().put("note", "<p>" + value
								.replaceAll("/n/n", "</p><p>")
								.replaceAll("/n", "<br/>")
								.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
								.replaceAll("  ", "&nbsp;"));
		}
		//abstract
		else if(tag.equals("N2") || tag.equals("AB"))
		{
			if(item.getFields().containsKey("abstractNote"))
				item.getFields().put("abstractNote", item.getFields().get("abstractNote")+"\n"+value);
			else
				item.getFields().put("abstractNote", value);
		}
		//start page
		else if(tag.equals("SP"))
		{
			if(!item.getFields().containsKey("pages")){
				item.getFields().put("pages", value);
				if(item.getItemType().equals("book"))
					item.getFields().put("numPages", value);
			}
			else{
				if(item.getFields().get("pages").charAt(0) == '-')
					item.getFields().put("pages", value + item.getFields().get("pages"));
				else
					item.getFields().put("pages", item.getFields().get("pages") +", " + value);
			}
				
		}
		//end page
		else if(tag.equals("EP")){
			if(!value.isEmpty()){
				if(!item.getFields().containsKey("pages"))
					item.getFields().put("pages", value);
				else if( !item.getFields().get("pages").equals(value) ){
					item.getFields().put("pages", "-"+value);
					if(item.getItemType().equals("book") && item.getFields().containsKey("numPages"))
						item.getFields().remove("numPages");
				}
			}
		}
		//ISSN/ISBN
		else if(tag.equals("SN")){
			if(!item.getFields().containsKey("ISBN"))
				item.getFields().put("ISBN", value);
			if(!item.getFields().containsKey("ISSN"))
				item.getFields().put("ISSN", value);
		}
		
	}
	
	private void doImport(String input){
		String tag;
		String value;
		String line;
		String output = "";
		Scanner scanner;
		scanner = new Scanner(this.input);
		do{
			line = scanner.nextLine();
			line = line.replaceAll("^\\s+", "");
		}
		while(scanner.hasNextLine() && !line.substring(0, 6).matches("^TY {1,2}- "));
		
		tag = "TY";
		value = line.substring(line.indexOf('-')+1).trim();
		
		String rawLine;
		while(scanner.hasNextLine()){
			rawLine = scanner.nextLine();
			line = rawLine.replaceFirst("^\\s+", "");
			if(line.matches("^([A-Z0-9]{2}) {1,2}-(?: ([^\n]*))?")){
				if(tag.matches("^[A-Z0-9]{2}"))
					if(output.isEmpty())
						processTag(tag,value);
					else
						processTag(tag, value);
				tag = line.substring(0, line.indexOf('-')).trim();
				value = line.substring(line.indexOf('-')+1).trim();
				if(tag.equals("ER"))
					return;
			}
			else
				if( tag == "N1" || tag == "N2" || tag == "AB" || tag == "KW")
					value += "\n"+rawLine;
				else if( !tag.isEmpty() )
					if(value.charAt(value.length()-1) == ' ')
						value += rawLine;
					else
						value += " "+rawLine;
		}
		
	}
	
	private void map(){
		
		//output mapping
		dataOutMap.put( "book" , "BOOK" );
		dataOutMap.put( "bookSection" , "CHAP" );
		dataOutMap.put( "journalArticle" , "JOUR" );
		dataOutMap.put( "magazineArticle" , "MGZN" );
		dataOutMap.put( "newspaperArticle" , "NEWS" );
		dataOutMap.put( "thesis" , "THES" );
		dataOutMap.put( "letter" , "PCOMM" );
		dataOutMap.put( "manuscript" , "PAMP" );
		dataOutMap.put( "interview" , "PCOMM" );
		dataOutMap.put( "film" , "MPCT" );
		dataOutMap.put( "artwork" , "ART" );
		dataOutMap.put( "report" , "RPRT" );
		dataOutMap.put( "bill" , "BILL" );
		dataOutMap.put( "case" , "CASE" );
		dataOutMap.put( "hearing" , "HEAR" );
		dataOutMap.put( "patent" , "PAT" );
		dataOutMap.put( "statute" , "STAT" );
		dataOutMap.put( "map" , "MAP" );
		dataOutMap.put( "blogPost" , "ELEC" );
		dataOutMap.put( "webpage" , "ELEC" );
		dataOutMap.put( "instantMessage" , "ICOMM" );
		dataOutMap.put( "forumPost" , "ICOMM" );
		dataOutMap.put( "email" , "ICOMM" );
		dataOutMap.put( "audioRecording" , "SOUND" );
		dataOutMap.put( "presentation" , "GEN" );
		dataOutMap.put( "videoRecording" , "VIDEO" );
		dataOutMap.put( "tvBroadcast" , "GEN" );
		dataOutMap.put( "radioBroadcast" , "GEN" );
		dataOutMap.put( "podcast" , "GEN" );
		dataOutMap.put( "computerProgram" , "COMP" );
		dataOutMap.put( "conferencePaper" , "CONF" );
		dataOutMap.put( "document" , "GEN" );
		
		//input mapping
		dataInMap.put( "ABST" , "journalArticle" );
		dataInMap.put( "ADVS" , "film" );
		dataInMap.put( "CTLG" , "magazineArticle" );
		dataInMap.put( "INPR" , "manuscript" );
		dataInMap.put( "JFULL" , "journalArticle" );
		dataInMap.put( "PAMP" , "manuscript" );
		dataInMap.put( "SER" , "book" );
		dataInMap.put( "SLIDE" , "artwork" );
		dataInMap.put( "UNBILL" , "manuscript" );
		dataInMap.put( "CPAPER" , "conferencePaper" );
		dataInMap.put( "WEB" , "webpage" );
		dataInMap.put( "EDBOOK" , "book" );
		dataInMap.put( "MANSCPT" , "manuscript" );
		dataInMap.put( "GOVDOC" , "document" );
		
		dataInMap.put( "TI" , "title" );
		dataInMap.put( "CT" , "title" );
		dataInMap.put( "CY" , "place" );
		dataInMap.put( "ST" , "shortTitle" );
		dataInMap.put( "DO" , "DOI" );
		
		dataInMap.put( "ID" , "itemID" );
		dataInMap.put( "T1" , "title" );
		dataInMap.put( "T2" , "publicationTitle" );
		dataInMap.put( "T3" , "series" );
		dataInMap.put( "T2" , "bookTitle" );
		dataInMap.put( "CY" , "place" );
		dataInMap.put( "JA" , "journalAbbreviation" );
		dataInMap.put( "M3" , "DOI" );
		
		dataInMap.put( "ID" , "itemID" );
		dataInMap.put( "T1" , "title" );
		dataInMap.put( "T3" , "series" );
		dataInMap.put( "JF" , "publicationTitle" );
		dataInMap.put( "CY" , "place" );
		dataInMap.put( "JA" , "journalAbbreviation" );
		dataInMap.put( "M3" , "DOI" );
		
	}

}
