package edu.nyu.library.citation;

import java.io.File;

public abstract class Format {


	public Format(String input)
	{
	}
	
	public Format(File CSF)
	{
	}
	
	public abstract String toCSF();
	
	//public abstract Format toThis();
}