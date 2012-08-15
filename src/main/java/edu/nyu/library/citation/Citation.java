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
	private Formats format;
	private CSF item;


	/** 
	 * Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param input format specified via string
	 * @throws IllegalArgumentException derived from loadData {@link Citation#loadData(String, Format)}
	 */
	public Citation(String data, Formats input) throws IllegalArgumentException{
		loadData(data,input);
	}

	public Citation(CSF file)
	{
		data = file.toCSF();
		item = file;
	}

	/** 
	 * Loads data into Citation after converting it to
	 * a common format.
	 * 
	 * @param data
	 * @param input
	 * @throws IllegalArgumentException thrown when input is not known or if data is not valid
	 */
	private void loadData(String data, Formats input) throws IllegalArgumentException{
		if(input.getClass() != Formats.class)
			throw new IllegalArgumentException();
		format = input;
		this.data = data;
		switch(input){
			case RIS:
				item = new RIS(data).CSF();
				break;
			case CSF:
				item = new CSF(data);
				break;
			case OPENURL:
				item = new OPENURL(data).CSF();
				break;
			case PNX:
				item = new PNX(data).CSF();
				break;
			case XERXES_XML:
				item = new XERXES_XML(data).CSF();
				break;
			case BIBTEX:
				item = new BIBTEX(data).CSF();
				break;
			default:
				throw new IllegalArgumentException();

		}
	}

	/** 
	 * Converts data to the specified output format in
	 * string representation.
	 * 
	 * @param output The format the data should be converted to 
	 * @return A string representation of the converted data.
	 * @throws IllegalArgumentException thrown when data has not been loaded or outputFormat is not known.
	 */
	public String output(Formats output) throws IllegalArgumentException {
		if(output == format)
			return data;
		switch(output){
			case CSF:
				return item.toCSF();
			case RIS:
				return new RIS(item).export();
			default:
				throw new IllegalArgumentException();
		}
	}


}