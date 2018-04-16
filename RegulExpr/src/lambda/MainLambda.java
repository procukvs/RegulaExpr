package lambda;
import java.util.*;
import lambda.use.*;

public class MainLambda {
	static Statistic st = new Statistic();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1 - libraryWork,  2 - modellingWork
		int work = 1;
		System.out.println("Hello, this is lambda !"); 
		switch(work){
		case 1 : libraryWork(); break;
		case 2 : modellingWork(100000000);
		}
		
	}
	
	public static void modellingWork(int N){
		Modelling l = new Modelling();
		System.out.println(".. N = " + N + " .. ");
		st.beginStage("ParallelStream");
		Map<Integer, Double> rc = l.parallelDiceRolls(N);
		System.out.println(rc.toString());
		st.beginStage("SequentialStream");
		rc = l.sequentialDiceRolls(N);
		System.out.println(rc.toString());
		st.beginStage("Fast");
		rc = l.fastDiceRolls(N);
		System.out.println(rc.toString());
		st.endStage();
		
		System.out.println(st.toString());
	}
	
	public static void libraryWork(){
		TestLambda tl = new TestLambda();
		StreamWork sw = new StreamWork();
		ArrayList <Book> library = tl.library();
		for(int i=0; i<library.size(); i++){
			System.out.println(library.get(i).toString());
		}
		Map <String, Integer> cntBooksC = sw.numberOfBooksC(library.stream());
		System.out.println(" Counts books in publishers (Col): " + cntBooksC.toString());
		Map <String, Long> cntBooksS = sw.numberOfBooksS(library.stream());
		System.out.println(" Counts books in publishers (Str): " + cntBooksS.toString());
		Map <String, List<String>> nameBooksC = sw.nameOfBooksC(library.stream());
		System.out.println(" Name books in publishers (Col): " + nameBooksC.toString());
		Map <String, List<String>> nameBooksS = sw.nameOfBooksS(library.stream());
		System.out.println(" Name books in publishers (Str): " + nameBooksS.toString());
	}

}
