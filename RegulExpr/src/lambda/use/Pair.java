package lambda.use;

public class Pair {
	int x, y;
	Pair(){ x=0; y=0;}
	
	Pair(int xi, int yi){
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
