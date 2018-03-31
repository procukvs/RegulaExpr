package regexpr;

import regexpr.use.*;
import regexpr.automata.*;

public class Main {
    // https://github.com/procukvs/RegulaExpr.git
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Work with regular expression!");
		System.out.println("Hello Regular ....");
		TestData td = new TestData();
		Regular r = new Regular();
		System.out.println("Hello Automata ....");
		System.out.println("re0 = " + td.re0().toString());
		System.out.println("nda0=" + td.nda0().toString());
		/*
		System.out.println("re1 = " + td.re1().toString());
		System.out.println("re2 = " + td.re2().toString());
		System.out.println("re3 = " + td.re3().toString());
		System.out.println("re4 = " + td.re4().toString());
		System.out.println("re5 = " + td.re5().toString());
		*/
		//r.simpleWork();
		
	}

}
