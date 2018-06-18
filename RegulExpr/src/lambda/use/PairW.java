package lambda.use;

public class PairW {
	int x, y;
	PairW(){ x=0; y=0;}
	
	PairW(int xi, int yi){
		x=xi; y=yi;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}
}
