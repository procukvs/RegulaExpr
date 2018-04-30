package regexpr.finite;

public class TestData {
	
	// a+|b+
	private int[][] go0 ={{0,'a',1}, {0,'b',2}, {1,'a',1}, {1,'b',3},
			              {2,'a',3}, {2,'b',2}, {3,'a',3}, {3,'b',3}};
	public FiniteDeter da0(){
		return new FiniteDeter(0,new int[]{1,2},go0);
	}
	
	// (a|b)*c
	private int[][] ngo0 ={{1,0,3,5},{3,0,4}, {4,'c',2}, {5,0,7,9},
			               {6,0,3,5}, {7,'a',8}, {8,0,6}, {9,'b',10},{10,0,6}};
	public FiniteNondeter nda0(){
		return new FiniteNondeter(1,new int[]{2},ngo0);
	}
	// b*a
	private int[][] ngo1 ={{1,0,2,5},{2,'a',3},{2,0,4},{4,0,4},{4,'b',5},{5,0,1}};  //
	public FiniteNondeter nda1(){
		return new FiniteNondeter(1,new int[]{3},ngo1);
	}
}
