package regexpr.finite;

import java.util.*;

public class FiniteDeter {
	// стани автомату  числа > 0
	private Integer start;
	private Set <Integer> terminal;
	private Map <Going,Integer> transitions;

	public FiniteDeter(int st, Set<Integer> term, Map <Going,Integer> tran){
		start = st;	terminal = term;
		transitions = tran;
	}	
	
	// for testing
	public FiniteDeter(int st, int[] term, int [][] tran){
		start = st;
		terminal = new TreeSet<>();
		for(int i:term) terminal.add(i); 
		transitions = new TreeMap <>();
		for(int[] go:tran){
			Character c = (Character)((char)go[1]);
			Going g = new Going((Integer)(go[0]),c);
			transitions.put(g, go[2]);
		}
	}	
	
	public boolean accepts(String s){
		boolean is = true;
		String ends = s;
		Integer st = start;
		Character nxt; 
		Going go;
		while(!ends.isEmpty()&& is){
			//System.out.println("....st=" + st + " ends=" + ends);
			nxt = ends.charAt(0); go = new Going(st,nxt);
			is = transitions.containsKey(go);
			if (is) {
				st = transitions.get(go);
				ends = ends.substring(1);
			}
			
		}
		if (is) is = terminal.contains(st);
		return is;
	}
	/*	
	class  Going implements Comparable<Going>{
		//private class  Going{
		Integer state;
		Character ch;
		Going(Integer s, Character c){
			state = s; ch = c;
		}
		public int compareTo(Going g){ 
			int r = state-g.state;
			if (r==0) r = ch-g.ch;
			return r;
		}
		@Override
		public String toString() {
			return "Going (state=" + state + ", ch=" + (ch==0?"Eps":ch)+ ")";
		}
	}
	*/
	public String toString(){
		String r= "FiniteDeter: start=" + start + " final=" + Arrays.toString(terminal.toArray());
		r += " transition=[\n   ";
		for(Going go : transitions.keySet())
			r += " (" + go.state + "," + go.ch + ")->" + transitions.get(go);
		r += "]";
		return r;
	}	
}
