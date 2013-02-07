package edu.nyu.library.citero.utils;

/**
 * This class can split a string that contains a persons name.
 * It splits to one first name, one last name, one middle name, and
 * one suffix in the order LastName Suffix, FirstName MiddleName.
 * 
 * Usage example:
 *          NameFormatter.from("James Bond").toFormatted()
 *              This will give you Bond, James.
 *          NameFormatter.from("Keith Richards 1943- ").toFormatted()
 *              This will give you Richards, Keith. Removed random artifacts.
 * You can store the instance to get each part of the name individually.
 *          NameFormatter nf = NameFormatter.from("James Bond");
 *          System.out.println(nf.toFormatted()+" "+nf.firstName());
 *              This will give you Bond, James Bond.
 * @author hab278
 *
 */
public final class NameFormatter {

    /** The three most common parts to the name. */
    private static String fName, lName, mName, suffix;

    /**
     * Public accessor for first name.
     * @return
     *          Returns the extracted first name.
     */
    public String firstName() {
        return fName;
    }

    /**
     * Public accessor for last name.
     * @return
     *          Returns the extracted last name.
     */
    public String lastName() {
        return lName;
    }

    /**
     * Public accessor for middle name.
     * @return
     *          Returns the extracted middle name.
     */
    public String middleName() {
        return mName;
    }

    /**
     * Public accessor for suffix.
     * @return
     *          Returns the extracted suffix.
     */
    public String suffix() {
        return suffix;
    }

    /**
     * Private constructor, instantiates all variables.
     * @param first
     *          Extracted first name.
     * @param last
     *          Extracted last name.
     * @param mid
     *          Extracted middle name.
     * @param sfx
     *          Extracted suffix.
     */
    private NameFormatter(final String first, final String last, final String mid,
            final String sfx) {
        setNames(first, last, mid, sfx);
    }

    /**
     * Static method to instantiate all variables.
     * @param first
     *          Extracted first name.
     * @param last
     *          Extracted last name.
     * @param mid
     *          Extracted middle name.
     * @param sfx
     *          Extracted suffix.
     */
    private static void setNames(final String first, final String last, final String mid,
            final String sfx) {
        fName = first;
        lName = last;
        mName = mid;
        suffix = sfx;
    }

    /**
     * Splits the names and calls the constructor.
     * This is how you get an instance of this class.
     * @param rawName
     *          The raw string containing the names.
     * @return
     *          Returns a NameFormatter object with variables set.
     */
    public static NameFormatter from(final String rawName) {
        fName = "";
        lName = "";
        mName = "";
        suffix = "";
        String[] arr = rawName.replaceAll("\\s+", " ").replaceAll("\\(.*\\)", "").split(" ");

        for (int i = 0; i < arr.length; ++i) {
            if (arr.length == 1 || (arr[i].contains(",") && i == 0)) {
                lName = arr[i].replace(",", "");
            } else if (i == 0 && lName.isEmpty()) {
                fName = arr[i].replace(",", "");
            } else if (i == 1 && fName.isEmpty() && !lName.isEmpty()) {
                fName = arr[i].replace(",", "");
            } else if (i >= 1 && !fName.isEmpty() && i < arr.length - 1) {
                mName += (mName.isEmpty() ? "" : " ") + arr[i].replace(",", "");
            } else if (!fName.isEmpty() && !mName.isEmpty()
                    && arr[i].matches("[^0-9]+")) {
                lName = arr[i].replace(",", "");
            } else if (i > 1 && !fName.isEmpty() && !lName.isEmpty()) {
                if (arr[i].matches("[a-zA-Z\\-'\\.]+")) {
                    mName += (mName.isEmpty() ? "" : " ")
                            + arr[i].replace(",", "");
                } else if (arr[i].matches("[a-zA-Z\\.0-9]{1,4}")) {
                    suffix = arr[i];
                }
            }
            if (i == arr.length - 1 && lName.isEmpty()) {
                lName = mName
                        .substring(
                                mName.lastIndexOf(' ') > 0 ? mName.lastIndexOf(' ')
                                        : 0, mName.length()).trim();
                mName = mName.replace(lName, "").trim();
            }
        }
        return new NameFormatter(fName, lName, mName, suffix);
    }

    /**
     * Formats the name in standard Last Suffix, First Middle.
     * @return
     *          String representation of the name in the format Last Suffix, First Middle.
     */
    public String toFormatted() {

        return (lName.isEmpty() ? "" : (suffix.isEmpty() ? lName : lName + " "
                + suffix))
                + (fName.isEmpty() && mName.isEmpty() ? ""
                        : (lName.isEmpty() ? "" : ", ")
                                + (fName.isEmpty() ? mName : fName
                                        + (mName.isEmpty() ? "" : " " + mName)));
    }

}
