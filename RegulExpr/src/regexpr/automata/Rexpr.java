package regexpr.automata;

public class Rexpr  { //implements Comparable <RE> {
	Rtype wh;
	char  ch=0;
	Rexpr    one, two;
	
	Rexpr() { 
		wh = Rtype.Null; 
	}
	Rexpr (char c){
		wh = Rtype.Term; ch=c;
	}
	Rexpr(Rtype w, Rexpr re){
		wh = w; one = re;
	}
	Rexpr(Rtype w, Rexpr re1, Rexpr re2){
		wh = w; one = re1; two = re2;
	}
	
	public Rtype getWh() {
		return wh;
	}
	public char getCh() {
		return ch;
	}
	public Rexpr getOne() {
		return one;
	}
	public Rexpr getTwo() {
		return two;
	}
	/*
	public int compareTo(RE er){
		int res=wh.compareTo(er.wh);
		if(res==0){
			switch(wh){
			case Alt:
			case Seq: res= one.compareTo(er.one);
			          if(res==0)res= two.compareTo(er.two);
			          break;
			case Rep: 
			case Plus: 
			case Opt: res= one.compareTo(er.one); break;
			case Term: res= (ch-er.ch); break;
			case Null: 
			}
		}
		return res;
	}
	*/
	public Rexpr simplify(){
		switch(wh){
		case Seq: return new Rexpr(Rtype.Seq,one.simplify(),two.simplify());
		case Alt: return new Rexpr(Rtype.Alt,one.simplify(),two.simplify());
		case Rep: return new Rexpr(Rtype.Rep,one.simplify());
		case Plus: return new Rexpr(Rtype.Seq,one,new Rexpr(Rtype.Rep,one));
		case Opt: return new Rexpr(Rtype.Alt,one,new Rexpr());
		default:  return this;
		}
	//	return r;
		
	}
	
	public String toString(){
		String r ="";
		switch(wh){
		case Seq: r = one.toString() + two.toString(); break;
		case Alt: r = "(" + one.toString() + "|" + two.toString() + ")"; break;
		case Rep: r = one.toString1() + "*"; break;
		case Plus: r = one.toString1() + "+"; break;
		case Opt: r = one.toString1() + "?"; break;
		default:  r = this.toString1();
		}
		return r;
	}
	
	public String toString1(){
		String r ="";
		switch(wh){
		case Null: break;
		case Term: r = "" + ch; break;
		case Alt: r = toString(); break;
		default: r = "(" + toString() + ")";
		}
		return r;
	}	
	
  
	
}
