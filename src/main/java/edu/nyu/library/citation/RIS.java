package edu.nyu.library.citation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
/**
 * RIS format class. Imports from RIS formatted strings and exports to RIS formatted strings.
 * @author hab278
 *
 */
public class RIS extends Format{

	private String input, prop;
	private CSF item;
	private Map<String,String>  dataOutMap, dataInMap;
	
	/**
	 * Default constructor, instantiates data maps and CSF item.
	 * @param input A string representation of the data payload.
	 */
	public RIS(String input) {
		super(input);
		this.input = input;
		prop = "";
		item = new CSF();
		dataOutMap = new HashMap<String,String>();
		dataInMap = new HashMap<String, String>();
		map();
		
		doImport(input);
		try {
			item.load(prop);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.prop();
	}
	
	/** 
	 * Constructor that accepts a CSF object. Does the same as the default Constructor.
	 * @param item The CSF object, it gets loaded into this object.
	 */
	public RIS(CSF item) {
		super(item);
		this.item = item;
		prop = "";
		input = item.toCSF();
		dataOutMap = new HashMap<String,String>();
		dataInMap = new HashMap<String, String>();
		map();
		item.prop();
	}
	

	public CSF CSF(){
		return item;
	}
	

	public String export(){
		
		String itemType = item.config().getString("itemType");
		if( itemType.equals("note") || itemType.equals("attachment"))
			return input;
		
		//first get Type
		String ris = "TY  - ";
		if(dataOutMap.containsKey(itemType))
			ris += dataOutMap.get(itemType)+"\n";
		else
			ris += "GEN\r\n";
		
		Iterator<?> itr = item.config().getKeys();
		while( itr.hasNext() ){
			String key = (String) itr.next();
			String[] value = item.config().getStringArray(key);
			if( key.equals("author") || key.equals("inventor") )
				for( int i = 0; i < value.length; ++i )
					if( i == 0 )
						ris += "A1  - " + value[i] + "\n";
					else
						ris += "A2  - " + value[i] + "\n";
			else if( key.equals("editor") )
				for( String str : value )
					ris += "ED  - " + str + "\n";
			else if( key.equals("contributor") || key.equals("assignee") )
				for( String str : value )
					ris += "A2  - " + str + "\n";
			else if( key.equals("volume") || key.equals("applicationNumber") || key.equals("reportNumber"))
				ris += "VL  - " + value[0] + "\n";
			else if( key.equals("issue") || key.equals("patentNumber") )
				ris += "IS  - " + value[0] + "\n";
			else if(key.equals("publisher") || key.equals("references"))
				ris += "PB  - " + value[0] + "\n";
			else if(key.equals("date"))
				ris += "PY  - " + value[0] + "\n";
			else if(key.equals("filingDate"))
				ris += "Y2  - " + value[0] + "\n";
			else if(key.equals("abstractNote"))
				ris += "N2  - " + value[0].replaceAll("(?:\r\n?|\n)", "\n") + "\n";
			else if(key.equals("pages"))
				if(itemType.equals("book"))
					ris += "EP  - " + value[0] + "\n";
				else{
					ris += "SP  - " + value[0].split("-", 0)[0] + "\n";
					ris += "SP  - " + value[0].split("-", 0)[1] + "\n";
				}
			else if(key.equals("ISBN"))
				ris += "SN  - " + value[0] + "\n";
			else if(key.equals("ISSN"))
				ris += "SN  - " + value[0] + "\n";
			else if(key.equals("URL"))
				ris += "UR  - " + value[0] + "\n";
			else if(key.equals("source") && value[0].substring(0, 7) == "http://")
				ris += "UR  - " + value[0] + "\n";
		}
		ris += "ER  -\n\n";
		System.out.println(ris);
		return ris;
	}
	
	private void addProperty(String field, String value){
		prop += field + ": " + value + "\n";
	}
	private void processTag(String tag, String value){
		
		if( value.isEmpty() || value == null || value.trim().isEmpty())
			return;
		
		String itemType = "";
		//if input type is mapped
		if(dataInMap.containsKey(tag))
			addProperty(dataInMap.get(tag), value);
		//for types
		else if(tag.equals("TY")){
			for(String val:dataOutMap.keySet())
				if(dataOutMap.get(val).equals(value))
					itemType = val;
			if(itemType.isEmpty())
				if(dataInMap.containsKey(value))
					itemType = dataInMap.get(value);
				else
					itemType = "document";
			addProperty("itemType", itemType);
		}
		//for journal type
		else if(tag.equals("JO")){
			if (itemType.equals("conferencePaper"))
				addProperty("conferenceName", value);
			else
				addProperty("publicationTitle", value);
		}
		//for booktitle
		else if(tag.equals("BT"))
		{
			if( itemType.equals("book") || itemType.equals("manuscropt")  )
				addProperty("title", value);
			else if( itemType.equals("bookSection") )
				addProperty("bookTitle", value);
			else
				addProperty("backupPublicationTitle", value);
		}
		//For t2
		else if(tag.equals("T2"))
			addProperty("backupPublicationTitle", value);
		//for authors, add first name last name?
		else if(tag.equals("AU") || tag.equals("A1")){
			String target = "";
			if(itemType.equals("patent"))
				target = "inventor";
			else
				target = "author";
			addProperty(target, value);
		}
		//for editor
		else if(tag.equals("ED"))
			addProperty("editor", value);
		//contributors and assignee
		else if(tag.equals("A2")){
			if(itemType.equals("patent")){
				if(prop.contains("assignee"))
					addProperty("assignee", value);
				else
					addProperty("assignee", value);
			}
			else
				addProperty("contributor", value);			
		}
		else if(tag.equals("Y1") || tag.equals("PY")){
			addProperty("date", value);
		}
		else if(tag.equals("Y2")){
				
			if (itemType.equals("patent"))
					addProperty("filingDate", value);
			else
				addProperty("accessDate", value);
		}
		//note
		else if(tag.equals("N1")){
			if(prop.contains("title"))
				if( !prop.contains("title: " + value + "\n"))
					if(value.contains("<br>") || value.contains("<p>"))
						addProperty("note", value);
					else
						addProperty("note", "<p>" + value
								.replaceAll("/n/n", "</p><p>")
								.replaceAll("/n", "<br/>")
								.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
								.replaceAll("  ", "&nbsp;"));
		}
		//abstract
		else if(tag.equals("N2") || tag.equals("AB"))
				addProperty("abstractNote", value);
		// keywords/tags
		else if(tag == "KW")
				addProperty("tags",value.replaceAll("\n", ","));
				
		//start page
		else if(tag.equals("SP"))
		{
			addProperty("startPage", value);
			if(itemType.equals("book"))
				addProperty("numPages", value);
		}
		//end page
		else if(tag.equals("EP")){
			addProperty("startPage", value);
			if(itemType.equals("book") && !prop.contains("numPages"))
				addProperty("numPages", value);
		}
		//ISSN/ISBN
		else if(tag.equals("SN")){
			if(!prop.contains("ISBN"))
				addProperty("ISBN", value);
			if(!prop.contains("ISSN"))
				addProperty("ISSN", value);
		}
		//URL
		else if(tag.equals("UR") || tag.equals("L3") || tag.equals("L2") || tag.equals("L4") )
		{
				addProperty("url", value);
			
//			if(tag.equals("UR"))
//				item.getAttachments().put("url", value);
//			else if(tag.equals("L1")){
//				item.getAttachments().put("url", value);
//				item.getAttachments().put("mimeType", "application/pdf");
//				item.getAttachments().put("title", "Full Text (PDF)");
//				item.getAttachments().put("dowloadable", "true");
//				
//			}
//			else if(tag.equals("L2")){
//				item.getAttachments().put("url", value);
//				item.getAttachments().put("mimeType", "text/html");
//				item.getAttachments().put("title", "Full Text (HTML)");
//				item.getAttachments().put("dowloadable", "true");
//				
//			}
//			else if(tag.equals("L4")){
//				item.getAttachments().put("url", value);
//				item.getAttachments().put("title", "Image");
//				item.getAttachments().put("dowloadable", "true");
//				
//			}
		}
		//issue number
		else if( tag == "IS"){
			if(itemType.equals("patent"))
				addProperty("patentNumber", value);
			else
				addProperty("issue", value);
		}
		//volume
		else if(tag =="VL"){
			if(itemType.equals("patent"))
				addProperty("applicationNumber", value);
			else if(itemType.equals("report"))
				addProperty("reportNumber", value);
			else
				addProperty("volume", value);
		}
		//publisher/references
		else if(tag.equals("PB"))
		{
			if(itemType.equals("patent"))
				addProperty("references", value);
			else
				addProperty("publisher", value);
		}
		//Misc fields
		else if(tag.equals("M1") || tag.equals("M2")){
			addProperty("extra", value);
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
				if(tag.equals("ER")){
					System.out.println(prop);
					return;
				}
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
		if(!tag.isEmpty() && !tag.equals("ER")){
			processTag(tag, value);
			completeItem();
		}
		
	}
	
	private void completeItem() {
		if(prop.contains("backupPublicationTitle")) {
			if(!prop.contains("publicationTitle")) {
				addProperty("publicationTitle" , prop.substring(prop.indexOf("backupPublicationTitle:", 0) + 23, prop.indexOf("\n", prop.indexOf("backupPublicationTitle:", 0) + 23)));
			}
			prop.replaceAll("backupPublicationTitle:\\s*[a-zA-Z0-9\\-\\\\_]*", "");
		}

		if(prop.contains("DOI")) {
			prop.replaceAll("\\s*doi:\\s*", "");
		}

		// hack for sites like Nature, which only use JA, journal abbreviation
		if(prop.contains("journalAbbreviation") && !prop.contains("publicationTitle")){
			addProperty("publicationTitle", prop.substring(prop.indexOf("journalAbbreviation:",0) + 20, prop.indexOf("\n", prop.indexOf("journalAbbreviation:",0) + 20)));
		}
		// Hack for Endnote exports missing full title
		if(prop.contains("shortTitle") && !prop.contains("title")){
			addProperty("title", prop.substring(prop.indexOf("shortTitle:", 0)+11,prop.indexOf("\n", prop.indexOf("shortTitle:", 0)+11)));
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
