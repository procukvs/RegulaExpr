package regexpr.identify;

import regexpr.finite.Going;

public class Finite {

	
	class Going implements Comparable<Going>{
		int kind; // Eps-go: 0-{}; 1-{fst}; 2-{fst,snd}; Char-go: 3-{ch,fst}
		int ch;
		int fst,snd;
		Going(Integer s, Character c){
			kind=3; fst = s; ch = c;
		}
		
		public int compareTo(Going g){ 
			int r = kind-g.kind;
			if (r==0) r = ch-g.ch;
			return r;
		}
		@Override
		public String toString() {
			return "Going (state=" + kind + ", ch=" + (ch==0?"Eps":ch)+ ")";
		}
	}	
}
