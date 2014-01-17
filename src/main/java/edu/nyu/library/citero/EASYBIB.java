package edu.nyu.library.citero;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.stream.JsonWriter;

import edu.nyu.library.citero.utils.NameFormatter;


/**
 * JSON format class for Easy BIB exports.
 * Exports to Easy Bib formatted JSON strings.
 * 
 * @author hab278
 *
 */

public class EASYBIB extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(EASYBIB.class);
    /** The unique CSF item. */
    private CSF item;
    /** String for the export. */
    protected String export;
    /** A bidirectional map for types. */
    private static final BiMap<String, String> TYPE_MAP;
    static {
        BiMap<String, String> typeMap = HashBiMap.create();

        typeMap.put("book", "book");
        typeMap.put("blog", "blogPost");
        typeMap.put("chapter", "bookSection");
        typeMap.put("conference", "conferencePaper");
        typeMap.put("dissertation", "thesis");
        typeMap.put("film", "videoRecording");
        typeMap.put("journal", "journalArticle");
        typeMap.put("magazine", "magazineArticle");
        typeMap.put("newspaper", "newspaperArticle");
        typeMap.put("painting", "artwork");
        typeMap.put("report", "report");
        typeMap.put("software", "computerProgram");
        typeMap.put("website", "webpage");

        TYPE_MAP = com.google.common.collect.Maps.unmodifiableBiMap(typeMap);
    }

    /**
     * Default constructor, instantiates CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     */
    public EASYBIB(final String in) {
        super(in);
        logger.debug("EASYBIB FORMAT");
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
    public EASYBIB(final CSF file) {
        super(file);
        logger.debug("EASYBIB FORMAT");
        item = file;
    }


    @Override
    public final CSF toCSF() {
        return item;
    }

    @Override
    public final String doExport() {
        logger.debug("Exporting to EasyBIB");
        StringWriter sWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(sWriter);
        String pubtype = "pubnonperiodical";
        String itemType = item.config().getString("itemType");
        try {
            writer.beginObject();
            writer.name("source");
            if (TYPE_MAP.containsValue(itemType)) {
                writer.value(TYPE_MAP.inverse().get(itemType));
                writer.name(TYPE_MAP.inverse().get(itemType));
            } else if (itemType.equals("journal")) {
                writer.value("journal");
                writer.name("journal");
            } else {
                writer.value("book");
                writer.name("book");
            }
            writer.beginObject();
            writer.name("title").value(item.config().getString("title"));
            writer.endObject();
            writer.name("pubtype");
            writer.beginObject();
            writer.name("main");
            if (itemType.equals("magazineArticle"))
                pubtype = "pubmagazine";
            else if (itemType.equals("newspaperArticle"))
                pubtype = "pubnewspaper";
            else if (itemType.equals("journalArticle") || itemType.equals("journal"))
                pubtype = "pubjournal";
            else if (itemType.equals("webpage"))
                pubtype = "pubonline";
            writer.value(pubtype);
            writer.endObject();
            writer.name(pubtype);
            writer.beginObject();
            if (pubtype.equals("pubnonperiodical")) {
                if (item.config().containsKey("title"))
                    writer.name("title")
                            .value(item.config().getString("title"));
                if (item.config().containsKey("publisher"))
                    writer.name("publisher").value(
                            item.config().getString("publisher"));
                if (item.config().containsKey("place"))
                    writer.name("city").value(item.config().getString("place"));
                if (item.config().containsKey("volume"))
                    writer.name("vol").value(item.config().getString("volume"));
                if (item.config().containsKey("edition"))
                    writer.name("edition").value(
                            item.config().getString("edition"));
            } else if (pubtype.equals("pubmagazine")) {
                if (item.config().containsKey("title"))
                    writer.name("title")
                            .value(item.config().getString("title"));
                if (item.config().containsKey("volume"))
                    writer.name("vol").value(item.config().getString("volume"));
            } else if (pubtype.equals("pubnewspaper")) {

                if (item.config().containsKey("title"))
                    writer.name("title")
                            .value(item.config().getString("title"));
                if (item.config().containsKey("edition"))
                    writer.name("edition").value(
                            item.config().getString("edition"));
                if (item.config().containsKey("section"))
                    writer.name("section").value(
                            item.config().getString("section"));
                if (item.config().containsKey("place"))
                    writer.name("city").value(item.config().getString("place"));
            } else if (pubtype.equals("pubjournal")) {

                if (item.config().containsKey("title"))
                    writer.name("title")
                            .value(item.config().getString("title"));
                if (item.config().containsKey("issue"))
                    writer.name("issue")
                            .value(item.config().getString("issue"));
                if (item.config().containsKey("volume"))
                    writer.name("volume").value(
                            item.config().getString("volume"));
                if (item.config().containsKey("series"))
                    writer.name("series").value(
                            item.config().getString("series"));
            } else if (pubtype.equals("pubonline")) {

                if (item.config().containsKey("title"))
                    writer.name("title")
                            .value(item.config().getString("title"));
                if (item.config().containsKey("institution"))
                    writer.name("inst").value(
                            item.config().getString("institution"));
                if (item.config().containsKey("date"))
                    writer.name("year").value(item.config().getString("date"));
                if (item.config().containsKey("url"))
                    writer.name("url").value(item.config().getString("url"));
                if (item.config().containsKey("accessDate"))
                    writer.name("dayaccessed").value(
                            item.config().getString("accessDate"));
            }
            if (!pubtype.equals("pubonline")) {
                if (item.config().containsKey("date"))
                    writer.name("year").value(item.config().getString("date"));
                if (item.config().containsKey("firstPage"))
                    writer.name("start").value(
                            item.config().getString("firstPage"));
                if (item.config().containsKey("numPages"))
                    if (item.config().containsKey("firstPage"))
                        writer.name("end").value(
                                item.config().getInt("firstPage")
                                        + item.config().getInt("numPages"));
                    else
                        writer.name("end").value(
                                item.config().getInt("numPages"));
            }
            writer.endObject();
            writer.name("contributors");
            writer.beginArray();
            if (item.config().containsKey("author")
                    || item.config().containsKey("inventor")
                    || item.config().containsKey("contributor")) {
                if (item.config().containsKey("author"))
                    for (String str : item.config().getStringArray("author"))
                        addContributor(writer, "author", str);
                if (item.config().containsKey("inventor"))
                    for (String str : item.config().getStringArray("inventor"))
                        addContributor(writer, "author", str);
                if (item.config().containsKey("contributor"))
                    for (String str : item.config().getStringArray(
                            "contributor"))
                        addContributor(writer, "author", str);
            } else if (item.config().containsKey("editor")
                    || item.config().containsKey("seriesEditor")) {
                if (item.config().containsKey("editor"))
                    for (String str : item.config().getStringArray("editor"))
                        addContributor(writer, "editor", str);
                if (item.config().containsKey("seriesEditor"))
                    for (String str : item.config().getStringArray(
                            "seriesEditor"))
                        addContributor(writer, "editor", str);
            } else if (item.config().containsKey("translator"))
                for (String str : item.config().getStringArray("translator"))
                    addContributor(writer, "translator",  str);
            writer.endArray();
            writer.endObject();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        export = sWriter.toString();
 
        //Allow subformatting
        subFormat();

        return export;
    }

    /**
     * Adds name to the writer by utilizing the NameFormatter class.
     * @param writer
     *              The writer to write the name to.
     * @param function
     *              The string that defines what function this object plays, for example, Author.
     * @param str
     *              The string that contains the raw name.
     * @throws IOException
     *              JsonWriter might throw this exception if it fails.
     */
    private void addContributor(final JsonWriter writer, final String function, final String str) throws IOException {
        NameFormatter name = NameFormatter.from(str);
        writer.beginObject();
        writer.name("function").value(function);
        if (!name.firstName().isEmpty())
            writer.name("first").value(name.firstName());
        if (!name.middleName().isEmpty())
            writer.name("middle").value(name.middleName());
        if (!name.lastName().isEmpty())
            writer.name("last").value(name.lastName());
        writer.endObject();
    }

    @Override
    public void subFormat() {
    }
}
