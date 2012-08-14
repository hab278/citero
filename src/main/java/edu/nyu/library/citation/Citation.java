package edu.nyu.library.citation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/** 
 * The citation class is the tool required to start
 * the data interchange process. 
 * 
 * @author hab278
 */
public class Citation {

	/**
	 * data variable is the string representation of the data in
	 * citations own common format.
	 */
	private Class<?> format;
	
	
	/** 
	 * Creates a Citation instance and loads the provided
	 * data.
	 * 
	 * @param data Input data represented as a string
	 * @param input format specified via string
	 * @throws IllegalArgumentException derived from loadData {@link Citation#loadData(String, Format)}
	 */
	public Citation(String data, Formats input) throws IllegalArgumentException{
		
		ClassLoader loader = Formats.class.getClassLoader();
		try{
			format = Class.forName("edu.nyu.library.citation."+input.toString());
			System.out.println("edu.nyu.library.citation."+input.toString());
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		try {
			Constructor<?> constructor = format.getConstructor(String.class);
			format = (Class<?>) constructor.newInstance(data);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Citation(CSF file)
	{
		ClassLoader loader = Formats.class.getClassLoader();
		try{
			format = loader.loadClass("edu.nyu.library.CSF");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	
	/** 
	 * Converts data to the specified output format in
	 * string representation.
	 * 
	 * @param output The format the data should be converted to 
	 * @return A string representation of the converted data.
	 * @throws IllegalArgumentException thrown when data has not been loaded or outputFormat is not known.
	 */
	public String output(Formats output) throws IllegalArgumentException {
		Method method;
		try{
		switch(output){
			case CSF:
				method = format.getMethod("toCSF", (Class<?>)null);
				return (String) method.invoke(format, (Object)null);
			case RIS:
				method = format.getMethod("name", (Class<?>)null);
				if(((String)method.invoke(format, (Object)null)).equals(output.name()))
					method = format.getMethod("raw", (Class<?>)null);
				return (String) method.invoke(format, (Object)null);
			default:
				throw new IllegalArgumentException();
		}
		} catch (Exception e){}
		return null;
	}
	
	
}