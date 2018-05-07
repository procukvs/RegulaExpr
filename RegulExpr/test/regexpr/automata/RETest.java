package regexpr.automata;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class RETest {
	static private TestData td;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		td = new TestData();
	}

	@Test
	public void simplifyTest1() {
		String sre5 = "(ab|)dd*";
		assertEquals("simplify", sre5, td.re5().simplify().toString());
	}
	
	@Test
	public void simplifyTest2() {
		String sad1 = "xx*";
		assertEquals("simplify-x+", sad1, td.ad1().simplify().toString());
	}
	
	@Test
	public void simplifyTest3() {
		String sad2 = "(x|)";
		assertEquals("simplify-x?", sad2, td.ad2().simplify().toString());
	}
	/*
	@Test
	public void simplifyTest2() {
		assertEquals("simplify1", td.re5S().compareTo(td.re5().simplify()), 0);
	}
	*/
	@Test
	public void simplifyTest4() {
		String sre1 = "(x|y)(1|2)";
		assertEquals("simplify-(x|y)(1|2)", sre1, td.re1().simplify().toString());
	}

}
