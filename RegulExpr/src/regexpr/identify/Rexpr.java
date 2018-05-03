package regexpr.identify;

public class Rexpr {
	// initial: 0-Empty {}, 1-Eps {""}, 2-a {"a"}
	// unary operation: 3-Iter (r*), 4-Plus (r+)
	// binary operation: 5-Union (r1+r2), 6-Conc (r1.r2) 
	int wh;        // type expression
	char  ch=0;    // symbol a <-> type=2
	Rexpr fst, snd;// 1+2 operand, 1 <-> type=3-6; 2 <-> type=5-6 
	
	Rexpr() { wh = 0; }
	Rexpr (char c){
		wh = 3; ch=c;
	}
	Rexpr(int w, Rexpr re){
		wh = 1; fst = re;
	}
	Rexpr(int w, Rexpr re1, Rexpr re2){
		wh = 2; fst = re1; snd = re2;
	}
	
	public int getWh() { return wh;}
	public char getCh() { return ch;}
	public Rexpr getOne() {	return fst;	}
	public Rexpr getTwo() {	return snd;	}
	
}
