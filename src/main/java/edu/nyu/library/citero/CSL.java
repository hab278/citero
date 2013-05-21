package edu.nyu.library.citero;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.stream.JsonWriter;

public class CSL extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(CSL.class);
    /** String for the export. */
    protected String export;
    /** A bidirectional map for types. */
    private static final BiMap<String, String> TYPE_MAP;
    static {
        BiMap<String, String> typeMap = HashBiMap.create();

        typeMap.put("artwork", "graphic");
        typeMap.put("attachment", "attachment");
        typeMap.put("audioRecording", "song");
        typeMap.put("bill", "bill");
        typeMap.put("blogPost", "post-weblog");
        typeMap.put("book", "book");
        typeMap.put("bookSection", "chapter");
        typeMap.put("case", "legal_case");
        typeMap.put("computerProgram", "book");
        typeMap.put("conferencePaper", "paper-conference");
        typeMap.put("dictionaryEntry", "entry-dictionary");
        typeMap.put("document", "document");
        typeMap.put("email", "personal_communication");
        typeMap.put("encyclopediaArticle", "entry-encyclopedia");
        typeMap.put("film", "motion_picture");
        typeMap.put("forumPost", "post");
        typeMap.put("hearing", "bill");
        typeMap.put("instantMessage", "personal_communication");
        typeMap.put("interview", "interview");
        typeMap.put("journalArticle", "article-journal");
        typeMap.put("letter", "personal_communication");
        typeMap.put("magazineArticle", "article-magazine");
        typeMap.put("manuscript", "manuscript");
        typeMap.put("map", "map");
        typeMap.put("newspaperArticle", "article-newspaper");
        typeMap.put("note", "note");
        typeMap.put("patent", "patent");
        typeMap.put("podcast", "song");
        typeMap.put("presentation", "speech");
        typeMap.put("radioBroadcast", "broadcast");
        typeMap.put("report", "report");
        typeMap.put("statute", "legislation");
        typeMap.put("thesis", "thesis");
        typeMap.put("tvBroadcast", "broadcast");
        typeMap.put("videoRecording", "motion_picture");
        typeMap.put("webpage", "webpage");

        TYPE_MAP = com.google.common.collect.Maps.unmodifiableBiMap(typeMap);
    }
    
    public CSL(String input) {
        super(input);
        // TODO Auto-generated constructor stub
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

}
