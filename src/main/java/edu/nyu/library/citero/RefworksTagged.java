package edu.nyu.library.citero;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
/**
 * 
 * @author hab278
 *
 */
@SourceFormat
public class RefworksTagged extends RIS {

    /**
     * Bimaps for all interchangeable tags/values.
     */
    private static final BiMap<String, String> TAG_OUT_MAP, TYPE_OUT_MAP;
    static {
        BiMap<String, String> tgMap = HashBiMap.create();
        tgMap.put("TY", "RT");
        tgMap.put("CY", "PP");
        tgMap.put("DP", "DS");
        tgMap.put("ET", "ED");
        tgMap.put("KW", "K1");
        tgMap.put("N1", "NO");
        tgMap.put("PY", "YR");
        tgMap.put("TI", "T1");
        TAG_OUT_MAP = com.google.common.collect.Maps.unmodifiableBiMap(tgMap);

        BiMap<String, String> toMap = HashBiMap.create();
        toMap.put("JOUR", "Journal Article");
        toMap.put("THES", "Dissertation");
        toMap.put("BOOK", "Book, whole");
        toMap.put("RPRT", "Report");
        toMap.put("CHAP", "Book, section");
        toMap.put("ELEC", "Webpage");
        TYPE_OUT_MAP = com.google.common.collect.Maps.unmodifiableBiMap(toMap);
    }


    /**
     * Constructor, calls toRis to convert the input to regular RIS then calls super constructor.
     * @param in Refworks Tagged flavor of RIS as a string.
     */
    public RefworksTagged(final String in) {
        super(toRis(in.trim()));
    }

    /**
     * Simply calls super with the same input.
     * @param file A CSF file.
     */
    public RefworksTagged(final CSF file) {
        super(file);
    }

    /**
     * Converts input to regular RIS by swapping interchangeable tags/values.
     * @param in The input string to convert.
     * @return A regular RIS as a string.
     */
    private static String toRis(final String in) {
        return interchangeRISandRefworks(in, " ", "  - ");
    }

    /**
     * Sets export to this format's flavor of RIS.
     */
    @Override
    public final void subFormat() {
        export = interchangeRISandRefworks(export, "-", " ");
    }

    /**
     * Swaps out the separators and uses bimaps to swap tags/values.
     * @param str The string to change.
     * @param oldSprtr The old separator.
     * @param newSprtr The new separator.
     * @return A string of the converted format.
     */
    private static String interchangeRISandRefworks(final String str, final String oldSprtr, final String newSprtr) {
        StringBuffer buff = new StringBuffer();
        for (Entry<String, String> s : toMap(str, oldSprtr).entrySet())
            buff.append(tryMapped(TAG_OUT_MAP, s.getKey()))
                .append(newSprtr)
                .append(tryMapped(TYPE_OUT_MAP, s.getValue()))
                .append("\n");
        return buff.toString();
    }

    /**
     * Checks if the tag/field is present in the given bimap, if not then it returns the tag/value unchanged.
     * @param map The map to reference.
     * @param key The tag/field to check.
     * @return Either the value in the map, or the key itself if it wasnt in the map.
     */
    private static String tryMapped(final BiMap<String, String> map, final String key) {
        return map.containsKey(key) ? map.get(key) : map.inverse().containsKey(key) ? map.inverse().get(key) : key;
    }

    /**
     * Turns an RIS flavor to a LinkedHashMap (order matters map).
     * @param str An RIS string to map.
     * @param sprtr The separator to split each tag/field on.
     * @return A LinkedHashMap that uses RIS tag/field as its key/value pairs.
     */
    private static LinkedHashMap<String, String> toMap(final String str, final String sprtr) {
        Scanner scanner = new Scanner(str);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        while (scanner.hasNextLine()) {
            String[] split = scanner.nextLine().split(sprtr, 2);
            map.put(split[0].trim(), split.length > 1 ? split[1].trim() : "");
        }
        return map;
    }
}
