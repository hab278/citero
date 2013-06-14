package edu.nyu.library.citero;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * enum type for the various supported formats.
 * 
 * @author hab278
 * 
 */
public enum Formats {
    /**
     * Xerxes standard XML. A format used by Xerxes.
     */
    XERXES_XML(edu.nyu.library.citero.XerxesXML.class),
    /**
     * Primo Normalized XML. A format used by Primo.
     */
    PNX(edu.nyu.library.citero.PNX.class),
    /**
     * OpenURL. A standard method of storing key-value pairs within a URL. Used
     * by Umlaut, which is an OpenURL resolver.
     */
    OPENURL(edu.nyu.library.citero.OpenURL.class),
    /**
     * Research Information Systems. A file format developed by Research
     * Information Systems that is used by many citation manager tools,
     * including RefWorks.
     */
    RIS(edu.nyu.library.citero.RIS.class),
    /**
     * Research Information Systems. A file format developed by Research
     * Information Systems that is used by many citation manager tools,
     * including RefWorks.
     */
    REFWORKS_TAGGED(edu.nyu.library.citero.RefworksTagged.class),
    /**
     * BibTeX Citero Management (LaTeX). This is used with LaTeX documents to
     * cite sources.
     */
    BIBTEX(edu.nyu.library.citero.BIBTEX.class),
    /**
     * Easy Bib.
     */
    EASYBIB(edu.nyu.library.citero.EASYBIB.class),
    /**
     * CSL.
     */
    CSL(edu.nyu.library.citero.CSL.class),
    /**
     * Citero Standard Format. A format designed specifically for this
     * application.
     */
    CSF(edu.nyu.library.citero.CSF.class);

    /**
     * Constructor that assigns a class to the enum, and its properties.
     * 
     * @param className
     *            Sets the class and properties of that class for this enum.
     */
    Formats(final Class<?> className) {
        clazz = className;
        isDestinationFormat = destination(clazz);
        isSourceFormat = source(clazz);
    }

    /**
     * Dynamically creates a new instance of a Format class mapped to the enum
     * type.
     * 
     * @param input
     *            An Object that represents the argument value of a Format type
     *            (String).
     * @return Returns an instance of a Format class that is mapped to the enum.
     * @throws IllegalArgumentException
     *             If the object could not be constructed, there must have been
     *             an illegal argument.
     */
    public Format getInstance(final Object input) throws IllegalArgumentException {
        if (input.getClass() == clazz)
            return (Format) input;
        Class<?>[] argClass = new Class<?>[] {input.getClass()};
        Object[] args = new Object[] {input};
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(argClass);
            return (Format) constructor.newInstance(args);
        } catch (SecurityException e) {
            throw new IllegalArgumentException("Invalid access to formats.");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Formats constructor could not be found.");
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Cannot create this format.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Class could not be found.");
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Check if data is consistent with format.");
        }
    }

    /** Whether or not this enum maps to a destination format. */
    private final boolean isDestinationFormat;
    /** Whether or not this enum maps to a source format. */
    private final boolean isSourceFormat;
    /** What class this enum maps to. */
    private final Class<?> clazz;

    /**
     * Accessor for boolean that represents whether or not the object is a
     * destination format.
     * 
     * @return True if the object implements DestinationFormat, false otherwise.
     */
    public boolean isDestinationFormat() {
        return isDestinationFormat;
    }

    /**
     * Accessor for boolean that represents whether or not the object is a
     * source format.
     * 
     * @return True if the object is annotated as a SourceFormat, false
     *         otherwise.
     */
    public boolean isSourceFormat() {
        return isSourceFormat;
    }

    /**
     * Setter for private variables, called by constructor.
     * 
     * @param obj
     *            The class object to inspect.
     * @return True if the object implements DestinationFormat, false otherwise.
     */
    private static boolean destination(final Class<?> obj) {
        return DestinationFormat.class.isAssignableFrom(obj);
    }

    /**
     * Setter for private variables, called by constructor.
     * 
     * @param obj
     *            The class object to inspect.
     * @return True if the object is annotated as a SourceFormat, false
     *         otherwise.
     */
    private static boolean source(final Class<?> obj) {
        return obj.isAnnotationPresent(SourceFormat.class);
    }

}
