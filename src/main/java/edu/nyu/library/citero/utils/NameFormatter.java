package edu.nyu.library.citero.utils;

/**
 * This class can split a string that contains a persons name.
 * It splits to one first name, one last name, one middle name, and 
 * one suffix in the order LastName Suffix, FirstName MiddleName.
 * 
 * @author hab278
 *
 */
public final class NameFormatter {

    private static String fName, lName, mName, suffix;

    public String firstName() {
        return fName;
    }

    public String lastName() {
        return lName;
    }

    public String middleName() {
        return mName;
    }

    public String suffix() {
        return suffix;
    }

    private NameFormatter(final String first, final String last, final String mid,
            final String sfx) {
        setNames(first, last, mid, sfx);
    }

    private static void setNames(final String first, final String last, final String mid,
            final String sfx) {
        NameFormatter.fName = first;
        NameFormatter.lName = last;
        NameFormatter.mName = mid;
        NameFormatter.suffix = sfx;
    }

    public static NameFormatter from(final String rawName) {
        fName = "";
        lName = "";
        mName = "";
        suffix = "";
        String[] arr = rawName.replaceAll("\\s+", " ").split(" ");

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

    public String toFormatted() {

        return (lName.isEmpty() ? "" : (suffix.isEmpty() ? lName : lName + " "
                + suffix))
                + (fName.isEmpty() && mName.isEmpty() ? ""
                        : (lName.isEmpty() ? "" : ", ")
                                + (fName.isEmpty() ? mName : fName
                                        + (mName.isEmpty() ? "" : " " + mName)));
    }

}
