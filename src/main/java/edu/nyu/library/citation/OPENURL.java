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

	@Override
	public String export() {
		// TODO Auto-generated method stub
		String output = "?";
		Iterator<?> itr = item.config().getKeys();
		while(itr.hasNext()){
			String key = (String) itr.next();
			if(key.equals("DOI"))
				output += "rft_id=info:doi/" + item.config().getString(key);
			else if(key.equals("ISBN"))
				output += "rft_id=urn:isbn:" + item.config().getString(key);
			if(key.equals("itemType"))
			{
				output += "rft_val_fmlt=";
				if(item.config().getString(key).equals("journalArticle"))
					output += "info:ofi/fmt:kev:mtx:journal&rft.genre=article";
				else if(item.config().getString(key).equals("bookSection"))
					output += "info:ofi/fmt:kev:mtx:book&rft.genre=bookitem";
				else if(item.config().getString(key).equals("conferencePaper"))
					output += "info:ofi/fmt:kev:mtx:book&rft.genre=conference";
				else if(item.config().getString(key).equals("report"))
					output += "info:ofi/fmt:kev:mtx:book&rft.genre=report";
				else if(item.config().getString(key).equals("document"))
					output += "info:ofi/fmt:kev:mtx:book&rft.genre=document";
				else if(item.config().getString(key).equals("book"))
					output += "info:ofi/fmt:kev:mtx:book";
				else if(item.config().getString(key).equals("thesis"))
					output += "info:ofi/fmt:kev:mtx:dissertation";
				else if(item.config().getString(key).equals("patent"))
					output += "info:ofi/fmt:kev:mtx:patent";
				else if(item.config().getString(key).equals("webpage"))
					output += "info:ofi/fmt:kev:mtx:dc";
			}
			if(item.getItemType().equals("journalArticle")){
				if(key.equals("title"))
					output += "rft.atitle=" + item.config().getString(key).replace(" ", "+");
				else if(key.equals("publicationTitle"))
					output += "rft.jtitle=" + item.config().getString(key).replace(" ", "+");
				else if(key.equals("journalAbbreviation"))
					output += "rft.stitle=" + item.config().getString(key).replace(" ", "+");
				else if(key.equals("volume"))
					output += "rft.volume=" + item.config().getString(key).replace(" ", "+");
				else if(key.equals("issue"))
					output += "rft.issue=" + item.config().getString(key).replace(" ", "+");
			}
			else if(item.getItemType().equals("book") || item.getItemType().equals("bookSection") || item.getItemType().equals("conferencePaper")){
				if(item.getItemType().equals("book"))
					if(key.equals("title"))
						output += "rft.btitle="+item.config().getString(key).replace(" ", "+");
				else if(item.getItemType().equals("bookSection")){
					if(key.equals("title"))
						output += "rft.atitle="+item.config().getString(key).replace(" ", "+");
					if(key.equals("proceedingsTitle"))
						output += "rft.btitle="+item.config().getString(key).replace(" ", "+");
				}
				else {
					if(key.equals("title"))
						output += "rft.atitle="+item.config().getString(key).replace(" ", "+");
					if(key.equals("publicationsTitle"))
						output += "rft.btitle="+item.config().getString(key).replace(" ", "+");
				}
				
				if(key.equals("place"))
					output += "rft.place="+item.config().getString(key).replace(" ", "+");
				if(key.equals("publisher"))
					output += "rft.publisher="+item.config().getString(key).replace(" ", "+");
				if(key.equals("edition"))
					output += "rft.edition="+item.config().getString(key).replace(" ", "+");
				if(key.equals("series"))
					output += "rft.series="+item.config().getString(key).replace(" ", "+");
			}
			else if(item.getItemType().equals("thesis") ){
				if(key.equals("title"))
					output += "rft.title="+item.config().getString(key).replace(" ", "+");
				if(key.equals("publisher"))
					output += "rft.inst="+item.config().getString(key).replace(" ", "+");
				if(key.equals("type"))
					output += "rft.degree="+item.config().getString(key).replace(" ", "+");
			}
			else if(item.getItemType().equals("patent") ){
				if(key.equals("title")) 
					output += "rft.title="+item.config().getString(key).replace(" ", "+");
				if(key.equals("assignee")) 
					output += "rft.assignee="+item.config().getString(key).replace(" ", "+");
				if(key.equals("patentNumber")) 
					output += "rft.number="+item.config().getString(key).replace(" ", "+");
				if(key.equals("issueDate"))
					output += "rft.date="+item.config().getString(key).replace(" ", "+");
			}
			
			output += "&";
		}
		return output;
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
