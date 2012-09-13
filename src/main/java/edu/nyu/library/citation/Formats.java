package edu.nyu.library.citation;
/**
 * enum type for the various supported formats
 * @author hab278
 *
 */
public enum Formats {
	/**
	 * Primo Normalized XML. A format used by Primo.
	 */
	PNX,
	/**
	 * XERXES XML. A format used be XERXES. Can be converted to RIS {@link Formats:RIS}, so functionality not currently required.
	 */
	XERXES_XML,
	/**
	 * OpenURL. A standard method of storing key-value pairs within a URL. Used by Umlaut, which is an OpenURL resolver.
	 */
	OPENURL,
	/**
	 * Research Information Systems. A file format developed by Research Information Systems that is used by many citation manager tools, including RefWorks.
	 */
	RIS,
	/**
	 * BibTeX Citation Management (LaTeX). This is used with LaTeX documents to cite sources.
	 */
	BIBTEX,
	/**
	 * Citation Standard Format. A format designed specifically for this application. 
	 */
	CSF;
	
}
