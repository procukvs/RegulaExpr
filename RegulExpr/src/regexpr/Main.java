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
		System.out.println("re1 = " + td.re1().toString());
		System.out.println("nda1=" + td.nda1().toString());
		System.out.println("da1=" + td.da1().toString());
		System.out.println("Build da1=" + td.da1().makeDA().toString());
		
		/*
		System.out.println("re2 = " + td.re2().toString() );
		System.out.println("nda2=" + td.nda2().toString());
		System.out.println("makeNDA(re2)=" + (new Automation(td.re2()).toString()));
		System.out.println(" ==== " + (td.nda2().eq(new Automation(td.re2())) ));
		
		System.out.println("re4 = " + td.re4().toString() + " simply = " + td.re4().simplify().toString());
		System.out.println("nda4=" + td.nda4().toString());
		System.out.println("makeNDA(re4)=" + (new Automation(td.re4()).toString()));
		System.out.println(" ==== " + (td.nda4().eq(new Automation(td.re4())) ));
		*/
		//Regular r = new Regular();
		//System.out.println("accepts nda0 ac? = " +td.nda0().accepts("ac"));
		//System.out.println("accepts nda0 aac? = " +td.nda0().accepts("aac"));
		//System.out.println("accepts nda0 ad? = " +td.nda0().accepts("ad"));
		//System.out.println("re5 = " + td.re5().toString());
		//System.out.println("simplify  = " + td.re5().simplify().toString());
		/*
		System.out.println("Hello Automata ....");
		System.out.println("re0 = " + td.re0().toString());
		System.out.println("nda0=" + td.nda0().toString());
		System.out.println("re1 = " + td.re1().toString());
		System.out.println("nda1=" + td.nda1().toString());
		System.out.println("re2 = " + td.re2().toString());
		System.out.println("nda2=" + td.nda2().toString());
		System.out.println("re3 = " + td.re3().toString());
		System.out.println("nda3=" + td.nda3().toString());
		System.out.println("re4 = " + td.re4().toString());
		System.out.println("nda4=" + td.nda4().toString());
		System.out.println("re5 = " + td.re5().toString());
		System.out.println("nda5=" + td.nda5().toString());
		*/
		//r.simpleWork();
		
	}

}
