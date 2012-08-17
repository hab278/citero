package edu.nyu.library.citation;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


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
		format = input;
		this.data = data;
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
		if(input.getClass() != Formats.class || data.isEmpty())
			throw new IllegalArgumentException();
		switch(input){
			case RIS:
				item = new RIS(data).CSF();
				break;
			case CSF:
				loadCSF();
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
				RIS ris = new RIS(item);
				String s = ris.export();
				return s;
			case OPENURL:
				return new OPENURL(item).export();
			case BIBTEX:
				return new BIBTEX(item).export();
			case XERXES_XML:
				return new XERXES_XML(item).export();
			case PNX:
				return new PNX(item).export();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private void loadCSF(){
		Constructor constructor = new Constructor(CSF.class);
		TypeDescription itemDescription = new TypeDescription(CSF.class);
		
		itemDescription.putMapPropertyType("creator", String.class, String.class);
		itemDescription.putMapPropertyType("fields", String.class, String.class);
		itemDescription.putMapPropertyType("attachments", String.class, String.class);
		
		constructor.addTypeDescription(itemDescription);
		
		Yaml yaml = new Yaml(constructor);
		
		item = (CSF)yaml.load(data);
		
		System.out.println("item loaded" + item.getItemType());
	}


}