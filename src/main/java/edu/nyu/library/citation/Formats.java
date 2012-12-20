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
	PNX(edu.nyu.library.citation.PNX.class) {
		@Override
		Format getInstance(String input) {
			return new PNX(input);
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new PNX(item);
		}
	},
	/**
	 * XERXES XML. A format used be XERXES. Can be converted to RIS
	 * {@link Formats#RIS}, so functionality not currently required.
	 * 
	 * @deprecated
	 */
	XERXES_XML(edu.nyu.library.citation.XERXES_XML.class) {
		@Override
		Format getInstance(String input) {
			return new XERXES_XML(input);
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new XERXES_XML(item);
		}

	},
	/**
	 * OpenURL. A standard method of storing key-value pairs within a URL. Used
	 * by Umlaut, which is an OpenURL resolver.
	 */
	OPENURL(edu.nyu.library.citation.OpenURL.class) {
		@Override
		Format getInstance(String input) {
			try {
				return new OpenURL(input);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException();
			}
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new OpenURL(item);
		}

	},
	/**
	 * Research Information Systems. A file format developed by Research
	 * Information Systems that is used by many citation manager tools,
	 * including RefWorks.
	 */
	RIS(edu.nyu.library.citation.RIS.class) {
		@Override
		Format getInstance(String input) {
			return new RIS(input);
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new RIS(item);
		}

	},
	/**
	 * BibTeX Citation Management (LaTeX). This is used with LaTeX documents to
	 * cite sources.
	 */
	BIBTEX(edu.nyu.library.citation.BIBTEX.class) {
		@Override
		Format getInstance(String input) {
			return new BIBTEX(input);
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new BIBTEX(item);
		}

	},
	/**
	 * Easy Bib.
	 */
	EASYBIB(edu.nyu.library.citation.EASYBIB.class) {
		@Override
		Format getInstance(String input) {
			return new EASYBIB(input);
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return new EASYBIB(item);
		}

	},
	/**
	 * Citation Standard Format. A format designed specifically for this
	 * application.
	 */
	CSF(edu.nyu.library.citation.CSF.class) {
		@Override
		Format getInstance(String input) {
			try {
				return new CSF(input);
			} catch (ConfigurationException e) {
				throw new IllegalArgumentException();
			}
		}

		@Override
		Format getInstance(edu.nyu.library.citation.CSF item) {
			return item;
		}

	};

	Formats(Class<?> className) {
		isDestinationFormat = destination(className);
		isSourceFormat = source(className);
	}

	abstract Format getInstance(String input) throws IllegalArgumentException;

	abstract Format getInstance(CSF item);

	private final boolean isDestinationFormat;
	private final boolean isSourceFormat;

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
