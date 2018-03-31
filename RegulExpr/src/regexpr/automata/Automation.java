package regexpr.automata;
import java.util.*;
public class Automation {
	int start;
	int[] terminal;
	Map <Going,int[]> transitions;
	
	Automation(int st, int[] term){
		start = st; terminal = term; 
		transitions = new TreeMap <>();
	}
	
	Automation(int st, int[] term, int [][] tran){
		start = st; terminal = term; 
		transitions = new TreeMap <>();
		for(int i=0; i<tran.length;i++){
			int t[] = new int[tran[i].length-2];
			for(int j=2; j<tran[i].length; j++) t[j-2]=tran[i][j];
			addGo(tran[i][0], (char)tran[i][1], t);
		}
	}
	
	public void addGo(int st, char c, int[] go){
		Going n = new Going(st,c);
		int [] goM = transitions.get(n);
		if(goM!= null){
			goM = merge(goM,go);
		} else goM = go;
		transitions.put(n, goM);
	}
	
	public String toString(){
		String r= "Automation: start=" + start + " final=" + Arrays.toString(terminal);
		r += " transition=[\n   ";
		for(Going go : transitions.keySet())
			r += " (" + go.state + "," + (go.ch==0?"Eps":go.ch) + ")->" + Arrays.toString(transitions.get(go));
		r += "]";
		return r;
	}
	private  class  Going implements Comparable<Going>{
	//private class  Going{
		int state;
		char ch;
		Going(int s, char c){
			state = s; ch = c;
		}
		public int compareTo(Going g){ 
			int r = state-g.state;
			if (r==0) r = ch-g.ch;
			return r;
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

}
