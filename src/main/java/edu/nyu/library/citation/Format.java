package edu.nyu.library.citation;



public abstract class Format {


	public Format(String input)
	{
	}
	
	public Format(CSF item)
	{
	}
	
	public abstract CSF CSF();
	
	public abstract String export();
	
}