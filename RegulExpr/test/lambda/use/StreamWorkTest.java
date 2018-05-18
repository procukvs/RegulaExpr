package lambda.use;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import lambda.use.*;

public class StreamWorkTest {
	static private TestLambda tl;
	static private StreamWork sw;
	static private ArrayList <Book> library;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tl = new TestLambda();
		sw = new StreamWork();
		library = tl.library();
	}

	@Test
	public void testNumberOfBookC() {
		Map <String, Integer> numberOfBooks = new HashMap<>();
		numberOfBooks.put("Abatis Publishers", 8);
		numberOfBooks.put("Schadenfreude Press", 3);
		numberOfBooks.put("Core Dump Books", 1);
		assertEquals("NumberOfBookC", numberOfBooks, 
				            sw.numberOfBooksC(library.stream()));
	}
	
	@Test
	public void testNumberOfBookS() {
		Map <String, Long> numberOfBooks = new HashMap<>();
		numberOfBooks.put("Abatis Publishers", (long)8);
		numberOfBooks.put("Core Dump Books", (long)1);
		numberOfBooks.put("Schadenfreude Press", (long)3);
		assertEquals("NumberOfBookS", numberOfBooks, 
				            sw.numberOfBooksS(library.stream()));
	}
	@Test
	public void testNameOfBookC() {
		assertEquals("NameOfBookC", nameOfBooksT(), 
				            sw.nameOfBooksC(library.stream()));
	}
	@Test
	public void testNameOfBookS() {
		assertEquals("NameOfBookS", nameOfBooksT(), 
				            sw.nameOfBooksS(library.stream()));
	}
	
	private Map <String, List<String>>  nameOfBooksT() {
		Map <String, List<String>> nameOfBooks = new HashMap<>();
		List<String> bl = new ArrayList<>();
		bl.add("200 Years of German Humor");
		bl.add("I Blame My Mother"); bl.add("What Are The Civilian Applications?");
		nameOfBooks.put("Schadenfreude Press", bl);
		bl = new ArrayList<>();
		bl.add("1977!"); bl.add("But I Did It Unconsciously");	bl.add("How About Never?"); 
		bl.add("Just Wait Until After School"); bl.add("Kiss My Boo-Boo"); 
		bl.add("Not Without My Faberge Egg"); bl.add("Perhaps It's a Glandular Problem"); 
		bl.add("Spontaneous, Not Annoying");
		nameOfBooks.put("Abatis Publishers", bl);
		bl = new ArrayList<>();
		bl.add("Ask Your System Administrator");
		nameOfBooks.put("Core Dump Books", bl);
		return nameOfBooks;
	}
}
