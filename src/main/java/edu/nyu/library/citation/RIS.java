package edu.nyu.library.citation;

import java.io.File;
import java.util.Scanner;

public class RIS extends Format{

	private String input;
	
	public RIS(String input) {
		super(input);
		this.input = input;
		toCSF();
	}
	
	public RIS(File CSF) {
		super(CSF);
	}

	@Override
	public String toCSF() {
		return doImport(input);
	}
	
	public String processTag(String tag, String data){
		System.out.println("Tag: " + tag + "\nData: " + data);
		return tag + data;
	}
	
	public String doImport(String input){
		String tag;
		String data;
		String line;
		String output = "";
		Scanner darkly;
		darkly = new Scanner(this.input);
		do{
			line = darkly.nextLine();
			line = line.replaceAll("^\\s+", "");
		}
		while(darkly.hasNextLine() && !line.substring(0, 6).matches("^TY {1,2}- "));
		
		tag = "TY";
		data = line.substring(line.indexOf('-')).trim();
		
		String rawLine;
		while(darkly.hasNextLine()){
			rawLine = darkly.nextLine();
			line = rawLine.replaceFirst("^\\s+", "");
			if(line.matches("^([A-Z0-9]{2}) {1,2}-(?: ([^\n]*))?")){
				if(tag.matches("^[A-Z0-9]{2}"))
					output = output + " " + processTag(tag, data);
				tag = line.substring(0, line.indexOf('-')).trim();
				data = line.substring(line.indexOf('-')).trim();
				if(tag == "ER")
					return output;
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
		
		return output;
		
	}

}
