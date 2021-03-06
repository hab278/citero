package edu.nyu.library.citero;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The CSF object stores all the input scrapped from various other formats. All
 * export methods will have their input come from CSF files.
 * 
 * @author hab278
 * 
 */
@SourceFormat
class CSF extends Format implements DestinationFormat {

    /** A logger for debugging. */
    private final Log logger = LogFactory.getLog(CSF.class);
    /** The universal seperator for CSF. */
    public static final char SEPARATOR = ':';
    /** A Configuration file that stores the input. */
    private Configuration config;

    /**
     * The default constructor. Creates a new Configuration file.
     */
    public CSF() {
        super("");
        logger.debug("CSF FORMAT");
        config = new PropertiesConfiguration();
        input = "";
    }

    /**
     * Constructor that also creates a configuration file.
     * 
     * @param in
     *            String representation of configuration.
     * @throws ConfigurationException
     *             Thrown when there is an error in syntax.
     */
    public CSF(final String in) throws ConfigurationException {
        super(in);
        logger.debug("CSF FORMAT");
        config = new PropertiesConfiguration();
        doImport(in);
    }

    /**
     * Constructor that also creates a configuration file.
     * 
     * @param in
     *            Reader representation of configuration.
     * @throws ConfigurationException
     *             Thrown when there is an error in syntax.
     */
    public CSF(final Reader in) throws ConfigurationException {
        super(in.toString());
        logger.debug("CSF FORMAT");
        config = new PropertiesConfiguration();
        doImport(in);
    }

    /**
     * This method loads the properties for the configuration from an outside
     * source.
     * 
     * @param in
     *            A string representation of the configuration, this is passed
     *            to {@link CSF#doImport(Reader)} as a StringReader object.
     * @throws ConfigurationException
     *             Inherited from {@link CSF#doImport(Reader)}
     */
    private void doImport(final String in) throws ConfigurationException {
        input = in;
        doImport(new StringReader(in));
    }

    /**
     * This method loads the properties for the configuration from an outside
     * source.
     * 
     * @param in
     *            A reader representation of the configuration.
     * @throws ConfigurationException
     *             Thrown by bad CSF syntax
     */
    private void doImport(final Reader in) throws ConfigurationException {

        logger.debug("Loading into CSF");

        try {
            ((PropertiesConfiguration) config).load(in);
        } catch (NoSuchMethodError e) {
            // For primo we have to manually load the properties
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                StringBuffer rawLine = new StringBuffer(scan.nextLine());
                while (rawLine.toString().trim().endsWith("\\")
                        && scan.hasNextLine()) {
                    rawLine.append(scan.nextLine());
                }
                if (!rawLine.toString().replaceAll("\\\\" + SEPARATOR, "")
                        .contains(String.valueOf(SEPARATOR))) {
                    continue;
                }
                String[] keyval = rawLine.toString().split(
                        String.valueOf(SEPARATOR), 2);
                for (int i = 0; i < keyval.length; ++i) {
                    keyval[i] = keyval[i].trim();
                }
                config.addProperty(keyval[0], keyval[1].replace("\\.", "."));
            }
        }
        ensureUniqueArraysUsingMaps();
    }

//    /**
//     * Removes duplicates from each properties entry.
//     */
//    private void ensureUniqueArrays() {
//        Iterator<?> itr = config.getKeys();
//        while (itr.hasNext()) {
//            String key = (String) itr.next();
//            String[] array = config.getStringArray(key);
//            try {
//                config.setProperty(key, removeDuplicates(array));
//                config.clearProperty(key);
//                config.setProperty(key, removeDuplicates(array));
//            } catch (NoSuchMethodError e) {
//                ensureUniqueArraysUsingMaps();
//            }
//        }
//    }

    /**
     * Removes duplicates from each properties entry in compatibility mode for primo.
     */
    private void ensureUniqueArraysUsingMaps() {
        Iterator<?> itr = config.getKeys();
        ArrayList<String> keys = new ArrayList<String>();
        Map<String, String[]> configMap = new HashMap<String, String[]>();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            keys.add(key);
            configMap.put(key, config.getStringArray(key));
        }
        config.clear();
        for (String key : keys)
            for (String str : removeDuplicates(configMap.get(key)))
                config.addProperty(key, str);
    }
 
    /**
     * Removes duplicates in a String array, ignoring case and whitespace.
     * @param strArray
     *          The String Array that contains 0 or more duplicates.
     * @return
     *          An array that does not contain duplicate values is returned.
     */
    private String[] removeDuplicates(final String[] strArray) {
        HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
        for (int i = 0; i < strArray.length; ++i) {
            if (!tempMap.containsKey(strArray[i].trim().toLowerCase()))
                tempMap.put(strArray[i].trim().toLowerCase(), i);
        }
        Object[] valuesArray = tempMap.values().toArray();
        String[] returnArray = new String[valuesArray.length];
        for (int i = 0; i < valuesArray.length; ++i)
            returnArray[i] = strArray[(Integer) valuesArray[i]].replace(",", "\\,");
        return returnArray;
    }

    /**
     * Accessor for the configuration properties.
     * 
     * @return The configuration properties.
     */
    public final Configuration config() {
        return config;
    }

    /**
     * Human readable output of the input.
     * 
     * @return A human readable format of properties.
     */
    @Override
    public final String doExport() {
        if (input.isEmpty()) {
            StringBuffer out = new StringBuffer();
            Iterator<?> itr = config().getKeys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                String[] value = config().getStringArray(key);
                out.append(key + " : ");
                for (int i = 0; i < value.length; ++i) {
                    if (i == value.length - 1) {
                        out.append(value[i] + '\n');
                    } else {
                        out.append(value[i] + ", ");
                    }
                }
            }
            input = out.toString();
        }
        return input;
    }

    @Override
    public final CSF toCSF() {
        return this;
    }

    /**
     * No subformatting.
     */
    @Override
    public final void subFormat() {
    }

}
