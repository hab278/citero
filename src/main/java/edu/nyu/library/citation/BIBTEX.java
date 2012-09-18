package edu.nyu.library.citation;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * BibTeX format class. Imports from BibTeX formatted strings and exports to BibTeX formatted strings.
 * @author hab278
 *
 */

public class BIBTEX extends Format{
	
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	private String input, prop;
	private StringReader reader;
	private Map<String,String> fieldMap;
	private Map<String,String> typeMap;
	private Map<String,String> exportTypeMap;
	private CSF item;
	private Map<String, String> exportFieldMap;

	/**
	 * Default constructor, instantiates data maps and CSF item.
	 * @param input A string representation of the data payload.
	 */
	public BIBTEX(String input) {
		super(input);
		logger.info("BIBTEX FORMAT");
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
		logger.debug(prop);
	}

	/** 
	 * Constructor that accepts a CSF object. Does the same as the default Constructor.
	 * @param item The CSF object, it gets loaded into this object.
	 */
	public BIBTEX(CSF item) {
		super(item);
		this.item = item;
		input = item.data();
		loadVars();
	}

	private void loadVars()
	{
		reader = new StringReader(this.input);
		prop = "";
		fieldMap = new HashMap<String,String>();
		typeMap = new HashMap<String,String>();
		exportTypeMap = new HashMap<String,String>();
		exportFieldMap = new HashMap<String,String>();
		populate();
	}
	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}
	
	private String mapValue( String key, String value ){
		logger.debug("Mapping " + value + " to " + key);
		String out = ",\n\t" + key + " = ";
		if(value.matches("^\\d+$") && !key.equals("numpages") && !key.equals("isbn") && !key.equals("issn"))
			return out + value;
		return out + "{" + value + "}";
	}

	@Override
	public String export() {
		String export = "", itemType = item.config().getString("itemType");
		export += "@" + ( exportTypeMap.containsKey(item.config().getString("itemType")) ? exportTypeMap.get(itemType) : "misc" ) 
				+"{" + citeKey();
		Iterator<?> itr = item.config().getKeys();
		while(itr.hasNext()){
			String key = (String) itr.next();
			if(key.equals("itemType"))
				continue;
			if(exportFieldMap.containsKey(key))
				export += mapValue(exportFieldMap.get(key), item.config().getString(key));
			else if( key.equals("reportNumber") || key.equals("issue") || key.equals("seriesNumber") || key.equals("patentNumber") )
				export += mapValue("number", item.config().getString(key));
			else if( key.equals("accessDate") )
				export += mapValue("urldate", item.config().getString(key));
			else if( key.equals("publicationTitle"))
				if( itemType.equals("bookSection") || itemType.equals("conferencePaper")  )
					export += mapValue("booktitle", item.config().getString(key));
				else
					export += mapValue("journal", item.config().getString(key));
			else if( key.equals("publisher"))
				if( itemType.equals("thesis")  )
					export += mapValue("school", item.config().getString(key));
				else if( itemType.equals("report")  )
					export += mapValue("institution", item.config().getString(key));
				else
					export += mapValue("publisher", item.config().getString(key));
			else if( key.equals("author") || key.equals("inventor") || key.equals("contributor") || key.equals("editor") 
					|| key.equals("seriesEditor") || key.equals("translator") ){
				String names = "";
				for(String str : item.config().getStringArray(key))
					names += " and " + str;
				if( key.equals("seriesEditor"))
					export += mapValue("editor", names.substring(5));
				else
					export += mapValue(key, names.substring(5));
			}
			else if( key.equals("extra") )
				export += mapValue("note", item.config().getString(key));
			else if( key.equals("date") )
				export += mapValue("date", item.config().getString(key));
			else if( key.equals("pages") )
				export += mapValue("pages", item.config().getString(key).replace("-","--"));
			else if( key.equals("date") )
				export += mapValue("date", item.config().getString(key));
			else if( itemType.equals("webpage") )
				export += mapValue("howpublished", item.config().getString(key));
			else if( key.equals("tags") ){
				String tags = "";
				for( String str : item.config().getStringArray(key) )
					tags += ", " + str;
				export += mapValue("keywords", tags.substring(2));
			}
			else if( key.equals("note") )
				for( String str : item.config().getStringArray(key) )
					export += mapValue("annote", str);
			else if( key.equals("attachments") )
				export += mapValue("file", item.config().getString(key));
			logger.debug(key);
		}
		return export +"\n}";
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
		if(item.config().containsKey("author"))
			cite += item.config().getStringArray("author")[0].split(",")[0].split(" ")[0].toLowerCase();
		else if(item.config().containsKey("contributor"))
			cite += item.config().getString("contributor").split(";")[0].split(",")[0].toLowerCase();
		if(item.config().containsKey("title "))
			cite += item.config().getString("title").split(",")[0];
		if(item.config().containsKey("date"))
			cite = item.config().getString("date").split(",")[0];
		else
			cite += "????";
		
		logger.debug(cite);
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
//					logger.debug(read);
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
//						logger.debug("Field: " + field + " Value: " + value);
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
//					logger.debug(type);
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
		
		exportFieldMap.put("place", "address");
		exportFieldMap.put("section", "chapter");
		exportFieldMap.put("rights", "copyright");
		exportFieldMap.put("ISBN", "isbn");
		exportFieldMap.put("ISSN", "issn");
		exportFieldMap.put("callNumber", "iccn");
		exportFieldMap.put("archiveLocation", "location");
		exportFieldMap.put("shortTitle", "shorttitle");
		exportFieldMap.put("DOI", "doi");
		exportFieldMap.put("abstractNote", "abstract");
		exportFieldMap.put("country", "nationality");
		exportFieldMap.put("edition","edition");
		exportFieldMap.put("type","type");
		exportFieldMap.put("series","series");
		exportFieldMap.put("title","title");
		exportFieldMap.put("volume","volume");
		exportFieldMap.put("shortTitle","shorttitle");
		exportFieldMap.put("language","language");
		exportFieldMap.put("assignee","assignee");
		
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
