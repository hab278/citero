package edu.nyu.library.citero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum CitationStyles {
    MLA("modern-language-association.csl"),
    CHICAGO_AUTHOR_DATE("chicago-author-date.csl"),
    APA("apa.csl");
    
    CitationStyles(String definition){
        String text = ""; 
        String filename = "src/main/java/edu/nyu/library/citero/vendor/csl/"+definition;
        try {
            text = new Scanner( new File(filename) ).useDelimiter("\\A").next();
//            System.out.println(text);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        STYLE_DEF = text.isEmpty() ?  definition.replace("\"","\\\"") : text.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n","").replace("\"", "\\\"").replaceAll("\n", "");
    }
    
    public final String STYLE_DEF;
}
