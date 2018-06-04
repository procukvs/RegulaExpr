package lambda;
import java.util.*;
import java.util.stream.*;

import lambda.use.*;


public class MainLambda {
	static Statistic st = new Statistic();

	public static void main(String[] args) throws Exception  {
		// TODO Auto-generated method stub
		// 1 - libraryWork,  2 - modellingWork, 3 - MandelbrotSet
		int work = 1;
		System.out.println("Hello, this is lambda !"); 
		switch(work){
		case 1 : libraryWork(); break;
		case 2 : modellingWork(100000000);break;
		case 3 : workMandelbrot();
		}
		
	}
	
	public static void workMandelbrot()  throws Exception  {
		Mandelbrot ms = new Mandelbrot();
		st.beginStage("ParallelStream");
		ms.parallelForm("mandelbrotPar.png");;
		st.beginStage("SequentialStream");
		ms.sequantialForm("mandelbrotTest.png");
		st.endStage();
		System.out.println(st.toString());
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
		
		System.out.println("Use array+regular");
		String[] ib = tl.libraryStr();
		for(String s:ib)System.out.println(s);
		Stream <Book>  bs = Stream.of(ib).map(Book::new); 
		Map <String, Long> cntBooksSAr = sw.numberOfBooksS(bs);
		System.out.println(" Counts books in publishers (ColArr): " + cntBooksSAr.toString());
		
	}

}
