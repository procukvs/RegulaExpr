package regexpr;

import java.util.*;
import regexpr.use.*;
import regexpr.automata.*;
import regexpr.finite.*;
import regexpr.identify.*;

public class Main {
    // https://github.com/procukvs/RegulaExpr.git
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 0 - onlyRegular, 1 - automata (Haskell),  2 - finite(Classic), 
		// 3 - identification (clasic RE), 4 - machineFailure, 5-suffixTree 
		//
		int work = 5;
		System.out.println("Hello Regular ....");
		switch(work){
		case 0: break;
		case 1 : automationWork(); break;
		case 2 : finiteWork();break;
		case 3 : identityWork();break;
		case 4 : identityMach();break;
		case 5 : suffixTree(); break;
		}
		//r.simpleWork();
		
	}
	
	public static void suffixTree(){
		System.out.println("SuffixTree ....");
		TestSuffix ts=new TestSuffix();
		System.out.println(" t1=" + ts.t1().toString());
		
		//String beg="banana";
		
		//String beg="mississippi";
		/*
		for(int i=0; i<=beg.length(); i++){
			String s = beg.substring(0, i);
			System.out.println(".. s=" + s + "..");
			System.out.println(".. SuffixTree(s)= \n" + (new SuffixTree(s)).toString()) ;
		}
		*/
	}
	
	public static void identityMach(){
		System.out.println("Identify Machine ....");
	    String w= "aabbaab";
	    Identify wi = new Identify(w);
	    System.out.println("...." + w + "....");
	    System.out.println(wi.toString());
	    String text="aabbaa";
	    //String text="aaabbaabcc";
	    //String text="abaabaabbaab";
	    System.out.println(" text=" + text + " => " + wi.find(text)); 
	    
	    
	}
	public static void identityWork(){
		System.out.println("Identify ....");
		TestIdentity ti = new TestIdentity();
		System.out.println("sre0 = " + ti.sre0());
		System.out.println("re0 = " + ti.re0().toString());
		System.out.println("fe0 = " + ti.fre0().toString());
		System.out.println("Build fe0 = " + (new Finite(ti.re0())).toString());
		//Regular r = new Regular();
		System.out.println("accepts fe0 ac? = " +ti.fre0().accepts("ac"));
		System.out.println("accepts fe0 abac? = " +ti.fre0().accepts("abac"));
		System.out.println("accepts fe0 ad? = " +ti.fre0().accepts("ad"));
		System.out.println("accepts fe0 c? = " +ti.fre0().accepts("c"));
		
		/*
		System.out.println("sre10 = " + ti.sre10());
		System.out.println("re10 = " + ti.re10().toString());
		System.out.println("fe10 = " + ti.fre10().toString());	
		System.out.println("Build fe10 = " + (new Finite(ti.re10())).toString());
		
		System.out.println("sre11 = " + ti.sre11());
		System.out.println("re11 = " + ti.re11().toString());
		System.out.println("fe11 = " + ti.fre11().toString());	
		System.out.println("Build fe11 = " + (new Finite(ti.re11())).toString());
		System.out.println("sre12 = " + ti.sre12());
		System.out.println("re12 = " + ti.re12().toString());
		System.out.println("fe12 = " + ti.fre12().toString());
		System.out.println("Build fe12 = " + (new Finite(ti.re12())).toString());
		*/	
	}	
	
	public static void finiteWork(){
		System.out.println("Deter+NonDeter ....");
		TestDataFinite tdf = new TestDataFinite();
		/*
		FiniteDeter da0 = tdf.da0();
		String[] wd = {"aaaa","","bbb","aab", "cc"};
		System.out.println("da0=" + da0.toString());
		System.out.println("accepts: da0");
		for(int i=0;i<wd.length;i++){
			System.out.println("...." + wd[i] + " " + da0.accepts(wd[i]));	
		}
		*/
		FiniteNondeter nda0 = tdf.nda0();
		FiniteNondeter nda1 = tdf.nda1();
		System.out.println("nda0=" + nda0.toString());
		System.out.println("da0W=" + nda0.buildDeter().toString());
		System.out.println("nda1=" + nda1.toString());
		System.out.println("da1W=" + nda1.buildDeter().toString());
		     //System.out.println("set1 = " + Collections.singleton((Integer)1));
		     //System.out.println("close = " + nda0.closure(Collections.singleton((Integer)1)));
		/*
		String[] wd = {"aaaac","c","bbb","abc", "cc"};
		System.out.println("accepts: nda0");
		for(int i=0;i<wd.length;i++){
			System.out.println("...." + wd[i] + " " + nda0.accepts(wd[i]));	
		}
		*/
		      //FiniteNondeter nda1 = tdf.nda1();
		        //System.out.println("nda1=" + nda1.toString());
		//System.out.println("set1 = " + Collections.singleton((Integer)1));
		//System.out.println("close = " + nda1.closure(Collections.singleton((Integer)1)));	
		
	}
	public static void automationWork(){
		TestData td = new TestData();
		System.out.println("re1 = " + td.re1().toString());
		System.out.println("nda1=" + td.nda1().toString());
		
		//td.nda1().makeDA();
		//System.out.println("Build da1=");
		
		System.out.println("Build da1=" +td.da1().makeDA().toString());
		System.out.println("da1=" + td.da1().toString());
		
		System.out.println("re2 = " + td.re2().toString());
		System.out.println("nda2=" + td.nda2().toString());
		System.out.println("Build da2=" +td.da2().makeDA().toString());
		System.out.println("da2=" + td.da2().toString());
		
		System.out.println("re3 = " + td.re3().toString());
		System.out.println("nda3=" + td.nda3().toString());
		System.out.println("Build da3=" +td.da3().makeDA().toString());
		System.out.println("da3=" + td.da3().toString());
		
		System.out.println("re4 = " + td.re4().toString());
		System.out.println("nda4=" + td.nda4().toString());
		System.out.println("Build da4=" +td.da4().makeDA().toString());
		System.out.println("da4=" + td.da4().toString());
		
		System.out.println("re5 = " + td.re5().toString());
		System.out.println("nda5=" + td.nda5().toString());
		System.out.println("Build da5=" +td.da5().makeDA().toString());
		System.out.println("da5=" + td.da5().toString());
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
	}

}
