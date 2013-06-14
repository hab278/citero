package edu.nyu.library.citero;
import org.junit.Test;

public class CiteProcTest {

    @Test
    public void citeProc() {
            System.out.println(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(CitationStyles.MLA));
            System.out.println(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(CitationStyles.APA));
            System.out.println(Citero.map(FormatsTest.XERXES_XML).from(Formats.XERXES_XML).to(CitationStyles.CHICAGO_AUTHOR_DATE));
    }

}
