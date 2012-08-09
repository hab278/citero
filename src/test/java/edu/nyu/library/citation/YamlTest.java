package edu.nyu.library.citation;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
	
	@Test
	public void testLoadManyDoc() throws FileNotFoundException {
		String location = System.getProperty("fileLoc");
		InputStream input = new FileInputStream( new File(location));
		Yaml yaml = new Yaml();
		int counter = 0;
		for(Object data : yaml.loadAll(input)){
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
	    System.out.println(writer.toString());
	    assertEquals("itemType: blogpost\nfields: {}\ncreator: {author: '', contributor: ''} ", writer.toString());
	}
	
	@Test
	public void testLoadYamlConstruct(){
	    Constructor constructor = new Constructor(CSF.class);
	    TypeDescription itemDescription =  new TypeDescription(CSF.class);
	    itemDescription.putMapPropertyType("creator", String.class, String.class);
	    itemDescription.putMapPropertyType("fields", String.class, String.class);
	    constructor.addTypeDescription(itemDescription);
	    Yaml yaml1 = new Yaml(constructor);
	    CSF i = (CSF)yaml1.load("---\nitemType: patent\nfields:\n  ? title\n  :\ncreator:\n  ? inventor\n  :\n");
	    System.out.println(i.toString());
	    assertEquals("itemType: patent\ncreator: {inventor=null}\nfields: {title=null}", i.toString());
	}
}
