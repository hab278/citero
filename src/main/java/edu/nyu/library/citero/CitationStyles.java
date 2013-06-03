package edu.nyu.library.citero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public enum CitationStyles {
    MLA("modern-language-association.csl"), CHICAGO_AUTHOR_DATE(
            "chicago-author-date.csl"), APA("apa.csl");

    CitationStyles(final String definition) {
        String text = "";
        String filename = "src/main/java/edu/nyu/library/citero/vendor/csl/"
                + definition;
        try {
            text = FileUtils.readFileToString(new File(filename));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        styleDef = text.isEmpty() ? definition.replace("\"", "\\\"") : text
                .replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n", "")
                .replace("\"", "\\\"").replaceAll("\n", "");
    }

    private final String styleDef;

    public String styleDef() {
        return styleDef;
    }
}
