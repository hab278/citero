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
            cx.evaluateString(global, "data = " + Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.CSL) +";", "<cmd>", 0, null);
            cx.evaluateString(global, "var style = \"" + Styles.CHICAGO_AUTHOR_DATE.styleDef + "\";", "<cmd>", 0, null);
//            System.out.println("var style = \"" + Styles.CHICAGO_AUTHOR_DATE.styleDef.replace("\"","\\\"") + "\";");
            FileReader chicago = new FileReader("src/main/java/edu/nyu/library/citero/chicago.js");
            cx.evaluateReader(global, xmle4x, "xmle4x.js", 0, null);
            cx.evaluateReader(global, citeproc, "citeproc.js", 0, null);
            cx.evaluateReader(global, chicago, "chicago.js", 0, null);
            Object res = cx.evaluateString(global, "get_formatted_bib();", "<cmd>", 0, null);
//            System.out.println(res.toString());
            System.out.println(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Styles.CHICAGO_AUTHOR_DATE));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
