package edu.nyu.library.citero;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Splitter;

import edu.nyu.library.citero.utils.XMLUtil;

/**
 * PNX format class. Imports from PNX formatted strings and exports to PNX
 * formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class PNX extends Format {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(PNX.class);
    /** The seminal CSF item. */
    private CSF item;
    /** Strings for the properties. */
    private String prop;

    /**
     * Default constructor, instantiates data maps and CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     */
    public PNX(final String in) {
        super(in);
        logger.debug("PNX FORMAT");
        input = in;
        prop = "";
        item = new CSF();
        doImport();
        try {
            item = new CSF(prop);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor that accepts a CSF object. Does the same as the default
     * Constructor.
     * 
     * @param file
     *            The CSF object, it gets loaded into this object.
     */
    public PNX(final CSF file) {
        super(file);
        logger.debug("PNX FORMAT");
        item = file;
        input = item.doExport();
    }

    @Override
    public final CSF toCSF() {
        return item;
    }

    /**
     * Uses configuration to build a CSF object.
     */
    private void doImport() {
        logger.debug("Importing to PNX");

        // Importing is easy thanks to xpath and XMLUtil
        XMLUtil xml = new XMLUtil();
        xml.load(input);
        String itemType = xml.xpath("//display/type");

        // Get itemtype by xpath
        if (itemType.equals("book") || itemType.equals("Books"))
            itemType = "book";
        else if (itemType.equals("audio"))
            itemType = "audioRecording";
        else if (itemType.equals("video"))
            itemType = "videoRecording";
        else if (itemType.equals("report"))
            itemType = "report";
        else if (itemType.equals("webpage"))
            itemType = "webpage";
        else if (itemType.equals("article"))
            itemType = "journalArticle";
        else if (itemType.equals("journal"))
            itemType = "journal";
        else if (itemType.equals("thesis"))
            itemType = "thesis";
        else if (itemType.equals("map"))
            itemType = "map";
        else
            itemType = "document";

        addProperty("itemType", itemType);
        addProperty("title", xml.xpath("//display/title"));

        // do the same with the creators
        String creators = xml.xpath("//display/creator");
        String contributors = xml.xpath("//display/contributor");

        if (creators.isEmpty() && !contributors.isEmpty()) {
            // <creator> not available using <contributor> as author instead
            creators = contributors;
            contributors = "";
        }

        if (creators.isEmpty() && contributors.isEmpty())
            creators = xml.xpath("//addata/addau");

        //once you have a list of creators and contributors add them
        if (!creators.isEmpty()) {
            for (String str : Splitter.on("; ").trimResults().split(creators))
                addProperty("author", str);
        }

        if (!contributors.isEmpty()) {
            for (String str : Splitter.on("; ").trimResults()
                    .split(contributors))
                addProperty("contributor", str);
        }

        // Then do it for everything else.
        if (!xml.xpath("//display/publisher").isEmpty()) {
            String publisher = "";
            String place = "";
            //Gets publisher and place, if there is a colon then place is present
            if (xml.xpath("//display/publisher").contains(" : "))
                for (String str : Splitter.on(" : ").split(xml.xpath("//display/publisher")))
                    if (!place.isEmpty())
                        publisher = str;
                    else
                        place = str;
            else
                //if there isn't, just the publisher is present
                publisher = xml.xpath("//display/publisher");
            addProperty("publisher", publisher);
            if (!place.isEmpty())
                addProperty("place", place);
        }

        if (!xml.xpath("//display/creationdate|//search/creationdate")
                .isEmpty())
            addProperty("date",
                    xml.xpath("//display/creationdate|//search/creationdate"));

        if (!xml.xpath("//display/language").isEmpty())
            addProperty("language", xml.xpath("//display/language"));

        String pages;
        pages = xml.xpath("//display/format");
        if (!pages.isEmpty())
            if (pages.matches(".*[0-9]+.*")) {
                pages = pages.replaceAll("[\\(\\)\\[\\]]", "")
                        .replaceAll("\\D", " ").trim().split(" ")[0];
//                addProperty("pages", pages);
                addProperty("numPages", pages);
            }

        if (!xml.xpath("//display/identifier").isEmpty()) {
            StringBuffer isbn = new StringBuffer();
            StringBuffer issn = new StringBuffer();
            for (String str : Splitter.on(';').trimResults().omitEmptyStrings()
                    .split(xml.xpath("//display/identifier"))) {
                String key = str.contains("isbn") ? "ISBN" : "ISSN";
                if (key.equals("ISBN"))
                    if (!isbn.toString().isEmpty())
                        isbn.append(", " + str.trim().replaceAll("\\D", ""));
                    else
                        isbn.append(str.trim().replaceAll("\\D", ""));
                else if (!issn.toString().isEmpty())
                    issn.append(", " + str.trim().replaceAll("\\D", ""));
                else
                    issn.append(str.trim().replaceAll("\\D", ""));
            }

            if (!isbn.toString().isEmpty())
                for (String str : Splitter.on(",").trimResults()
                        .omitEmptyStrings().split(isbn.toString()))
                    addProperty("ISBN", str);
            if (!issn.toString().isEmpty())
                for (String str : Splitter.on(",").trimResults()
                        .omitEmptyStrings().split(issn.toString()))
                    addProperty("ISSN", str);
        }

        if (!xml.xpath("//display/edition").isEmpty())
            addProperty("edition", xml.xpath("//display/edition"));
        if (!xml.xpath("//search/subject").isEmpty())
            addProperty("tags", xml.xpath("//search/subject"));
        if (!xml.xpath("//display/subject").isEmpty())
            for (String str : xml.xpath("//display/subject").split(";"))
                addProperty("tags", str);
        if (!xml.xpath("//enrichment/classificationlcc").isEmpty())
            addProperty("callNumber",
                    xml.xpath("//enrichment/classificationlcc"));
        if (!xml.xpath("//control/recordid").isEmpty())
            addProperty("pnxRecordId", xml.xpath("//control/recordid"));

        addProperty("importedFrom", "PNX");
        logger.debug(prop);
    }

    /**
     * Method that maps value to field in a property format and adds it to the
     * property string.
     * 
     * @param field
     *            Represents the CSF key.
     * @param value
     *            Represents the value to be mapped.
     */
    private void addProperty(final String field, final String value) {
        prop += field + CSF.SEPARATOR + value.replace(",", "\\,").replace(".", "\\.")
                + "\n";
    }
}
