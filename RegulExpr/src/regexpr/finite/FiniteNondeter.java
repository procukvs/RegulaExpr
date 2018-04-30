package regexpr.finite;

import java.util.*;
import java.util.stream.*;

public class FiniteNondeter {
	// стани автомату  числа > 0
	private Integer start;
	private Set <Integer> terminal;
	private Map <Going,Set<Integer>> transitions;
	private Map<Integer,Set<Integer>> empty = null;

	public FiniteNondeter(){
		start = 1; terminal = new TreeSet<>(); 
		transitions = new TreeMap <>();
	}
	
	public FiniteNondeter(Integer st, Set <Integer> term){
		start = st; terminal = term; 
		transitions = new TreeMap <>();
	}
	
	public FiniteNondeter(int st, int[] term, int [][] tran){
		start = st;
		terminal = new TreeSet<>();
		for(int i:term) terminal.add(i); 
		transitions = new TreeMap <>();
		for(int[] go:tran){
			Optional<Character> c = (go[1]==0?Optional.empty():Optional.of((Character)((char)go[1])));
			Going g = new Going((Integer)(go[0]),c);
			Set<Integer> mg; 
			if(transitions.containsKey(g)) mg=transitions.get(g); else mg = new TreeSet<>();
			for(int i = 2; i<go.length; i++) mg.add(go[i]); 
			transitions.put(g,mg);
		}
	}
	
	public boolean accepts(String s){
		boolean is = false;
		String rs = s;
		buildEmpty();
		Set<Integer> nxt = new TreeSet<>(empty.get(start));
		Set<Integer> gos; 
		while (!rs.isEmpty()){
			Character ch = rs.charAt(0);
			rs = rs.substring(1);
			gos = new TreeSet<>();
			for(Integer st:nxt){
				Going go = new Going(st,Optional.of(ch));
				if(transitions.containsKey(go)) gos.addAll(transitions.get(go));
			}
			nxt = closure(gos);
		}
		for(Integer st:nxt) is = is || terminal.contains(st); 
		return is;
	}
	
	// всі стани, в які може перейти автомат по порожнім переходам зі станів bs
	public Set<Integer> closure(Set<Integer> bs){
		Set<Integer> cls = new TreeSet<>(bs);
		buildEmpty();
		for(Integer s:bs) if (empty.containsKey(s)) cls.addAll(empty.get(s));
		return cls;
	}
	
	private void buildEmpty(){
		if (empty==null){
    		// initial
			empty  = new TreeMap<>();
	        for(Map.Entry<Going,Set<Integer>> gom : transitions.entrySet()){ 
	        	if(!gom.getKey().ch.isPresent()){ 
	        		empty.put(gom.getKey().state, gom.getValue());
	        	}
	        }
	        
	        for(Integer st:empty.keySet()){
	        	Set<Integer> cl = closure(st,empty);
	        	empty.put(st, cl);
	        }
		}
	}
	
	private  Set<Integer> closure(Integer ini, Map<Integer,Set<Integer>> start){
		Set<Integer> cls = new TreeSet<>();
		Set<Integer> no = new TreeSet(start.get(ini));
		while (!no.isEmpty()){
			// take any element from set !!!!!
			Integer s = no.iterator().next();
			no.remove(s); cls.add(s);
			//Integer s = ((NavigableSet<Integer>)no).pollFirst();
			//cls.add(s);
			if(start.containsKey(s)){ 
				for(Integer ns:start.get(s))
					if(!cls.contains(ns)) no.add(ns);
			} 
		}
		return cls;
	}
	
	
	class  Going implements Comparable<Going>{
		//private class  Going{
		Integer state;
		Optional<Character> ch;
		Going(Integer s, Optional<Character> c){
			state = s; ch = c;
		}
		public int compareTo(Going g){ 
			int r = state-g.state;
			if (r==0){
				if (ch.isPresent())	r = (g.ch.isPresent() ? ch.get()-g.ch.get() : 1);				
				else r = (g.ch.isPresent() ? -1 : 0); 
			}	
			return r;
		}
		@Override
		public String toString() {
			return "Going (state=" + state + ", ch=" + (ch.isPresent()?ch.get():"Eps")+ ")";
		}
	}

	public static String optStr(Optional<Character> ch){
		return (ch.isPresent()?ch.get()+"":"Eps");
	}
	public String toString(){
		String r= "FiniteNondeter: start=" + start + " final=" + Arrays.toString(terminal.toArray());
		r += " transition=[\n   ";
		for(Going go : transitions.keySet())
			r += " (" + go.state + "," + optStr(go.ch) + ")->" + 
		                     Arrays.toString(transitions.get(go).toArray());
		r += "]";
		return r;
	}
}
