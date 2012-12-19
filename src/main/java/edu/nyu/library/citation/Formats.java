package edu.nyu.library.citation;

import java.net.MalformedURLException;

import org.apache.commons.configuration.ConfigurationException;

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
	PNX,
	/**
	 * XERXES XML. A format used be XERXES. Can be converted to RIS
	 * {@link Formats#RIS}, so functionality not currently required.
	 */
	XERXES_XML,
	/**
	 * OpenURL. A standard method of storing key-value pairs within a URL. Used
	 * by Umlaut, which is an OpenURL resolver.
	 */
	OPENURL,
	/**
	 * Research Information Systems. A file format developed by Research
	 * Information Systems that is used by many citation manager tools,
	 * including RefWorks.
	 */
	RIS,
	/**
	 * BibTeX Citation Management (LaTeX). This is used with LaTeX documents to
	 * cite sources.
	 */
	BIBTEX,
	/**
	 * Easy Bib.
	 */
	EASYBIB,
	/**
	 * Citation Standard Format. A format designed specifically for this
	 * application.
	 */
	CSF;
	
	
	public Format klass(String input) throws IllegalArgumentException{
		switch (this) {
		case RIS:
			return new RIS(input);
		case CSF:
			try {
				return new CSF(input);
			} catch (ConfigurationException e) {
				throw new IllegalArgumentException();
			}
		case OPENURL:
			try {
				return new OpenURL(input);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException();
			}
		case PNX:
			return new PNX(input);
		case BIBTEX:
			return new BIBTEX(input);
		case EASYBIB:
			return new EASYBIB(input);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public Format klass(CSF item) throws IllegalArgumentException{
		switch (this) {
		case RIS:
			return new RIS(item);
		case CSF:
			return item;
		case OPENURL:
			return new OpenURL(item);
		case PNX:
			return new PNX(item);
		case BIBTEX:
			return new BIBTEX(item);
		case EASYBIB:
			return new EASYBIB(item);
		default:
			throw new IllegalArgumentException();
		}
	}

}
