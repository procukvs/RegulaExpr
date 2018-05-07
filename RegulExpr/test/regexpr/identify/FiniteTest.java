package regexpr.identify;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.*;

//import regexpr.automata.TestData;

public class FiniteTest {
	static private TestIdentity ti;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ti = new TestIdentity();
	}

	@Test
	public void testAcceptTrue1() {
		String str = "ac";
		assertTrue("Accept " + str, ti.fre0().accepts(str));
	}
	@Test
	public void testAcceptTrue2() {
		String str = "c";
		assertTrue("Accept " + str, ti.fre0().accepts(str));
	}
	@Test
	public void testAcceptTrue3() {
		String str = "ababbbc";
		assertTrue("Accept " + str, ti.fre0().accepts(str));
	}
	@Test
	public void testAcceptFalse1() {
		String str = "ad";
		assertFalse("Accept " + str, ti.fre0().accepts(str));
	}
	@Test
	public void testBuildFinite1() {
		assertThat(new Finite(ti.re0()),equalTo(ti.fre0()));
	}	
	@Test
	public void testBuildFinite2() {
		assertThat(new Finite(ti.re12()),equalTo(ti.fre11()));
	}	
}
