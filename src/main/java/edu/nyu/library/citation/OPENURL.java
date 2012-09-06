package edu.nyu.library.citation;

import java.net.MalformedURLException;
import java.net.URL;

public class OPENURL extends Format{
	
	private CSF item;
	private String input;

	public OPENURL(String input) {
		super(input);
		this.input = input;
		item = new CSF();
		doImport();
	}
	
	public OPENURL(CSF item) {
		super(item);
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
			input = query;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
