package edu.nyu.library.citation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class EASYBIB extends Format {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** The unique CSF item */
	private CSF item;
	/** Strings for the data and properties */
	private String input, prop;
	private BiMap<String, String> typeMap;

	public EASYBIB(String input) {
		super(input);
		logger.debug("EASYBIB FORMAT");
		// set up the input and csf object
		this.input = input;
		item = new CSF();

		loadVars();
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
		loadVars();
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
		String pubtype = "pubnonperiodical";
		String itemType = item.config().getString("itemType");
		try {
			writer.beginObject();
			writer.name("source");
			if (typeMap.containsValue(itemType))
				writer.value(typeMap.inverse().get(itemType));
			// else
			// writer.value("nil");
			writer.name("pubtype");
			writer.beginObject();
			writer.name("main");
			if (itemType.equals("magazineArticle"))
				pubtype = "pubmagazine";
			else if (itemType.equals("newspaperArticle"))
				pubtype = "pubnewspaper";
			else if (itemType.equals("magazineArticle"))
				pubtype = "pubmagazine";
			else if (itemType.equals("journalArticle"))
				pubtype = "pubjournal";
			else if (itemType.equals("webpage"))
				pubtype = "pubonline";
			writer.value(pubtype);
			writer.endObject();
			writer.name(pubtype);
			writer.beginObject();
			//pubtype
			writer.endObject();
			writer.name("contributor");
			writer.beginArray();
			writer.beginObject();
			//authors
			writer.endObject();
			writer.endArray();
//			writer.endObject();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return export.toString();
	}

	private void doImport() {
		JsonReader reader = new JsonReader(new StringReader(input));
		String name, field, itemType = "";
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				name = reader.nextName();
				if (name.equals("source")) {
					field = reader.nextString();
					if (typeMap.containsKey(field))
						itemType = typeMap.get(field);
					else if (field.equals("bible"))
						itemType = "book";
					else if (field.equals("database"))
						itemType = field;
					else if (field.equals("image"))
						itemType = "artwork";
					else if (field.equals("govt"))
						itemType = field;
					else if (field.equals("thesis") || field.equals("map"))
						itemType = field;
					else
						itemType = "document";
					addProperty("itemType", itemType);
					itemType = field;

				} else if (name.equals(itemType))
					continue;
				else if (name.equals("pubtype"))
					continue;
				else if (name.equals("contributors"))
					continue;
				else
					continue;
			}
			reader.endObject();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addProperty(String key, String value) {
		item.config().addProperty(key, value);
		// prop += key + ": " + value + "\n";
	}

	private void loadVars() {
		typeMap = HashBiMap.create();

		typeMap.put("book", "book");
		typeMap.put("blog", "blogPost");
		typeMap.put("chapter", "bookSection");
		typeMap.put("conference", "conferencePaper");
		typeMap.put("dissertation", "thesis");
		typeMap.put("film", "videoRecording");
		typeMap.put("journal", "journalArticle");
		typeMap.put("magazine", "magazineArticle");
		typeMap.put("newspaper", "newspaperArticle");
		typeMap.put("painting", "artwork");
		typeMap.put("report", "report");
		typeMap.put("software", "computerProgram");
		typeMap.put("website", "webpage");

	}

}