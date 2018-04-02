package regexpr.automata;

public class TestData {
	//---------------------- examples
	// (a|b)*c
	public RE re0(){
		return new RE(Rtype.Seq,
					    new RE(Rtype.Rep,
					           new RE(Rtype.Alt, new RE('a'), new RE('b'))),
					    new RE('c')
					 );
	}
	// (x|y)(1|2)
	public RE re1(){
		return  new RE(Rtype.Seq,
					       new RE(Rtype.Alt, new RE('x'), new RE('y')),
					       new RE(Rtype.Alt, new RE('1'), new RE('2'))
					  );
	}
	// x'*	
	public RE re2(){
		return new RE(Rtype.Seq, 
					      new RE('x'),
					      new RE(Rtype.Rep, new RE('\''))
					 );
	}
	// (ab|c)*
	public  RE re3(){
		return  new RE(Rtype.Rep, 
				       new RE(Rtype.Alt, 
				              new RE(Rtype.Seq, new RE('a'), new RE('b')), 
						      new RE('c'))
				       );
	}
	// (a?)a
	public  RE re4(){
		return new RE(Rtype.Seq,
					   new RE(Rtype.Opt, new RE('a')), 
					   new RE('a')
					 );
	}
	// (ab)?d+
	public RE re5(){
		return new RE(Rtype.Seq,
				      new RE(Rtype.Opt,
				             new RE(Rtype.Seq, new RE('a'), new RE('b'))),
				      new RE(Rtype.Plus ,new RE('d'))       
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
	
	
	public RE re6(){
		return new RE('a');
	}
}
