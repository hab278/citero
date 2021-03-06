package edu.nyu.library.citero;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbibtex.LaTeXObject;
import org.jbibtex.LaTeXParser;
import org.jbibtex.LaTeXPrinter;
import org.jbibtex.ParseException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * BibTeX format class. Imports from BibTeX formatted strings and exports to
 * BibTeX formatted strings.
 *
 * @author hab278
 *
 */
@SourceFormat
public class BIBTEX extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(BIBTEX.class);
    /** The properties string. */
    private String prop;
    /** The reader to read the chars. */
    private StringReader reader;
    /** The much needed CSF item. */
    private CSF item;
    /** String for the export. */
    protected String export;
    /** Static maps, these translations do not change. */
    private static final Map<String, String> FIELD_MAP, TYPE_MAP, EXPORT_TYPE_MAP,
            EXPORT_FIELD_MAP;
    static {
        Map<String, String> fMap = new HashMap<String, String>();
        fMap.put("address", "place");
        fMap.put("chapter", "section");
        fMap.put("copyright", "rights");
        fMap.put("isbn", "isbn");
        fMap.put("issn", "issn");
        fMap.put("iccn", "callNumber");
        fMap.put("location", "archiveLocation");
        fMap.put("shorttitle", "shortTitle");
        fMap.put("doi", "doi");
        fMap.put("booktitle", "publicationTitle");
        fMap.put("school", "publisher");
        fMap.put("institution", "publisher");
        FIELD_MAP = Collections.unmodifiableMap(fMap);

        Map<String, String> tMap = new HashMap<String, String>();
        tMap.put("article", "journalArticle");
        tMap.put("inbook", "bookSection");
        tMap.put("incollection", "bookSection");
        tMap.put("phdthesis", "thesis");
        tMap.put("unpublished", "manuscript");
        tMap.put("inproceedings", "conferencePaper");
        tMap.put("conference", "conferencePaper");
        tMap.put("techreport", "report");
        tMap.put("booklet", "book");
        tMap.put("manual", "book");
        tMap.put("mastersthesis", "thesis");
        tMap.put("misc", "book");
        TYPE_MAP = Collections.unmodifiableMap(tMap);

        Map<String, String> etMap = new HashMap<String, String>();
        etMap.put("book", "book");
        etMap.put("bookSection", "incollection");
        etMap.put("journalArticle", "article");
        etMap.put("magazineArticle", "article");
        etMap.put("newspaperArticle", "article");
        etMap.put("thesis", "phdthesis");
        etMap.put("letter", "misc");
        etMap.put("manuscript", "unpublished");
        etMap.put("patent", "patent");
        etMap.put("interview", "misc");
        etMap.put("film", "misc");
        etMap.put("artwork", "misc");
        etMap.put("webpage", "misc");
        etMap.put("conferencePaper", "inproceedings");
        etMap.put("report", "techreport");
        EXPORT_TYPE_MAP = Collections.unmodifiableMap(etMap);

        Map<String, String> efMap = new HashMap<String, String>();
        efMap.put("place", "address");
        efMap.put("section", "chapter");
        efMap.put("rights", "copyright");
        efMap.put("isbn", "isbn");
        efMap.put("issn", "issn");
        efMap.put("callNumber", "iccn");
        efMap.put("archiveLocation", "location");
        efMap.put("shortTitle", "shorttitle");
        efMap.put("doi", "doi");
        efMap.put("abstractNote", "abstract");
        efMap.put("country", "nationality");
        efMap.put("edition", "edition");
        efMap.put("type", "type");
        efMap.put("series", "series");
        efMap.put("title", "title");
        efMap.put("volume", "volume");
        efMap.put("shortTitle", "shorttitle");
        efMap.put("language", "language");
        efMap.put("assignee", "assignee");
        EXPORT_FIELD_MAP = Collections.unmodifiableMap(efMap);
    }

    /**
     * Default constructor, instantiates CSF item.
     *
     * @param in
     *            A string representation of the data payload.
     */
    public BIBTEX(final String in) {
        super(in);
        logger.debug("BIBTEX FORMAT");
        // set up the input and csf object
        input = in;
        // load the variables
        loadVars();
        // import and load
        item = new CSF();
        doImport();
        try {
            item = new CSF(prop);
        } catch (ConfigurationException e) {
            // e.printStackTrace();
            logger.debug(e.toString());
        }
        logger.debug(prop);
    }

    /**
     * Constructor that accepts a CSF object. Does the same as the default
     * Constructor.
     *
     * @param file
     *            The CSF object, it gets loaded into this object.
     */
    public BIBTEX(final CSF file) {
        super(file);
        logger.debug("BIBTEX FORMAT");
        item = file;
        input = item.doExport();
        loadVars();
    }

    /**
     * This method instantiates each variable and populates the maps.
     */
    private void loadVars() {
        reader = new StringReader(this.input);
        prop = "";
    }

    @Override
    public final CSF toCSF() {
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
    private String mapValue(final String key, final String value) {
        logger.debug("Mapping " + value + " to " + key);
        String out = ",\n\t" + key + " = ";
        if (value.matches("^\\d+$") && !key.equals("numpages")
                && !key.equals("isbn") && !key.equals("issn"))
            return out + value;
        return out + "{" + value + "}";
    }

    @Override
    public final String doExport() {
        logger.debug("Exporting to BibTeX");
        // Simply reverse import.
        StringBuffer buff = new StringBuffer();
        String itemType = item.config().getString("itemType");
        // in BibTeX formatting
        buff.append("@"
                + (EXPORT_TYPE_MAP.containsKey(item.config()
                        .getString("itemType")) ? EXPORT_TYPE_MAP.get(itemType)
                        : "misc") + "{" + citeKey());
        Iterator<?> itr = item.config().getKeys();
        while (itr.hasNext()) {
            // Try to map every key we have
            String key = (String) itr.next();
            if (key.equals("itemType"))
                continue;
            if (EXPORT_FIELD_MAP.containsKey(key))
                buff.append(mapValue(EXPORT_FIELD_MAP.get(key), item.config()
                        .getString(key)));
            else if (key.equals("reportNumber") || key.equals("issue")
                    || key.equals("seriesNumber") || key.equals("patentNumber"))
                buff.append(mapValue("number", item.config().getString(key)));
            else if (key.equals("accessDate"))
                buff.append(mapValue("urldate", item.config().getString(key)));
            else if (key.equals("publicationTitle"))
                if (itemType.equals("bookSection")
                        || itemType.equals("conferencePaper"))
                    buff.append(mapValue("booktitle", item.config()
                            .getString(key)));
                else
                    buff.append(mapValue("journal",
                            item.config().getString(key)));
            else if (key.equals("publisher"))
                if (itemType.equals("thesis"))
                    buff.append(mapValue("school",
                            item.config().getString(key)));
                else if (itemType.equals("report"))
                    buff.append(mapValue("institution", item.config()
                            .getString(key)));
                else
                    buff.append(mapValue("publisher", item.config()
                            .getString(key)));
            else if (key.equals("author") || key.equals("inventor")
                    || key.equals("contributor") || key.equals("editor")
                    || key.equals("seriesEditor") || key.equals("translator")) {
                StringBuffer names = new StringBuffer();
                for (String str : item.config().getStringArray(key))
                    names.append(" and " + str);
                if (key.equals("seriesEditor"))
                    buff.append(mapValue("editor", names.substring(" and ".length())));
                else
                    buff.append(mapValue(key, names.substring(" and ".length())));
            } else if (key.equals("extra"))
                buff.append(mapValue("note", item.config().getString(key)));
            else if (key.equals("date"))
                buff.append(mapValue("date", item.config().getString(key)));
            else if (key.equals("pages"))
                buff.append(mapValue("pages", item.config().getString(key)
                        .replace("-", "--")));
            else if (key.equals("date"))
                buff.append(mapValue("date", item.config().getString(key)));
            else if (itemType.equals("webpage"))
                buff.append(mapValue("howpublished",
                        item.config().getString(key)));
            else if (key.equals("tags")) {
                StringBuffer tags = new StringBuffer();
                for (String str : item.config().getStringArray(key))
                    tags.append(", " + str);
                buff.append(mapValue("keywords", tags.substring(2)));
            } else if (key.equals("note"))
                for (String str : item.config().getStringArray(key))
                    buff.append(mapValue("annote", str));
            else if (key.equals("attachments"))
                buff.append(mapValue("file", item.config().getString(key)));
            logger.debug(key);
        }
        // return the BibTeX entry as a string
        export = buff.append("\n}").toString();

        // Subformatting
        subFormat();
        return export;
    }

    /**
     * Generates a citekey, a key unique to that reference. This use the authors
     * last name, the title, and the date.
     *
     * @return The generated citekey.
     */
    private String citeKey() {
        String cite = "";
        final int maxLength = 3;
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
                    + (temp.length() > maxLength ? temp.substring(0, maxLength + 1) : temp);
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
    private void processField(final String field, final String value) {
        final int protocolLength = 7;
        // No use for empty values.
        if (value.trim().isEmpty())
            return;
        // If the fieldmap has the field, just add that and the value.
        if (FIELD_MAP.containsKey(field))
            addProperty(FIELD_MAP.get(field), value.replace(",", "\\,"));
        // Otherwise we have to map case by case.
        else if (field.equals("journal") || field.equals("fjournal"))
            if (prop.contains("publicationTitle"))
                addProperty("journalAbbreviation", value.replace(",", "\\,"));
            else
                addProperty("publicationTitle", value.replace(",", "\\,"));
        else if (field.equals("author") || field.equals("editor")
                || field.equals("translator")) {
            addProperty(field, value.replace(",", "\\,"));
        } else if (field.equals("institution") || field.equals("organization"))
            addProperty("backupPublisher", value.replace(",", "\\,"));
        else if (field.equals("number")) {
            if (prop.contains("itemType: report"))
                addProperty("reportNumber", value.replace(",", "\\,"));
            else if (prop.contains("itemType: book\n")
                    || prop.contains("itemType: bookSection"))
                addProperty("seriesNumber", value.replace(",", "\\,"));
            else
                addProperty("issue", value.replace(",", "\\,"));
        } else if (field.equals("month")) {
            addProperty("month", value.replace(",", "\\,"));
        } else if (field.equals("year")) {
            addProperty("year", value.replace(",", "\\,"));
        } else if (field.equals("pages")) {
            if (prop.contains("book\n") || prop.contains("thesis")
                    || prop.contains("manuscript"))
                addProperty("numPages", value.replace(",", "\\,"));
            else
                addProperty("pages",
                        value.replaceAll("--", "-").replace(",", "\\,"));
        } else if (field.equals("note")) {
            addProperty("extra", value.replace(",", "\\,"));
        } else if (field.equals("howpublished")) {
            if (value.length() >= protocolLength) {
                String str = value.substring(0, protocolLength);
                if (str.equals("http://") || str.equals("https:/")
                        || str.equals("mailto:"))
                    addProperty("url", value.replace(",", "\\,"));
                else
                    addProperty("published", value.replace(",", "\\,"));
            }
        } else if (field.equals("keywords") || field.equals("keyword"))
            addProperty("tags", value.replaceAll(",", ", "));
        else if (field.equals("comment") || field.equals("annote")
                || field.equals("review")) {
            addProperty("note", value.replace(",", "\\,"));
        } else if (field.equals("pdf")) {
            addProperty("attachments", "{path: " + value.replace(",", "\\,")
                    + " mimeType: application/pdf}");
        } else if (field.equals("sentelink")) {
            addProperty("attachments", "{path: " + value.split(",")[0]
                    + ", mimeType: application/pdf}".replace(",", "\\,"));
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
                    addProperty(
                            "attachments",
                            "{path: "
                                    + filepath.replace(",", "\\,")
                                    + ", mimeType: application/pdf, title: "
                                            .replace(",", "\\,")
                                    + fileTitle.replace(",", "\\,") + "}");
                else
                    addProperty("attachments",
                            "{path: " + filepath.replace(",", "\\,")
                                    + ", title: ".replace(",", "\\,")
                                    + fileTitle.replace(",", "\\,") + "}");
            }
        } else
            addProperty(field, value.replace(",", "\\,"));
        // if that wasn't enough, just add the field as is.
    }

    /**
     * A method to see if the current character is alphanumeric.
     *
     * @param c
     *            The character to be tested.
     * @return Returns true if the character is a letter or number, false
     *         otherwise.
     */
    private boolean testAlphaNum(final char c) {
        final int maxNum = 9;
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= 0 && c <= maxNum
                || c == '-' || c == '_';
    }

    /**
     * Imports data into CSF item.
     */
    private void doImport() {
        try {
            type();
            String parsed = printLaTeX(parseLaTeX(input));
            Scanner scanner = new Scanner(parsed);
            while (scanner.hasNextLine()) {
                String[] keyval = scanner.nextLine().split("=", 2);
                String delim = ",";
                if (keyval.length < 2) {
                    logger.debug(keyval[0]);
                    continue;
                }
                String key = keyval[0].trim();
                String vals = keyval[1];
                if (key.equals("author") || key.equals("title"))
                    delim = "and";
                for (String value : Splitter.on(delim).omitEmptyStrings()
                        .trimResults().split(vals)) {
                    if (value.endsWith(","))
                        value = value.substring(0, value.length() - 1);
                    processField(key, value);
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
            logger.debug(e.toString());
        } catch (ParseException e) {
            // e.printStackTrace();
            logger.debug(e.toString());
        }
        addProperty("importedFrom", "BibTeX");
    }

    /**
     * Parses the LaTeX object.
     * @param string
     *          String representation of the LaTeX object.
     * @return
     *          A List of LaTeXObjects with data parsed from string.
     * @throws IOException
     *          Throws this exception if Reader cannot open string.
     * @throws ParseException
     *          Throws this exception if string is not valid LaTeX.
     */
    private static List<LaTeXObject> parseLaTeX(final String string)
            throws IOException, ParseException {
        Reader reader = new StringReader(string);
        try {
            LaTeXParser parser = new LaTeXParser();
            return parser.parse(reader);
        } finally {
            reader.close();
        }
    }

    /**
     * Prints out the LaTeX objects.
     * @param objects
     *          A List of LaTeX objects.
     * @return
     *          A string representation of the LaTex objects.
     */
    private static String printLaTeX(final List<LaTeXObject> objects) {
        LaTeXPrinter printer = new LaTeXPrinter();
        return printer.print(objects);
    }

    /**
     * Adds the itemType field to the properties.
     * @param tmpType
     *          Raw type name, to be converted to CSF friendly type.
     */
    private void getType(final String tmpType) {
        // The key value pairs
        // Removing whitespace from type.
        String type = tmpType;
        type = CharMatcher.WHITESPACE.trimAndCollapseFrom(type.toLowerCase(),
                ' ');
        if (!type.equals("string")) {
            String itemType = TYPE_MAP.containsKey(type) ? TYPE_MAP.get(type) : type;
            // from map
            // if not in map, error
            addProperty("itemType", itemType);
        }
    }

    /**
     * Tries to parse BibTeX to get the items type.
     */
    private void type() {
        logger.debug("Importing to BibTeX");

        // the item's type, if not found yet it is set to 'false'
        String type = "false";
        // the character being read.
        char read = ' ';

        try {
            read = (char) reader.read();
            // Read character by character until there are none left
            while ((byte) (read) != -1) {
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
                    else if (read == '{') {
                        getType(type);
                        reader.close();
                        return;
                    } else if (testAlphaNum(read))
                        type += read;
                read = (char) reader.read();
            }
        } catch (IOException e) {
            // e.printStackTrace();
            logger.debug(e.toString());
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
    private void addProperty(final String key, final String value) {
        prop += key + CSF.SEPARATOR + " " + value.replace(".", "\\.") + "\n";
    }

    @Override
    public void subFormat() {
    }
}
