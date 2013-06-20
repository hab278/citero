package edu.nyu.library.citero;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

/**
 * An enum for packaged citeproc styles. More can be added, drop the CSL file
 * into vendor/csl and add the name and file location here.
 * 
 * @author hab278
 * 
 */
public enum CitationStyles {
    /**
     * Modern Language Association.
     */
    MLA("modern-language-association.csl"),
    /**
     * Chicago Author Date style.
     */
    CHICAGO_AUTHOR_DATE("chicago-author-date.csl"),
    /**
     * American Psychological Association 6th Edition.
     */
    APA("apa.csl");

    /**
     * This constructor reads the CSL file and extracts the releveant XML.
     * 
     * @param fileName
     *            The filename for the file that contains the CSL.
     */
    CitationStyles(final String fileName) {
        String text = "";
        InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/CSL/" + fileName);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        text = writer.toString();

        styleDef = text.isEmpty() ? "" : text.replace("\"", "\\\"").replaceAll(
                "\n", "");
    }

    /** The style's definition, loaded from the csl file in vendor/csl. */
    private final String styleDef;

    /**
     * Getter for styleDef.
     * 
     * @return Returns the style's definition, an XML string.
     */
    public String styleDef() {
        return styleDef;
    }
}
