package edu.nyu.library.citation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RIS extends Format{

	private String input;
	private CSF item;
	private Map<String,String>  dataMap;
	
	public RIS(String input) {
		super(input);
		this.input = input;
		CSF();
		
		dataMap = new HashMap<String,String>();
		map();
	}
	
	public RIS(CSF item) {
		super(item);
	}

	@Override
	public String toCSF() {
		return doImport(input);
	}
	
	public void CSF(){
		
	}
	
	public String processTag(CSF item, String tag, String data){
		if(tag.equals("TY"))
			item.setItemType(dataMap.get(data));
		if(tag.equals("AU"))
			item.getCreator().put("Author", data);
		System.out.println(item.toString());
		System.out.println("Tag: " + tag + "\nData: " + data);
		return tag +"  -  "+ data;
	}
	
	public String doImport(String input){
		String tag;
		String data;
		String line;
		String output = "";
		Scanner scanner;
		CSF item = new CSF();
		scanner = new Scanner(this.input);
		do{
			line = scanner.nextLine();
			line = line.replaceAll("^\\s+", "");
		}
		while(scanner.hasNextLine() && !line.substring(0, 6).matches("^TY {1,2}- "));
		
		tag = "TY";
		data = line.substring(line.indexOf('-')+1).trim();
		
		String rawLine;
		while(scanner.hasNextLine()){
			rawLine = scanner.nextLine();
			line = rawLine.replaceFirst("^\\s+", "");
			if(line.matches("^([A-Z0-9]{2}) {1,2}-(?: ([^\n]*))?")){
				if(tag.matches("^[A-Z0-9]{2}"))
					if(output.isEmpty())
						output = processTag(item, tag,data);
					else
						output = output + "\n" + processTag(item, tag, data);
				tag = line.substring(0, line.indexOf('-')).trim();
				data = line.substring(line.indexOf('-')+1).trim();
				if(tag == "ER")
					return item.toYaml();
			}
			else
				if( tag == "N1" || tag == "N2" || tag == "AB" || tag == "KW")
					data += "\n"+rawLine;
				else if( !tag.isEmpty() )
					if(data.charAt(data.length()-1) == ' ')
						data += rawLine;
					else
						data += " "+rawLine;
		}
		
		return item.toYaml();
		
	}
	
	public void map(){
		dataMap.put("JOUR", "journalArticle");
	}

}
