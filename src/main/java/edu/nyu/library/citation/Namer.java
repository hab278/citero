package edu.nyu.library.citation;

public class Namer {

	private static String fName, lName, mName, suffix;

	private Namer(String fName, String lName, String mName, String suffix) {
		Namer.fName = fName;
		Namer.lName = lName;
		Namer.mName = mName;
		Namer.suffix = suffix;
	}
	
	public static Namer from(String rawName){
		fName = lName = mName = suffix = "";
		String arr[] = rawName.split(" ");
		
		for(int i = 0; i < arr.length; ++i)
		{
			if( arr.length == 1 || (arr[i].contains(",") && i == 0))
				lName = arr[i].replace(",", "");
			if( i == 0 && lName.isEmpty() )
				fName = arr[i].replace(",", "");
			if( i == 1 && fName.isEmpty() && !lName.isEmpty() )
				fName = arr[i].replace(",", "");
			if( i == 1 && !fName.isEmpty() && lName.isEmpty() )
				lName = arr[i].replace(",", "");
			if( i > 1 && !fName.isEmpty() && !lName.isEmpty())
			{
				if(arr[i].matches("[a-zA-Z\\.0-9]{1,4}"))
					suffix = arr[i];
				else if(arr[i].matches("[a-zA-Z\\-']+"))
					mName = arr[i].replace(",", "");
			}
		}
		return new Namer(fName, lName, mName, suffix);
	}

	public String toFormatted() {
		
		return (lName.isEmpty() ? "" : (suffix.isEmpty() ? lName
				: lName + " " + suffix))
				+ (fName.isEmpty() && mName.isEmpty() ? "" : (lName.isEmpty() ? "" : ", ")
						+ (fName.isEmpty() ? mName : fName
								+ (mName.isEmpty() ? "" : ", " + mName)));
	}
}
