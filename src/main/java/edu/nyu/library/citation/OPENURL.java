package edu.nyu.library.citation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;

import com.google.common.base.Splitter;

public class OPENURL extends Format{
	
	private CSF item;
	private String input, prop;

	public OPENURL(String input) {
		super(input);
		this.input = input;
		item = new CSF();
		prop = "";
		doImport();
		try {
			item.load(prop);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OPENURL(CSF item) {
		super(item);
		this.item = item;
		this.item.prop();
		input = item.toCSF();
		
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}
	private String mapValue(String key, String value){
		return mapValue(key, value, true);
	}
	private String mapValue(String key, String value, boolean addPrefix){
		if (addPrefix)
			return "rft." + key + "=" + value.replaceAll("\\s", "+");
		return key + "=" + value.replaceAll("\\s", "+");
	}

	@Override
	public String export() {
		// TODO Auto-generated method stub
		String output = "?";
		Iterator<?> itr = item.config().getKeys();
		while(itr.hasNext()){
			String key = (String) itr.next();
			output += mapValue("ulr_ver", "Z39.88-2004") + '&' + mapValue("ctx_ver", "Z39.88-2004") 
					+ '&' + mapValue("rfr_id", "info:sid/libraries.nyu.edu:citation");
			if(key.equals("DOI"))
				output += "rft_id=info:doi/" + item.config().getString(key);
			else if(key.equals("ISBN"))
				output += "rft_id=urn:isbn:" + item.config().getString(key);
			if(key.equals("itemType"))
			{
				if(item.config().getString(key).equals("journalArticle"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:journal&rft.genre=article", false);
				else if(item.config().getString(key).equals("bookSection"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:book&rft.genre=bookitem", false);
				else if(item.config().getString(key).equals("conferencePaper"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:book&rft.genre=conference", false);
				else if(item.config().getString(key).equals("report"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:book&rft.genre=report", false);
				else if(item.config().getString(key).equals("document"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:book&rft.genre=document", false);
				else if(item.config().getString(key).equals("book"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:book", false);
				else if(item.config().getString(key).equals("thesis"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:dissertation", false);
				else if(item.config().getString(key).equals("patent"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:patent", false);
				else if(item.config().getString(key).equals("webpage"))
					output += mapValue( "rft_val_fmlt", "info:ofi/fmt:kev:mtx:dc", false);
			}
			if(item.getItemType().equals("journalArticle")){
				if(key.equals("title"))
					output += mapValue("atitle", item.config().getString(key));
				else if(key.equals("publicationTitle"))
					output += mapValue("jtitle", item.config().getString(key));
				else if(key.equals("journalAbbreviation"))
					output += mapValue("stitle", item.config().getString(key));
				else if(key.equals("volume"))
					output += mapValue("volume", item.config().getString(key));
				else if(key.equals("issue"))
					output += mapValue("issue", item.config().getString(key));
			}
			else if(item.getItemType().equals("book") || item.getItemType().equals("bookSection") || item.getItemType().equals("conferencePaper")){
				if(item.getItemType().equals("book"))
					if(key.equals("title"))
						output += mapValue("btitle", item.config().getString(key));
				else if(item.getItemType().equals("bookSection")){
					if(key.equals("title"))
						output += mapValue("atitle", item.config().getString(key));
					if(key.equals("proceedingsTitle"))
						output += mapValue("btitle", item.config().getString(key));
				}
				else {
					if(key.equals("title"))
						output += mapValue("atitle", item.config().getString(key));
					if(key.equals("publicationsTitle"))
						output += mapValue("btitle", item.config().getString(key));
				}
				
				if(key.equals("place"))
					output += mapValue("place", item.config().getString(key));
				if(key.equals("publisher"))
					output += mapValue("publisher", item.config().getString(key));
				if(key.equals("edition"))
					output += mapValue("edition", item.config().getString(key));
				if(key.equals("series"))
					output += mapValue("series", item.config().getString(key));
			}
			else if(item.getItemType().equals("thesis") ){
				if(key.equals("title"))
					output += mapValue("title", item.config().getString(key));
				if(key.equals("publisher"))
					output += mapValue("inst", item.config().getString(key));
				if(key.equals("type"))
					output += mapValue("degree", item.config().getString(key));
			}
			else if(item.getItemType().equals("patent") ){
				if(key.equals("title")) 
					output += mapValue("title", item.config().getString(key));
				if(key.equals("assignee")) 
					output += mapValue("assignee", item.config().getString(key));
				if(key.equals("patentNumber")) 
					output += mapValue("number", item.config().getString(key));
				if(key.equals("issueDate"))
					output += mapValue("date", item.config().getString(key));
			}
			
			if(key.equals("date"))
				output += mapValue(( item.getItemType().equals("patent") ? "appldate" : "date"), item.config().getString(key));
			if(key.equals("pages")){
				output += mapValue("pages", item.config().getString(key));
				String[] pages = item.config().getString(key).split("[--]");
				if(pages.length > 1){
					output +="&" + mapValue("spage", pages[0]);
					if(pages.length >=2 )
						output += "&" + mapValue("epage", pages[1]);
				}
			}
			if(key.equals("numPages"))
				output += mapValue("tpages", item.config().getString(key));
			if(key.equals("ISBN"))
				output += mapValue("isbn", item.config().getString(key));
			if(key.equals("ISSN"))
				output += mapValue("isbn", item.config().getString(key));
			if(key.equals("author"))
				for(String str : item.config().getStringArray(key))
					output += mapValue( "au", str ) + '&';
			if(key.equals("inventor"))
				for(String str : item.config().getStringArray(key))
					output += mapValue( key, str ) + '&';
			if(key.equals("contributor"))
				for(String str : item.config().getStringArray(key))
					output += mapValue( key, str ) + '&';
			
			
			if(output.charAt(output.length()-1) != '&')
				output += "&";
		}
		return (output.lastIndexOf('&') == output.length()-1 ? output.replaceFirst("&$", "") : output);
	}
	
	private void doImport(){
		URL open;
		String type = "", pageKey = "";
		try {
			open = new URL(input);
			String query = open.getQuery();
			this.input = query;
			for(String str: Splitter.on("&").trimResults().omitEmptyStrings().split(query)){
				System.out.println(str);
				String key = str.split("=")[0];
				String value = str.split("=")[1].replace("+", " ");
				if(key.equals("rft_val_fmt")){
					if(value.equals("info:ofi/fmt:kev:mtx:journal"))
						type = "journalArticle";
					else if(value.equals("info:ofi/fmt:kev:mtx:book")){
						if(query.contains("rft.genre=bookitem"))
							type = "bookSection";
						else if(query.contains("rft.genre=conference") || query.contains("rft.genre=proceeding"))
							type = "conferencePaper";
						else if(query.contains("rft.genre=report"))
							type = "report";
						else if(query.contains("rft.genre=document"))
							type = "document";
						else
							type = "book";
					}
					else if(value.equals("info:ofi/fmt:kev:mtx:dissertation"))
						type = "thesis";
					else if(value.equals("info:ofi/fmt:kev:mtx:patent"))
						type = "patent";
					else if(value.equals("info:ofi/fmt:kev:mtx:dc"))
						type = "webpage";
					addProperty("itemType", type);					
				}
				else if(key.equals("rft_id")){
					String firstEight = value.substring(0,8).toLowerCase();
					if(firstEight.equals("info:doi"))
						addProperty("doi", value.substring(9)); 
					else if(firstEight.equals("urn.isbn"))
						addProperty("ISBN", value.substring(9));
					else if(value.matches("^https?:\\/\\/")){
						addProperty("url", value);
						addProperty("accessDate", "");
					}
				}
				else if(key.equals("rft.btitle")){
					if(type.equals("book") || type.equals("report"))
						addProperty("title", value);
					else if(type.equals("bookSection") || type.equals("conferencePaper"))
						addProperty("publicationTitle", value);
						
				}
				else if(key.equals("rft.atitle") && (type.equals("journalArticle") || type.equals("bookSection") || type.equals("conferencePaper") )){
					addProperty("title", value);
				}
				else if(key.equals("rft.jtitle") && type.equals("journalArticle")){
					addProperty("publicationTitle", value);
				}
				else if(key.equals("rft.stitle") && type.equals("journalArticle")){
					addProperty("journalAbbreviation", value);
				}
				else if(key.equals("rft.title")){
					if(type.equals("journalArticle") || type.equals("bookSection") || type.equals("conferencePaper") )
						addProperty("publicationTitle", value);
					else
						addProperty("title", value);
				}
				else if(key.equals("rft.date")){
					if(type.equals("patent"))
						addProperty("issueDate", value);
					else
						addProperty("date", value);
				}
				else if(key.equals("rft.volume")){
					addProperty("volume", value);
				}
				else if(key.equals("rft.issue")){
					addProperty("issue", value);
				}
				else if(key.equals("rft.pages")){
					addProperty("pages", value);
					pageKey = key;
				}
				else if(key.equals("rft.spage")){
					if(!pageKey.equals("rft.pages")){
						addProperty("startPage", value);
						pageKey = key;
					}
				}
				else if(key.equals("rft.epage")){
					if(!pageKey.equals("rft.pages")){
						addProperty("endPage", value);
						pageKey = key;
					}
				}
				else if(key.equals("rft.issn") || (key.equals("rft.eissn") && !prop.contains("\nISSN: "))){
					addProperty("ISSN", value);
				}
				else if(key.equals("rft.aulast") || key.equals("rft.invlast")){}
				else if(key.equals("rft.aufirst") || key.equals("rft.invfirst") ){}
				else if(key.equals("rft.au") || key.equals("rft.creator")  || key.equals("rft.contributor")  || key.equals("rft.inventor") ){
					if(key.equals("rft.inventor"))
						addProperty("inventor", value);
					else if(key.equals("rft.contributor"))
						addProperty("contributor", value);
					else
						addProperty("author", value);
				}
				else if(key.equals("rft.aucorp")){}
				else if(key.equals("rft.isbn") && !prop.contains("\nISBN: ")){
					addProperty("ISBN", value);
				}
				else if(key.equals("rft.pub") || key.equals("rft.publisher") ){
					addProperty("publisher", value);
				}
				else if(key.equals("rft.place")){
					addProperty("place", value);
				}
				else if(key.equals("rft.tpages")){
					addProperty("numPages", value);
				}
				else if(key.equals("rft.edition")){
					addProperty("edition", value);
				}
				else if(key.equals("rft.series")){
					addProperty("series", value);
				}
				else if(type.equals("thesis")){
					if(key.equals("rft.inst")){
						addProperty("publisher", value);
					}
					else if(key.equals("rft.degree")){
						addProperty("type", value);
					}
				}
				else if(type.equals("patent")){
					if(key.equals("rft.assignee")){
						addProperty("assignee", value);
					}
					else if(key.equals("rft.number")){
						addProperty("patentNumber", value);
					}
					else if(key.equals("rft.appldate")){
						addProperty("date", value);
					}
				}
				else if(type.equals("webpage")){
					if(key.equals("rft.identifier")){
						if(value.length()  > 8){
							if(value.substring(0, 5).equals("ISBN "))
								addProperty("ISBN", value.substring(5));
							if(value.substring(0, 5).equals("ISSN "))
								addProperty("ISSN", value.substring(5));
							if(value.substring(0, 8).equals("urn:doi:"))
								addProperty("DOI", value.substring(8));
							if(value.substring(0, 7).equals("http://") || value.substring(0, 8).equals("https://") )
								addProperty("url", value);
						}
					}
					else if(key.equals("rft.description")){
						addProperty("abstractNote",  value);
					}
					else if(key.equals("rft.rights")){
						addProperty("rights",  value);
					}
					else if(key.equals("rft.language")){
						addProperty("language",  value);
					}
					else if(key.equals("rft.subject")){
						addProperty("tags",  value);
					}
					else if(key.equals("rft.type")){
						type = value;
						addProperty("itemType", type);
					}
					else if(key.equals("rft.source")){
						addProperty("publicationTitle",  value);
					}
				}
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addProperty(String field, String value){
		prop += field + ": " + value + "\n";
	}

}
