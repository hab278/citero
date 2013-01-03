package edu.nyu.library.citero;

/**
 * Object representation of the Xerxes XML format.
 * 
 * @author hab278
 * @deprecated Use RIS format instead.
 */
@SourceFormat
public class XERXES_XML extends Format implements DestinationFormat {

	/**
	 * Constructor that takes in a string input.
	 * 
	 * @param input
	 *            string representation of the data.
	 * @deprecated Use RIS format instead.
	 */
	public XERXES_XML(String input) {
		super(input);
	}

	/**
	 * Constructor that takes in a CSF input.
	 * 
	 * @param item
	 *            CSF representation of the data.
	 * @deprecated Use RIS format instead.
	 */
	public XERXES_XML(CSF item) {
		super(item);
	}

	/**
	 * Function that returns the CSF object that gets loaded into this object.
	 * 
	 * @return The CSF object.
	 * @deprecated Use RIS format instead.
	 */
	@Override
	public edu.nyu.library.citero.CSF toCSF() {
		return null;
	}

	/**
	 * Export converts CSF objects to a string representation of this object's
	 * format.
	 * 
	 * @return A string representation of the inputed data in this object's
	 *         format.
	 * @deprecated Use RIS format instead.
	 */
	public String export() {
		return null;
	}
}
