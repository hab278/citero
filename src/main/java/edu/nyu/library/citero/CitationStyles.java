package edu.nyu.library.citero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

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
        String file = "src/main/java/edu/nyu/library/citero/vendor/csl/"
                + fileName;
        try {
            text = FileUtils.readFileToString(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
