package edu.nyu.library.citation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class EASYBIB extends Format {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The unique CSF item */
	private CSF item;
	/** Strings for the data and properties */
	private String input, prop;
	
	public EASYBIB(String input) {
		super(input);
		logger.debug("EASYBIB FORMAT");
		// set up the input and csf object
		this.input = input;
		item = new CSF();

		// import and laod
		doImport();
		try {
			item.load(prop);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		logger.debug(prop);
		// TODO Auto-generated constructor stub
	}
	
	public EASYBIB(CSF item) {
		super(item);
		logger.debug("EASYBIB FORMAT");
		this.item = item;
		input = item.data();
		// TODO Auto-generated constructor stub
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public String export() {
		StringWriter export = new StringWriter();
		JsonWriter writer = new JsonWriter(export);
		try {
			writer.beginObject();
			writer.name("source").value(item.config().getString("itemType"));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return export.toString();
	}
	
	private void doImport(){
		JsonReader reader = new JsonReader(new StringReader(input));
		String name, field, itemType = "";
		try {
			reader.beginObject();
			while(reader.hasNext())
			{
				name = reader.nextName();
				if(name.equals("source"))
				{
					field = reader.nextString();
					if (field.equals("bible"))
						itemType = "book";
					else if(field.equals("blog"))
						itemType = "blogPost";
					else if(field.equals("book"))
						itemType = "book";
					else if(field.equals("chapter"))
						itemType = "bookSection";
					else if(field.equals("conference"))
						itemType = "conferencePaper";
					else if(field.equals("database"))
						itemType = field;
					else if(field.equals("image"))
						itemType = "artwork";
					else if(field.equals("dissertation"))
						itemType = "thesis";
					else if(field.equals("film"))
						itemType = "videoRecording";
					else if(field.equals("govt"))
						itemType = field;
					else if(field.equals("journal"))
						itemType = "journalArticle";
					else if(field.equals("magazine"))
						itemType = "magazineArticle";
					else if(field.equals("map"))
						itemType = "map";
					else if(field.equals("newspaper"))
						itemType = "newspaperArticle";
					else if(field.equals("painting"))
						itemType = "artwork";
					else if(field.equals("report"))
						itemType = "report";
					else if(field.equals("software"))
						itemType = "computerProgram";
					else if(field.equals("thesis"))
						itemType = "thesis";
					else if(field.equals("website"))
						itemType = "webpage";
					else
						itemType = "document";
					addProperty("itemType", itemType);
					itemType = field;
					
				}
				else if(name.equals(itemType))
					continue;
				else if(name.equals("pubtype"))
					continue;
				else if(name.equals("contributors"))
					continue;
				else
					continue;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private void addProperty(String key, String value) {
		item.config().addProperty(key, value);
		//prop += key + ": " + value + "\n";
	}

}
