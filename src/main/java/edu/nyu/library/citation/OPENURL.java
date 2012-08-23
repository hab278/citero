package edu.nyu.library.citation;


public class OPENURL extends Format{
	
	private CSF item;
	private String input;

	public OPENURL(String input) {
		super(input);
	}
	
	public OPENURL(CSF item) {
		super(item);
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String export() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void doImport(){
		
	}

}
