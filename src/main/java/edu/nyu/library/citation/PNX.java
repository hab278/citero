package edu.nyu.library.citation;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Splitter;

/**
 * PNX format class. Imports from PNX formatted strings and exports to PNX
 * formatted strings.
 * 
 * @author hab278
 * 
 */

public class PNX extends Format {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The seminal CSF item */
	private CSF item;
	/** Strings for the data and properties */
	private String input, prop;

	/**
	 * Default constructor, instantiates data maps and CSF item.
	 * 
	 * @param input
	 *            A string representation of the data payload.
	 */
	public PNX(String input) {
		super(input);
		logger.info("PNX FORMAT");
		this.input = input;
		item = new CSF();
		prop = "";
		doImport();
		try {
			item.load(prop);
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
	public PNX(CSF item) {
		super(item);
		logger.info("PNX FORMAT");
		this.item = item;
		this.input = item.data();
	}

	@Override
	public CSF CSF() {
		return item;
	}

	@Override
	public String export() {
		// Export is simple, just use the XMLStringParser!
		String itemType = item.config().getString("itemType");
		XMLStringParser xml = new XMLStringParser();
		// For each type, simply use xPath to build a PNX

		if (itemType.equals("audioRecording"))
			xml.build("//display/type", "audio");
		else if (itemType.equals("videoRecording"))
			xml.build("//display/type", "video");
		else if (itemType.equals("journalArticle"))
			xml.build("//display/type", "article");
		else
			xml.build("//display/type", itemType);
		Iterator<?> itr = item.config().getKeys();
		// Now do it for each key...
		while (itr.hasNext()) {
			String key = (String) itr.next();
			String value = item.config().getString(key);
			if (key.equals("author"))
				for (String str : item.config().getStringArray(key))
					xml.build("//display//creator", str);
			else if (key.equals("contributor"))
				for (String str : item.config().getStringArray(key))
					xml.build("//display//contributor", str);
			else if (key.equals("publisher"))
				if (item.config().containsKey("place"))
					xml.build("//display/publisher", value + " : "
							+ item.config().getString("place"));
				else
					xml.build("//display/publisher", value);
			else if (key.equals("date"))
				xml.build("//display/creationdate", value);
			else if (key.equals("language"))
				xml.build("//display/language", value);
			else if (key.equals("pages"))
				xml.build("//display/format", value);
			else if (key.equals("ISBN"))
				xml.build("//display/identifier", "$$Cisbn$$V" + value);
			else if (key.equals("ISSN"))
				xml.build("//display/identifier", "$$Cissn$$V" + value);
			else if (key.equals("edition"))
				xml.build("//display/edition", value);
			else if (key.equals("tags"))
				xml.build("//search/subject", value);
			else if (key.equals("callNumber"))
				xml.build("//enrichment/classificationlcc", value);
		}
		// return the xml
		return xml.out();
	}

	/**
	 * Uses configuration to build a CSF object.
	 */
	private void doImport() {
		// Importing is easy thanks to xpath and XMLStringParser
		XMLStringParser xml = new XMLStringParser(input);
		String itemType = xml.xpath("//display/type");

		// Get itemtype by xpath
		if (itemType.equals("book") || item.equals("Books"))
			itemType = "book";
		else if (itemType.equals("audio"))
			itemType = "audioRecording";
		else if (itemType.equals("video"))
			itemType = "videoRecording";
		else if (itemType.equals("report"))
			itemType = "report";
		else if (itemType.equals("webpage"))
			itemType = "webpage";
		else if (itemType.equals("article") || itemType.equals("journal"))
			itemType = "journalArticle";
		else if (itemType.equals("thesis"))
			itemType = "thesis";
		else if (itemType.equals("map"))
			itemType = "map";
		else
			itemType = "document";

		addProperty("itemType", itemType );
		addProperty("title", xml.xpath("//display/title") );

		// do the same with the creators
		String creators = xml.xpath("//display/creator");
		String contributors = xml.xpath("//display/contributor");

		if (creators.isEmpty() && !contributors.isEmpty()) { // <creator> not
																// available
																// using
																// <contributor>
																// as author
																// instead
			creators = contributors;
			contributors = "";
		}

		if (creators.isEmpty() && contributors.isEmpty())
			creators = xml.xpath("//addata/addau");

		if (!creators.isEmpty()) {
			String authors = "";
			for (String str : Splitter.on("; ").trimResults().split(creators))
				if (!authors.isEmpty())
					authors += ", " + str;
				else
					authors += str;
			addProperty("creator.author", authors );
		}

		String contribs = "";
		if (!contributors.isEmpty()) {
			for (String str : Splitter.on("; ").trimResults()
					.split(contributors))
				if (!contribs.isEmpty())
					contribs += ", " + str;
				else
					contribs += str;
			addProperty("creator.contributor", contribs );
		}

		// Then do it for everything else.
		if (!xml.xpath("//display/publisher").isEmpty()) {
			String publisher = "";
			String place = "";
			if (xml.xpath("//display/publisher").contains(" : "))
				for (String str : Splitter.on(" : ").split(
						xml.xpath("//display/publisher")))
					if (!place.isEmpty())
						publisher = str.replaceAll(
								",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", "");
					else
						place = str.replaceAll(
								",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", "");
			else
				publisher = xml.xpath("//display/publisher").replaceAll(
						",\\s*c?\\d+|[\\(\\)\\[\\]]|(\\.\\s*)?", "");
			addProperty("publisher", publisher );
			addProperty("place", place );
		}

		if (!xml.xpath("//display/creationdate|//search/creationdate")
				.isEmpty())
			addProperty("date"
					, xml.xpath("//display/creationdate|//search/creationdate")
					);

		if (!xml.xpath("//display/language").isEmpty())
			addProperty("language: ", xml.xpath("//display/language") );

		String pages;
		pages = xml.xpath("//display/format");
		if (!pages.isEmpty())
			if (pages.matches(".*[0-9]+.*")) {
				pages = pages.replaceAll("[\\(\\)\\[\\]]", "")
						.replaceAll("\\D", " ").trim().split(" ")[0];
				addProperty("pages", pages );
				addProperty("numPages", pages );
			}

		if (!xml.xpath("//display/identifier").isEmpty()) {
			String isbn = "";
			String issn = "";
			for (String str : Splitter.on(';').trimResults().omitEmptyStrings()
					.split(xml.xpath("//display/identifier"))) {
				String key = str.contains("isbn") ? "ISBN" : "ISSN";
				if (key.equals("ISBN"))
					if (!isbn.isEmpty())
						isbn += ", " + str.trim().replaceAll("\\D", "");
					else
						isbn += str.trim().replaceAll("\\D", "");
				else if (!issn.isEmpty())
					issn += ", " + str.trim().replaceAll("\\D", "");
				else
					issn += str.trim().replaceAll("\\D", "");
			}

			if (!isbn.isEmpty())
				addProperty("ISBN", isbn );
			if (!issn.isEmpty())
				addProperty("ISSN", issn );
		}

		if (!xml.xpath("//display/edition").isEmpty())
			addProperty("edition", xml.xpath("//display/edition") );
		if (!xml.xpath("//search/subject").isEmpty())
			addProperty("tags", xml.xpath("//search/subject") );
		if (!xml.xpath("//enrichment/classificationlcc").isEmpty())
			addProperty("callNumber"
					, xml.xpath("//enrichment/classificationlcc") );

		// logger.debug(prop);
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
		prop += field + ": " + value + "\n";
	}
}
