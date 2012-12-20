package edu.nyu.library.citation;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.stream.JsonWriter;

import edu.nyu.library.citation.utils.NameFormatter;
@SourceFormat
public class EASYBIB extends Format implements DestinationFormat {

	/** A logger for debugging */
	private final Log logger = LogFactory.getLog(EASYBIB.class);
	/** The unique CSF item */
	private CSF item;
	private BiMap<String, String> typeMap;

	public EASYBIB(String input) {
		super(input);
		logger.debug("EASYBIB FORMAT");
		// set up the input and csf object
		item = new CSF();

		loadVars();
	}

	public EASYBIB(CSF item) {
		super(item);
		logger.debug("EASYBIB FORMAT");
		loadVars();
		this.item = item;
		// TODO Auto-generated constructor stub
	}

	@Override
	public edu.nyu.library.citation.CSF toCSF() {
		// TODO Auto-generated method stub
		return item;
	}

	public String export() {
		StringWriter export = new StringWriter();
		JsonWriter writer = new JsonWriter(export);
		String pubtype = "pubnonperiodical";
		String itemType = item.config().getString("itemType");
		try {
			writer.beginObject();
			writer.name("source");
			if (typeMap.containsValue(itemType)){
				writer.value(typeMap.inverse().get(itemType));
			// else
			// writer.value("nil");
				writer.name(typeMap.inverse().get(itemType));
			}
			writer.beginObject();
			writer.name("title").value(item.config().getString("title"));
			writer.endObject();
			writer.name("pubtype");
			writer.beginObject();
			writer.name("main");
			if (itemType.equals("magazineArticle"))
				pubtype = "pubmagazine";
			else if (itemType.equals("newspaperArticle"))
				pubtype = "pubnewspaper";
			else if (itemType.equals("journalArticle"))
				pubtype = "pubjournal";
			else if (itemType.equals("webpage"))
				pubtype = "pubonline";
			writer.value(pubtype);
			writer.endObject();
			writer.name(pubtype);
			writer.beginObject();
			if(pubtype.equals("pubnonperiodical"))
			{
				if(item.config().containsKey("title"))
					writer.name("title").value(item.config().getString("title"));
				if(item.config().containsKey("publisher"))
					writer.name("publisher").value(item.config().getString("publisher"));
				if(item.config().containsKey("place"))
					writer.name("city").value(item.config().getString("place"));
				if(item.config().containsKey("volume"))
					writer.name("vol").value(item.config().getString("volume"));
				if(item.config().containsKey("edition"))
					writer.name("edition").value(item.config().getString("edition"));
				if(item.config().containsKey("date"))
					writer.name("year").value(item.config().getString("date"));
				if(item.config().containsKey("firstPage"))
					writer.name("start").value(item.config().getString("firstPage"));
				if(item.config().containsKey("numPages"))
					if(item.config().containsKey("firstPage"))
						writer.name("end").value(item.config().getInt("firstPage") + item.config().getInt("numPages"));
					else
						writer.name("end").value(item.config().getInt("numPages"));
			}
			else if (pubtype.equals("pubmagazine")){

				if(item.config().containsKey("title"))
					writer.name("title").value(item.config().getString("title"));
				if(item.config().containsKey("volume"))
					writer.name("vol").value(item.config().getString("volume"));
				if(item.config().containsKey("date"))
					writer.name("year").value(item.config().getString("date"));
				if(item.config().containsKey("firstPage"))
					writer.name("start").value(item.config().getString("firstPage"));
				if(item.config().containsKey("numPages"))
					if(item.config().containsKey("firstPage"))
						writer.name("end").value(item.config().getInt("firstPage") + item.config().getInt("numPages"));
					else
						writer.name("end").value(item.config().getInt("numPages"));
			}
			else if (pubtype.equals("pubnewspaper")){

				if(item.config().containsKey("title"))
					writer.name("title").value(item.config().getString("title"));
				if(item.config().containsKey("edition"))
					writer.name("edition").value(item.config().getString("edition"));
				if(item.config().containsKey("section"))
					writer.name("section").value(item.config().getString("section"));
				if(item.config().containsKey("place"))
					writer.name("city").value(item.config().getString("place"));
				if(item.config().containsKey("date"))
					writer.name("year").value(item.config().getString("date"));
				if(item.config().containsKey("firstPage"))
					writer.name("start").value(item.config().getString("firstPage"));
				if(item.config().containsKey("numPages"))
					if(item.config().containsKey("firstPage"))
						writer.name("end").value(item.config().getInt("firstPage") + item.config().getInt("numPages"));
					else
						writer.name("end").value(item.config().getInt("numPages"));
			}
			else if (pubtype.equals("pubjournal")){

				if(item.config().containsKey("title"))
					writer.name("title").value(item.config().getString("title"));
				if(item.config().containsKey("issue"))
					writer.name("issue").value(item.config().getString("issue"));
				if(item.config().containsKey("volume"))
					writer.name("volume").value(item.config().getString("volume"));
				if(item.config().containsKey("series"))
					writer.name("series").value(item.config().getString("series"));
				if(item.config().containsKey("date"))
					writer.name("year").value(item.config().getString("date"));
				if(item.config().containsKey("firstPage"))
					writer.name("start").value(item.config().getString("firstPage"));
				if(item.config().containsKey("numPages"))
					if(item.config().containsKey("firstPage"))
						writer.name("end").value(item.config().getInt("firstPage") + item.config().getInt("numPages"));
					else
						writer.name("end").value(item.config().getInt("numPages"));
			}
			else if (pubtype.equals("pubonline")){

				if(item.config().containsKey("title"))
					writer.name("title").value(item.config().getString("title"));
				if(item.config().containsKey("institution"))
					writer.name("inst").value(item.config().getString("institution"));
				if(item.config().containsKey("date"))
					writer.name("year").value(item.config().getString("date"));
				if(item.config().containsKey("url"))
					writer.name("url").value(item.config().getString("url"));
				if(item.config().containsKey("accessDate"))
					writer.name("dayaccessed").value(item.config().getString("accessDate"));
			}
			writer.endObject();
			writer.name("contributors");
			writer.beginArray();
			writer.beginObject();
			if(item.config().containsKey("author") 
					|| item.config().containsKey("inventor") 
					|| item.config().containsKey("contributor")){
				if(item.config().containsKey("author"))
				{
					for( String str : item.config().getStringArray("author") )
					{
						writer.name("function").value("author");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				}
				if(item.config().containsKey("inventor"))
				{
					for( String str : item.config().getStringArray("inventor") )
					{
						writer.name("function").value("author");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				}
				if(item.config().containsKey("contributor"))
				{
					for( String str : item.config().getStringArray("contributor") )
					{
						writer.name("function").value("author");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				}
				
			}
			else if(item.config().containsKey("editor") 
					|| item.config().containsKey("seriesEditor") ){
				writer.name("function").value("editor");
				writer.name("first").value("");
				writer.name("middle").value("");
				writer.name("last").value("");
				if(item.config().containsKey("editor"))
				{
					for( String str : item.config().getStringArray("editor") )
					{
						writer.name("function").value("editor");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				}
				if(item.config().containsKey("seriesEditor"))
				{
					for( String str : item.config().getStringArray("seriesEditor") )
					{
						writer.name("function").value("editor");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				}
				
			}
			else if( item.config().containsKey("translator")){
					for( String str : item.config().getStringArray("translator") )
					{
						writer.name("function").value("translator");
						NameFormatter name = NameFormatter.from(str);
						if(!name.firstName().isEmpty())
							writer.name("first").value(name.firstName());
						if(!name.middleName().isEmpty())
							writer.name("middle").value(name.middleName());
						if(!name.lastName().isEmpty())
							writer.name("last").value(name.lastName());
					}
				
			}
			writer.endObject();
			writer.endArray();
			writer.endObject();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return export.toString();
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