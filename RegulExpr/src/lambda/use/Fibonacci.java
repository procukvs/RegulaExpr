package lambda.use;

import java.util.stream.*;
public class Fibonacci {
	
	void fibonacciIter(){
		System.out.println("Fibonacci - Iter"); 
		int[] f = new int[10];
		f[0] = 1; f[1] = 1;
		System.out.println(f[0]);
		System.out.println(f[1]);
		for(int i=2; i<10; i++) {
			f[i] = f[i-2]+ f[i-1];
			System.out.println(f[i]); 
		}
	}

	class Pair {
		int f, s;
		Pair(int fi, int si){
			f=fi; s=si;
		}
		int fst() {return f;}
		int snd() {return s;}
	}	
	void fibonacciStream(){
		System.out.println("Fibonacci - Stream"); 
		System.out.println(1);
		Stream.iterate(new Pair(1,1), p-> new Pair(p.snd(),p.fst()+p.snd()))
		      .limit(9)
		      .forEachOrdered(p->System.out.println(p.snd()));
	}
	
	
	public static void main(String[] args) {
		Fibonacci work = new Fibonacci();
		work.fibonacciIter();
		work.fibonacciStream();
	}
}

