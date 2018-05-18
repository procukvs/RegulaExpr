package regexpr.finite;

public class Going implements Comparable<Going>{
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
