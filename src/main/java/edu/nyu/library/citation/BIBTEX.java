package edu.nyu.library.citation;

import com.google.common.base.CharMatcher;


public class BIBTEX extends Format{
	
	String input, prop;

	public BIBTEX(String input) {
		super(input);
		this.input = input;
		prop = "";
		doImport();
	}

	public BIBTEX(CSF item) {
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
	
	private void beginRecord(String type, char closeChar, int at){
		String value = "";
		String field = "";
		type = CharMatcher.WHITESPACE.trimAndCollapseFrom(type.toLowerCase(), ' ');
		if(!type.equals("string")){
			String itemType = type;//from map
			char read;
			//if not in map, error
			prop += "itemType: " + itemType +"\n";
			for(int i = at; i < input.length(); ++i){
				read = input.charAt(i);
				if(read == '='){
					do
						i++;
					while(testWhiteSpace(input.charAt(i)));
					if(testAlphaNum(read)){
						value = "";
						do{
							value += read;
							read = input.charAt(++i);
						}while(testAlphaNum(read));
						
						//check map for value
					}
					else;//
						//get from map [read]
					//process item
					System.out.println("Field: " + field + " Value: " + value);
					field = "";
				}
				else if( read == ',')
					field = "";
				else if( read == closeChar )
					return;
				else if(!testWhiteSpace(read))
					field += read;
			}
			
		}
		
	}
	private void doImport(){
		String type = "false";
		char read;
		
		for(int i = 0; i < input.length(); ++i){
			read = input.charAt(i);
			if(read == '@')
				type = "";
			else if( !type.equals("false"))
				if(type.equals("common"))
					type = "false";
				else if(read == '{'){
					beginRecord(type,'}',i);
					type = "false";
				}
				else if(read == '('){
					beginRecord(type,')',i);
					type = "false";
				}
				else if(testAlphaNum(read))
					type += read;
			System.out.println(type);
		}
	}
	
	private boolean testAlphaNum(char c){
		return c >= 65 && c <= 90 || c >= 97 && c <= 122 || c >= 0 && c <= 9  || c == 45|| c <= 95;
	}
	
	private boolean testWhiteSpace(char c){
		return c == '\n' || c == '\r' || c ==  't' || c == ' ';
	}
}
