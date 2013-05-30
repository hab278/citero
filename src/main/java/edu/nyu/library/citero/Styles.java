package edu.nyu.library.citero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum Styles {
    MLA("modern-language-association.csl"),
    CHICAGO_AUTHOR_DATE("chicago-author-date.csl"),
    APA("apa.csl");
    
    Styles(String definition){
        String text = ""; 
        String filename = "src/main/java/edu/nyu/library/citero/"+definition;
        try {
            text = new Scanner( new File(filename) ).useDelimiter("\\A").next();
//            System.out.println(text);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        styleDef = text.isEmpty() ?  definition.replace("\"","\\\"") : text.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n","").replace("\"", "\\\"").replaceAll("\n", "");
    }
    
    public final String styleDef;
}
