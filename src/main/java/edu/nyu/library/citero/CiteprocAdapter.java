package edu.nyu.library.citero;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
        String location = "src/main/java/edu/nyu/library/citero/vendor/csl/";
        readJS(location, "xmle4x.js");
        readJS(location, "citeproc.js");
        readJS(location, "citeproc-interface.js");

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
     */
    private void readJS(final String location, final String fileName) {
        try {
            String file = FileUtils.readFileToString(new File(location
                    + fileName), "UTF-8");
            context.evaluateString(global, file, fileName, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
