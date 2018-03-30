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
		//r.simpleWork();
		
	}

}
