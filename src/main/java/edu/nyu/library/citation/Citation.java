package edu.nyu.library.citation;


/** 
 * The citation class is the tool required to start
 * the data interchange process. 
 * 
 * @author hab278
 * @version 1.0.0-alpha.1
 */
public class Citation {

	/**
	 * data variable is the string representation of the data in
	 * citations own common format.
	 */
	private String data;
	
	/** 
	 * The default constructor instantiates the citation
	 * with no data payload and no formats available.
	 * It creates a Citation instance.
	 */
	public Citation(){
	}
	
	/** 
	 * Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param inputFormat Input format specified via string
	 * @throws Exception derived from loadData {@link Citation#loadData(String, String)}
	 */
	public Citation(String data, String inputFormat) throws Exception{
		loadData(data,inputFormat);
	}
	
	/** 
	 * Loads data into Citation after converting it to
	 * a common format.
	 * 
	 * @param data
	 * @param inputFormat
	 * @throws Exception thrown when inputFormat is not known or if data is not valid
	 */
	public void loadData(String data, String inputFormat) throws Exception{
		throw new Exception();
	}
	
	/** 
	 * Converts data to the specified output format in
	 * string representation.
	 * 
	 * @param outputFormat The format the data should be converted to 
	 * @return A string representation of the converted data.
	 * @throws Exception thrown when data has not been loaded or outputFormat is not known.
	 */
	public String output(String outputFormat) throws Exception {
		throw new Exception();
		//return "";
	}
	
	
}