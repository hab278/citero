package edu.nyu.library.citero;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.tools.shell.Global;

/**
 * An adapter for citeproc-js, loads all necessary files and variables.
 * 
 * @author hab278
 * 
 */
public final class CiteprocAdapter {

    /** The context in which the JavaScript will run in. */
    private Context context;
    /** The global context JavaScript will be running in. */
    private Global global;

    /**
     * The private constructor to allow for builder pattern. This class is one
     * time use anyway.
     * 
     * @param data
     *            The data to be converted into a style.
     * @param styleDef
     *            The style's definition so it can convert the data.
     */
    private CiteprocAdapter(final String data, final String styleDef) {
        // Setting context
        ContextFactory factory = new ContextFactory();
        context = factory.enterContext();
        global = new Global(context);

        // Setting necessary variables in JavaScript.
        setVar("data", data);
        setVar("style", "\"" + styleDef + "\"");

        // loading up scripts
        String location = "META-INF/JavaScript/";
        String encoding = "UTF-8";
        readJS(location, "xmle4x.js", encoding);
        readJS(location, "citeproc.js", encoding);
        readJS(location, "citeproc-interface.js", encoding);

    }

    /**
     * Public builder, sets the data and style's definition by calling
     * constructor.
     * 
     * @param data
     *            The data to be converted into a style.
     * @param styleDef
     *            The style's definition so it can convert the data.
     * @return The CiteprocAdapter class.
     */
    public static CiteprocAdapter dataAndStyle(final String data,
            final String styleDef) {
        return new CiteprocAdapter(data, styleDef);
    }

    /**
     * The resulting style.
     * 
     * @return The style returned from running the get_formatted_bib() method in
     *         JavaScript as a String.
     */
    public String result() {
        Object res = context.evaluateString(global, "get_formatted_bib();",
                "<cmd>", 0, null);
        return res.toString();
    }

    /**
     * Sets variable names and values in JavaScript, useful for setting data and
     * style's definition.
     * 
     * @param varname
     *            The variable name to assign to.
     * @param value
     *            The value to assign the variable name to.
     */
    private void setVar(final String varname, final String value) {
        String statement = "var " + varname + " = " + value + ";";
        context.evaluateString(global, statement, "<cmd>", 0, null);
    }

    /**
     * Evaluates a file, given the location and filename.
     * 
     * @param location
     *            The location of the file, relative to project root.
     * @param fileName
     *            The filename, a .js file preferably.
     * @param encoding
     *            The encoding for the string, usually UTF-8.
     */
    private void readJS(final String location, final String fileName, final String encoding) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(location + fileName);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String file = writer.toString();
        context.evaluateString(global, file, fileName, 0, null);
    }

}
