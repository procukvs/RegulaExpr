package regexpr.identify;

import java.util.*;

public class Finite {
	private Integer start;
	private Set <Integer> terminal;
	private Map <Integer,Going> transitions;

	public Finite (Integer st, Set<Integer> term, Map<Integer,Going> tran){
		start = st; terminal = term; transitions = tran;
	}
	
	// for testing
	// {5,0,7}    = (5 -Eps-> 7)
	// {5,0,8,10} = (5 -Eps-> 8,10)
	// {5,'a',9}  = (5 -a-> 9)
	public Finite(int st, int[] term, int [][] tran){
		start = st;
		terminal = new TreeSet<>();
		for(int i:term) terminal.add(i); 
		transitions = new TreeMap <>();
		for(int[] go:tran){
			//System.out.println(".."+Arrays.toString(go));
			Going g;
			if (go[1]==0){
				if(go.length==3) g = new Going(go[2]);
				else g = new Going(go[2], go[3]);
			} else g = new Going((Character)(char)go[1],go[2]);
			char c = (char)go[1];
			transitions.put(go[0],g);
			//System.out.println("...."+go[0]+"--"+g.toString());
		}
	}		
	// побудова автомата за регулярним виразом 
	public Finite(RE exp){
		start = 1;   
		terminal = new TreeSet<>(Collections.singleton(2));
		transitions = new TreeMap <>();
		int next = make(exp.simplify(),1,2,3);
		//int next = make(exp,1,2,3);
	}
	// будує таблицю переходів, що реалізує вираз e
	// beg+end - початковий і заключний стани, nxt - наступний стан для використання
	private int make(RE e, int beg,int end, int nxt){
		int next = nxt;
		switch(e.getWh()){
		case 0: break;      //Empty ($) {}
		case 1: transitions.put(beg, new Going(end)); break; //Eps (#) {""}
		case 2: transitions.put(beg, new Going((Character)e.getCh(),end)); break; //a (a){"a"}
		case 3: next = make(e.getFst(), nxt, nxt+1, next+4); 
				next = make(e.getSnd(), nxt+2, nxt+3, next);
				transitions.put(beg, new Going(nxt,nxt+2));
				transitions.put(nxt+1, new Going(end));
				transitions.put(nxt+3, new Going(end)); break;//Union (r1|r2)
		case 4: next = make(e.getFst(), beg, nxt, next+2);
				next = make(e.getSnd(), nxt+1, end, next);
				transitions.put(nxt, new Going(nxt+1)); break; //Conc (r1.r2
		case 5: next = make(e.getFst(), nxt, nxt+1, next+2);
		        transitions.put(beg, new Going(nxt,end));
		        transitions.put(nxt+1, new Going(nxt,end));break; //Iter (r*)
		default:           
		}
		return next;
	}

	public boolean accepts(String s){
		boolean is = false;
		String rs = s;
		Set<Integer> nxt = closure(Collections.singleton(start));                       //empty.get(start);
		//System.out.println(" nxt= " +  Arrays.toString(nxt.toArray()));
		//Set<Integer> nxt = new TreeSet<>(closure(empty.get(start)));
		Set<Integer> gos; 
		while (!rs.isEmpty()&& !nxt.isEmpty()){
			int ch = rs.charAt(0);
			rs = rs.substring(1);
			gos = new TreeSet<>();
			for(Integer st:nxt){
				Going go = transitions.get(st);
				if((go.kind==0)&&(go.ch==ch))gos.add(go.fst);
			}
			//System.out.println(" ch= " + ch + " gos= " +  Arrays.toString(gos.toArray()));
			nxt = closure(gos);
		}
		if(rs.isEmpty())
			for(Integer st:nxt) is = is || terminal.contains(st); 
		return is;
	}	
	// всі стани, в які може перейти автомат по порожнім переходам зі станів start
	public Set<Integer> closure(Set<Integer> start){
		Set<Integer> cls = new TreeSet<>(start);
		Set<Integer> query = new TreeSet<>(start);
		while (!query.isEmpty()){
			// take any element from set !!!!!
			Integer s = query.iterator().next();
			query.remove(s); //cls.add(s);
			if (transitions.containsKey(s)){
				//Going go = transitions.get(s);
				//for(int st:go.goEsp()){
				for(int st:transitions.get(s).goEsp() )
					if(!cls.contains(st)){
						cls.add(st); query.add(st);
					}
			}
				/*
				switch(go.kind){
				case 1: if(!cls.contains(go.fst)){
					    	cls.add(go.fst); query.add(go.fst); 
				         }; break;
				case 2: if(!cls.contains(go.fst)){
			    			cls.add(go.fst); query.add(go.fst); 
		         		}; 
		         		if(!cls.contains(go.snd)){
					    	cls.add(go.snd); query.add(go.snd); 
				        }; break;
				 default: break;       
				}
				*/
		}
		return cls;
	}		
		
	@Override
    public String toString(){
		String r= "Finite: start=" + start + " final=" + Arrays.toString(terminal.toArray());
		r += " transition=<\n   ";
		for(Integer st : transitions.keySet())
			r += " (" + st +  transitions.get(st).toString() + ")";
		r += "\n    >";
		return r;
    }
	class Going  {    // implements Comparable<Going>{
		int kind; //Char-go: 0-{ch,fst}, Eps-go: 1-{fst}; 2-{fst,snd};  
		int ch;
		int fst,snd;
		Going(Character c,int s ){
			kind=0; fst = s; ch = c;
		}
		Going(int s){ kind=1; fst = s;	}
		Going(int s1, int s2){ kind=2; fst = s1; snd=s2;	}
		@Override
		public String toString() {
			switch (kind){
			case 0: return "-" + (Character)(char)ch + "->" + fst;	  //(ch==0?"Eps":ch)
			case 1: return "-Eps->" + fst;	
			default: return "-Eps->" + fst + "," + snd;	
			}
		}
		int[] goEsp(){
			int[] rs=new int[0];
			switch(kind){
			case 0: break;
			case 1: rs = new int[1]; rs[0]=fst; break;
			case 2: rs = new int[2]; rs[0]=fst; rs[1]=snd;
			}
			return rs;
		}
		/*
		public int compareTo(Going g){ 
			int r = kind-g.kind;
			if (r==0) r = ch-g.ch;
			return r;
		}
		*/
		@Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        Going other = (Going) obj;
	        if(kind!=other.kind)return false;
	        if(kind==1 && fst!=other.fst)return false;
	        //if(kind==2 && !(fst!=other.fst || snd!=other.snd))return false;
	        if (kind==2){
	        	if (!((fst==other.fst) && (snd==other.snd) ||
	        		(fst==other.snd) && (snd==other.fst))) return false;
	        }
	        return true;
		}    
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Finite other = (Finite) obj;
        if(!start.equals(other.start))return false;
        if(!terminal.equals(other.terminal))return false;
        if(!transitions.equals(other.transitions))return false;
        return true;
	}		

}
