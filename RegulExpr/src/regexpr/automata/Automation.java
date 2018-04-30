package regexpr.automata;
import java.util.*;
//import java.util.stream.*;

public class Automation {
	// стани автомату  числа > 0
	private Integer start;
	private Set <Integer> terminal;
	private Map <Going,Set<Integer>> transitions;
	//private int max;
	
	public Automation(Integer st, Set <Integer> term){
		start = st; terminal = term; 
		transitions = new TreeMap <>();
		//max = 0;
	}
	
	public Automation(Integer st, Set <Integer> term, Map <Going,Set<Integer>> tr){
		start = st; terminal = term; 
		transitions = tr;
		//max = 0;
	}
	
	public Automation(int st, int[] term, int [][] tran){
		start = st;// max = st;
		terminal = new TreeSet<>();
		for(int i:term) {
			terminal.add(i); //if(i>max) max=i;
		}
		transitions = new TreeMap <>();
		for(int[] go:tran){
			Character c = (Character)((char)go[1]);
			Going g = new Going((Integer)(go[0]),c);
			//if(go[0] > max) max=go[0];
			Set<Integer> mg = new TreeSet<>();
			for(int i = 2; i<go.length; i++){
				mg.add(go[i]); //if(go[i] > max) max=go[i];
			}
			transitions.put(g,mg);
		}
	}
		
	// реалізація makeNDA, як конструктора автомата за регулярним виразом 
	public Automation(RE exp){
		start = 1;   
		terminal = new TreeSet<>();
		terminal.add(2);
		transitions = new TreeMap <>();
		int next = make(exp.simplify(),1,2,3);
	}
	// будує таблицю переходів, що реалізує вираз e
	// beg+end - початковий і заключний стани, init - наступний стан для використання
	private int make(RE e, int beg,int end, int nxt){
		int next = nxt;
		switch(e.getWh()){
		case Null: addGoing(beg,(char)0, buildSet1(end)); break;
		case Term: addGoing(beg,e.getCh(), buildSet1(end)); break;
		case Seq:  next = make(e.getOne(), beg, nxt, next+2);
				   next = make(e.getTwo(), nxt+1, end, next);
				   addGoing(nxt,(char)0, buildSet1(nxt+1)); break;
		case Alt:  next = make(e.getOne(), nxt, nxt+1, next+4);
		           next = make(e.getTwo(), nxt+2, nxt+3, next);
		           addGoing(beg,(char)0, buildSet2(nxt,nxt+2));
		           addGoing(nxt+1,(char)0, buildSet1(end));
		           addGoing(nxt+3,(char)0, buildSet1(end)); break;
		case Rep:  next = make(e.getOne(), nxt, nxt+1, next+2);
		           addGoing(beg,(char)0, buildSet2(nxt,end));
		           addGoing(nxt+1,(char)0, buildSet2(nxt,end)); 
		default:           
		}
		return next;
	}
	
	private void addGoing(int st, char ch, Set <Integer> to){
		Character c = (Character)((char)ch);
		transitions.put(new Going(st,c), to);
	}
	
	private Set <Integer> buildSet2(int st1, int st2){
		Set <Integer> r = buildSet1(st1);
		r.add(st2);	return r;
	}
	
	private Set <Integer> buildSet1(int st){
		 Set <Integer> r = new TreeSet<>();
		 r.add(st);	 return r;
	}
	
	public boolean accepts(String s){
		return try1(start, s);
	}
	
	// чи допускає автомат слово s, починаючи з одного зі станів sets? 
	//   Умова: автомат не має циклів по порожнім переходам !!!!!
	private boolean accepts1(Set <Integer> sets, String s){
	    boolean no = true;
	    Iterator<Integer> is = sets.iterator();
	    while(is.hasNext() && no){
	    	Integer i = is.next();
	    	no = !((terminal.contains(i) && s.isEmpty()));
	    	if (no) no = !try1(i,s); 
	    };			
	    return !no;
	}
	//чи допускає автомат слово s, починаючи зі стану st?
	private boolean try1(Integer st, String s){
		boolean is = false;
		Going gEsp = new Going(st,(Character)((char) 0));
		if(transitions.containsKey(gEsp)) 
			is = accepts1(transitions.get(gEsp),s);
		if (!is && !s.isEmpty()){
			Going gChar = new Going(st, (Character)s.charAt(0));
			if(transitions.containsKey(gChar)) 
				is = accepts1(transitions.get(gChar),s.substring(1));
		}
		return is;
	}
	// Детермінізація не получається: проблеми з Set<Integer>  в середині колекції !!!!  
	//  --- можливий варіант Set<Integer> використовувати лише в невпорядкованих списках 
	//   --- інший ввести клас State, що містить Set<Integer> і реалізує Comparable !!  
	//
	// список нових станів !!
	private Set<State> metaStates;
	private Map <MetaGoing, State> metaTrans;
	
	public Automation makeDA(){
		Set<Integer> ts = new TreeSet<>();
		Map <Going,Set<Integer>> tran = new TreeMap<>(); 
		metaStates = new TreeSet<>();
		metaTrans = new TreeMap<>();
		Set <Integer> init = new TreeSet();  init.add(start);
		
		State beg = makeDA1(init);
		//System.out.println(" metaStates = " + metaStates.toString());
		//System.out.println(" metaTrans = " + metaTrans.toString());
		// ----------------coding-----------
		Map<State,Integer> coding = new TreeMap<>();
		int i=1;
		for(State ms:metaStates) coding.put(ms,(Integer)(i++));
		for(State ms:metaStates){
			if (ms.isTerm()) ts.add(coding.get(ms));
		}
		for(MetaGoing mg:metaTrans.keySet())
			tran.put(new Going(coding.get(mg.state),mg.ch), buildSet1(coding.get(metaTrans.get(mg))));
		return new Automation(coding.get(beg), ts,tran);
	}
	
	private State makeDA1(Set<Integer> ms){
		//System.out.println(" makeDA1(" + ms.toString()+ ")");
		Set<Integer> mrs = getFrontier(ms);
		State mrsSt = new State(mrs);
	
		if (!metaStates.contains(mrsSt)){  
			//System.out.println(" metaTrans = " + metaTrans.toString());
			metaStates.add(mrsSt);
			for(Character ch:labels(mrs)){
				Set<Integer> nms = new TreeSet<>();
				for(Integer st:mrs){
					Going g = new Going(st,ch);
					if(transitions.containsKey(g))nms.addAll(transitions.get(g)); 
				}
				//System.out.println(" ch = " + ch + " nms = " + nms.toString());
				metaTrans.put(new MetaGoing(mrsSt,ch), makeDA1(nms));
			};
		}
		return mrsSt;
	}
	
	// будує множину всіх станів, що досяжні по порожнім переходам + заключні !!!
	private Set<Integer> getFrontier(Set<Integer> ms){
		Set<Integer> fr = new TreeSet<>();
		Queue<Integer> test = new ArrayDeque<>();
		test.addAll(ms);
		//System.out.println(" ms = "+ ms.toString() + " test = " + test.toString());
		while(!test.isEmpty()){
			Integer st = test.remove();
			Going g = new Going(st,(Character)('\0'));
			if(isFrontier(st)) fr.add(st);
			if(transitions.containsKey(g)) test.addAll(transitions.get(g));
			//System.out.println(" fr = "+ fr.toString() + " test = " + test.toString());
		}
		return fr;
	}
	//labels
	private Set<Character> labels(Set<Integer> ms){
		Set<Character> sc = new TreeSet<>();
		for(Going g:transitions.keySet())
			if(ms.contains(g.state) && !g.ch.equals((Character)'\0')) sc.add(g.ch);
		return sc;
	}
		
	private boolean isFrontier(Integer s){
		boolean no=true;
		Iterator trB = transitions.keySet().iterator();
		while(no && trB.hasNext()){
			Going g = (Going)trB.next();
			if(g.state.equals(s))no = (g.ch == '\0');
		}
		return !no || terminal.contains(s);
	}
		
	public String toString(){
		String r= "Automation: start=" + start + " final=" + Arrays.toString(terminal.toArray());
		r += " transition=[\n   ";
		for(Going go : transitions.keySet())
			r += " (" + go.state + "," + (go.ch==0?"Eps":go.ch) + ")->" + 
		                     Arrays.toString(transitions.get(go).toArray());
		r += "]";
		return r;
	}
	
	public boolean eq(Automation b){
		boolean b1,b2,b3;
		b1 = start.equals(b.start);
		b2 = terminal.equals(b.terminal);
		b3 = (transitions.size() == b.transitions.size());
		if (b3)
			for(Going g:b.transitions.keySet()){
				b3 = b3 && (transitions.containsKey(g));
				if(b3) b3 = transitions.get(g).equals(b.transitions.get(g));
			}
		return b1 && b2 && b3;
	}
	
	// 
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
		public boolean isTerm(){
			boolean is = false;
			for(Integer t:st) if(terminal.contains(t)) is=true;
			return is;
		}
		public String toString(){
			return "{" + st.toString() + "}";
		}
	}
	
	class  MetaGoing implements Comparable<MetaGoing>{
		State state;
		Character ch;
		MetaGoing(State ms, Character c){
			state = ms; ch = c;
		}
		public int compareTo(MetaGoing g){ 
			int r = state.compareTo(g.state);
			if (r==0) r = ch-g.ch;
			return r;
		}
		@Override
		public String toString() {
			return "MetaGoing [state=" + state + ", ch=" + ch + "]";
		}
	}
	
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
	
	
	
	private int[] merge(int[] s1, int[] s2){
		List<Integer> w = new ArrayList<>();
		int[] res = null; 
		int i=0,j=0, r;
		while ((i<s1.length) ||(j < s2.length)){
			if (i<s1.length){
				if (j < s2.length){
					r = s1[i]-s2[j];
					if (r==0) {
						w.add(s1[i++]); j++;
					} else if (r<0) w.add(s1[i++]); 
					else w.add(s2[j++]); 
				} else w.add(s1[i++]);
			} else w.add(s2[j++]); 
		}
		res = new int[w.size()];
		for(i=1; i<res.length; i++) res[i] = w.get(i);
		return res;
	}
   /* 
	private String showMetaState(){
		String r="[";
		for(Set<Integer> is:metaState) r += is.toString(); 
		return r+"]";
	}*/
	
	/*
	Going g = new Going(1,'\0');
	Going g1 = new Going(1,'x');
	Set<Going> gs = transitions.keySet();
	System.out.println("  this Autom = " +  this.toString()); 
	System.out.println("  gs = " +  gs.toString()); 
	System.out.println(" g = " + g.toString() + " g1 = " + g1.toString()+ " g.compareTo(g1) "  +  g.compareTo(g1));
	System.out.println(" g = " + g.toString() + " transitions.containsKey(g) = " + transitions.containsKey(g));
	*/
	//Set <Integer> front = getFrontier(init);
	/*
	State inits = new State(init); 
	State fronts = new State(front); 
	System.out.println(" init = " + init.toString());
	System.out.println(" inits = " + inits.toString());
	System.out.println(" front = " + front.toString());
	System.out.println(" fronts = " + fronts.toString());
	System.out.println(" fronts ? inits = " + fronts.compareTo(inits));
	*/
	/*
	Set <Integer> s1 = new TreeSet<>(); s1.add(9);s1.add(11); 
	State st1 = new State(s1); 
	Set <Integer> s2 = new TreeSet<>(); s2.add(5);s2.add(71); 
	State st2 = new State(s2); 	
	System.out.println( st1.toString() + " ? " + st2.toString() + " = " + st1.compareTo(st2));
	*/
	//System.out.println(" makeDA1(ms)  ms =  " + ms.toString() + " mrs = " + mrs.toString()+ " mrsSt = " + mrsSt.toString());
	//System.out.println(" metaStates init = " + metaStates.toString() + " metaStates.contains(mrsSt) = " + metaStates.contains(mrsSt) );
	//System.out.println("Add metaStates! = " + metaStates.toString());
	//System.out.println(" mrs = " + mrs.toString()+" labels(mrs) = " + labels(mrs).toString());
	/*
	private boolean isInSetMetaState(Set <Set<Integer>> mss, Set<Integer> ms){
		boolean no = true;
		Iterator <Set<Integer>> ims = mss.iterator();
		while(no && ims.hasNext()){
			Set<Integer> ms1=ims.next();
			if(ms1.equals(ms)) no = false;
		}
		return !no;
	}
	
	*/
	//System.out.println(" g = " + g.toString() + " transitions.containsKey(g) = " + transitions.containsKey(g)+
	//		" transitions.get(g) = " + transitions.get(g) + " isFrontier(st) = " + isFrontier(st)   );
}
