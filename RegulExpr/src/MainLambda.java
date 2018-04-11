import java.util.*;
import lambda.use.*;

public class MainLambda {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello, this is lambda !"); 
		TestLambda tl = new TestLambda();
		StreamWork sw = new StreamWork();
		ArrayList <Book> library = tl.library();
		for(int i=0; i<library.size(); i++){
			System.out.println(library.get(i).toString());
		}
		
		/*
		Map <String, Integer> cntBooksC = sw.numberOfBooksC(library.stream());
		System.out.println(" Counts books in publishers (Col): " + cntBooksC.toString());
		Map <String, Long> cntBooksS = sw.numberOfBooksS(library.stream());
		System.out.println(" Counts books in publishers (Str): " + cntBooksS.toString());
		Map <String, List<String>> nameBooksC = sw.nameOfBooksC(library.stream());
		System.out.println(" Name books in publishers (Col): " + nameBooksC.toString());
		Map <String, List<String>> nameBooksS = sw.nameOfBooksS(library.stream());
		System.out.println(" Name books in publishers (Str): " + nameBooksS.toString());
		*/
	}

}
