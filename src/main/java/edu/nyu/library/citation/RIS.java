package edu.nyu.library.citation;

import java.io.File;

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
		String csf;
		csf = "---\njournalArticle:\n  creator:\n    author: Shannon,Claude E.\n";
		input = csf;
		return input;
	}

}
