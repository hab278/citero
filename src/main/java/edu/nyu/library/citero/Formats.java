package edu.nyu.library.citero;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * enum type for the various supported formats
 * 
 * @author hab278
 * 
 */
public enum Formats {
	/**
	 * Primo Normalized XML. A format used by Primo.
	 */
	PNX(edu.nyu.library.citero.PNX.class),
	/**
	 * XERXES XML. A format used be XERXES. Can be converted to RIS
	 * {@link Formats#RIS}, so functionality not currently required.
	 * 
	 * @deprecated
	 */
	XERXES_XML(edu.nyu.library.citero.XERXES_XML.class) ,
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
	RIS(edu.nyu.library.citero.RIS.class) ,
	/**
	 * BibTeX Citero Management (LaTeX). This is used with LaTeX documents to
	 * cite sources.
	 */
	BIBTEX(edu.nyu.library.citero.BIBTEX.class) ,
	/**
	 * Easy Bib.
	 */
	EASYBIB(edu.nyu.library.citero.EASYBIB.class) ,
	/**
	 * Citero Standard Format. A format designed specifically for this
	 * application.
	 */
	CSF(edu.nyu.library.citero.CSF.class) ;

	Formats(Class<?> className) {
		clazz = className;
		isDestinationFormat = destination(clazz);
		isSourceFormat = source(clazz);
	}

	
	public Format getInstance(Object input) throws IllegalArgumentException{
		if(input.getClass() == clazz)
			return (Format) input;
		Class<?>[] argClass = new Class<?>[] {input.getClass()};
		Object[] args = new Object[]{input};
		Constructor<?> constructor;
		try {
			constructor = clazz.getConstructor(argClass);
			return (Format) constructor.newInstance(args);
		} catch (SecurityException e) {
			throw new IllegalArgumentException();
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException();
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException();
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException();
		}
	}

	private final boolean isDestinationFormat;
	private final boolean isSourceFormat;
	private final Class<?> clazz;

	public boolean isDestinationFormat() {
		return isDestinationFormat;
	}

	public boolean isSourceFormat() {
		return isSourceFormat;
	}

	private static boolean destination(Class<?> obj) {
		return DestinationFormat.class.isAssignableFrom(obj);
	}

	private static boolean source(Class<?> obj) {
		return obj.isAnnotationPresent(SourceFormat.class);
	}

}
