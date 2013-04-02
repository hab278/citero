package edu.nyu.library.citero;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.google.common.base.Splitter;

import edu.nyu.library.citero.utils.XMLUtil;

/**
 * Xerxes_XML format class. Imports from Xerxes_XML formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class Xerxes_XML extends Format {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(Xerxes_XML.class);
    /** The seminal CSF item. */
    private CSF item;
    /** Strings for the properties. */
    private String prop;
    private XMLUtil xml;
    private static final Map<String, String> TYPE_MAP;

    static {
    Map<String, String> typeMap = new HashMap<String, String>();
    typeMap.put("Article", "journalArticle");
//    typeMap.put("ADVS", "film");
//    typeMap.put("CTLG", "magazineArticle");
//    typeMap.put("INPR", "manuscript");
//    typeMap.put("JFULL", "journal");
//    typeMap.put("PAMP", "manuscript");
//    typeMap.put("SER", "book");
//    typeMap.put("SLIDE", "artwork");
//    typeMap.put("UNBILL", "manuscript");
//    typeMap.put("CPAPER", "conferencePaper");
//    typeMap.put("WEB", "webpage");
//    typeMap.put("EDBOOK", "book");
//    typeMap.put("MANSCPT", "manuscript");
//    typeMap.put("GOVDOC", "document");
    TYPE_MAP = Collections.unmodifiableMap(typeMap);

}

    /**
     * Default constructor, instantiates data maps and CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     */
    public Xerxes_XML(final String in) {
        super(in);
        logger.debug("Xerxes_XML FORMAT");
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
    public Xerxes_XML(final CSF file) {
        super(file);
        logger.debug("Xerxes_XML FORMAT");
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
        logger.info("Importing to Xerxes_XML");
        addItemType("//record/xerxes_record/format");
        checkAndAdd("//record/xerxes_record/book_title", "bookTitle");
        checkAndAdd("//record/xerxes_record/year", "year");
        checkAndAdd("//record/xerxes_record/description", "note");
        checkAndAdd("//record/xerxes_record/start_page", "startPage");
        checkAndAdd("//record/xerxes_record/end_page", "endPage");
        checkAndAdd("//record/xerxes_record/journal_title", "publicationTitle");
        checkAndAdd("//record/xerxes_record/volume", "volume");
        checkAndAdd("//record/xerxes_record/issue", "issue");
        checkAndAdd("//record/xerxes_record/place", "place");
        checkAndAdd("//record/xerxes_record/publisher", "publisher");
        checkAndAdd("//record/xerxes_record/series_title", "series");
        checkAndAdd("//record/xerxes_record/abstract", "abstractNote");
        checkAndAdd("//record/xerxes_record/standard_numbers/issn", "ISSN");
        checkAndAdd("//record/xerxes_record/standard_numbers/oclc", "OCLC");
        checkAndAdd("//record/xerxes_record/standard_numbers/isbn", "ISBN");
        checkAndAdd("//record/xerxes_record/links/link[@type='pdf']/url", "attachments");
        checkAndAdd("//record/xerxes_record/links/link[@type='online']/url", "url");
        addProperty("importedFrom", "Xerxes XML");
        
        
        logger.debug("Final Properties String:\n" + prop);
    }
    
    private void checkAndAdd(String check, String add){
        if(xml.xpath(check).isEmpty())
            addProperty(add,check);
    }
    
    private void addItemType(String check){
        String itemType = "document";
        String rawType = xml.xpath(check);
        if(!rawType.isEmpty() && TYPE_MAP.containsKey(rawType))
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
