package regexpr.automata;

public class TestData {
	//---------------------- examples
	// (a|b)*c
	public Rexpr re0(){
		return new Rexpr(Rtype.Seq,
					    new Rexpr(Rtype.Rep,
					           new Rexpr(Rtype.Alt, new Rexpr('a'), new Rexpr('b'))),
					    new Rexpr('c')
					 );
	}
	// (x|y)(1|2)
	public Rexpr re1(){
		return  new Rexpr(Rtype.Seq,
					       new Rexpr(Rtype.Alt, new Rexpr('x'), new Rexpr('y')),
					       new Rexpr(Rtype.Alt, new Rexpr('1'), new Rexpr('2'))
					  );
	}
	// x'*	
	public Rexpr re2(){
		return new Rexpr(Rtype.Seq, 
					      new Rexpr('x'),
					      new Rexpr(Rtype.Rep, new Rexpr('\''))
					 );
	}
	// (ab|c)*
	public  Rexpr re3(){
		return  new Rexpr(Rtype.Rep, 
				       new Rexpr(Rtype.Alt, 
				              new Rexpr(Rtype.Seq, new Rexpr('a'), new Rexpr('b')), 
						      new Rexpr('c'))
				       );
	}
	// (a?)a
	public  Rexpr re4(){
		return new Rexpr(Rtype.Seq,
					   new Rexpr(Rtype.Opt, new Rexpr('a')), 
					   new Rexpr('a')
					 );
	}
	// (ab)?d+
	public Rexpr re5(){
		return new Rexpr(Rtype.Seq,
				      new Rexpr(Rtype.Opt,
				             new Rexpr(Rtype.Seq, new Rexpr('a'), new Rexpr('b'))),
				      new Rexpr(Rtype.Plus ,new Rexpr('d'))       
				     );
	}
	public Rexpr re5S(){
		return new Rexpr(Rtype.Seq,
				      new Rexpr(Rtype.Alt,
				             new Rexpr(Rtype.Seq, new Rexpr('a'), new Rexpr('b')),new Rexpr()),
				      new Rexpr(Rtype.Seq ,new Rexpr('d'), new Rexpr(Rtype.Rep,new Rexpr('d')))       
				     );
	}
	// (a|b)*c
	private int[][] go0 ={{1,0,3,5},{3,0,4}, {4,'c',2}, {5,0,7,9},
			{6,0,3,5}, {7,'a',8}, {8,0,6}, {9,'b',10},{10,0,6}};
	public Automation nda0(){
		return new Automation(1,new int[]{2},go0);
	}
	// (x|y)(1|2)
	private int[][] go1 ={{1,0,7,5},{3,0,4}, {4,0,9,11}, {5,'x',6},
			{6,0,3}, {7,'y',8}, {8,0,3}, {9,'1',10},{10,0,2}, {11,'2',12}, {12,0,2}};
	public Automation nda1(){
		return new Automation(1,new int[]{2},go1);
	}
	private int[][] goD1 ={{1,'x',2},{1,'y',2}, {2,'1',3}, {2,'2',3}};
	public Automation da1(){
		return new Automation(1,new int[]{3},goD1);
	}
	
	// x'*	
	private int[][] go2 ={{1,'x',3},{3,0,4}, {4,0,2,5}, {5,'\'',6},	{6,0,2,5}};
	public Automation nda2(){
		return new Automation(1,new int[]{2},go2);
	}
	private int[][] goD2 ={{1,'x',2}, {2,'\'',2}};
	public Automation da2(){
		return new Automation(1,new int[]{2},goD2);
	}
	// (ab|c)*
	private int[][] go3 ={{1,0,2,3},{3,0,5,7}, {4,0,2,3}, {5,'a',9},
			{6,0,4}, {7,'c',8}, {8,0,4}, {9,0,10}, {10,'b',6}};
	public Automation nda3(){
		return new Automation(1,new int[]{2},go3);
	}
	private int[][] goD3 ={{1,'a',2},{1,'c',1}, {2,'b',1}};
	public Automation da3(){
		return new Automation(1,new int[]{1},goD3);
	}
	// (a?)a
	private int[][] go4 ={{1,0,5,7},{3,0,4}, {4,'a',2}, {5,'a',6},
			{6,0,3}, {7,0,8}, {8,0,3}};
	public Automation nda4(){
		return new Automation(1,new int[]{2},go4);
	}
	private int[][] goD4 ={{1,'a',2},{2,'a',3}};
	public Automation da4(){
		return new Automation(1,new int[]{2,3},goD4);
	}
	// (ab)?d+
	private int[][] go5 ={{1,0,7,5},{3,0,4}, {4,'d',11}, {5,'a',9}, {6,0,3}, {7,0,8}, 
			{8,0,3}, {9,0,10},{10,'b',6}, {11,0,12}, {12,0,2,13}, {13,'d',14},{14,0,2,13}};
	public Automation nda5(){
		return new Automation(1,new int[]{2},go5);
	}
	private int[][] goD5 ={{1,'a',3},{1,'d',2}, {2,'d',2}, {3,'b',4}, {4,'d',2}};
	public Automation da5(){
		return new Automation(1,new int[]{2},goD5);
	}
	// x+
	public Rexpr ad1(){
		return new Rexpr(Rtype.Plus, new Rexpr('x'));
	}
	// x?
	public Rexpr ad2(){
		return new Rexpr(Rtype.Opt, new Rexpr('x'));
	}
	public Rexpr re6(){
		return new Rexpr('a');
	}
	
	
}
