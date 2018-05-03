package regexpr.finite;

import java.util.*;

public class MainFinite {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestDataFinite td = new TestDataFinite();
		
		System.out.println("Work with finite automations !");
		FiniteNondeter nda1 = td.nda1();
		System.out.println("nda1=" + td.nda1().toString());
		//Set<Integer> bs = new TreeSet<> ();
		//bs.add(1); bs.add(2);
		//Set<Integer> cl = nda1.closure(bs);
		System.out.println("accepts =" + nda1.accepts("bbbba"));
		//System.out.println("da0=" + td.da0().toString());
		//System.out.println("da0: " + "bbbb " +td.da0().accepts("bbbb"));
		//System.out.println("nda0=" + td.nda0().toString());
		//System.out.println("nda1=" + td.nda1().toString());
		//Optional<Character> ch = Optional.of((Character)((char)'a'));
		//System.out.println("Optional<Character> ch=" + FiniteNondeter.optStr(ch));
	}

}
