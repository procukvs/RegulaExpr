package regexpr.identify;

import java.util.*;

public class TestSuffix  {
	
	public SuffixTree t1(){
		/*
		ArrayList<String> astr = new ArrayList<>();
		ArrayList<SuffixTree> atree = new ArrayList<>();
		astr.add("banana"); astr.add("a"); astr.add("na");
		atree.add(new SuffixTree(0)); 
		atree.add(new SuffixTree(10)); 
		atree.add(new SuffixTree(5));
		*/
		String[] astr = new String[]{"banana","a","na"}; 
		SuffixTree[] atree = new SuffixTree[]{
			new SuffixTree(0),
			new SuffixTree(new String[]{"na",""},
						   new SuffixTree[]{
								new SuffixTree(new String[]{"na",""}, 
										       new SuffixTree[]{new SuffixTree(1),
										    		            new SuffixTree(3)}),
								new SuffixTree(5)}),
			new SuffixTree(new String[]{"na",""}, 
					       new SuffixTree[]{new SuffixTree(2),
					    		            new SuffixTree(4)}) };
		return new SuffixTree(astr,atree);
	}
	
}
