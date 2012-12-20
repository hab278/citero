package edu.nyu.library.citation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.nyu.library.citation.utils.NameFormatter;

/**
 * RIS format class. Imports from RIS formatted strings and exports to RIS
 * formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class RIS extends Format implements DestinationFormat {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The unique CSF item */
	private CSF item;
	/** Strings for the data and properties */
	private String input, prop;
	/** Maps for fields and data types */
	private static final Map<String, String> dataOutMap ,dataInMap;
	static {
		Map<String,String> doMap = new HashMap<String,String>();
		doMap.put("book", "BOOK");
		doMap.put("bookSection", "CHAP");
		doMap.put("journalArticle", "JOUR");
		doMap.put("journal", "JFULL");
		doMap.put("magazineArticle", "MGZN");
		doMap.put("newspaperArticle", "NEWS");
		doMap.put("thesis", "THES");
		doMap.put("letter", "PCOMM");
		doMap.put("manuscript", "PAMP");
		doMap.put("interview", "PCOMM");
		doMap.put("film", "MPCT");
		doMap.put("artwork", "ART");
		doMap.put("report", "RPRT");
		doMap.put("bill", "BILL");
		doMap.put("case", "CASE");
		doMap.put("hearing", "HEAR");
		doMap.put("patent", "PAT");
		doMap.put("statute", "STAT");
		doMap.put("map", "MAP");
		doMap.put("blogPost", "ELEC");
		doMap.put("webpage", "ELEC");
		doMap.put("instantMessage", "ICOMM");
		doMap.put("forumPost", "ICOMM");
		doMap.put("email", "ICOMM");
		doMap.put("audioRecording", "SOUND");
		doMap.put("presentation", "GEN");
		doMap.put("videoRecording", "VIDEO");
		doMap.put("tvBroadcast", "GEN");
		doMap.put("radioBroadcast", "GEN");
		doMap.put("podcast", "GEN");
		doMap.put("computerProgram", "COMP");
		doMap.put("conferencePaper", "CONF");
		doMap.put("document", "GEN");
		dataOutMap = Collections.unmodifiableMap(doMap);

		// input mapping
		Map<String,String> diMap = new HashMap<String,String>();
		diMap.put("ABST", "journalArticle");
		diMap.put("ADVS", "film");
		diMap.put("CTLG", "magazineArticle");
		diMap.put("INPR", "manuscript");
		diMap.put("JFULL", "journal");
		diMap.put("PAMP", "manuscript");
		diMap.put("SER", "book");
		diMap.put("SLIDE", "artwork");
		diMap.put("UNBILL", "manuscript");
		diMap.put("CPAPER", "conferencePaper");
		diMap.put("WEB", "webpage");
		diMap.put("EDBOOK", "book");
		diMap.put("MANSCPT", "manuscript");
		diMap.put("GOVDOC", "document");
		diMap.put("TI", "title");
		diMap.put("CT", "title");
		diMap.put("CY", "place");
		diMap.put("ST", "shortTitle");
		diMap.put("DO", "DOI");
		diMap.put("ID", "itemID");
		diMap.put("T1", "title");
		diMap.put("T2", "publicationTitle");
		diMap.put("T3", "series");
		diMap.put("T2", "bookTitle");
		diMap.put("CY", "place");
		diMap.put("JA", "journalAbbreviation");
		diMap.put("M3", "DOI");
		diMap.put("ID", "itemID");
		diMap.put("T1", "title");
		diMap.put("T3", "series");
		diMap.put("JF", "publicationTitle");
		diMap.put("CY", "place");
		diMap.put("JA", "journalAbbreviation");
		diMap.put("M3", "DOI");
		dataInMap = Collections.unmodifiableMap(diMap);

	}

	/**
	 * Default constructor, instantiates data maps and CSF item.
	 * 
	 * @param input
	 *            A string representation of the data payload.
	 */
	public RIS(String input) {
		super(input);
		logger.debug("RIS FORMAT");
		// set up variables
		this.input = input;
		prop = "";
		item = new CSF();


		doImport();
		try {
			item.doImport(prop);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor that accepts a CSF object. Does the same as the default
	 * Constructor.
	 * 
	 * @param item
	 *            The CSF object, it gets loaded into this object.
	 */
	public RIS(CSF item) {
		super(item);
		logger.debug("RIS FORMAT");
		this.item = item;
		prop = "";
		input = item.export();
	}

	@Override
	public CSF toCSF() {
		return item;
	}

	public String export() {
		logger.info("Exporting to RIS");

		// if it just a note, the whole thing is an RIS
		String itemType = item.config().getString("itemType");
		if (itemType.equals("note") || itemType.equals("attachment"))
			return input;

		// first get Type
		StringBuffer ris = new StringBuffer("TY  - ");
		if (dataOutMap.containsKey(itemType))
			ris.append(dataOutMap.get(itemType) + "\n");
		else
			ris.append("GEN\r\n");

		// For each property output the mapped tag
		Iterator<?> itr = item.config().getKeys();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			String[] value = item.config().getStringArray(key);
			if (key.equals("author") || key.equals("inventor"))
				for (int i = 0; i < value.length; ++i)
					if (i == 0)
						ris.append("A1  - " + NameFormatter.from(value[i]).toFormatted() + "\n");
					else
						ris.append("A3  - " + NameFormatter.from(value[i]).toFormatted() + "\n");
			else if (key.equals("bookTitle"))
				ris.append("BT  - " + value[0] + "\n");
			else if (key.equals("title"))
				ris.append("TI  - " + value[0] + "\n");
			else if (key.equals("publicationTitle") )
				ris.append((item.config().containsKey("title") ? "JO  - " : "TI  - ") + value[0] + "\n");
			else if (key.equals("backupPublicationTitle"))
				ris.append("T2  - " + value[0] + "\n");
			else if (key.equals("editor"))
				for (String str : value)
					ris.append("ED  - " + NameFormatter.from(str).toFormatted() + "\n");
			else if (key.equals("contributor") || key.equals("assignee"))
				for (String str : value)
					ris.append("A2  - " + NameFormatter.from(str).toFormatted() + "\n");
			else if (key.equals("volume") || key.equals("applicationNumber")
					|| key.equals("reportNumber"))
				ris.append("VL  - " + value[0] + "\n");
			else if (key.equals("issue") || key.equals("patentNumber"))
				ris.append("IS  - " + value[0] + "\n");
			else if (key.equals("publisher") || key.equals("references"))
				ris.append("PB  - " + value[0] + "\n");
			else if (key.equals("place"))
				ris.append("CY  - " + value[0] + "\n");
			else if (key.equals("date"))
				ris.append("PY  - " + value[0] + "\n");
			else if (key.equals("filingDate"))
				ris.append("Y2  - " + value[0] + "\n");
			else if (key.equals("abstractNote"))
				ris.append("N2  - " + value[0].replaceAll("(?:\r\n?|\n)", "\n")
						+ "\n");
			else if (key.equals("pages")) {
				if (itemType.equals("book"))
					ris.append("EP  - " + value[0] + "\n");
				else if (!key.contains("startPage") || !key.contains("endPage")) {
					ris.append("SP  - " + value[0].split("-", 0)[0] + "\n");
					if (value[0].split("-", 0).length > 1)
						ris.append("EP  - " + value[0].split("-", 0)[1] + "\n");
				}
			} else if (key.equals("startPage"))
				ris.append("SP - " + value[0] + "\n");
			else if (key.equals("endPage"))
				ris.append("EP - " + value[0] + "\n");
			else if (key.equals("ISBN"))
				for (String str : value)
					ris.append("SN  - " + str + "\n");
			else if (key.equals("ISSN"))
				ris.append("SN  - " + value[0] + "\n");
			else if (key.equals("URL"))
				ris.append("UR  - " + value[0] + "\n");
			else if (key.equals("tags")) 
				for (String str : value)
					ris.append("KW  - " + str + '\n');
			else if (key.equals("source")
					&& value[0].substring(0, 7).equals("http://"))
				ris.append("UR  - " + value[0] + "\n");
		}
		ris.append("ER  -\n\n");
		logger.debug(ris.toString());
		return ris.toString();
	}

	/**
	 * This method take each tag and maps it to its CSF key
	 * 
	 * @param tag
	 *            This is going to be mapped to a CSF key.
	 * @param value
	 *            This is going to mapped to the CSF key.
	 */
	private void processTag(String tag, String value) {

		if (value == null || value.isEmpty() || value.trim().isEmpty())
			return;

		String itemType = "";
		// if input type is mapped
		if (dataInMap.containsKey(tag))
			addProperty(dataInMap.get(tag), value);
		// for types
		else if (tag.equals("TY")) {
			for (String val : dataOutMap.keySet())
				if (dataOutMap.get(val).equals(value))
					itemType = val;
			if (itemType.isEmpty())
				if (dataInMap.containsKey(value))
					itemType = dataInMap.get(value);
				else
					itemType = "document";
			addProperty("itemType", itemType);
		}
		// for journal type
		else if (tag.equals("JO")) {
			if (itemType.equals("conferencePaper"))
				addProperty("conferenceName", value);
			else
				addProperty("publicationTitle", value);
		}
		// for booktitle
		else if (tag.equals("BT")) {
			if (itemType.equals("book") || itemType.equals("manuscript"))
				addProperty("title", value);
			else if (itemType.equals("bookSection"))
				addProperty("bookTitle", value);
			else
				addProperty("backupPublicationTitle", value);
		}
		// For t2
		else if (tag.equals("T2"))
			addProperty("backupPublicationTitle", value);
		// for authors, add first name last name?
		else if (tag.equals("AU") || tag.equals("A1")) {
			String target = "";
			if (itemType.equals("patent"))
				target = "inventor";
			else
				target = "author";
			addProperty(target, value.replace(",", "\\,").replace(".", "\\."));
		}
		// for editor
		else if (tag.equals("ED"))
			addProperty("editor", value);
		// contributors and assignee
		else if (tag.equals("A2")) {
			if (itemType.equals("patent")) {
				if (prop.contains("assignee"))
					addProperty("assignee", value);
				else
					addProperty("assignee", value);
			} else
				addProperty("contributor", value);
		} else if (tag.equals("Y1") || tag.equals("PY")) {
			addProperty("date", value);
		} else if (tag.equals("Y2")) {

			if (itemType.equals("patent"))
				addProperty("filingDate", value);
			else
				addProperty("accessDate", value);
		}
		// note
		else if (tag.equals("N1")) {
			if (prop.contains("title"))
				if (!prop.contains("title: " + value + "\n"))
					if (value.contains("<br>") || value.contains("<p>"))
						addProperty("note", value);
					else
						addProperty(
								"note",
								"<p>"
										+ value.replaceAll("/n/n", "</p><p>")
												.replaceAll("/n", "<br/>")
												.replaceAll("\t",
														"&nbsp;&nbsp;&nbsp;&nbsp;")
												.replaceAll("  ", "&nbsp;"));
		}
		// abstract
		else if (tag.equals("N2") || tag.equals("AB"))
			addProperty("abstractNote", value);
		// keywords/tags
		else if (tag.equals("KW"))
			addProperty("tags", value.replaceAll("\n", ","));

		// start page
		else if (tag.equals("SP")) {
			addProperty("startPage", value);
			if (itemType.equals("book"))
				addProperty("numPages", value);
		}
		// end page
		else if (tag.equals("EP")) {
			addProperty("endPage", value);
			if (itemType.equals("book") && !prop.contains("numPages"))
				addProperty("numPages", value);
		}
		// ISSN/ISBN
		else if (tag.equals("SN")) {
			if (!prop.contains("ISBN"))
				addProperty("ISBN", value);
			if (!prop.contains("ISSN"))
				addProperty("ISSN", value);
		}
		// URL
		else if (tag.equals("UR") || tag.equals("L3") || tag.equals("L2")
				|| tag.equals("L4")) 
			addProperty("url", value);
		// issue number
		else if (tag.equals("IS")) {
			if (itemType.equals("patent"))
				addProperty("patentNumber", value);
			else
				addProperty("issue", value);
		}
		// volume
		else if (tag.equals("VL")) {
			if (itemType.equals("patent"))
				addProperty("applicationNumber", value);
			else if (itemType.equals("report"))
				addProperty("reportNumber", value);
			else
				addProperty("volume", value);
		}
		// publisher/references
		else if (tag.equals("PB")) {
			if (itemType.equals("patent"))
				addProperty("references", value);
			else
				addProperty("publisher", value);
		}
		// Misc fields
		else if (tag.equals("M1") || tag.equals("M2")) {
			addProperty("extra", value);
		}

	}

	/**
	 * Uses configuration to build a CSF object.
	 */
	private void doImport() {
		logger.info("Importing to RIS");
		
		String tag;
		String value;
		String line;
		// RIS is fortunately line separated, so we can go line by line using a
		// scanner.
		Scanner scanner;
		scanner = new Scanner(this.input);
		// skips whitespace
		do {
			line = scanner.nextLine();
			line = line.replaceAll("^\\s+", "");
		} while (scanner.hasNextLine()
				&& !line.substring(0, 6).matches("^TY {1,2}- "));
		// Item type
		tag = "TY";
		value = line.substring(line.indexOf('-') + 1).trim();

		// Rawline is the unchanged line.
		String rawLine;
		while (scanner.hasNextLine()) {
			rawLine = scanner.nextLine();
			line = rawLine.replaceFirst("^\\s+", "");
			// RIS format detected
			if (line.matches("^([A-Z0-9]{2}) {1,2}-(?: ([^\n]*))?")) {
				// tag is RIS format as well
				if (tag.matches("^[A-Z0-9]{2}"))
					processTag(tag, value);
				// splits tag and value
				tag = line.substring(0, line.indexOf('-')).trim();
				value = line.substring(line.indexOf('-') + 1).trim();
				if (tag.equals("ER")) {
					// end file
					completeItem();
					logger.debug(prop);
					return;
				}
				continue;
			} else
			// notes go for multiple lines
			if (tag == "N1" || tag == "N2" || tag == "AB" || tag == "KW")
				value += "\n" + rawLine;
			else if (!tag.isEmpty())
				if (value.charAt(value.length() - 1) == ' ')
					value += rawLine;
				else
					value += " " + rawLine;

			// process tag anyway
			if (!tag.isEmpty() && !tag.equals("ER"))
				processTag(tag, value);
		}
	}

	/**
	 * Takes care of some last minute stuff for the item.
	 */
	private void completeItem() {
		// if it has backupPublicationTitle but not publicationTitle, it swaps
		// the two
		if (prop.contains("backupPublicationTitle")) {
			if (!prop.contains("publicationTitle")) {
				addProperty("publicationTitle", prop.substring(prop.indexOf(
						"backupPublicationTitle:", 0) + 23, prop.indexOf("\n",
						prop.indexOf("backupPublicationTitle:", 0) + 23)));
			}
			prop = prop.replaceAll("backupPublicationTitle:\\s*[a-zA-Z0-9\\-\\\\_]*",
					"");
		}

		// removes excess from DOI
		if (prop.contains("DOI")) {
			prop = prop.replaceAll("\\s*doi:\\s*", "");
		}

		// hack for sites like Nature, which only use JA, journal abbreviation
		if (prop.contains("journalAbbreviation")
				&& !prop.contains("publicationTitle")) {
			addProperty("publicationTitle", prop.substring(prop.indexOf(
					"journalAbbreviation:", 0) + 20, prop.indexOf("\n",
					prop.indexOf("journalAbbreviation:", 0) + 20)));
		}
		// Hack for Endnote exports missing full title
		if (prop.contains("shortTitle") && !prop.contains("title")) {
			addProperty("title", prop.substring(
					prop.indexOf("shortTitle:", 0) + 11,
					prop.indexOf("\n", prop.indexOf("shortTitle:", 0) + 11)));
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
	private void addProperty(String field, String value) {
		prop += field + item.SEPARATOR + value + "\n";
	}
}
