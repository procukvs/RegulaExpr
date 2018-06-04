package lambda.use;

import java.util.*;

public class TestLambda {
	ArrayList <Book> books; // = new ArrayList<> ();
	ArrayList <String> aut; // = new ArrayList<> (1);
	Set <String> ts; // = new TreeSet<>();
	
	public  ArrayList <Book> library(){
		books = new ArrayList <> ();
		aut = new ArrayList<> (1);
		aut.add("Buchman S."); 
		ts = new TreeSet();	ts.add("history"); ts.add("biography");
		books.add(new Book(aut,"1977!", 140, ts, "Abatis Publishers"));
		ts = new TreeSet();	ts.add("history");
		books.add(new Book(aut,"200 Years of German Humor", 107, ts, "Schadenfreude Press"));
		aut = new ArrayList<> (1);
		aut.add("Kells C."); 
		ts = new TreeSet();	ts.add("computer"); ts.add("dataBase");
		books.add(new Book(aut,"Ask Your System Administrator", 1226, ts, "Core Dump Books"));
		aut = new ArrayList<> (2);
		aut.add("Hull H."); aut.add("Hull K."); 
		ts = new TreeSet();	ts.add("psychology"); 
		books.add(new Book(aut,"But I Did It Unconsciously", 510, ts, "Abatis Publishers"));
		aut = new ArrayList<> (1);
		aut.add("Heydemark W."); 
		ts = new TreeSet(); ts.add("biography");
		books.add(new Book(aut,"How About Never?", 473, ts, "Abatis Publishers"));
		aut = new ArrayList<> (2);
		aut.add("Heydemark W."); aut.add("Hull K."); 
		ts = new TreeSet(); ts.add("biography");
		books.add(new Book(aut,"I Blame My Mother", 333, ts, "Schadenfreude Press"));
		aut = new ArrayList<> (1);
		aut.add("Kellsey"); 
		ts = new TreeSet(); ts.add("children"); ts.add("fantasy");
		books.add(new Book(aut,"Just Wait Until After School", 86, ts, "Abatis Publishers"));
		aut = new ArrayList<> (1);
		aut.add("Kellsey"); 
		ts = new TreeSet(); ts.add("children"); 
		books.add(new Book(aut,"Kiss My Boo-Boo", 55, ts, "Abatis Publishers"));
		aut = new ArrayList<> (1);
		aut.add("Heydemark W."); 
		ts = new TreeSet(); ts.add("biography"); ts.add("history");
		books.add(new Book(aut,"Not Without My Faberge Egg", 523, ts, "Abatis Publishers"));
		
		aut = new ArrayList<> (3);
		aut.add("Kellsey"); aut.add("Hull H."); aut.add("Hull K."); 
		ts = new TreeSet();	ts.add("psychology"); 
		books.add(new Book(aut,"Perhaps It's a Glandular Problem", 826, ts, "Abatis Publishers"));
		aut = new ArrayList<> (1);
		aut.add("Heydemark W."); 
		ts = new TreeSet(); ts.add("biography"); 
		books.add(new Book(aut,"Spontaneous, Not Annoying", 507, ts, "Abatis Publishers"));
		aut = new ArrayList<> (1);
		aut.add("Buchman S."); 
		ts = new TreeSet();	ts.add("history"); ts.add("fantasy");
		books.add(new Book(aut,"What Are The Civilian Applications?", 802, ts, "Schadenfreude Press"));
		return books;
	};
	
	public String[] libraryStr(){
		String[] informS = 
			{"Buchman S. , 1977! , 140,  history: biography , Abatis Publishers ",
			 "Buchman S. , 200 Years of German Humor , 107, history  , Schadenfreude Press ",
			 "Hull H. : Hull K. , But I Did It Unconsciously , 510  , psychology  , Abatis Publishers "
			}; 
		return informS;			
	}
	

}
