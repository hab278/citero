package edu.nyu.library.citation;

public class FormatsTest {

	public final static  String RIS_REGEX = "(^[\\w\\d]{1,6}  - [\\w\\W]*$|\\s)+",
			PNX_REGEX = "<[\\w\\W]*><[\\w\\s\"]+>[\\w\\W]*<\\/[\\w\\s]+>",
			OPENURL = "http://resolver.example.edu/cgi?url_ver=Z39.88-2004&rft_val_fmt=info:ofi/fmt:kev:mtx:book&rft.isbn=0836218310&rft.btitle=The+Far+Side+Gallery+3",
			XERXES_XML = "test",
			BIBTEX_REGEX = "^\\s*@[a-zA-Z]+[{(]",
			BIBTEX = "@article{Adams2001,\nauthor = {Adams, Nancy K and DeSilva, Shanaka L and Self, Steven and Salas, Guido and Schubring, Steven and Permenter, Jason L and Arbesman, Kendra},\nfile = {:Users/heatherwright/Documents/Scientific Papers/Adams\\_Huaynaputina.pdf:pdf;::},\njournal = {Bulletin of Volcanology},\nkeywords = {Vulcanian eruptions,breadcrust,plinian},\npages = {493--518},\ntitle = {{The physical volcanology of the 1600 eruption of Huaynaputina, southern Peru}},\nvolume = {62},\nyear = {2001}\n}",
			CSF_REGEX = "---\nitemType:\\s+.*\ncreator:\n(\\s+\\?\\s+[a-zA-Z]+\n\\s+:\\s+[\\w\\W\\s\\S\\d\\D\\<>.\\/\\\\]*\n)*fields:(\\s+\\?\\s+[a-zA-Z]+\n\\s+:\\s+[\\w\\W\\s\\S\\d\\D\\<>.\\/\\\\]*\n?)*",
			PNX = "<display>"
				+ "<type>book</type>"
				+ "<title>"
				+ "Information literacy : infiltrating the agenda, challenging minds"
				+ "</title>"
				+ "<contributor>Geoff Walton (Geoff L.); Alison Pope</contributor>"
				+ "<publisher>Oxford : Chandos Publishing</publisher>"
				+ "<creationdate>2011</creationdate>"
				+ "<format>xxvi, 294 p. ; 24 cm.</format>"
				+ "<identifier>$$Cisbn$$V1843346109; $$Cisbn$$V9781843346104</identifier>"
				+ "<subject>"
				+ "Information literacy; Information literacy -- Study and teaching; Information literacy -- Web-based instruction; Library orientation for graduate students; Information services -- User education"
				+ "</subject>"
				+ "<description>"
				+ "$$Ccontents$$VIntroduction / Alison Pope and Geoff Walton -- Information literate pedagogy : developing a levels framework for the Open University / Katharine Reedy and Kirsty Baker -- Information literacy in the workplace and the employablity agenda / John Crawford and Christine Irving -- Information literacy in the context of contemporary teaching methods in higher education / Chris Wakeman -- 'Enquiring Minds' and the role of information literacy in the design, management and assessment of student research tasks / Keith Puttick -- Are we sharing our toys in the sandpit? Issues surrounding the design, creation, reuse and re-purposing of learning objects to support information skills and teaching? / Nancy Graham -- Spielburg your way to information literacy : producing educational movies and videos / Gareth Johnson -- Information literacy and noö̈politics / Andrew Whitworth -- Contemporary technologies' influence on learning as a social practice / Ben Scoble -- Understanding the information literacy competencies of UK Higher Education students / Jillian R. Griffiths and Bob Glass."
				+ "</description>"
				+ "<language>eng</language>"
				+ "<relation>"
				+ "$$Cseries $$VChandos information professional series"
				+ "</relation>"
				+ "<source>nyu_aleph</source>"
				+ "<availlibrary>"
				+ "$$INYU$$LBOBST$$1Main Collection$$2(ZA3075 .I54 2011 )$$Savailable$$31$$40$$5N$$60$$XNYU50$$YBOBST$$ZMAIN"
				+ "</availlibrary>"
				+ "<lds02>nyu_aleph003522839</lds02>"
				+ "<lds01>NYU</lds01>"
				+ "<availinstitution>$$INYU$$Savailable</availinstitution>"
				+ "<availpnx>available</availpnx>"
				+ "</display>",
				CSF = "itemType: journalArticle\ncreator: {}\nfields: {}",
				RIS = "TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n";
	
}
