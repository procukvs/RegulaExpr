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
				             new RE(Rtype.Seq, new RE('a'), new RE('a'))),
				      new RE(Rtype.Plus ,new RE('a'))       
				     );
	}
	
	
	
	
	public RE re6(){
		return new RE('a');
	}
}
