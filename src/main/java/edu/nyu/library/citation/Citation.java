package edu.nyu.library.citation;


/** The citation class is the tool required to start
 * the data interchange process. 
 * 
 * @author hab278
 * @version 1.0.0-alpha.1
 */
public class Citation {

	private String data;
	
	/** The default constructor instantiates the citation
	 * with no data payload and no formats available.
	 * It creates a Citation instance.
	 */
	public Citation(){
	}
	
	/** Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param inputFormat Input format specified via string
	 */
	public Citation(String data, String inputFormat){
		loadData(data,inputFormat);
	}
	
	/** Loads data into Citation after converting it to
	 * a common format.
	 * 
	 * @param data
	 * @param inputFormat
	 */
	public void loadData(String data, String inputFormat){
	}
	
	/** Converts data to the specified output format in
	 * string representation.
	 * 
	 * @param outputFormat The format the data should be converted to 
	 * @return A string representation of the converted data.
	 */
	public String output(String outputFormat){
		return "";
	}
	
	
}