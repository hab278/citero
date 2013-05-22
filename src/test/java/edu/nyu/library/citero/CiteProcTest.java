package edu.nyu.library.citero;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Global;

public class CiteProcTest {

    @Test
    public void citeProc() {
        ContextFactory factory = new ContextFactory();
        Context cx = factory.enterContext();

        Global global = new Global(cx);

        try {
            FileReader xmle4x = new FileReader("src/main/java/edu/nyu/library/citero/xmle4x.js");
            FileReader citeproc = new FileReader("src/main/java/edu/nyu/library/citero/citeproc.js");
            FileReader chicago = new FileReader("src/main/java/edu/nyu/library/citero/chicago.js");
            Object result = cx.evaluateReader(global, xmle4x, "xmle4x.js", 0, null);
            Object result2 = cx.evaluateReader(global, citeproc, "citeproc.js", 0, null);
            Object result3 = cx.evaluateReader(global, chicago, "chicago.js", 0, null);
            String s = "print( get_formatted_bib() );";
            Object res = cx.evaluateString(global, s, "<cmd>", 0, null);
            CSL csl = new CSL("");
            System.out.println(csl.doExport());

//            // Convert the result to a string and print it.
//            System.out.println(result.toString());
//            System.out.println(result2.toString());
//            System.out.println(res.toString());
        
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
