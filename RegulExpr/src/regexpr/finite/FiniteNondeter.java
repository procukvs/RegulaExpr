package regexpr.finite;

import java.util.*;
import java.util.stream.*;

public class FiniteNondeter {
	// стани автомату  числа > 0
	private Integer start;
	private Set <Integer> terminal;
	private Map <GoingOpt,Set<Integer>> transitions;
	private Map<Integer,Set<Integer>> empty = null;

	public FiniteNondeter(){
		start = 1; terminal = new TreeSet<>(); 
		transitions = new TreeMap <>();
	}
	
	public FiniteNondeter(Integer st, Set <Integer> term){
		start = st; terminal = term; 
		transitions = new TreeMap <>();
	}
	// for testing
	public FiniteNondeter(int st, int[] term, int [][] tran){
		start = st;
		terminal = new TreeSet<>();
		for(int i:term) terminal.add(i); 
		transitions = new TreeMap <>();
		for(int[] go:tran){
			Optional<Character> c = (go[1]==0?Optional.empty():Optional.of((Character)((char)go[1])));
			GoingOpt g = new GoingOpt((Integer)(go[0]),c);
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
		//System.out.println(" empty= " + emptyToString()); 
		Set<Integer> nxt = closure(Collections.singleton(start));                       //empty.get(start);
		//System.out.println(" nxt= " +  Arrays.toString(nxt.toArray()));
		//Set<Integer> nxt = new TreeSet<>(closure(empty.get(start)));
		Set<Integer> gos; 
		while (!rs.isEmpty()&& !nxt.isEmpty()){
			Character ch = rs.charAt(0);
			rs = rs.substring(1);
			gos = new TreeSet<>();
			for(Integer st:nxt){
				GoingOpt go = new GoingOpt(st,Optional.of(ch));
				if(transitions.containsKey(go)) gos.addAll(transitions.get(go));
			}
			//System.out.println(" ch= " + ch + " gos= " +  Arrays.toString(gos.toArray()));
			nxt = closure(gos);
		}
		if(nxt.isEmpty())
			for(Integer st:nxt) is = is || terminal.contains(st); 
		return is;
	}
	
	// всі стани, в які може перейти автомат по порожнім переходам зі станів start
	public Set<Integer> closure(Set<Integer> start){
		Set<Integer> cls = new TreeSet<>();
		Set<Integer> query = new TreeSet<>(start);
		buildEmpty();
		while (!query.isEmpty()){
			// take any element from set !!!!!
			Integer s = query.iterator().next();
			query.remove(s); cls.add(s);
			//Integer s = ((NavigableSet<Integer>)no).pollFirst();
			//cls.add(s);
			for(Integer ns:empty.get(s))
				if(!cls.contains(ns)) query.add(ns);
		}
		return cls;
	}
	
	//-------------------- build FiniteDeter -------------------------
	public Set<State> allMS , work ;
	public Map<GoingState,State> tranMS ;
	public State init;
	
	public FiniteDeter buildDeter(){
		buildMega();
		Set<Integer> ts = new TreeSet<>();
		Map <Going,Integer> tran = new TreeMap<>();;
		// ----------------coding-----------
		Map<State,Integer> coding = new TreeMap<>();
		int i=1;
		for(State ms:allMS) coding.put(ms,(Integer)(i++));
		for(State ms:allMS){
			if (ms.isTerminal()) ts.add(coding.get(ms));
		}
		for(GoingState mg:tranMS.keySet())
			tran.put(new Going(coding.get(mg.state),mg.ch), coding.get(tranMS.get(mg)));
		return new FiniteDeter(coding.get(init), ts,tran);		
	}
	
	public void buildMega(){
		allMS = new TreeSet<>(); work = new TreeSet<>();
		tranMS = new TreeMap<>();
		init = new State(closure(Collections.singleton(start)));
		allMS.add(init); work.add(init);
		while(!work.isEmpty()){
			// take one State ms
			State ms = work.iterator().next();
			work.remove(ms); //cls.add(s);
			Map<Character,Set<Integer>> stepMs = oneStep(ms.st);
			for(Character ch : stepMs.keySet()){
				State nms = new State(closure(stepMs.get(ch)));
				if(!allMS.contains(nms)){
					allMS.add(nms); work.add(nms);
				}
				tranMS.put(new GoingState(ms,ch), nms);
			}
		}
	}
	
	public Map<Character,Set<Integer>> oneStep(Set<Integer> als){
		Map<Character,Set<Integer>> csm = new TreeMap<>();
		for(GoingOpt go:transitions.keySet()){
			if(als.contains(go.state) && go.ch.isPresent()){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				Integer st = go.state;
				Character ch = go.ch.get();
				Set<Integer> goCh = (csm.containsKey(ch))?csm.get(ch):new TreeSet<>();
				goCh.addAll(transitions.get(go));
				csm.put(ch, goCh);
			}
		}
		return csm;
	}
	
	class State implements Comparable<State>{
		Set<Integer> st;
		State(){
			st = new TreeSet<Integer>();
		}
		State(Set<Integer> s){st = s;}
		public int compareTo(State ns){ 
			int r = st.size()- ns.st.size();
			if (r==0) {
				Iterator<Integer> sti = st.iterator();
				Iterator<Integer> nsi = ns.st.iterator();
				while((r==0) && sti.hasNext()){
					Integer is = sti.next(); 
					Integer in = nsi.next();
					r = is-in;
				}
			}
			//System.out.println("State compare " + this.toString() + " ? " + ns.toString() + " = " + r);
			return r;
		}
		public boolean isTerminal(){
			boolean is = false;
			for(Integer t:st) if(terminal.contains(t)) is=true;
			return is;
		}
		public String toString(){
			return "{" + st.toString() + "}";
		}
	}
	
	class  GoingState implements Comparable<GoingState>{
		State state;
		Character ch;
		GoingState(State ms, Character c){
			state = ms; ch = c;
		}
		public int compareTo(GoingState g){ 
			int r = state.compareTo(g.state);
			if (r==0) r = ch-g.ch;
			return r;
		}
		@Override
		public String toString() {
			return "MetaGoing [state=" + state + ", ch=" + ch + "]";
		}
	}

	private void buildEmpty(){
		if (empty==null){
    		// for any st empty.get(st)= {s1,s2,...,sk} , mayby = {} !!  
			empty  = new TreeMap<>();
			for(Integer st:allState())empty.put(st, new TreeSet<>());
	        for(Map.Entry<GoingOpt,Set<Integer>> gom : transitions.entrySet()){
	           	if(!gom.getKey().ch.isPresent()) empty.put(gom.getKey().state, gom.getValue());
	        }
		}
	}

	private Set<Integer> allState(){
		Set<Integer> res = new TreeSet<>(Collections.singleton(start));
		for(Map.Entry<GoingOpt,Set<Integer>> gom : transitions.entrySet()){
			res.add(gom.getKey().state);
			res.addAll(gom.getValue());
		}
		return res;
	}
	
	class  GoingOpt implements Comparable<GoingOpt>{
		Integer state;
		Optional<Character> ch;
		GoingOpt(Integer s, Optional<Character> c){
			state = s; ch = c;
		}
		public int compareTo(GoingOpt g){ 
			int r = state-g.state;
			if (r==0){
				if (ch.isPresent())	r = (g.ch.isPresent() ? ch.get()-g.ch.get() : 1);				
				else r = (g.ch.isPresent() ? -1 : 0); 
			}	
			return r;
		}
		@Override
		public String toString() {
			return "GoingOpt (state=" + state + ", ch=" + (ch.isPresent()?ch.get():"Eps")+ ")";
		}
	}

	public static String optStr(Optional<Character> ch){
		return (ch.isPresent()?ch.get()+"":"Eps");
	}
	public String toString(){
		String r= "FiniteNondeter: start=" + start + " final=" + Arrays.toString(terminal.toArray());
		r += " transition=<\n   ";
		for(GoingOpt go : transitions.keySet())
			r += " (" + go.state + "," + optStr(go.ch) + ")->" + 
		                     Arrays.toString(transitions.get(go).toArray());
		r += "\n    >";
		return r;
	}
	public String emptyToString(){
		String res="Empty = [";
		for(Integer s:empty.keySet()){
			res += "\n   " + s + "->" +  Arrays.toString(empty.get(s).toArray());
		}
		return res;
	}
}
