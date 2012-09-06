package edu.nyu.library.citation;

import java.net.MalformedURLException;
import java.net.URL;

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
		return input;
	}
	
	private void doImport(){
		URL open;
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
						prop += "itemType: journalArticle\n";
					else if(value.equals("info:ofi/fmt:kev:mtx:book")){
						if(query.contains("rft.genre=bookitem"))
							prop += "itemType: bookSection\n";
						else if(query.contains("rft.genre=conference") || query.contains("rft.genre=proceeding"))
							prop += "itemType: conferencePaper\n";
						else if(query.contains("rft.genre=report"))
							prop += "itemType: report\n";
						else if(query.contains("rft.genre=document"))
							prop += "itemType: document\n";
						else
							prop += "itemType: book\n";
					}
					else if(value.equals("info:ofi/fmt:kev:mtx:dissertation"))
						prop += "itemType: thesis\n";
					else if(value.equals("info:ofi/fmt:kev:mtx:patent"))
						prop += "itemType: patent\n";
					else if(value.equals("info:ofi/fmt:kev:mtx:dc"))
						prop += "itemType: webpage\n";
				}
				else if(key.equals("rft.id")){}
				else if(key.equals("rft.btitle")){}
				else if(key.equals("rft.atitle") && !(prop.contains("journalArticle") || prop.contains("bookSection") || prop.contains("conferencePaper") )){}
				else if(key.equals("rft.jtitle") && prop.contains("journalArticle")){}
				else if(key.equals("rft.stitle") && prop.contains("journalArticle")){}
				else if(key.equals("rft.title")){}
				else if(key.equals("rft.date")){}
				else if(key.equals("rft.volume")){}
				else if(key.equals("rft.issue")){}
				else if(key.equals("rft.pages")){}
				else if(key.equals("rft.spage")){}
				else if(key.equals("rft.epage")){}
				else if(key.equals("rft.issn") || (key.equals("rft.eissn") && !prop.contains("issn"))){}
				else if(key.equals("rft.aulast") || key.equals("rft.invlast")){}
				else if(key.equals("rft.aufirst") || key.equals("rft.invfirst") ){}
				else if(key.equals("rft.au") | key.equals("rft.creator")  || key.equals("rft.contributor")  || key.equals("rft.inventor") ){}
				else if(key.equals("rft.aucorp")){}
				else if(key.equals("rft.isbn") && !prop.contains("isbn")){}
				else if(key.equals("rft.pub") || key.equals("rft.publisher") ){}
				else if(key.equals("rft.place")){}
				else if(key.equals("rft.tpages")){}
				else if(key.equals("rft.edition")){}
				else if(key.equals("rft.series")){}
				else if(prop.contains("thesis")){
					if(key.equals("rft.inst")){}
					else if(key.equals("rft.degree")){}
				}
				else if(prop.contains("patent")){
					if(key.equals("rft.assignee")){}
					else if(key.equals("rft.number")){}
					else if(key.equals("rft.appldate")){}
				}
				else if(prop.contains("webpage")){
					if(key.equals("rft.identifier")){}
					else if(key.equals("rft.description")){}
					else if(key.equals("rft.rights")){}
					else if(key.equals("rft.language")){}
					else if(key.equals("rft.subject")){}
					else if(key.equals("rft.type")){}
					else if(key.equals("rft.source")){}
				}
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
