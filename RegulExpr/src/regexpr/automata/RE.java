package regexpr.automata;

public class RE {
	Rtype wh;
	char  ch=0;
	RE    one, two;
	
	RE() { 
		wh = Rtype.Null; 
	}
	RE (char c){
		wh = Rtype.Term; ch=c;
	}
	RE(Rtype w, RE re){
		wh = w; one = re;
	}
	RE(Rtype w, RE re1, RE re2){
		wh = w; one = re1; two = re2;
	}
	
	public Rtype getWh() {
		return wh;
	}
	public char getCh() {
		return ch;
	}
	public RE getOne() {
		return one;
	}
	public RE getTwo() {
		return two;
	}
	public RE simplify(){
		switch(wh){
		case Seq: return new RE(Rtype.Seq,one.simplify(),two.simplify());
		case Alt: return new RE(Rtype.Alt,one.simplify(),two.simplify());
		case Rep: return new RE(Rtype.Rep,one.simplify());
		case Plus: return new RE(Rtype.Seq,one,new RE(Rtype.Rep,one));
		case Opt: return new RE(Rtype.Alt,one,new RE());
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
