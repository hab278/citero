package edu.nyu.library.citero;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.stream.JsonWriter;

import edu.nyu.library.citero.utils.NameFormatter;

public class CSL extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(CSL.class);
    /** The unique CSF item. */
    private CSF item;
    /** String for the export. */
    protected String export;
    /** A bidirectional map for types. */
    private static final Map<String, String> TYPE_MAP, FIELD_MAP, NAME_MAP, DATE_MAP;
    static {
        Map<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("book", "book");
        typeMap.put("bookSection", "chapter");
        typeMap.put("journalArticle", "article-journal");
        typeMap.put("magazineArticle", "article-magazine");
        typeMap.put("newspaperArticle", "article-newspaper");
        typeMap.put("thesis", "thesis");
        typeMap.put("encyclopediaArticle", "entry-encyclopedia");
        typeMap.put("dictionaryEntry", "entry-dictionary");
        typeMap.put("conferencePaper", "paper-conference");
        typeMap.put("letter", "personal_communication");
        typeMap.put("manuscript", "manuscript");
        typeMap.put("interview", "interview");
        typeMap.put("film", "motion_picture");
        typeMap.put("artwork", "graphic");
        typeMap.put("webpage", "webpage");
        typeMap.put("report", "report");
        typeMap.put("bill", "bill");
        typeMap.put("case", "legal_case");
        typeMap.put("hearing", "bill");
        typeMap.put("patent", "patent");
        typeMap.put("statute", "legislation");
        typeMap.put("email", "personal_communication");
        typeMap.put("map", "map");
        typeMap.put("blogPost", "post-weblog");
        typeMap.put("instantMessage", "personal_communication");
        typeMap.put("forumPost", "post");
        typeMap.put("audioRecording", "song");
        typeMap.put("presentation", "speech");
        typeMap.put("videoRecording", "motion_picture");
        typeMap.put("tvBroadcast", "broadcast");
        typeMap.put("radioBroadcast", "broadcast");
        typeMap.put("podcast", "song");
        typeMap.put("computerProgram", "book");
        TYPE_MAP = Collections.unmodifiableMap(typeMap);
        
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("title", "title");
        fieldMap.put("publicationTitle", "container-title");
        fieldMap.put("reporter", "container-title");
        fieldMap.put("code", "container-title");
        fieldMap.put("seriesTitle", "collection-title");
        fieldMap.put("series", "collection-title");
        fieldMap.put("seriesNumber", "collection-number");
        fieldMap.put("publisher", "publisher");
        fieldMap.put("distributor", "publisher");
        fieldMap.put("place", "publisher-place");
        fieldMap.put("court", "authority");
        fieldMap.put("pages", "page");
        fieldMap.put("volume", "volume");
        fieldMap.put("issue", "issue");
        fieldMap.put("numberOfVolumes", "number-of-volumes");
        fieldMap.put("numPages", "number-of-pages");
        fieldMap.put("edition", "edition");
        fieldMap.put("version", "version");
        fieldMap.put("section", "section");
        fieldMap.put("type", "genre");
        fieldMap.put("libraryCatalog", "source");
        fieldMap.put("artworkSize", "dimension");
        fieldMap.put("runningTime", "dimension");
        fieldMap.put("medium", "medium");
        fieldMap.put("system", "medium");
        fieldMap.put("scale", "scale");
        fieldMap.put("archive", "archive");
        fieldMap.put("archiveLocation", "archive_location");
        fieldMap.put("meetingName", "event");
        fieldMap.put("conferenceName", "event");
        fieldMap.put("place", "event-place");
        fieldMap.put("abstractNote", "abstract");
        fieldMap.put("url", "URL");
        fieldMap.put("DOI", "DOI");
        fieldMap.put("ISBN", "ISBN");
        fieldMap.put("ISSN", "ISSN");
        fieldMap.put("callNumber", "call-number");
        fieldMap.put("extra", "note");
        fieldMap.put("number", "number");
        fieldMap.put("history", "references");
        fieldMap.put("shortTitle", "shortTitle");
        fieldMap.put("journalAbbreviation", "journalAbbreviation");
        fieldMap.put("language", "language");
        FIELD_MAP = Collections.unmodifiableMap(fieldMap);
        
        Map<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("author", "author");
        nameMap.put("editor", "editor");
        nameMap.put("bookAuthor", "container-author");
        nameMap.put("composer", "composer");
        nameMap.put("director", "director");
        nameMap.put("interviewer", "interviewer");
        nameMap.put("recipient", "recipient");
        nameMap.put("reviewedAuthor", "reviewed-author");
        nameMap.put("seriesEditor", "collection-editor");
        nameMap.put("translator", "translator");
        NAME_MAP = Collections.unmodifiableMap(nameMap);
        
        Map<String, String> dateMap = new HashMap<String, String>();
        dateMap.put("date", "issued");
        dateMap.put("accessDate", "accessed");
        DATE_MAP = Collections.unmodifiableMap(dateMap);
        
        
        
    }
    
    /**
     * Default constructor, instantiates CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     */
    public CSL(final String in) {
        super(in);
        logger.debug("CSL FORMAT");
        // set up the input and csf object
        try {
            item = new CSF(in);
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
    public CSL(final CSF file) {
        super(file);
        logger.debug("EASYBIB FORMAT");
        item = file;
    }

    @Override
    public String doExport() {
        logger.debug("Exporting to CSL");
        
        String cslType = item.config().getString("itemType");
        cslType = (TYPE_MAP.containsKey(cslType))? TYPE_MAP.get(cslType) : "article";
        
        StringWriter sWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(sWriter);
        try {
            writer.beginObject();
            writer.name("ITEM-1");
            writer.beginObject();
            writer.name("id");
            writer.value("ITEM-1");
            Iterator<?> itr = item.config().getKeys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                if (key.equals("itemType"))
                    continue;
                if(FIELD_MAP.containsKey(key)){
                    writer.name(FIELD_MAP.get(key));
                    writer.value(item.config().getString(key));
                }
                if(NAME_MAP.containsKey(key)){
                    writer.name(NAME_MAP.get(key));
                    NameFormatter name = NameFormatter.from(item.config().getString(key));
                    writer.beginArray();
                    writer.beginObject();
                    writer.name("family");
                    writer.value(name.lastName());
                    writer.name("given");
                    writer.value(name.firstName());
                    writer.endObject();
                    writer.endArray();
                }
                if(DATE_MAP.containsKey(key)){
                    writer.name(DATE_MAP.get(key));
                    writer.value(item.config().getString(key));
                }
            }
            writer.name("type");
            writer.value(cslType);
            writer.endObject();
            writer.endObject();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sWriter.toString();
    }

    @Override
    public void subFormat() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CSF toCSF() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private String exportType(String key){
        if(TYPE_MAP.containsKey(key))
            return TYPE_MAP.get(key);
        return key;
    }

}
