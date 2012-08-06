package edu.nyu.library.citation;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;
import org.yaml.snakeyaml.*;

public class YamlTest {

	@Test
	public void loadYaml(){
		Yaml yaml = new Yaml();
		Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
		assertEquals(obj.toString(), "{a=1, b=2, c=[aaa, bbb]}");
	}
	
	@Test
	public void dumpYaml(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("YAML", "test");
		Yaml yaml = new Yaml();
		String output = yaml.dump(map);
		assertEquals(output, "{YAML: test} ");
	}
}
