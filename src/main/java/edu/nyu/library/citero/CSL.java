package edu.nyu.library.citero;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.stream.JsonWriter;

public class CSL extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(CSL.class);
    /** The unique CSF item. */
    private CSF item;
    /** String for the export. */
    protected String export;
    /** A bidirectional map for types. */
    private static final Map<String, String> TYPE_MAP;
    static {
        Map<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("artwork", "graphic");
        typeMap.put("blogPost", "post-weblog");
        typeMap.put("bookSection", "chapter");
        typeMap.put("case", "legal_case");
        typeMap.put("conferencePaper", "paper-conference");
        typeMap.put("dictionaryEntry", "entry-dictionary");
        typeMap.put("encyclopediaArticle", "entry-encyclopedia");
        typeMap.put("forumPost", "post");
        typeMap.put("journalArticle", "article-journal");
        typeMap.put("magazineArticle", "article-magazine");
        typeMap.put("newspaperArticle", "article-newspaper");
        typeMap.put("presentation", "speech");
        typeMap.put("statute", "legislation");
        typeMap.put("computerProgram", "book");
        typeMap.put("email", "personal_communication");
        typeMap.put("instantMessage", "personal_communication");
        typeMap.put("letter", "personal_communication");
        typeMap.put("tvBroadcast", "broadcast");
        typeMap.put("radioBroadcast", "broadcast");
        typeMap.put("audioRecording", "song");
        typeMap.put("podcast", "song");
        typeMap.put("hearing", "bill");
        typeMap.put("videoRecording", "motion_picture");
        typeMap.put("film", "motion_picture");
        TYPE_MAP = Collections.unmodifiableMap(typeMap);
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
        StringWriter sWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(sWriter);
        try {
            writer.beginObject();
            writer.name("ITEM-1");
            writer.beginObject();
            writer.name("id");
            writer.value("ITEM-1");
            writer.name("title");
            writer.value("The Three Musketeers");
            writer.name("author");
            writer.beginArray();
            writer.beginObject();
            writer.name("family");
            writer.value("Dumas");
            writer.name("given");
            writer.value("Alexander");
            writer.endObject();
            writer.endArray();
            writer.name("type");
            writer.value(exportType("case"));
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
