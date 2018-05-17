package syntax.grammar;

import java.util.*;

public class Grammar {
	// 
	Character start;
	Set<Character> nonterminals, terminals;
	ArrayList<Production> product;
	
	
	
	// for testing	
	public Grammar(char[] left, String[] right){
		product = new ArrayList<> ();
		nonterminals = new TreeSet<>();
		terminals = new TreeSet<>();
		start = null;
		for(int i=0;i<left.length; i++){
			char n=left[i];
			if(i<right.length && 'A'<=n && n<='Z') {
				String rull = right[i];
				if (start==null) start = n;
				nonterminals.add(n);
				for(char c:rull.toCharArray()){
					if('A'<=n && n<='Z') nonterminals.add(c);
					else terminals.add(n);
				}
				product.add(new Production(n,rull));
			}
		}
	}
	
	public boolean leftRecursion(){
		boolean r = false;
		for(Production pr:product){
			r = r || ((char)pr.non == pr.rull.charAt(0));
		}
		return r;
	}
	@Override
	public String toString(){
		String r = "";
		String ns = Arrays.toString(nonterminals.toArray());
		String ts = Arrays.toString(terminals.toArray());
		String pl = Arrays.toString(product.toArray());
		return "[nonterminals -> " + ns + ",\n  terminals -> " 
				+ ",\n  product -> " + pl + ",\n  start - " + start + "]";
	}
	/*
	
	public String toString() {
		return "Grammar [start=" + start + ", nonterminals=" + nonterminals + ", terminals=" + terminals + ", product="
				+ product + "]";
	}

	
	*/
	class Production{
		Character non;
		String rull;
		Production(Character n, String r){
			non=n; rull=r;
		}
		//public String toString(){
		//	return non + "->" + (rull.isEmpty()?"Eps":rull);
		//}
	}
	
	
}
