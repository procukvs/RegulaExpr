package syntax.grammar;

import java.util.*;

public class Grammar {
	// 
	Character start;
	Set<Character> nonterminals, terminals;
	ArrayList<Production> product;
	public Map<Character,Set<Character>> fstp, fst, nstp, nst;
	
	
	
	// for testing	
	public Grammar(char[] left, String[] right){
		product = new ArrayList<> ();
		nonterminals = new TreeSet<>();
		terminals = new TreeSet<>();
		start = null;
		for(int i=0;i<left.length; i++){
			char n = left[i];
			if(i<right.length && Character.isUpperCase(n)){ 
				String rull = right[i];
				if (start==null) start = n;
				nonterminals.add(n);
				for(char c:rull.toCharArray()){
					if(Character.isUpperCase(c)) nonterminals.add(c);
					else terminals.add(c);
				}
				product.add(new Production(n,rull));
			}
		}
	}
	/*
	public boolean leftRecursion(){
		boolean r = false;
		for(Production pr:product){
			r = r || ((char)pr.non == pr.rull.charAt(0));
		}
		return r;
	}
	*/
	
	private Character findLeft(){
		for(Production pr:product){
			if(!pr.rull.isEmpty() && (char)pr.non == pr.rull.charAt(0)) return pr.non;
		}
		return null;
	}
	public boolean leftRecursion(){ return (findLeft()!=null);}
	
	public void removeLeft(){
		// A->As1 ... A->Asn  ----> N->s1N ... N->snN  N->Eps
		// A->b1  ... A->bm   ----> A->b1N ... A->bmN
		Character ln=null;
		do{	ln=findLeft();
			if(ln!=null){
				char nn = newNonterminal();
				for(int i=0;i<product.size();i++){
					Production pr = product.get(i);
					if(pr.non.equals(ln)){
						if((char)pr.non == pr.rull.charAt(0)){
							pr=new Production(nn,pr.rull.substring(1)+nn);
						}  else pr=new Production(pr.non, pr.rull+nn);
						product.set(i,pr);
					}
				}
				product.add(new Production(nn,""));
			}
		} while (ln!=null);
	}
	
	public void buildFst(){
		fstp = new TreeMap<>();
		fst  = new TreeMap<>();
		// initial
		for(Character c: terminals){
			TreeSet sc = new TreeSet<>(); fstp.put(c, sc); 
			sc.add(c); fst.put(c, sc);
		}
		for(Character c: nonterminals){
			TreeSet sc = new TreeSet<>();
			fstp.put(c, sc); fst.put(c, sc); 		
		}
		for(Production pr:product){
			if(pr.rull.isEmpty()){
				Set ns = fst.get(pr.non);
				ns.add('$');	fst.put(pr.non,ns);
			}
		}
		while (!fstp.equals(fst)){
			fstp= new TreeMap(fst);
			for(Production pr:product){
				Character n = pr.non;
				Set<Character> ns = fstp.get(n);
				String rull = pr.rull;
				Boolean go = true;
				int i=0;
				while (i<rull.length() && go){
					Set<Character> cs = fstp.get((Character)rull.charAt(i));
					go = cs.contains('$');
					if(go) cs.remove('$');
					ns.addAll(cs); i++;
				}
				if (i==rull.length() && go) ns.add('$');
				fst.put(n,ns);
			}
		}
	}
	
	
	private char newNonterminal(){
		char r = 'A';
		while(nonterminals.contains(r)) r++;
		nonterminals.add(r);
		return r;
	}
	@Override
	public String toString(){
		String r = "";
		String ns = Arrays.toString(nonterminals.toArray());
		String ts = Arrays.toString(terminals.toArray());
		String pl = Arrays.toString(product.toArray());  
		return "[nonterminals -> " + ns + ",\n terminals -> " + ts 
		+ ",\n product -> " + pl + ",\n start - " + start + "\n]";
		
		// "["; 
		/*
		boolean snd = false;
		for(Production pr:product){
			if (snd) pl += ","; else snd=true;
			pl += pr.toString();
		}
		return "[nonterminals -> " + ns + ",\n  terminals -> " 
				+ ",\n  product -> " + pl + "],\n  start - " + start + "]"; */
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
		public String toString(){
			return non + "->" + (rull.isEmpty()?"Eps":rull);
		}
	}
	
	
}
