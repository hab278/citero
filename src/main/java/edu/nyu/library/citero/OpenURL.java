package edu.nyu.library.citero;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Splitter;

/**
 * OpenURL format class. Imports from OpenURL formatted strings and exports to
 * OpenURL formatted strings.
 * 
 * @author hab278
 * 
 */
@SourceFormat
public class OpenURL extends Format implements DestinationFormat {

    /** A logger for debugging */
    private final Log logger = LogFactory.getLog(BIBTEX.class);
    /** The one and only CSF item */
    private CSF item;
    /** Strings for the data and properties */
    private String input, prop;

    /**
     * Default constructor, instantiates data maps and CSF item.
     * 
     * @param in
     *            A string representation of the data payload.
     * @throws MalformedURLException
     *            Inherited from @link{OpenURL#doImport()}.
     */
    public OpenURL(final String in) throws MalformedURLException {
        super(in);
        logger.debug("OpenURL FORMAT");
        input = in;
        item = new CSF();
        prop = "";
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
    public OpenURL(final CSF file) {
        super(file);
        logger.debug("OpenURL FORMAT");
        item = file;
        input = item.export();
    }

    @Override
    public final CSF toCSF() {
        return item;
    }

    /**
     * A wrapper to the other mapValue method.
     * 
     * @param key
     *            The key to be mapped to the value.
     * @param value
     *            The value that is being mapped to the key.
     * @return Returns @link{OpenURL#mapValue(string, string, boolean)} with
     *         addPrefix set to true.
     */
    private String mapValue(final String key, final String value) {
        return mapValue(key, value, true, true);
    }

    /**
     * Formats and maps the value to the key.
     * 
     * @param key
     *            The key to be mapped to the value.
     * @param value
     *            The value that is being mapped to the key.
     * @param addPrefix
     *            The prefix is added in some cases, this boolean decides when.
     * @param encode
     *            If the value should be URLEncoded with UTF-8
     * @return A string with keys mapped to values in OpenURL formatting.
     */
    private String mapValue(final String key, final String value, final boolean addPrefix,
            final boolean encode) {

        if (encode)
            try {
                if (addPrefix)
                    return "rft." + key + "="
                            + URLEncoder.encode(value, "UTF-8");
                return key + "=" + URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        if (addPrefix)
            return "rft." + key + "=" + value.replace(" ", "+");
        return key + "=" + value.replace(" ", "+");
    }

    @Override
    public final String export() {
        logger.info("Exporting to OpenURL");
        // Start the query string
        StringBuffer output = new StringBuffer("?");
        Iterator<?> itr = item.config().getKeys();
        // This is putting some metadata in
        output.append(mapValue("ulr_ver", "Z39.88-2004", true, false)
                + '&'
                + mapValue("ctx_ver", "Z39.88-2004", true, false)
                + '&'
                + mapValue("rfr_id", "info:sid/libraries.nyu.edu:citation",
                        true, false) + '&');
        String itemType = item.config().getString("itemType");
        // for every property in the properties configuration
        while (itr.hasNext()) {
            String key = (String) itr.next();
            // simply add these items.
            if (key.equals("DOI"))
                output.append("rft_id=info:doi/" + item.config().getString(key));
            else if (key.equals("ISBN"))
                output.append("rft_id=urn:isbn:" + item.config().getString(key));
            if (key.equals("itemType")) {
                if (item.config().getString(key).equals("journalArticle"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:journal", false, false)
                            + '&'
                            + mapValue("genre", "article"));
                else if (item.config().getString(key).equals("bookSection"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:book", false, false)
                            + '&'
                            + mapValue("genre", "bookitem"));
                else if (item.config().getString(key).equals("conferencePaper"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:book", false, false)
                            + '&'
                            + mapValue("genre", "conference"));
                else if (item.config().getString(key).equals("report"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:book", false, false)
                            + '&'
                            + mapValue("genre", "report"));
                else if (item.config().getString(key).equals("document"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:book", false, false)
                            + '&'
                            + mapValue("genre", "document"));
                else if (item.config().getString(key).equals("book"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:book", false, false)
                            + '&'
                            + mapValue("genre", "book"));
                else if (item.config().getString(key).equals("thesis"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:dissertation", false, false)
                            + '&' + mapValue("genre", "dissertation"));
                else if (item.config().getString(key).equals("patent"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:patent", false, false)
                            + '&'
                            + mapValue("genre", "patent"));
                else if (item.config().getString(key).equals("webpage"))
                    output.append(mapValue("rft_val_fmlt",
                            "info:ofi/fmt:kev:mtx:dc", false, false));
            }
            // And do specific tasks for itemtypes as well.
            // journal
            if (itemType.equals("journalArticle")) {
                if (key.equals("title"))
                    output.append(mapValue("atitle",
                            item.config().getString(key)));
                else if (key.equals("publicationTitle"))
                    output.append(mapValue("jtitle",
                            item.config().getString(key)));
                else if (key.equals("journalAbbreviation"))
                    output.append(mapValue("stitle",
                            item.config().getString(key)));
                else if (key.equals("volume"))
                    output.append(mapValue("volume",
                            item.config().getString(key)));
                else if (key.equals("issue"))
                    output.append(mapValue("issue", item.config()
                            .getString(key)));
            } else if (itemType.equals("book") || itemType.equals("bookSection")
                    || itemType.equals("conferencePaper")) { // books and conferencepaper
                if (itemType.equals("book"))
                    if (key.equals("title"))
                        output.append(mapValue("btitle", item.config()
                                .getString(key)));
                    else if (itemType.equals("bookSection")) {
                        if (key.equals("title"))
                            output.append(mapValue("atitle", item.config()
                                    .getString(key)));
                        if (key.equals("proceedingsTitle"))
                            output.append(mapValue("btitle", item.config()
                                    .getString(key)));
                    } else {
                        if (key.equals("title"))
                            output.append(mapValue("atitle", item.config()
                                    .getString(key)));
                        if (key.equals("publicationsTitle"))
                            output.append(mapValue("btitle", item.config()
                                    .getString(key)));
                    }

                if (key.equals("place"))
                    output.append(mapValue("place", item.config()
                            .getString(key)));
                if (key.equals("publisher"))
                    output.append(mapValue("publisher", item.config()
                            .getString(key)));
                if (key.equals("edition"))
                    output.append(mapValue("edition",
                            item.config().getString(key)));
                if (key.equals("series"))
                    output.append(mapValue("series",
                            item.config().getString(key)));
            } else if (itemType.equals("thesis")) { //thesis
                if (key.equals("title"))
                    output.append(mapValue("title", item.config()
                            .getString(key)));
                if (key.equals("publisher"))
                    output.append(mapValue("inst", item.config().getString(key)));
                if (key.equals("type"))
                    output.append(mapValue("degree",
                            item.config().getString(key)));
            } else if (itemType.equals("patent")) { //patent
                if (key.equals("title"))
                    output.append(mapValue("title", item.config()
                            .getString(key)));
                if (key.equals("assignee"))
                    output.append(mapValue("assignee",
                            item.config().getString(key)));
                if (key.equals("patentNumber"))
                    output.append(mapValue("number",
                            item.config().getString(key)));
                if (key.equals("issueDate"))
                    output.append(mapValue("date", item.config().getString(key)));
            }
            // all else
            if (key.equals("date"))
                output.append(mapValue((itemType.equals("patent") ? "appldate"
                        : "date"), item.config().getString(key)));
            if (key.equals("pages")) {
                output.append(mapValue("pages", item.config().getString(key)));
                String[] pages = item.config().getString(key).split("[--]");
                if (pages.length > 1) {
                    output.append("&" + mapValue("spage", pages[0]));
                    if (pages.length >= 2)
                        output.append("&" + mapValue("epage", pages[1]));
                }
            }
            if (key.equals("numPages"))
                output.append(mapValue("tpages", item.config().getString(key)));
            if (key.equals("ISBN"))
                output.append('&' + mapValue("isbn",
                        item.config().getString(key)));
            if (key.equals("ISSN"))
                output.append(mapValue("isbn", item.config().getString(key)));
            if (key.equals("author"))
                for (String str : item.config().getStringArray(key)) {
                    logger.debug(str);
                    logger.debug(item.config().getString(key));
                    output.append(mapValue("au", str) + '&');
                }
            if (key.equals("inventor"))
                for (String str : item.config().getStringArray(key))
                    output.append(mapValue(key, str) + '&');
            if (key.equals("contributor"))
                for (String str : item.config().getStringArray(key))
                    output.append(mapValue(key, str) + '&');

            if (output.charAt(output.length() - 1) != '&')
                output.append("&");
        }
        logger.debug(output.toString());
        return (output.toString().lastIndexOf('&') == output.toString()
                .length() - 1 ? output.toString().substring(0,
                output.length() - 1) : output.toString());
    }

    /**
     * A fairly simply import method, transfers keys and values from OpenURL to
     * CSF.
     * 
     * @throws MalformedURLException
     *          Throws this exception when it cannot extract the query from the URL.
     */
    private void doImport() throws MalformedURLException {
        logger.info("Importing to OpenURL");
        final int identifierLength = 8;
        final int protocolLength = 7;
        // get the url
        URL open;
        String type = "", pageKey = "", query = "";
        // get query (the actual data in an OpenURL)
        open = new URL(input);
        query = open.getQuery();
        this.input = query;
        HashMap<String, String> queries = new HashMap<String, String>();
        for (String str : Splitter.on("&").trimResults().omitEmptyStrings()
                .split(query)) {
            logger.debug(str);
            // get type
            if (str.split("=").length < 2)
                continue;
            String key = str.split("=")[0];
            String value = str.split("=")[1].replace("+", " ");
            if (queries.containsKey(key))
                queries.remove(key);
            try {
                queries.put(key, URLDecoder.decode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new MalformedURLException();
            }
        }
        if (queries.containsKey("rft_val_fmt")) {
            String fmt = queries.get("rft_val_fmt");
            if (fmt.equals("info:ofi/fmt:kev:mtx:journal"))
                type = "journalArticle";
            else if (fmt.equals("info:ofi/fmt:kev:mtx:book")) {
                if (query.contains("rft.genre=bookitem"))
                    type = "bookSection";
                else if (query.contains("rft.genre=conference")
                        || query.contains("rft.genre=proceeding"))
                    type = "conferencePaper";
                else if (query.contains("rft.genre=report"))
                    type = "report";
                else if (query.contains("rft.genre=document"))
                    type = "document";
                else
                    type = "book";
            } else if (fmt.equals("info:ofi/fmt:kev:mtx:dissertation"))
                type = "thesis";
            else if (fmt.equals("info:ofi/fmt:kev:mtx:patent"))
                type = "patent";
            else if (fmt.equals("info:ofi/fmt:kev:mtx:dc"))
                type = "webpage";
            if (!type.isEmpty())
                addProperty("itemType", type);
        } else
            addProperty("itemType", "document");
        Set<Entry<String, String>> set = queries.entrySet();
        for (Entry<String, String> ent : set) {

            // parse each key, its that simple
            if (ent.getKey().equals("rft_id")) {
                if (ent.getValue().length() < identifierLength)
                    continue;
                String firstEight = ent.getValue().substring(0, identifierLength)
                        .toLowerCase();
                if (firstEight.equals("info:doi"))
                    addProperty("doi", ent.getValue().substring(identifierLength + 1));
                else if (firstEight.equals("urn.isbn"))
                    addProperty("ISBN", ent.getValue().substring(identifierLength + 1));
                else if (ent.getValue().matches("^https?:\\/\\/")) {
                    addProperty("url", ent.getValue());
                    addProperty("accessDate", "");
                }
            } else if (ent.getKey().equals("rft.btitle")) {
                if (type.equals("book") || type.equals("report"))
                    addProperty("title", ent.getValue());
                else if (type.equals("bookSection")
                        || type.equals("conferencePaper"))
                    addProperty("publicationTitle", ent.getValue());

            } else if (ent.getKey().equals("rft.atitle")
                    && (type.equals("journalArticle")
                            || type.equals("bookSection") || type
                                .equals("conferencePaper"))) {
                addProperty("title", ent.getValue());
            } else if (ent.getKey().equals("rft.jtitle")
                    && type.equals("journalArticle")) {
                addProperty("publicationTitle", ent.getValue());
            } else if (ent.getKey().equals("rft.stitle")
                    && type.equals("journalArticle")) {
                addProperty("journalAbbreviation", ent.getValue());
            } else if (ent.getKey().equals("rft.title")) {
                if (!queries.containsKey("rft.jtitle")
                        && (type.equals("journalArticle")
                                || type.equals("bookSection") || type
                                    .equals("conferencePaper")))
                    addProperty("publicationTitle", ent.getValue());
                else
                    addProperty("title", ent.getValue());
            } else if (ent.getKey().equals("rft.date")) {
                if (type.equals("patent"))
                    addProperty("issueDate", ent.getValue());
                else
                    addProperty("date", ent.getValue());
            } else if (ent.getKey().equals("rft.volume")) {
                addProperty("volume", ent.getValue());
            } else if (ent.getKey().equals("rft.issue")) {
                addProperty("issue", ent.getValue());
            } else if (ent.getKey().equals("rft.pages")) {
                addProperty("pages", ent.getValue());
                pageKey = ent.getKey();
            } else if (ent.getKey().equals("rft.spage")) {
                if (!pageKey.equals("rft.pages")) {
                    addProperty("startPage", ent.getValue());
                    pageKey = ent.getKey();
                }
            } else if (ent.getKey().equals("rft.epage")) {
                if (!pageKey.equals("rft.pages")) {
                    addProperty("endPage", ent.getValue());
                    pageKey = ent.getKey();
                }
            } else if (ent.getKey().equals("rft.issn")
                    || (ent.getKey().equals("rft.eissn") && !prop
                            .contains("\nISSN: "))) {
                addProperty("ISSN", ent.getValue());
            } else if ((!queries.containsKey("rft.au") && !queries
                    .containsKey("rft.creator"))
                    && (ent.getKey().equals("rft.aulast") || ent.getKey()
                            .equals("rft.aufirst"))) {
                String author = queries.containsKey("rft.aulast") ? queries
                        .get("rft.aulast")
                        + (queries.containsKey("rft.aufirst") ? ", "
                                + queries.get("rft.aufirst") : "") : queries
                        .get("rft.aufirst");
                addProperty("author", author);
            } else if (!queries.containsKey("rft.inventor")
                    && (ent.getKey().equals("rft.invlast") || ent.getKey()
                            .equals("rft.invfirst"))) {
                String author = queries.containsKey("rft.invlast") ? queries
                        .get("rft.invlast")
                        + (queries.containsKey("rft.invfirst") ? ", "
                                + queries.get("rft.invfirst") : "") : queries
                        .get("rft.invfirst");
                addProperty("author", author);
            } else if (ent.getKey().equals("rft.au")
                    || ent.getKey().equals("rft.creator")
                    || ent.getKey().equals("rft.addau"))

                addProperty("author", ent.getValue());
            else if (ent.getKey().equals("rft.inventor"))
                addProperty("inventor", ent.getValue());
            else if (ent.getKey().equals("rft.contributor"))
                addProperty("contributor", ent.getValue());
            else if (ent.getKey().equals("rft.aucorp")) {
                addProperty("author", ent.getValue());
            } else if (ent.getKey().equals("rft.isbn")
                    && !prop.contains("\nISBN: ")) {
                addProperty("ISBN", ent.getValue());
            } else if (ent.getKey().equals("rft.pub")
                    || ent.getKey().equals("rft.publisher")) {
                addProperty("publisher", ent.getValue());
            } else if (ent.getKey().equals("rft.place")) {
                addProperty("place", ent.getValue());
            } else if (ent.getKey().equals("rft.tpages")) {
                addProperty("numPages", ent.getValue());
            } else if (ent.getKey().equals("rft.edition")) {
                addProperty("edition", ent.getValue());
            } else if (ent.getKey().equals("rft.series")) {
                addProperty("series", ent.getValue());
            } else if (type.equals("thesis")) {
                if (ent.getKey().equals("rft.inst")) {
                    addProperty("publisher", ent.getValue());
                } else if (ent.getKey().equals("rft.degree")) {
                    addProperty("type", ent.getValue());
                }
            } else if (type.equals("patent")) {
                if (ent.getKey().equals("rft.assignee")) {
                    addProperty("assignee", ent.getValue());
                } else if (ent.getKey().equals("rft.number")) {
                    addProperty("patentNumber", ent.getValue());
                } else if (ent.getKey().equals("rft.appldate")) {
                    addProperty("date", ent.getValue());
                }
            } else if (type.equals("webpage")) {
                if (ent.getKey().equals("rft.identifier")) {
                    if (ent.getValue().length() > identifierLength) {
                        if (ent.getValue().substring(0, "ISBN ".length()).equals("ISBN "))
                            addProperty("ISBN", ent.getValue().substring("ISBN ".length()));
                        if (ent.getValue().substring(0, "ISSN ".length()).equals("ISSN "))
                            addProperty("ISSN", ent.getValue().substring("ISSN ".length()));
                        if (ent.getValue().substring(0, identifierLength).equals("urn:doi:"))
                            addProperty("DOI", ent.getValue().substring(identifierLength));
                        if (ent.getValue().substring(0, protocolLength).equals("http://")
                                || ent.getValue().substring(0, protocolLength + 1)
                                        .equals("https://"))
                            addProperty("url", ent.getValue());
                    }
                } else if (ent.getKey().equals("rft.description")) {
                    addProperty("abstractNote", ent.getValue());
                } else if (ent.getKey().equals("rft.rights")) {
                    addProperty("rights", ent.getValue());
                } else if (ent.getKey().equals("rft.language")) {
                    addProperty("language", ent.getValue());
                } else if (ent.getKey().equals("rft.subject")) {
                    addProperty("tags", ent.getValue());
                } else if (ent.getKey().equals("rft.type")) {
                    type = ent.getValue();
                    addProperty("itemType", type);
                } else if (ent.getKey().equals("rft.source")) {
                    addProperty("publicationTitle", ent.getValue());
                }
            }
        }
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
        try {
            prop += field + CSF.SEPARATOR
                    + URLDecoder.decode(value, "UTF-8").replace(",", "\\,")
                    + "\n";
        } catch (UnsupportedEncodingException e) {
            prop += field + CSF.SEPARATOR + value.replace(",", "\\,") + "\n";
        }
    }

}
