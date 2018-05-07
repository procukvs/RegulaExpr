package regexpr.identify;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;

public class RETest {
	static private TestIdentity ti;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ti = new TestIdentity();
	}

	@Test
	public void simplifyTest1() {
		String sre5 = "(ab|#)dd*";
		assertEquals("simplify", sre5, ti.re5().simplify().toString());
	}
	
	@Test
	public void simplifyTest2() {
		assertThat(new REext(ti.re5S()),equalTo(new REext(ti.re5().simplify())));
		
	}	

	@Test
	public void simplifyTest3() {
		assertThat(new REext(ti.re5S()),equalTo(new REext(ti.re5())));
		
	}	
	
}
