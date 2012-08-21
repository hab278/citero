package edu.nyu.library.citation;


public class PNX extends Format{
	
	private CSF item;
	private String input;

	public PNX(String input) {
		super(input);
		this.input = input;
		item = new CSF();
		doImport();
	}
	
	public void doImport(){
		XMLStringParser xml = new XMLStringParser(input);
		String itemType = xml.xpath("//display/type");
		
		
		if(itemType.equals("book") || item.equals("Books"))
			item.setItemType("book");
		else if (itemType.equals("audio"))
			item.setItemType("audioRecording");
		else if (itemType.equals("video"))
			item.setItemType("videoRecording");
		else if (itemType.equals("report"))
			item.setItemType("report");
		else if (itemType.equals("webpage"))
			item.setItemType("webpage");
		else if (itemType.equals("article"))
			item.setItemType("article");
		else if (itemType.equals("thesis"))
			item.setItemType("thesis");
		else if (itemType.equals("map"))
			item.setItemType("map");
		else
			item.setItemType("document");
		
		
		
	}
	
	public PNX(CSF item) {
		super(item);
	}

	@Override
	public String toCSF() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public String export() {
		// TODO Auto-generated method stub
		return "";
	}
}
