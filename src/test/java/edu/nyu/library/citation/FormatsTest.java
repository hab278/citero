package edu.nyu.library.citation;

import java.util.Scanner;

import com.google.gson.JsonParser;

public class FormatsTest {

	public final static  String RIS_REGEX = "(^[\\w\\d]{1,6}  - [\\w\\W]*$|\\s)+",
			PNX_REGEX = "<[\\w\\W]*><[\\w\\s\"]+>[\\w\\W]*<\\/[\\w\\s]+>",
			//OPENURL = "https://getit.library.nyu.edu/resolve?url_ver=Z39.88-2004&url_ctx_fmt=info:ofi/fmt:kev:mtx:ctx&ctx_ver=Z39.88-2004&ctx_tim=2012-11-20T13:40:11-05:00&ctx_id=&ctx_enc=info:ofi/enc:UTF-8&rft.genre=journal&rft.issn=0893-3456&rft.jtitle=Los+Alamos+monitor&rft.language=eng&rft.object_id=991042747005504&rft.object_type=JOURNAL&rft.page=1&rft.place=Los+Alamos,+N.M.&rft.pub=%5BH.+Markley+McMahon%5D&rft.stitle=ALAMOS+MONITOR+(LOS+ALAMOS,+NM)&rft.title=Los+Alamos+monitor&rft_val_fmt=info:ofi/fmt:kev:mtx:journal&rft_id=L&req.ip=127.0.0.1",
			OPENURL = "http://localhost:3000/cite/cite?format=ris&url_ver=Z39.88-2004&url_ctx_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Actx&ctx_ver=Z39.88-2004&ctx_tim=2012-11-21T16%3A34%3A01-05%3A00&ctx_id=&ctx_enc=info%3Aofi%2Fenc%3AUTF-8&rft.genre=journal&rft.issn=0893-3456&rft.jtitle=Los+Alamos+monitor&rft.language=eng&rft.object_id=991042747005504&rft.object_type=JOURNAL&rft.page=1&rft.place=Los+Alamos%2C+N.M.&rft.pub=%5BH.+Markley+McMahon%5D&rft.stitle=ALAMOS+MONITOR+%28LOS+ALAMOS%2C+NM%29&rft.title=Los+Alamos+monitor&rft_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Ajournal&rft_id=L&req.ip=127.0.0.1",
			OPENURL_REGEX = "[:\\/%$\\-_\\.\\+!\\*'\\(\\),a-zA-Z0-9]*\\?[&?[:\\/%$\\-_\\.\\+!\\*'\\(\\),a-zA-Z0-9]+=[:\\/%$\\-_\\.\\+!\\*'\\(\\),a-zA-Z0-9]+]+",
			XERXES_XML = "test",
			BIBTEX_REGEX = "@[^\\{]+\\{(?:[^\\{\\}]|\\{[^\\{\\}]*\\})*\\}",
			BIBTEX = "@article{Adams2001,\nauthor = {Adams, Nancy K and DeSilva, Shanaka L and Self, Steven and Salas, Guido and Schubring, Steven and Permenter, Jason L and Arbesman, Kendra},\nfile = {:Users/heatherwright/Documents/Scientific Papers/Adams\\_Huaynaputina.pdf:pdf;::},\njournal = {Bulletin of Volcanology},\nkeywords = {Vulcanian eruptions,breadcrust,plinian},\npages = {493--518},\ntitle = {{The physical volcanology of the 1600 eruption of Huaynaputina, southern Peru}},\nvolume = {62},\nyear = {2001}\n}",
			CSF_REGEX = "(^[.a-zA-Z0-9]+(\\s*:\\s*)[^\\n]+$)+",
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
				CSF = "itemType: journalArticle",
				RIS = "TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n";
	
	
	public static final boolean isValidCSF( String input ){
		Scanner scan = new Scanner(input);
		while(scan.hasNextLine())
		{
			String next = scan.nextLine();
			if(!next.matches("[.a-zA-Z0-9]+(\\s*:\\s*)[^\\n]+"))
				return false;
		}
		return true;
	}
	
	public static final boolean isValidJson( String input ){
		System.out.println(input);
		JsonParser parser = new JsonParser();
		try{
			parser.parse(input);
		}catch(Exception e)	{	return false;	}
		return true;
	}
	
}
