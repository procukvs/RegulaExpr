package regexpr.finite;

import static org.junit.Assert.*;
import org.junit.*;
import regexpr.finite.*;

public class FiniteDeterTest {
	static private TestData td;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		td = new TestData();
	}
	
	@Test
	public void acceptsTest1() {
		String is = "aaaa";
		assertTrue("accepts", td.da0().accepts(is));
		
	}
	@Test
	public void acceptsTest2() {
		assertTrue("accepts", td.da0().accepts("bbb"));
		
	}
	@Test
	public void acceptsTest3() {
		assertFalse("accepts", td.da0().accepts("bbbc"));
		
	}
}
