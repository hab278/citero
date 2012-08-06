package edu.nyu.library.citation;
import static org.junit.Assert.*;

import org.junit.*;
import org.yaml.snakeyaml.*;

public class YamlTest {

	@Test
	public void loadYaml(){
		Yaml yaml = new Yaml();
		Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
		assertEquals(obj.toString(), "{b=2, c=[aaa, bbb], a=1}");
	}
}
