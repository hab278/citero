package edu.nyu.library.citero.utils;



public class NameFormatter {

	private static String fName, lName, mName, suffix;

	public String firstName()	{	return fName;	}
	public String lastName()	{	return lName;	}
	public String middleName()	{	return mName;	}
	public String suffix()		{	return suffix;	}
	
	private NameFormatter(String fName, String lName, String mName, String suffix) {
		setNames(fName, lName, mName, suffix);
	}
	
	private static void setNames( String fName, String lName, String mName, String suffix )
	{
		NameFormatter.fName = fName;
		NameFormatter.lName = lName;
		NameFormatter.mName = mName;
		NameFormatter.suffix = suffix;
	}
	public static NameFormatter from(String rawName){
		fName = lName = mName = suffix = "";
		String arr[] = rawName.replaceAll("\\s+", " ").split(" ");
		
		for(int i = 0; i < arr.length; ++i)
		{
			if( arr.length == 1 || (arr[i].contains(",") && i == 0))
				lName = arr[i].replace(",", "");
			else if( i == 0 && lName.isEmpty() )
				fName = arr[i].replace(",", "");
			else if( i == 1 && fName.isEmpty() && !lName.isEmpty() )
				fName = arr[i].replace(",", "");
			else if( i >= 1 && !fName.isEmpty() && i < arr.length - 1 )
				mName += (mName.isEmpty() ? "" : " " ) + arr[i].replace(",", "") ;
			else if( !fName.isEmpty() && !mName.isEmpty() && arr[i].matches("[^0-9]+"))
				lName = arr[i].replace(",", "");
			else if( i > 1 && !fName.isEmpty() && !lName.isEmpty())
			{
				
				if(arr[i].matches("[a-zA-Z\\-'\\.]+"))
					mName += (mName.isEmpty() ? "" : " " ) + arr[i].replace(",", "");
				else if(arr[i].matches("[a-zA-Z\\.0-9]{1,4}"))
					suffix = arr[i];
			}
			if(i == arr.length-1 && lName.isEmpty()){
				lName = mName.substring(mName.lastIndexOf(' ') > 0 ? mName.lastIndexOf(' ') : 0, mName.length()).trim();
				mName = mName.replace(lName, "").trim();
			}
		}
		return new NameFormatter(fName, lName, mName, suffix);
	}

	public String toFormatted() {
		
		return (lName.isEmpty() ? "" : (suffix.isEmpty() ? lName
				: lName + " " + suffix))
				+ (fName.isEmpty() && mName.isEmpty() ? "" : (lName.isEmpty() ? "" : ", ")
						+ (fName.isEmpty() ? mName : fName
								+ (mName.isEmpty() ? "" : " " + mName)));
	}
	
	
}
