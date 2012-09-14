package edu.nyu.library.citation;

import org.apache.commons.configuration.ConfigurationException;
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

	/** data variable is the string representation of the data in citations own common format. */
	private String data;
	/** format variable is the original format the data was in. */
	private Formats format;
	/** item variable is a CSF object that contains the data. */
	private CSF item;


	/** 
	 * Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param input format specified via string
	 * @throws IllegalArgumentException derived from {@link Citation#loadData(String, Formats)}
	 */
	public Citation(String data, Formats input) throws IllegalArgumentException{
		format = input;
		this.data = data;
		loadData(data,input);
	}

	/**
	 * Creates a citation instance and loads the provided CSF object.
	 * @param file A CSF object that contains the data payload.
	 */
	public Citation(CSF file)
	{
		data = file.toCSF();
		item = file;
	}

	/** 
	 * Loads data into Citation after converting it to
	 * a common format.
	 * 
	 * @param data A string representation of the data.
	 * @param input The input format of the data.
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
				//loadCSF();
				try {
					item = new CSF();
					item.load(data);
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				return item.props;
			case RIS:
				RIS ris = new RIS(item);
				return ris.export();
			case OPENURL:
				OPENURL openurl = new OPENURL(item);
				return openurl.export();
			case BIBTEX:
				BIBTEX bibtex = new BIBTEX(item);
				return bibtex.export();
			case XERXES_XML:
				XERXES_XML xerxes_xml = new XERXES_XML(item);
				return xerxes_xml.export();
			case PNX:
				PNX pnx = new PNX(item);
				return pnx.export();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Converts a string representation of the CSF and makes it into an object using Yaml.
	 * @deprecated
	 */
	private void loadCSF(){
		Constructor constructor = new Constructor(CSF.class);
		TypeDescription itemDescription = new TypeDescription(CSF.class);
		
		itemDescription.putMapPropertyType("creator", String.class, String.class);
		itemDescription.putMapPropertyType("fields", String.class, String.class);
		itemDescription.putMapPropertyType("attachments", String.class, String.class);
		
		constructor.addTypeDescription(itemDescription);
		
		Yaml yaml = new Yaml(constructor);
		
		item = (CSF)yaml.load(data);
	}
}