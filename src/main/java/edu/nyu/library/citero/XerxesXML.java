package edu.nyu.library.citero;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.nyu.library.citero.utils.XMLUtil;

/**
 * XerxesXML format class. Imports from XerxesXML formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class XerxesXML extends Format {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(XerxesXML.class);
    /** The seminal CSF item. */
    private CSF item;
    /** Strings for the properties. */
    private String prop;
    /** Using XMLUtil to parse the XML. **/
    private XMLUtil xml;
    /** A map that maps item types for CSF. **/
    private static final Map<String, String> TYPE_MAP;
    static {
        Map<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("Article", "journalArticle");
        typeMap.put("Report", "report");
        typeMap.put("Book", "book");
        typeMap.put("Tests & Measures", "document");
        typeMap.put("Patent", "patent");
        typeMap.put("Newspaper", "newspaperArticle");
        typeMap.put("Dissertation", "thesis");
        typeMap.put("Thesis", "thesis");
        typeMap.put("Conference Proceeding", "document");
        typeMap.put("Conference Paper", "conferencePaper");
        typeMap.put("Hearing", "hearing");
        typeMap.put("Working Paper", "report");
        typeMap.put("Book Review", "report");
        typeMap.put("Film Review", "report");
        typeMap.put("Review", "report");
        typeMap.put("Pamphlet", "manuscript");
        typeMap.put("Essay", "document");
        typeMap.put("Book Chapter", "bookSection");
        typeMap.put("Microfilm", "document");
        typeMap.put("Microfiche", "document");
        typeMap.put("Microopaque", "document");
        typeMap.put("Book--Large print", "book");
        typeMap.put("Book--Braille", "book");
        typeMap.put("eBook", "book");
        typeMap.put("Archive", "document");
        typeMap.put("Map", "map");
        typeMap.put("Printed Music", "document");
        typeMap.put("Audio Book", "audioRecording");
        typeMap.put("Sound Recording", "audioRecording");
        typeMap.put("Photograph or Slide", "presentation");
        typeMap.put("Video", "videoRecording");
        typeMap.put("Electronic Resource", "attachment");
        typeMap.put("Journal", "document");
        typeMap.put("Website", "webpage");
        typeMap.put("Unknown", "document");
        TYPE_MAP = Collections.unmodifiableMap(typeMap);
    }

    /**
     * Default constructor, instantiates data maps and CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     */
    public XerxesXML(final String in) {
        super(in);
        logger.debug("XerxesXML FORMAT");
        input = in;
        prop = "";
        item = new CSF();
        xml = new XMLUtil();
        xml.load(input);
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
    public XerxesXML(final CSF file) {
        super(file);
        logger.debug("XerxesXML FORMAT");
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
        logger.debug("Importing to XerxesXML");
        addItemType();
        addAuthors();
        checkAndAdd("//record/xerxes_record/book_title", "bookTitle");
        checkAndAdd("//record/xerxes_record/journal_title", "journalTitle");
        checkAndAdd("//record/xerxes_record/year", "year");
        checkAndAdd("//record/xerxes_record/description", "note");
        checkAndAdd("//record/xerxes_record/subjects/subject", "tags");
        checkAndAdd("//record/xerxes_record/start_page", "startPage");
        checkAndAdd("//record/xerxes_record/end_page", "endPage");
        checkAndAdd("//record/xerxes_record/journal_title", "publicationTitle");
        checkAndAdd("//record/xerxes_record/volume", "volume");
        checkAndAdd("//record/xerxes_record/issue", "issue");
        checkAndAdd("//record/xerxes_record/place", "place");
        checkAndAdd("//record/xerxes_record/publisher", "publisher");
        checkAndAdd("//record/xerxes_record/series_title", "series");
        checkAndAdd("//record/xerxes_record/abstract", "abstractNote");
        checkAndAdd("//record/xerxes_record/standard_numbers/issn", "issn");
        checkAndAdd("//record/xerxes_record/standard_numbers/oclc", "oclc");
        checkAndAdd("//record/xerxes_record/standard_numbers/isbn", "isbn");
        checkAndAdd("//record/xerxes_record/links/link[@type='pdf']/url", "attachments");
        checkAndAdd("//record/xerxes_record/links/link[@type='online']/url", "url");
        checkAndAdd("//record/xerxes_record/call_number", "callNumber");
        checkAndAdd("//record/xerxes_record/title_normalized", "normalizedTitle");
        addProperty("importedFrom", "Xerxes XML");

        logger.debug("Final Properties String:\n" + prop);
    }

    /**
     * This method adds authors, beginning with rank 1, primary author, up until
     * there are no more ranks. Each successive author is a contributor.
     */
    private void addAuthors() {
        int i = 1;
        String check = "//record/xerxes_record/authors/author[@rank='" + i + "']/display";
        checkAndAdd(check, "author");
        do {
            check = "//record/xerxes_record/authors/author[@rank='" + ++i + "']/display";
            checkAndAdd(check, "contributor");
        } while (!xml.xpath(check).isEmpty());
    }

    /**
     * Checks if the xpath expression returns anything, then iterates through all possible
     * values and adds them all.
     * @param check The xpath expression to check.
     * @param add The field to add the value to.
     */
    private void checkAndAdd(final String check, final String add) {
        if (!xml.xpath(check).isEmpty()) {
            String[] arr = xml.xpathArray(check);
            for (String s : arr)
                addProperty(add, s);
        }
    }

    /**
     * A Method to scan the map and convert itemType from Xerxes to CSF style items.
     */
    private void addItemType() {
        String itemType = "document";
        String rawType = xml.xpath("//record/xerxes_record/format");
        if (!rawType.isEmpty() && TYPE_MAP.containsKey(rawType))
            itemType = TYPE_MAP.get(rawType);
        addProperty("itemType", itemType);
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
