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
	
	private void processTag(String tag, String data){
		//For the Tag item type
		if(tag.equals("TY")){
			for(String val:dataOutMap.keySet())
				if(dataOutMap.get(val).equals(data))
					item.setItemType(val);
			if(item.getItemType().isEmpty())
				if(dataInMap.containsKey(data))
					item.setItemType(dataInMap.get(data));
				else
					item.setItemType("document");
		}
		else if(dataInMap.containsKey(tag))
			item.getFields().put(dataInMap.get(tag), data);
		else if(tag.equals("AU") || tag.equals("A1")){
			String target = "";
			if(item.getItemType().equals("patent"))
				target = "inventor";
			else
				target = "author";
			item.getCreator().put(target, data);
		}
		else if(tag.equals("ED"))
			item.getCreator().put("editor", data);
		
	}
	
	private void doImport(String input){
		String tag;
		String data;
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
		data = line.substring(line.indexOf('-')+1).trim();
		
		String rawLine;
		while(scanner.hasNextLine()){
			rawLine = scanner.nextLine();
			line = rawLine.replaceFirst("^\\s+", "");
			if(line.matches("^([A-Z0-9]{2}) {1,2}-(?: ([^\n]*))?")){
				if(tag.matches("^[A-Z0-9]{2}"))
					if(output.isEmpty())
						processTag(tag,data);
					else
						processTag(tag, data);
				tag = line.substring(0, line.indexOf('-')).trim();
				data = line.substring(line.indexOf('-')+1).trim();
				if(tag.equals("ER"))
					return;
			}
			else
				if( tag == "N1" || tag == "N2" || tag == "AB" || tag == "KW")
					data += "\n"+rawLine;
				else if( !tag.isEmpty() )
					if(data.charAt(data.length()-1) == ' ')
						data += rawLine;
					else
						data += " "+rawLine;
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
