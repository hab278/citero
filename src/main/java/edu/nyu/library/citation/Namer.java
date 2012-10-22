package edu.nyu.library.citation;

public class Namer {

	private String fName, lName, mName, suffix;

	public Namer(String rawName) {
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
	}

	public String goodName() {
		
		return (lName.isEmpty() ? "" : (suffix.isEmpty() ? lName
				: lName + " " + suffix))
				+ (fName.isEmpty() && mName.isEmpty() ? "" : (lName.isEmpty() ? "" : ", ")
						+ (fName.isEmpty() ? mName : fName
								+ (mName.isEmpty() ? "" : ", " + mName)));
	}
}
