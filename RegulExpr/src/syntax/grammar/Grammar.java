package syntax.grammar;

import java.util.*;

public class Grammar {
	// 
	protected Character start;
	protected Set<Character> nonterminals, terminals;
	protected ArrayList<Production> product;
	// for testing	
	public Grammar(char[] left, String[] right){
		product = new ArrayList<> ();
		nonterminals = new TreeSet<>();
		terminals = new TreeSet<>();
		start = null;
		for(int i=0;i<left.length && i<right.length; i++){
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
		if(start==null) { // початковий нетермінал ЗАВЖДИ є!
			start = 'S'; nonterminals.add('S');
		}
	}
	
	public Character getStart() {
		return start;
	}
	public Set<Character> getNonterminals() {
		return nonterminals;
	}
	public Set<Character> getTerminals() {
		return terminals;
	}
	public ArrayList<Production> getProduct() {
		return product;
	}
	
	private Character findLeft(){
		for(Production pr:product){
			if(!pr.getRull().isEmpty() && (char)pr.getNon() == pr.getRull().charAt(0)) return pr.getNon();
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
					if(pr.getNon().equals(ln)){
						if((char)pr.getNon() == pr.getRull().charAt(0)){
							pr=new Production(nn,pr.getRull().substring(1)+nn);
						}  else pr=new Production(pr.getNon(), pr.getRull()+nn);
						product.set(i,pr);
					}
				}
				product.add(new Production(nn,""));
			}
		} while (ln!=null);
	}
	
	// перевіряє, що список номерів продукцій задає деякий лівосторонній вивід в граматиці!
	public boolean leftDerivation(ArrayList<Integer> dv){
		boolean r=true;
		Production pr; 
		ArrayDeque<Character> sb = new ArrayDeque<>();
		int i=0;
		sb.push(start);
		while(r && i<dv.size() ){
			r = (dv.get(i)>=0 && dv.get(i) < product.size() && !sb.isEmpty());
			if(r){
				pr=product.get(dv.get(i++));
				r = pr.getNon().equals(sb.peek());
				if(r){
					sb.pop();
					for(int j=pr.getRull().length(); j>0; j--){
						Character c = pr.getRull().charAt(j-1);
						if (Character.isUpperCase(c))sb.push(c);
					}
					//i++;
				}
			}
		}
		return r;
	}
		
	private char newNonterminal(){
		char r = 'A';
		while(nonterminals.contains(r)) r++;
		nonterminals.add(r);
		return r;
	}
	
	public SynTree buildSynTree(ArrayList<Integer> dv){
		SynTree tr = null;
		if(leftDerivation(dv)){
			tr = new SynTree(start);
			buildInDepth(tr,0,dv);
		}
		return tr;
	}
	
	private int buildInDepth(SynTree t, int k, ArrayList<Integer> dv){
		if(Character.isUpperCase(t.getRoot()) && k < dv.size()) {
			String rull = product.get(dv.get(k++)).getRull();
			if(!rull.isEmpty())
				for(int j=0; j<rull.length(); j++){
					SynTree tr1 = new SynTree(rull.charAt(j));
					t.addSon(tr1);
					k = buildInDepth(tr1, k, dv);
				}
			else t.addSon(new SynTree());
		}
		return k;
	}
	
	@Override
	public String toString(){
		String r = "";
		String ns = Arrays.toString(nonterminals.toArray());
		String ts = Arrays.toString(terminals.toArray());
		String pl = Arrays.toString(product.toArray());  
		return "[nonterminals -> " + ns + ",\n terminals -> " + ts 
		+ ",\n product -> " + pl + ",\n start - " + start + "\n]";
	}

	/*
	public boolean leftDerivation(ArrayList<Integer> dv){
		boolean r=true;
		Production pr; 
		StringBuilder sb = new StringBuilder();
		int i=0;
		sb.append(start);
		while(r && i<dv.size() ){
			r = (dv.get(i)>=0 && dv.get(i) < product.size() && sb.length()> 0);
			if(r){
				pr=product.get(dv.get(i));
				r = pr.getNon().equals(sb.charAt(sb.length()-1));
				if(r){
					sb.deleteCharAt(sb.length()-1);
					for(int j=pr.getRull().length(); j>0; j--){
						Character c = pr.getRull().charAt(j-1);
						if (Character.isUpperCase(c))sb.append(c);
					}
					i++;
				}
			}
		}
		return r;
	}
	*/	
	
	
	/*
	private ArrayList<Integer> dv1;
	private int it;
	private void buildInDepth(SynTree t){
		if(Character.isUpperCase(t.getRoot()) && it < dv1.size()) {
			String rull = product.get(dv1.get(it++)).getRull();
			if(!rull.isEmpty())
				for(int j=0; j<rull.length(); j++){
					SynTree tr1 = new SynTree(rull.charAt(j));
					t.addSon(tr1);
					buildInDepth(tr1);
				}
			else t.addSon(new SynTree());
		}
		//return null;
	}
	*/	
	
	/*
	public boolean leftDerivation(int[] dv){
		boolean r=true;
		Production pr; 
		StringBuilder sb = new StringBuilder();
		int i=0;
		sb.append(start);
		while(r && i<dv.length ){
			r = (dv[i]>=0 && dv[i] < product.size() && sb.length()> 0);
			if(r){
				pr=product.get(dv[i]);
				r = pr.getNon().equals(sb.charAt(sb.length()-1));
				if(r){
					sb.deleteCharAt(sb.length()-1);
					for(int j=pr.getRull().length(); j>0; j--){
						Character c = pr.getRull().charAt(j-1);
						if (Character.isUpperCase(c))sb.append(c);
					}
					i++;
				}
			}
		}
		return r;
	}
	*/
	
 /*	
	public boolean isLL1(){
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
	
	private void add(Character N, Character t, int i){
		String key = ""+N +t;
		if(test.containsKey(key)){
			int pr = test.get(key);
			if (pr!=i){
				addError(key,i);
				if(pr>=0){
					addError(key,pr);
					test.put(key, -4);  // not LL(1) !!!!!!
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
	public void buildFst(){
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
					addWithoutEps(ns,cs);
					i++;
				}
				if (i==rull.length() && go) ns.add('$');
				fst.put(n,ns);
			}
		}
	}
	private Set<Character> addWithoutEps(Set<Character> bs, Set<Character> as){
		for(Character c:as)                 //******
			if(!c.equals('$')) bs.add(c);   //******
		return bs;
	}
	public void buildNxt(){
		Map<Character,Set<Character>> nxtp;
		// nxtp + nxt - не повинні посилатися на ОДНУ і ТУ множину (бо вони змінюються !!!!)
		nxtp = new TreeMap<>();
		nxt  = new TreeMap<>();
		// initial
		for(Character c: nonterminals){
			nxtp.put(c, new TreeSet<>());
			if (c.equals(start)){
				Set<Character> ts = new TreeSet<>();
				ts.add('$'); nxt.put(c, ts);
			} else nxt.put(c, new TreeSet<>());
		}
		while (!nxtp.equals(nxt)){
			nxtp = new TreeMap<>();
			for(Character c:nxt.keySet()) nxtp.put(c, new TreeSet(nxt.get(c)));
			for(Production pr:product){
				Character n = pr.getNon();
				String rull = pr.getRull();
				int i=0;
				while (i<rull.length()){
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
*/	
}
