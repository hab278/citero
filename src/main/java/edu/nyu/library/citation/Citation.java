package edu.nyu.library.citation;


/** 
 * The citation class is the tool required to start
 * the data interchange process. 
 * 
 * @author hab278
 */
public class Citation {

	/**
	 * data variable is the string representation of the data in
	 * citations own common format.
	 */
	private String data;
	/**
	 * InputFormat enum type
	 * @author hab278
	 *
	 */
	public enum Format { PNX, XERXEX_XML, OPENURL, RIS, BIBTEX }
	
	
	/** 
	 * Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param inputFormat Input format specified via string
	 * @throws IllegalArgumentException derived from loadData {@link Citation#loadData(String, String)}
	 */
	public Citation(String data, Format input) throws IllegalArgumentException{
		loadData(data,input);
	}
	
	/** 
	 * Loads data into Citation after converting it to
	 * a common format.
	 * 
	 * @param data
	 * @param inputFormat
	 * @throws IllegalArgumentException thrown when inputFormat is not known or if data is not valid
	 */
	private void loadData(String data, Format input) throws IllegalArgumentException{
		throw new IllegalArgumentException();
	}
	
	/** 
	 * Converts data to the specified output format in
	 * string representation.
	 * 
	 * @param outputFormat The format the data should be converted to 
	 * @return A string representation of the converted data.
	 * @throws IllegalArgumentException thrown when data has not been loaded or outputFormat is not known.
	 */
	public String output(Format output) throws IllegalArgumentException {
		throw new IllegalArgumentException();
		//return "";
	}
	
	
}