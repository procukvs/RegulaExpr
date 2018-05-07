package regexpr.identify;

import regexpr.automata.Rexpr;
import regexpr.automata.Rtype;

public class RE {
	// initial: 0-Empty ($) {}, 1-Eps (#) {""}, 2-a (a){"a"}
	// binary operation: 3-Union (r1|r2), 4-Conc (r1.r2) 
	// unary operation: 5-Iter (r*), 6-Plus (r+), 7-Opt (r?)
	
	int wh;        // type expression
	char  ch=0;    // symbol a <-> type=2
	RE fst, snd;// 1+2 operand, 1 <-> type=3-7; 2 <-> type=3-4 
	
	RE() { wh = 0; }
	RE (int c){
		wh = c;
		if(c>1){ 
			wh = 2; ch=(char)c;
		}
	}
	RE(int w, RE re){
		wh = w; fst = re;
	}
	RE(int w, RE re1, RE re2){
		wh = w; fst = re1; snd = re2;
	}
	
	public int getWh() { return wh;}
	public char getCh() { return ch;}
	public RE getFst() {	return fst;	}
	public RE getSnd() {	return snd;	}

	public RE simplify(){
		switch(wh){
		case 3: return new RE(3,fst.simplify(),snd.simplify());
		case 4: return new RE(4,fst.simplify(),snd.simplify()); 
		case 5: return new RE(5,fst.simplify());
		case 6: return new RE(4,fst,new RE(5,fst));
		case 7: return new RE(3,fst,new RE(1));
		default:  return this;
		}
	}
	
	
	public String toString(){
		String r ="";
		switch(wh){
		case 3: r = "(" + fst.toString() + "|" + snd.toString() + ")"; break;//Union 
		case 4: r = fst.toString() + snd.toString(); break;     //Conc
		case 5: r = fst.toString1() + "*"; break;               //Iter
		case 6: r = fst.toString1() + "+"; break;               //Plus
		case 7: r = fst.toString1() + "?"; break;               //Opt
		default:  r = this.toString1();
		}
		return r;
	}
	
	public String toString1(){
		String r ="";
		switch(wh){
		case 0: r = "$"; break;
		case 1: r = "#"; break;
		case 2: r = "" + ch; break;
		case 3: r = toString(); break;    // ?????????????????
		default: r = "(" + toString() + ")";
		}
		return r;
	}		
	

}
