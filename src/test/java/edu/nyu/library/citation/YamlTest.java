package edu.nyu.library.citation;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	@Test
	public void testLoadYaml(){
		Yaml yaml = new Yaml();
		Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
		assertEquals(obj.toString(), "{a=1, b=2, c=[aaa, bbb]}");
	}
	
	@Test
	public void testDumpYaml(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("YAML", "test");
		Yaml yaml = new Yaml();
		String output = yaml.dump(map);
		assertEquals(output, "{YAML: test}\n");
	}
	
	@Test
	public void testLoadManyDoc(){
		String input = "Note:\n---Note:\n---Note:\n---Note:\n---Note:\n---";
		Yaml yaml = new Yaml();
		int counter = 0;
		for(Object data : yaml.loadAll(input)){
			counter++;
		}
		assertEquals(5,counter);
	}
}