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
	
	private int[][] go0 ={{1,0,3,5},{3,0,4}, {4,'c',2}, {5,0,7,9},
			{6,0,3,5}, {7,'a',8}, {8,0,6}, {9,'b',10},{10,0,6}};
	public Automation nda0(){
		return new Automation(1,new int[]{2},go0);
	}
	
	public Automation nda0X(){
		int f1[] = new int[1]; f1[0] = 2;
		Automation r = new Automation(1, f1);
		int[] f2 = new int[2];
		f2[0]=3; f2[1]=5; r.addGo(1, (char)0, f2);
		f1[0] = 10; r.addGo(1, 'b', f1);
		return r;
	}
	
	
	
	
	public RE re6(){
		return new RE('a');
	}
}
