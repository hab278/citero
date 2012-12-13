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
 * BibTeX format class. Imports from BibTeX formatted strings and exports to
 * BibTeX formatted strings.
 * 
 * @author hab278
 * 
 */

public class BIBTEX extends Format {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The input and properties string */
	private String input, prop;
	/** The reader to read the chars */
	private StringReader reader;
	/** The much needed CSF item */
	private CSF item;
	/** Various maps for fields and types */
	private static Map<String, String> fieldMap,  typeMap, exportTypeMap, exportFieldMap;

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
		item = new CSF();
		// load the variables
		loadVars();

		// import and laod
		doImport();
		try {
			item.load(prop);
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
		input = item.getData();
		loadVars();
	}

	/**
	 * This method instantiates each variable and populates the maps
	 */
	private void loadVars() {
		reader = new StringReader(this.input);
		prop = "";
		
		populate();
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
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
		String export = "", itemType = item.config().getString("itemType");
		// in BibTeX formatting
		export += "@"
				+ (exportTypeMap.containsKey(item.config()
						.getString("itemType")) ? exportTypeMap.get(itemType)
						: "misc") + "{" + citeKey();
		Iterator<?> itr = item.config().getKeys();
		while (itr.hasNext()) {
			// Try to map every key we have
			String key = (String) itr.next();
			if (key.equals("itemType"))
				continue;
			if (exportFieldMap.containsKey(key))
				export += mapValue(exportFieldMap.get(key), item.config()
						.getString(key));
			else if (key.equals("reportNumber") || key.equals("issue")
					|| key.equals("seriesNumber") || key.equals("patentNumber"))
				export += mapValue("number", item.config().getString(key));
			else if (key.equals("accessDate"))
				export += mapValue("urldate", item.config().getString(key));
			else if (key.equals("publicationTitle"))
				if (itemType.equals("bookSection")
						|| itemType.equals("conferencePaper"))
					export += mapValue("booktitle", item.config()
							.getString(key));
				else
					export += mapValue("journal", item.config().getString(key));
			else if (key.equals("publisher"))
				if (itemType.equals("thesis"))
					export += mapValue("school", item.config().getString(key));
				else if (itemType.equals("report"))
					export += mapValue("institution",
							item.config().getString(key));
				else
					export += mapValue("publisher", item.config()
							.getString(key));
			else if (key.equals("author") || key.equals("inventor")
					|| key.equals("contributor") || key.equals("editor")
					|| key.equals("seriesEditor") || key.equals("translator")) {
				String names = "";
				for (String str : item.config().getStringArray(key))
					names += " and " + str;
				if (key.equals("seriesEditor"))
					export += mapValue("editor", names.substring(5));
				else
					export += mapValue(key, names.substring(5));
			} else if (key.equals("extra"))
				export += mapValue("note", item.config().getString(key));
			else if (key.equals("date"))
				export += mapValue("date", item.config().getString(key));
			else if (key.equals("pages"))
				export += mapValue("pages", item.config().getString(key)
						.replace("-", "--"));
			else if (key.equals("date"))
				export += mapValue("date", item.config().getString(key));
			else if (itemType.equals("webpage"))
				export += mapValue("howpublished", item.config().getString(key));
			else if (key.equals("tags")) {
				String tags = "";
				for (String str : item.config().getStringArray(key))
					tags += ", " + str;
				export += mapValue("keywords", tags.substring(2));
			} else if (key.equals("note"))
				for (String str : item.config().getStringArray(key))
					export += mapValue("annote", str);
			else if (key.equals("attachments"))
				export += mapValue("file", item.config().getString(key));
			logger.debug(key);
		}
		// return the BibTeX entry as a string
		return export + "\n}";
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
		if (value.trim() == "")
			return;
		// If the fieldmap has the field, just add that and the value.
		if (fieldMap.containsKey(field))
			addProperty(fieldMap.get(field), value);
		// Otherwise we have to map case by case.
		else if (field.equals("journal") || field.equals("fjournal"))
			if (prop.contains("publicationTitle"))
				addProperty("journalAbbreviation", value);
			else
				addProperty("publicationTitle", value);
		else if (field.equals("author") || field.equals("editor")
				|| field.equals("translator")) {
			String authors = "";
			for (String str : Splitter.on(" and ").trimResults().split(value))
				authors += str.replace(",", "\\,") + ", ";
			authors = authors.substring(0, authors.lastIndexOf(","));
			addProperty(field, authors);
		} else if (field.equals("institution") || field.equals("organization"))
			addProperty("backupPublisher", value);
		else if (field.equals("number")) {
			if (prop.contains("itemType: report"))
				addProperty("reportNumber", value);
			else if (prop.contains("itemType: book\n")
					|| prop.contains("itemType: bookSection"))
				addProperty("seriesNumber", value);
			else
				addProperty("issue", value);
		} else if (field.equals("month")) {
			addProperty("month", value);
		} else if (field.equals("year")) {
			// if (prop.contains("date:")) {
			// if (!prop.contains(value))
			// ;// prop =
			// // prop.substring(prop.indexOf("date: "),prop.indexOf('\n',
			// // prop.indexOf("date: ") ))
			// } else
			addProperty("year", value);
		} else if (field.equals("pages")) {
			if (prop.contains("book\n") || prop.contains("thesis")
					|| prop.contains("manuscript"))
				addProperty("numPages", value);
			else
				addProperty("pages", value.replaceAll("--", "-"));
		} else if (field.equals("note")) {
			addProperty("extra", value);
		} else if (field.equals("howpublished")) {
			if (value.length() >= 7) {
				String str = value.substring(0, 7);
				if (str.equals("http://") || str.equals("https:/")
						|| str.equals("mailto:"))
					addProperty("url", value);
				else
					addProperty("Published", value);
			}
		} else if (field.equals("keywords") || field.equals("keyword"))
			addProperty("tags", value.replaceAll(",", ", "));
		else if (field.equals("comment") || field.equals("annote")
				|| field.equals("review")) {
			addProperty("note", value);
		} else if (field.equals("pdf")) {
			addProperty("attachments", "{path: " + value
					+ " mimeType: application/pdf}");
		} else if (field.equals("sentelink")) {
			addProperty("attachments", "{path: " + value.split(",")[0]
					+ ", mimeType: application/pdf}");
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
					addProperty("attachments", "{path: " + filepath
							+ ", mimeType: application/pdf, title: "
							+ fileTitle + "}");
				else
					addProperty("attachments", "{path: " + filepath
							+ ", title: " + fileTitle + "}");

			}
		} else
			addProperty(field, value);

		// if that wasn't enough, just add the field as is.

	}

	/**
	 * Extracts the value.
	 * 
	 * @param read
	 *            The char to start at.
	 * @return The value.
	 */
	private String getFieldValue(char read) {
		String value = "";
		try {
			if (read == '{') {
				// for nesting braces
				int openBraces = 1;
				while ((byte) (read = (char) reader.read()) != -1) {
					if (read == '{'
							&& (value.length() == 0 || value.charAt(value
									.length() - 1) != '\\')) {
						openBraces++;
						value += read;
					} else if (read == '}'
							&& (value.length() == 0 || value.charAt(value
									.length() - 1) != '\\')) {
						openBraces--;
						if (openBraces == 0)
							break;
						else
							value += read;
					} else
						value += read; // Add every character that isn't part of
										// the nesting braces
				}
			} else if (read == '"') { // do the same thing here, except
										// surrounded by quotes
				int openBraces = 1;
				while ((byte) (read = (char) reader.read()) != -1) {
					if (read == '{'
							&& (value.length() == 0 || value.charAt(value
									.length() - 1) != '\\')) {
						openBraces++;
						value += read;
					} else if (read == '}'
							&& (value.length() == 0 || value.charAt(value
									.length() - 1) != '\\')) {
						openBraces--;
						value += read;
					} else if (read == '"' && openBraces == 0)
						break;
					else
						value += read;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value.replaceAll("[{}\\\\]", "");
	}

	/**
	 * This method parses the BibTeX record.
	 * 
	 * @param type
	 *            The item's type.
	 * @param closeChar
	 *            The closing character, where to stop the record.
	 */
	private void beginRecord(String type, char closeChar) {
		// The key value pairs
		String field = "";
		String value = "";

		// Removing whitespace from type.
		type = CharMatcher.WHITESPACE.trimAndCollapseFrom(type.toLowerCase(),
				' ');
		if (!type.equals("string")) {
			String itemType = typeMap.containsKey(type) ? typeMap.get(type)
					: type;// from map
			char read;
			// if not in map, error
			addProperty("itemType", itemType);
			try {
				// keep reading char by char
				while ((byte) (read = (char) reader.read()) != -1) {
					// logger.debug(read);
					if (read == '=') {
						// if there is an equal sign, keep reading up the
						// whitespace
						do
							read = (char) reader.read();
						while (testWhiteSpace(read));
						if (testAlphaNum(read)) {
							// if its alphanumeric, add it to value
							value = "";
							do {
								value += read;
								read = (char) reader.read();
							} while (testAlphaNum(read));

							// check map for value
						} else
							value = getFieldValue(read);//
						// get from map [read]
						// process item
						// logger.debug("Field: " + field + " Value: " + value);
						processField(field, value);
						field = "";
					} else if (read == ',')
						field = ""; // reset field if a comma is there
					else if (read == closeChar)
						return; // if its close char, we reached the end.
					else if (!testWhiteSpace(read))
						field += read; // if all else, and the char is not a
										// whitespace, add it to field
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method initiates the import from BibTeX to CSF
	 */
	private void doImport() {
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
					else if (read == '{')
						beginRecord(type, '}');
					// same with an open parenthesis
					else if (read == '(')
						beginRecord(type, ')');
					// if its alphanumeric, it must be the item type, keep
					// adding that to type.
					else if (testAlphaNum(read))
						type += read;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/**
	 * A method to see if the current character is a whitespace
	 * 
	 * @param c
	 *            The character to be tested.
	 * @return Returns true if the character is a whitespace, false otherwise.
	 */
	private boolean testWhiteSpace(char c) {
		return c == '\n' || c == '\r' || c == '\t' || c == ' ';
	}

	/**
	 * This method populates the maps used to match BibTeX only fields/types to
	 * CSF fields/types and vice versa.
	 */
	private void populate() {
		if(!(fieldMap == null && typeMap == null  && exportTypeMap == null && exportFieldMap == null))
			return;
		fieldMap = new HashMap<String, String>();
		typeMap = new HashMap<String, String>();
		exportTypeMap = new HashMap<String, String>();
		exportFieldMap = new HashMap<String, String>();
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
		exportFieldMap.put("edition", "edition");
		exportFieldMap.put("type", "type");
		exportFieldMap.put("series", "series");
		exportFieldMap.put("title", "title");
		exportFieldMap.put("volume", "volume");
		exportFieldMap.put("shortTitle", "shorttitle");
		exportFieldMap.put("language", "language");
		exportFieldMap.put("assignee", "assignee");

		typeMap.put("article", "journalArticle");
		typeMap.put("inbook", "bookSection");
		typeMap.put("incollection", "bookSection");
		typeMap.put("phdthesis", "thesis");
		typeMap.put("unpublished", "manuscript");
		typeMap.put("inproceedings", "conferencePaper");
		typeMap.put("conference", "conferencePaper");
		typeMap.put("techreport", "report");
		typeMap.put("booklet", "book");
		typeMap.put("manual", "book");
		typeMap.put("mastersthesis", "thesis");
		typeMap.put("misc", "book");

		exportTypeMap.put("book", "book");
		exportTypeMap.put("bookSection", "incollection");
		exportTypeMap.put("journalArticle", "article");
		exportTypeMap.put("magazineArticle", "article");
		exportTypeMap.put("newspaperArticle", "article");
		exportTypeMap.put("thesis", "phdthesis");
		exportTypeMap.put("letter", "misc");
		exportTypeMap.put("manuscript", "unpublished");
		exportTypeMap.put("patent", "patent");
		exportTypeMap.put("interview", "misc");
		exportTypeMap.put("film", "misc");
		exportTypeMap.put("artwork", "misc");
		exportTypeMap.put("webpage", "misc");
		exportTypeMap.put("conferencePaper", "inproceedings");
		exportTypeMap.put("report", "techreport");
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
		prop += key + item.SEPARATOR + value + "\n";
	}
}
