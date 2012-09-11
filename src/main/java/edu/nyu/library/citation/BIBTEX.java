package edu.nyu.library.citation;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.ConfigurationException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;


public class BIBTEX extends Format{
	
	private String input, prop;
	private StringReader reader;
	private Map<String,String> fieldMap;
	private Map<String,String> typeMap;
	private Map<String,String> exportTypeMap;
	private CSF item;

	public BIBTEX(String input) {
		super(input);
		this.input = input;
		item = new CSF();
		loadVars();
		
		doImport();
		try {
			item.load(prop);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(prop);
	}

	public BIBTEX(CSF item) {
		super(item);
		this.item = item;
		input = item.toCSF();
		item.prop();
		loadVars();
	}

	private void loadVars()
	{
		reader = new StringReader(this.input);
		prop = "";
		fieldMap = new HashMap<String,String>();
		typeMap = new HashMap<String,String>();
		exportTypeMap = new HashMap<String,String>();
		populate();
	}
	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}
	
	private String mapValue( String key, String value ){
		String out = ",\n\t" + key +" = {" + value + "}";
		
		return out;
	}

	@Override
	public String export() {
		String export = "";
		export += "@" + ( exportTypeMap.containsKey(item.config().getString("itemType")) ? exportTypeMap.get(item.getItemType()) : "misc" ) 
				+"{" + citeKey();
		Iterator<?> itr = item.config().getKeys();
		iterate:
			while(itr.hasNext()){
				String key = (String) itr.next();
				for(Entry<String, String>  entry : fieldMap.entrySet())
					if(entry.getValue().equals(key)){
						export += mapValue(key, item.config().getString(key));
						continue iterate;
					}
				if(exportTypeMap.containsKey(item.config().getString(key)))
					export += mapValue(key, exportTypeMap.get(item.config().getString(key)));
				else
					export += mapValue(key, item.config().getString(key));
				System.out.println(key);
			}
		System.out.println(item.props);
		return export +"}";
	}
	
	private String citeKey(){
		String cite = "";
		//Citekey will be first lastname, first word of title, year
//		if(item.config().containsKey("creator.author"))
//			cite += item.config().getString("creator.author");
//		if(item.config().containsKey("title"))
//			cite += item.config().getString("title");
//		if(item.config().containsKey("year"))
//			cite += item.config().getString("year");
//		else
//			cite+= "????";
		if(item.getCreator().containsKey("author"))
			cite += item.getCreator().get("author").split(";")[0].split(",")[0].toLowerCase();
		else if(item.getCreator().containsKey("contributor"))
			cite += item.getCreator().get("contributor").split(";")[0].split(",")[0].toLowerCase();
		if(item.getFields().containsKey("title "))
			cite += item.getFields().get("title").split(",")[0];
		if(item.getFields().containsKey("date"))
			cite = item.getFields().get("date").split(",")[0];
		else
			cite += "????";
		
		System.out.println(cite);
		return cite;
	}
	
	private void processField(String field, String value){
		if (value.trim() == "")
			return;
		if(fieldMap.containsKey(field))
			addProperty( fieldMap.get(field) , value );
		else if( field.equals("journal") || field.equals("fjournal"))
			if(prop.contains("publicationTitle"))
				addProperty( "journalAbbreviation" , value);
			else
				addProperty("publicationTitle" ,  value) ;
		else if(field.equals("author") || field.equals("editor") || field.equals("translator")){
			String authors = "";
			for(String str : Splitter.on(" and ").trimResults().split(value))
				authors += str.replace(",", "\\,") + ", ";
			authors = authors.substring(0, authors.lastIndexOf(","));
			addProperty(field , authors);
		}
		else if( field.equals("institution") || field.equals("organization"))
			addProperty("backupPublisher" ,  value);
		else if( field.equals("number")){
			if(prop.contains("itemType: report"))
				addProperty("reportNumber" ,  value);
			else if (prop.contains("itemType: book\n") || prop.contains("itemType: bookSection"))
				addProperty("seriesNumber", value);
			else
				addProperty("issue", value);
		}
		else if( field.equals("month") ){}
		else if( field.equals("year") ){
			if(prop.contains("date:")){
				if(!prop.contains(value))
					;//prop = prop.substring(prop.indexOf("date: "),prop.indexOf('\n', prop.indexOf("date: ") ))
			}
			else
				addProperty("year", value);
		}
		else if( field.equals("pages") ){
			if(prop.contains("book\n") || prop.contains("thesis") || prop.contains("manuscript") )
				addProperty("numPages", value);
			else
				addProperty("pages", value.replaceAll("--", "-"));
		}
		else if( field.equals("note") ){
			addProperty("extra", value);
		}
		else if( field.equals("howpublished") ){
			if(value.length() >= 7){
				String str = value.substring(0,7);
				if(str.equals("http://") || str.equals("https:/") || str.equals("mailto:"))
					addProperty("url", value);
				else
					addProperty("Published", value);
			}
		}
		else if( field.equals("keywords") || field.equals("keyword") )
			addProperty("tags", value.replaceAll(",", ", "));
		else if( field.equals("comment") || field.equals("annote") || field.equals("review") ){
			addProperty("note", value);
		}
		else if( field.equals("pdf") ){
			addProperty("attachments", "{path: " + value +" mimeType: application/pdf}");
		}
		else if( field.equals("sentelink") ){
			addProperty("attachments", "{path: " + value.split(",")[0] +", mimeType: application/pdf}");
		}
		else if( field.equals("file") ){
			for(String attachments: Splitter.on(";").trimResults().omitEmptyStrings().split(value)){
				String[] parts = attachments.split(":");
				if(parts.length == 0) continue;
				String fileTitle = parts[0];				
				String filepath = parts[1];
				if(filepath.trim().isEmpty()) continue;
				String fileType = parts[2];
				if(fileTitle.trim().isEmpty())
					fileTitle = "attachment";
				if(fileType.matches("pdf"))
					addProperty("attachments", "{path: " + filepath+", mimeType: application/pdf, title: " + fileTitle + "}" );
				else
					addProperty("attachments", "{path: " + filepath+", title: " + fileTitle + "}" );
				
			}
		}
		else 
			addProperty(field, value);
		
	}
	
	private String getFieldValue(char read){
		String value = "";
		try {
			if(read == '{'){
				int openBraces = 1;
				while((byte)(read = (char) reader.read() ) != -1){
					if(read == '{' && (value.length() == 0 || value.charAt(value.length()-1) != '\\')){
						openBraces++;
						value += read;
					}
					else if(read == '}' && (value.length() == 0 || value.charAt(value.length()-1) != '\\')){
						openBraces--;
						if(openBraces == 0 )
							break;
						else
							value += read;
					}
					else
						value += read;
				}
			}
			else if( read == '"'){
				int openBraces = 1;
				while((byte)(read = (char) reader.read() ) != -1){
					if(read == '{' && (value.length() == 0 || value.charAt(value.length()-1) != '\\')){
						openBraces++;
						value += read;
					}
					else if(read == '}' && (value.length() == 0 || value.charAt(value.length()-1) != '\\')){
						openBraces--;
						value += read;
					}
					else if(read == '"' && openBraces == 0)
						break;
					else
						value +=read;
				}
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return value.replaceAll("[{}\\\\]", "");
	}
	
	
	private void beginRecord(String type, char closeChar){
		String value = "";
		String field = "";
		type = CharMatcher.WHITESPACE.trimAndCollapseFrom(type.toLowerCase(), ' ');
		if(!type.equals("string")){
			String itemType = typeMap.containsKey(type) ? typeMap.get(type) :  type;//from map
			char read;
			//if not in map, error
			addProperty("itemType", itemType);
			try{
				while((byte)(read = (char) reader.read() ) != -1){
//					System.out.println(read);
					if(read == '='){
						do
							read = (char) reader.read();
						while(testWhiteSpace(read));
						if(testAlphaNum(read)){
							value = "";
							do{
								value += read;
								read = (char) reader.read();
							}while(testAlphaNum(read));
							
							//check map for value
						}
						else
							value = getFieldValue(read);//
							//get from map [read]
						//process item
//						System.out.println("Field: " + field + " Value: " + value);
						processField(field,value);
						field = "";
					}
					else if( read == ',')
						field = "";
					else if( read == closeChar )
						return;
					else if(!testWhiteSpace(read))
						field += read;
				} 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void doImport(){
		String type = "false";
		char read;
		
		try {
			while( (byte)(read = (char) reader.read() ) != -1 )
			{
				if(read == '@')
					type = "";
				else if( !type.equals("false"))
					if(type.equals("common"))
						type = "false";
					else if(read == '{'){
						beginRecord(type,'}');
						type = "false";
					}
					else if(read == '('){
						beginRecord(type,')');
						type = "false";
					}
					else if(testAlphaNum(read))
						type += read;
//				if(!type.equals("false"))
//					System.out.println(type);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean testAlphaNum(char c){
		return c >= 65 && c <= 90 || c >= 97 && c <= 122 || c >= 0 && c <= 9  || c == 45|| c <= 95;
	}
	
	private boolean testWhiteSpace(char c){
		return c == '\n' || c == '\r' || c ==  '\t' || c == ' ';
	}
	
	private void populate(){
		fieldMap.put("address", "place");
		fieldMap.put("chapter", "section");
		fieldMap.put("copyright", "rights");
		fieldMap.put("isbn", "ISBN");
		fieldMap.put("issn", "ISSN");
		fieldMap.put("iccn", "callNumber");
		fieldMap.put("location", "archiveLocation");
		fieldMap.put("shorttitle", "shortTitle");
		fieldMap.put("doi", "DOI");
		fieldMap.put("booktitle", "publicationTitle");
		fieldMap.put("school", "publisher");
		fieldMap.put("institution", "publisher");
		
		typeMap.put("article","journalArticle");
		typeMap.put("inbook","bookSection");
		typeMap.put("incollection","bookSection");
		typeMap.put("phdthesis","thesis");
		typeMap.put("unpublished","manuscript");
		typeMap.put("inproceedings","conferencePaper");
		typeMap.put("conference","conferencePaper");
		typeMap.put("techreport","report");
		typeMap.put("booklet","book");
		typeMap.put("manual","book");
		typeMap.put("mastersthesis","thesis");
		typeMap.put("misc","book");
		typeMap.put("proceedings","book");
		
		
		exportTypeMap.put("book","book");
		exportTypeMap.put("bookSection","incollection");
		exportTypeMap.put("journalArticle","article");
		exportTypeMap.put("magazineArticle","article");
		exportTypeMap.put("newspaperArticle","article");
		exportTypeMap.put("thesis","phdthesis");
		exportTypeMap.put("letter","misc");
		exportTypeMap.put("manuscript","unpublished");
		exportTypeMap.put("patent","patent");
		exportTypeMap.put("interview","misc");
		exportTypeMap.put("film","misc");
		exportTypeMap.put("artwork","misc");
		exportTypeMap.put("webpage","misc");
		exportTypeMap.put("conferencePaper","inproceedings");
		exportTypeMap.put("report","techreport");
	}
	
	private void addProperty(String field, String value){
		prop += field + ": " + value + "\n";
	}
}
