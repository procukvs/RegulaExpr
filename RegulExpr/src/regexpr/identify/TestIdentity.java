package regexpr.identify;


public class TestIdentity {
	// (a|b)*c
	public String sre0(){
		return "(a|b)*c";
	}
	public RE re0(){
		return new RE(4,new RE(5,
				           new RE(3, new RE('a'), new RE('b'))),
					    new RE('c')
					 );
	}
	private int[][] go0 ={{1,0,3,5},{3,0,4}, {4,'c',2}, {5,0,7,9},
			{6,0,3,5}, {7,'a',8}, {8,0,6}, {9,'b',10},{10,0,6}};
	public Finite fre0(){
		return new Finite(1,new int[]{2},go0);
	}
	
	// (ab)?d+
	public RE re5(){
		return new RE(4,
				      new RE(7,
				             new RE(4, new RE('a'), new RE('b'))),
				      new RE(6 ,new RE('d'))       
				     );
	}
	// (ab|)dd*
	public RE re5S(){
		return new RE(4,
				      new RE(3,
				             new RE(4, new RE('a'), new RE('b')),new RE(1)),
				      new RE(4 ,new RE('d'), new RE(5,new RE('d')))       
				     );
	}	
	
	
	// Empty ($) {}
	public String sre10(){
		return "$";
	}
	public RE re10(){
		return new RE(0);
	}
	private int[][] go10 ={};
	public Finite fre10(){
		return new Finite(1,new int[]{2},go10);
	}	
	// Eps (#) {""}
	public String sre11(){
		return "#";
	}
	public RE re11(){
		return new RE(1);
	}
	private int[][] go11 ={{1,0,2}};
	public Finite fre11(){
		return new Finite(1,new int[]{2},go11);
	}		
	// Char (a) {"a"}
	public String sre12(){
		return "a";
	}
	public RE re12(){
		return new RE('a');
	}
	private int[][] go12 ={{1,'a',2}};
	public Finite fre12(){
		return new Finite(1,new int[]{2},go12);
	}	
	
	
}
