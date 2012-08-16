package edu.nyu.library.citation;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlTest {

	@Test
	public void testLoadYaml(){
		Yaml yaml = new Yaml();
		Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
		assertEquals( "{a=1, b=2, c=[aaa, bbb]}", obj.toString());
	}
	
	@Test
	public void testDumpYaml(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("YAML", "test");
		Yaml yaml = new Yaml();
		String output = yaml.dump(map);
		assertEquals("{YAML: test}\n", output);
	}
	

	@Ignore("not needed for functionality")
	@Test
	public void testLoadManyDoc() throws FileNotFoundException {
		String location = System.getProperty("fileLoc");
		InputStream input = new FileInputStream( new File(location));
		Yaml yaml = new Yaml();
		int counter = 0;
		for(@SuppressWarnings("unused") Object data : yaml.loadAll(input)){
			counter++;
		}
		assertEquals(36,counter);
	}
	
	@Test
	public void testDumpWriter() {
	    Map<String, Object> data = new HashMap<String, Object>();
	    data.put("itemType", "blogpost");
	    HashMap<String, String> creators = new HashMap<String, String>();
	    creators.put("author", "");
	    creators.put("contributor", "");
	    data.put("creator", creators);
	    HashMap<String,String> fields = new HashMap<String,String>();
	    data.put("fields", fields);
	    Yaml yaml = new Yaml();
	    StringWriter writer = new StringWriter();
	    yaml.dump(data, writer);
	    assertEquals("itemType: blogpost\nfields: {}\ncreator: {author: '', contributor: ''}\n", writer.toString());
	}
	
	@Test
	public void testToString(){
	    Constructor constructor = new Constructor(CSF.class);
	    TypeDescription itemDescription =  new TypeDescription(CSF.class);
	    itemDescription.putMapPropertyType("creator", String.class, String.class);
	    itemDescription.putMapPropertyType("fields", String.class, String.class);
	    constructor.addTypeDescription(itemDescription);
	    Yaml yaml1 = new Yaml(constructor);
	    CSF i = (CSF)yaml1.load("itemType: patent\ncreator: {inventor: null}\nfields: {title: null}");
	    assertEquals("itemType: patent\ncreator: {inventor: null}\nfields: {title: null}\nattachments: {}", i.toString());
	}
	
	@Test
	public void testYamlOut(){
		Constructor constructor = new Constructor(CSF.class);
		TypeDescription itemDescription = new TypeDescription(CSF.class);
		
		itemDescription.putMapPropertyType("creator", String.class, String.class);
		itemDescription.putMapPropertyType("fields", String.class, String.class);
		itemDescription.putMapPropertyType("attachments", String.class, String.class);
		
		constructor.addTypeDescription(itemDescription);
		
		Yaml yaml = new Yaml(constructor);
		
		String book = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers\nattachments:\n  ? te\n  : as";
		
		CSF item = (CSF)yaml.load( book );
		
		assertEquals(book, item.toCSF());
		
	}
}
