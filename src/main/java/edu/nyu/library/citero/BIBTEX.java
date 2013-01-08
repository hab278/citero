package edu.nyu.library.citero;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbibtex.LaTeXObject;
import org.jbibtex.LaTeXParser;
import org.jbibtex.LaTeXPrinter;
import org.jbibtex.ParseException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * BibTeX format class. Imports from BibTeX formatted strings and exports to
 * BibTeX formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class BIBTEX extends Format implements DestinationFormat {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The input and properties string */
	private String input, prop;
	/** The reader to read the chars */
	private StringReader reader;
	/** The much needed CSF item */
	private CSF item;
	/** Static maps, these translations do not change*/
	private static final Map<String, String> fieldMap , typeMap , exportTypeMap , exportFieldMap; 
	static {
		Map<String, String> fMap = new HashMap<String,String>();
		fMap.put("address", "place");
		fMap.put("chapter", "section");
		fMap.put("copyright", "rights");
		fMap.put("isbn", "ISBN");
		fMap.put("issn", "ISSN");
		fMap.put("iccn", "callNumber");
		fMap.put("location", "archiveLocation");
		fMap.put("shorttitle", "shortTitle");
		fMap.put("doi", "DOI");
		fMap.put("booktitle", "publicationTitle");
		fMap.put("school", "publisher");
		fMap.put("institution", "publisher");
		fieldMap = Collections.unmodifiableMap(fMap);
		
		Map<String, String> tMap = new HashMap<String,String>();
		tMap.put("article", "journalArticle");
		tMap.put("inbook", "bookSection");
		tMap.put("incollection", "bookSection");
		tMap.put("phdthesis", "thesis");
		tMap.put("unpublished", "manuscript");
		tMap.put("inproceedings", "conferencePaper");
		tMap.put("conference", "conferencePaper");
		tMap.put("techreport", "report");
		tMap.put("booklet", "book");
		tMap.put("manual", "book");
		tMap.put("mastersthesis", "thesis");
		tMap.put("misc", "book");
		typeMap = Collections.unmodifiableMap(tMap);

		Map<String, String> etMap = new HashMap<String,String>();
		etMap.put("book", "book");
		etMap.put("bookSection", "incollection");
		etMap.put("journalArticle", "article");
		etMap.put("magazineArticle", "article");
		etMap.put("newspaperArticle", "article");
		etMap.put("thesis", "phdthesis");
		etMap.put("letter", "misc");
		etMap.put("manuscript", "unpublished");
		etMap.put("patent", "patent");
		etMap.put("interview", "misc");
		etMap.put("film", "misc");
		etMap.put("artwork", "misc");
		etMap.put("webpage", "misc");
		etMap.put("conferencePaper", "inproceedings");
		etMap.put("report", "techreport");
		exportTypeMap = Collections.unmodifiableMap(etMap);
		
		Map<String, String> efMap = new HashMap<String,String>();
		efMap.put("place", "address");
		efMap.put("section", "chapter");
		efMap.put("rights", "copyright");
		efMap.put("ISBN", "isbn");
		efMap.put("ISSN", "issn");
		efMap.put("callNumber", "iccn");
		efMap.put("archiveLocation", "location");
		efMap.put("shortTitle", "shorttitle");
		efMap.put("DOI", "doi");
		efMap.put("abstractNote", "abstract");
		efMap.put("country", "nationality");
		efMap.put("edition", "edition");
		efMap.put("type", "type");
		efMap.put("series", "series");
		efMap.put("title", "title");
		efMap.put("volume", "volume");
		efMap.put("shortTitle", "shorttitle");
		efMap.put("language", "language");
		efMap.put("assignee", "assignee");
		exportFieldMap = Collections.unmodifiableMap(efMap);
	}

	/**
	 * Default constructor, instantiates data maps and CSF item.
	 * 
	 * @param input
	 *            A string representation of the data payload.
	 */
	public BIBTEX(String input) {
		super(input);
		logger.debug("BIBTEX FORMAT");
		// set up the input and csf object
		this.input = input;
		// load the variables
		loadVars();
		// import and load
		item = new CSF();
		doImport();
		try {
			item = new CSF(prop);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		logger.debug(prop);
	}

	/**
	 * Constructor that accepts a CSF object. Does the same as the default
	 * Constructor.
	 * 
	 * @param item
	 *            The CSF object, it gets loaded into this object.
	 */
	public BIBTEX(CSF item) {
		super(item);
		logger.debug("BIBTEX FORMAT");
		this.item = item;
		input = item.export();
		loadVars();
	}

	/**
	 * This method instantiates each variable and populates the maps
	 */
	private void loadVars() {
		reader = new StringReader(this.input);
		prop = "";
	}

	@Override
	public edu.nyu.library.citero.CSF toCSF() {
		return item;
	}

	/**
	 * Maps key value pairs for exporting.
	 * 
	 * @param key
	 *            The key to be mapped.
	 * @param value
	 *            The value the key will be mapped to.
	 * @return A string that is BibTeX formatted and maps value to key.
	 */
	private String mapValue(String key, String value) {
		logger.debug("Mapping " + value + " to " + key);
		String out = ",\n\t" + key + " = ";
		if (value.matches("^\\d+$") && !key.equals("numpages")
				&& !key.equals("isbn") && !key.equals("issn"))
			return out + value;
		return out + "{" + value + "}";
	}
	

	@Override
	public String export() {
		logger.info("Exporting to BibTeX");
		// Simply reverse import.
		StringBuffer export = new StringBuffer();
		String itemType = item.config().getString("itemType");
		// in BibTeX formatting
		export.append("@"
				+ (exportTypeMap.containsKey(item.config()
						.getString("itemType")) ? exportTypeMap.get(itemType)
						: "misc") + "{" + citeKey());
		Iterator<?> itr = item.config().getKeys();
		while (itr.hasNext()) {
			// Try to map every key we have
			String key = (String) itr.next();
			if (key.equals("itemType"))
				continue;
			if (exportFieldMap.containsKey(key))
				export.append(mapValue(exportFieldMap.get(key), item.config()
						.getString(key)));
			else if (key.equals("reportNumber") || key.equals("issue")
					|| key.equals("seriesNumber") || key.equals("patentNumber"))
				export.append(mapValue("number", item.config().getString(key)));
			else if (key.equals("accessDate"))
				export.append(mapValue("urldate", item.config().getString(key)));
			else if (key.equals("publicationTitle"))
				if (itemType.equals("bookSection")
						|| itemType.equals("conferencePaper"))
					export.append(mapValue("booktitle", item.config()
							.getString(key)));
				else
					export.append(mapValue("journal", item.config().getString(key)));
			else if (key.equals("publisher"))
				if (itemType.equals("thesis"))
					export.append(mapValue("school", item.config().getString(key)));
				else if (itemType.equals("report"))
					export.append(mapValue("institution",
							item.config().getString(key)));
				else
					export.append(mapValue("publisher", item.config()
							.getString(key)));
			else if (key.equals("author") || key.equals("inventor")
					|| key.equals("contributor") || key.equals("editor")
					|| key.equals("seriesEditor") || key.equals("translator")) {
				StringBuffer names = new StringBuffer();
				for (String str : item.config().getStringArray(key))
					names.append(" and " + str);
				if (key.equals("seriesEditor"))
					export.append(mapValue("editor", names.substring(5)));
				else
					export.append(mapValue(key, names.substring(5)));
			} else if (key.equals("extra"))
				export.append(mapValue("note", item.config().getString(key)));
			else if (key.equals("date"))
				export.append(mapValue("date", item.config().getString(key)));
			else if (key.equals("pages"))
				export.append(mapValue("pages", item.config().getString(key)
						.replace("-", "--")));
			else if (key.equals("date"))
				export.append(mapValue("date", item.config().getString(key)));
			else if (itemType.equals("webpage"))
				export.append(mapValue("howpublished", item.config().getString(key)));
			else if (key.equals("tags")) {
				StringBuffer tags = new StringBuffer();
				for (String str : item.config().getStringArray(key))
					tags.append(", " + str);
				export.append(mapValue("keywords", tags.substring(2)));
			} else if (key.equals("note"))
				for (String str : item.config().getStringArray(key))
					export.append(mapValue("annote", str));
			else if (key.equals("attachments"))
				export.append(mapValue("file", item.config().getString(key)));
			logger.debug(key);
		}
		// return the BibTeX entry as a string
		return export.append("\n}").toString();
	}

	/**
	 * Generates a citekey, a key unique to that reference. This use the authors
	 * last name, the title, and the date.
	 * 
	 * @return The generated citekey.
	 */
	private String citeKey() {
		String cite = "";
		if (item.config().containsKey("author")) {
			cite += item.config().getStringArray("author")[0].split(",")[0]
					.split(" ")[0].toLowerCase();
		} else if (item.config().containsKey("contributor")) {
			cite += item.config().getString("contributor").split(";")[0]
					.split(",")[0].toLowerCase();
		}
		if (item.config().containsKey("title"))
			cite += (!cite.isEmpty() ? "_" : "")
					+ item.config()
							.getString("title")
							.replaceAll(
									"^(([Aa]+|[tT][Hh][Ee]+|[Oo][Nn]+)\\s)+",
									"").split(" ")[0].toLowerCase();

		if (item.config().containsKey("date")) {
			String temp = item.config().getString("date").split(",")[0];
			cite += (!cite.isEmpty() ? "_" : "")
					+ (temp.length() > 3 ? temp.substring(0, 4) : temp);
		} else
			cite += (!cite.isEmpty() ? "_" : "") + "????";

		logger.debug(cite);
		return cite;
	}

	/**
	 * This method process each field, mapping it to the appropriate key and
	 * formatting.
	 * 
	 * @param field
	 *            The variable that will be mapped to a key.
	 * @param value
	 *            The variable that will be mapped to the key after formatting.
	 */
	private void processField(String field, String value) {
		// No use for empty values.
		if (value.trim().isEmpty())
			return;
		// If the fieldmap has the field, just add that and the value.
		if (fieldMap.containsKey(field))
			addProperty(fieldMap.get(field), value.replace(",", "\\,"));
		// Otherwise we have to map case by case.
		else if (field.equals("journal") || field.equals("fjournal"))
			if (prop.contains("publicationTitle"))
				addProperty("journalAbbreviation", value.replace(",", "\\,"));
			else
				addProperty("publicationTitle", value.replace(",", "\\,"));
		else if (field.equals("author") || field.equals("editor")
				|| field.equals("translator")) {
			StringBuffer authors = new StringBuffer();
			for (String str : Splitter.on(" and ").trimResults().split(value))
				authors.append(str.replace(",", "\\,") + ", ");
			authors.append(authors.substring(0, authors.lastIndexOf(",")));
			addProperty(field, authors.toString());
		} else if (field.equals("institution") || field.equals("organization"))
			addProperty("backupPublisher", value.replace(",", "\\,"));
		else if (field.equals("number")) {
			if (prop.contains("itemType: report"))
				addProperty("reportNumber", value.replace(",", "\\,"));
			else if (prop.contains("itemType: book\n")
					|| prop.contains("itemType: bookSection"))
				addProperty("seriesNumber", value.replace(",", "\\,"));
			else
				addProperty("issue", value.replace(",", "\\,"));
		} else if (field.equals("month")) {
			addProperty("month", value.replace(",", "\\,"));
		} else if (field.equals("year")) {
			addProperty("year", value.replace(",", "\\,"));
		} else if (field.equals("pages")) {
			if (prop.contains("book\n") || prop.contains("thesis")
					|| prop.contains("manuscript"))
				addProperty("numPages", value.replace(",", "\\,"));
			else
				addProperty("pages", value.replaceAll("--", "-").replace(",", "\\,"));
		} else if (field.equals("note")) {
			addProperty("extra", value.replace(",", "\\,"));
		} else if (field.equals("howpublished")) {
			if (value.length() >= 7) {
				String str = value.substring(0, 7);
				if (str.equals("http://") || str.equals("https:/")
						|| str.equals("mailto:"))
					addProperty("url", value.replace(",", "\\,"));
				else
					addProperty("Published", value.replace(",", "\\,"));
			}
		} else if (field.equals("keywords") || field.equals("keyword"))
			addProperty("tags", value.replaceAll(",", ", "));
		else if (field.equals("comment") || field.equals("annote")
				|| field.equals("review")) {
			addProperty("note", value.replace(",", "\\,"));
		} else if (field.equals("pdf")) {
			addProperty("attachments", "{path: " + value.replace(",", "\\,")
					+ " mimeType: application/pdf}");
		} else if (field.equals("sentelink")) {
			addProperty("attachments", "{path: " + value.split(",")[0]
					+ ", mimeType: application/pdf}".replace(",", "\\,"));
		} else if (field.equals("file")) {
			for (String attachments : Splitter.on(";").trimResults()
					.omitEmptyStrings().split(value)) {
				String[] parts = attachments.split(":");
				if (parts.length == 0)
					continue;
				String fileTitle = parts[0];
				String filepath = parts[1];
				if (filepath.trim().isEmpty())
					continue;
				String fileType = parts[2];
				if (fileTitle.trim().isEmpty())
					fileTitle = "attachment";
				if (fileType.matches("pdf"))
					addProperty("attachments", "{path: " + filepath.replace(",", "\\,")
							+ ", mimeType: application/pdf, title: ".replace(",", "\\,")
							+ fileTitle.replace(",", "\\,") + "}");
				else
					addProperty("attachments", "{path: " + filepath.replace(",", "\\,")
							+ ", title: ".replace(",", "\\,") + fileTitle.replace(",", "\\,") + "}");
			}
		} else
			addProperty(field, value.replace(",", "\\,"));
		// if that wasn't enough, just add the field as is.
	}



	/**
	 * A method to see if the current character is alphanumeric
	 * 
	 * @param c
	 *            The character to be tested.
	 * @return Returns true if the character is a letter or number, false
	 *         otherwise.
	 */
	private boolean testAlphaNum(char c) {
		return c >= 65 && c <= 90 || c >= 97 && c <= 122 || c >= 0 && c <= 9
				|| c == 45 || c <= 95;
	}
	
	private void doImport()
	{
		try {
			type();
			String parsed = printLaTeX(parseLaTeX(input));
			Scanner scanner = new Scanner(parsed);
			while(scanner.hasNextLine())
			{
				String[] keyval = scanner.nextLine().split("=",2);
				String delim = ",";
				if(keyval.length < 2){
					logger.debug(keyval[0]);
					continue;
				}
				String key = keyval[0].trim();
				String vals = keyval[1];
				if( key.equals("author") || key.equals("title") )
					delim = "and";
				for(String value : Splitter.on(delim).omitEmptyStrings().trimResults().split(vals))
				{
					if(value.endsWith(","))
						value = value.substring(0,value.length()-1);
					processField(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	static private List<LaTeXObject> parseLaTeX(String string) throws IOException, ParseException {
            Reader reader = new StringReader(string);
            try {
                    LaTeXParser parser = new LaTeXParser();
                    return parser.parse(reader);
            } finally {
                    reader.close();
            }
    }

    static private String printLaTeX(List<LaTeXObject> objects){
            LaTeXPrinter printer = new LaTeXPrinter();
            return printer.print(objects);
    }
    
    private void getType(String type) {
		// The key value pairs
		// Removing whitespace from type.
		type = CharMatcher.WHITESPACE.trimAndCollapseFrom(type.toLowerCase(), ' ');
		if (!type.equals("string")) {
			String itemType = typeMap.containsKey(type) ? typeMap.get(type) : type;// from map
			// if not in map, error
			addProperty("itemType", itemType);
		}
	}
    
    private void type() {
		logger.info("Importing to BibTeX");
		
		// the item's type, if not found yet it is set to 'false'
		String type = "false";
		// the character being read.
		char read;

		try {
			// Read character by character until there are none left
			while ((byte) (read = (char) reader.read()) != -1) {
				// If '@' is visible, the type exists as well.
				if (read == '@')
					type = "";
				// If there is a type, you can import everything else
				else if (!type.equals("false"))
					// common is not a type, so ignore it
					if (type.equals("common"))
						type = "false";
					// if the character is an open brace, start recording the
					// fields
					else if (read == '{'){
						getType(type);
						reader.close();
						return;
					}
					else if (testAlphaNum(read))
						type += read;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Method that maps key to value in a property format and adds it to the
	 * property string.
	 * 
	 * @param key
	 *            Represents the CSF key.
	 * @param value
	 *            Represents the value to be mapped.
	 */
	private void addProperty(String key, String value) {
		prop += key + CSF.SEPARATOR + " " + value.replace(".", "\\.") + "\n";
	}
}
