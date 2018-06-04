package syntax.grammar;

import java.util.*;

public class GrammarLL1 extends Grammar {
	private Map<String,Set<Integer>> terrors;
	private Map<String,Integer> test;
	private Map<Character,Set<Character>> fst, nxt;
	
	public GrammarLL1(char[] left, String[] right) {
		super(left,right);
	}
	// Зазори для доступу до проміжних таблиць, що формуються після виклику isLL1() !!!!!! 
	public Map<String,Set<Integer>> getTerrors(){ return terrors;}
	public Map<String,Integer> getTest(){ return test;}
	public Map<Character,Set<Character>> getFst() { return fst;}
	public Map<Character,Set<Character>> getNxt() { return nxt;}
	
	// analys(input) != null --> повертає вивод dv, якщо input належить мові, що задає граматика
	// analys(input) == null --> якщо input НЕ належить мові, що задає граматика
	// $ - виділений символ -- !terminals.contains('$') !!!!
	public ArrayList<Integer> analys(String input){
		ArrayList<Integer> dv = new ArrayList<> ();
		String wd = input + '$';
		String stack = "" + start + '$';
		while(stack.charAt(0)!='$'){
			//System.out.println("wd = " + wd + " stack = " + stack);
		   	Character r = stack.charAt(0);
		   	stack = stack.substring(1);
		   	if(nonterminals.contains(r)){
		   		String key = ""+r+wd.charAt(0);
				if(test.containsKey(key)){
					Integer j = test.get(key);
					dv.add(j);
					stack = product.get(j).getRull() + stack;
				} else return null;
		   	} else
		   		if(r.equals(wd.charAt(0))) wd = wd.substring(1); else return null;
		}
		if (wd.charAt(0)!='$') return null;
		return dv;
	}
	
	public boolean isLL1(){
		if (leftRecursion())return false;
		buildFst(); buildNxt();
		terrors = new TreeMap<String,Set<Integer>>();
		test = new TreeMap<String,Integer>();
		for(int i=0; i<product.size();i++){
			Production pr = product.get(i);
			Character n = pr.getNon();
			String rull = pr.getRull();
			Set<Character> fs = first(rull); //(""+n);
			Set<Character> ns = follow(n); 
			//System.out.println("..i="+i + " production= " + pr.toString() + " first=" + fs.toString() + " follow=" + ns.toString() );
			for(Character c:fs){
				if(c=='$')	for(Character s:ns) add(n,s,i);
				else add(n,c,i);
			}
		}
		//if(!terrors.isEmpty()) return false;
		//return true;
		return terrors.isEmpty();
	}
	
	private void add(Character N, Character t, int i){
		String key = ""+N +t;
		if(test.containsKey(key)){
			int pr = test.get(key);
			if (pr!=i){
				addError(key,i);
				if(pr>=0){
					addError(key,pr);
					test.put(key, -1);  // not LL(1) !!!!!!
				}
			}
		} else test.put(key, i);
	
	}
	private void addError(String key, int r){
		Set<Integer> si;
		if(terrors.containsKey(key)) si=terrors.get(key);
		else si = new TreeSet<Integer>();
		si.add(r);
		terrors.put(key, si);
	}
	
	// first + follow : ==>  зроблені public лише для тестування  
	public Set<Character> first(String wd){
		Set<Character> rs = new TreeSet<>();
		if (!wd.isEmpty()){
			Character c = wd.charAt(0);
			if(wd.length()>=1){
				if(fst.get(c).contains('$')){
					addWithoutEps(rs,fst.get(c));
					rs.addAll(first(wd.substring(1)));
				} else rs = fst.get(c);
			}
			else rs = fst.get(c);				
		}
		else rs.add('$');
		return rs;
	}
	
	public Set<Character> follow(Character c){
		Set<Character> rs = new TreeSet<>();
		if(nonterminals.contains(c)) rs = nxt.get(c);
		return rs;
	}	
	
	private void buildFst(){
		Map<Character,Set<Character>> fstp;
		//int st=0;
		// fstp + fst - не повинні посилатися на ОДНУ і ТУ множину (бо вони змінюються !!!!)
		fstp = new TreeMap<>();
		fst  = new TreeMap<>();
		// initial
		for(Character c: terminals){
			fstp.put(c, new TreeSet<>()); 
			TreeSet sc = new TreeSet<>();sc.add(c); 
			fst.put(c, sc);
		}
		for(Character c: nonterminals){
			fstp.put(c, new TreeSet<>()); 
			fst.put(c, new TreeSet<>()); 		
		}
		for(Production pr:product){
			if(pr.getRull().isEmpty()){
				Set ns = fst.get(pr.getNon());
				ns.add('$'); fst.put(pr.getNon(),ns);
			}
		}
		// iteration
		while (!fstp.equals(fst)){
			fstp = new TreeMap<>();
			for(Character c:fst.keySet()) fstp.put(c, new TreeSet(fst.get(c)));
			                                   //fstp= new TreeMap(fst);
			for(Production pr:product){
				Character n = pr.getNon();
				Set<Character> ns = fst.get(n);
				String rull = pr.getRull();
				Boolean go = true;
				int i=0;
				while (i<rull.length() && go){
					Set<Character> cs = fstp.get((Character)rull.charAt(i));
					go = cs.contains('$');
					//for(Character c:cs)                 //******
					//	if(!c.equals('$')) ns.add(c);   //******
					addWithoutEps(ns,cs);
					i++;
				}
				if (i==rull.length() && go) ns.add('$');
				fst.put(n,ns);
			}
			//st++;
		}
	}

	private Set<Character> addWithoutEps(Set<Character> bs, Set<Character> as){
		for(Character c:as)                 //******
			if(!c.equals('$')) bs.add(c);   //******
		return bs;
	}
	private void buildNxt(){
		Map<Character,Set<Character>> nxtp;
		//int st=0;
		// nxtp + nxt - не повинні посилатися на ОДНУ і ТУ множину (бо вони змінюються !!!!)
		nxtp = new TreeMap<>();
		nxt  = new TreeMap<>();
		// initial
		for(Character c: nonterminals){
			nxtp.put(c, new TreeSet<>());
			Set<Character> ts = new TreeSet<>();
			if (c.equals(start)) ts.add('$');
			nxt.put(c, ts);
			/*
			if (c.equals(start)){
				Set<Character> ts = new TreeSet<>();
				ts.add('$'); nxt.put(c, ts);
			} else nxt.put(c, new TreeSet<>());
			*/
		}
		// iteration
		while (!nxtp.equals(nxt)){
			// deep copy
			nxtp = new TreeMap<>();
			for(Character c:nxt.keySet()) nxtp.put(c, new TreeSet(nxt.get(c)));
			for(Production pr:product){
				Character n = pr.getNon();
				String rull = pr.getRull();
				int i=0;
				while (i<rull.length()){
					//System.out.println("i=" + i + " c= " + rull.charAt(i));
					Character nt = (Character)rull.charAt(i);
					if (nonterminals.contains(nt)){
						Set<Character> cs = nxt.get(nt);			
						if(i<rull.length()-1){
							Set ntc = fst.get((Character)rull.charAt(i+1));
							addWithoutEps(cs,ntc);
							Boolean go = ntc.contains('$');
							int j=i+2;
							while(j<rull.length() && go){
								ntc = fst.get((Character)rull.charAt(j));
								addWithoutEps(cs,ntc);
								go = ntc.contains('$');
								j++;
							}
							if(go)cs.addAll(nxtp.get(n));
						}
						else cs.addAll(nxtp.get(n));
					}
					i++;
				}
			}
		}	
	}
	// -----------------------------------------------------
	// first version FULL table test
	public boolean isLL1Base(){
		if (leftRecursion())return false;
		buildFst(); buildNxt();
		terrors = new TreeMap<String,Set<Integer>>();
		test = new TreeMap<String,Integer>();
		for(int i=0; i<product.size();i++){
			Production pr = product.get(i);
			Character n = pr.getNon();
			//Set<Character> ns = fst.get(n);
			String rull = pr.getRull();
			Set<Character> fs = first(rull); //(""+n);
			Set<Character> ns = follow(n); 
			//System.out.println("..i="+i + " production= " + pr.toString() + " first=" + fs.toString() + " follow=" + ns.toString() );
			for(Character c:fs){
				if(c=='$'){
					//Set<Character> ns = follow(n); 
					for(Character s:ns) add(n,s,i);
				} else add(n,c,i);
			}
		}
	    //boolean r = true;	
	    //for(String s:terrors.keySet()){
	   // 	r=r &&(terrors.get(s).size()< 2);
	   // }
		if(!terrors.isEmpty()) return false;
		// make test - full!!!
		Set<Character> termAll = new TreeSet<>(terminals);
		termAll.add('$');
		for(Character n : nonterminals)
			for(Character t : termAll){
				String key = "" + n +t;
				if(!test.containsKey(key)) test.put(key,-2);
			}
		for(Character t1 : termAll)
			for(Character t2 : termAll){
				String key = "" + t1 + t2;
				if(t1.equals(t2))test.put(key,(t1.equals('$')?-3:-1));
				else test.put(key,-2);
			}
		return true;
	}
	
}
