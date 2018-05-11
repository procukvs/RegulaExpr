package syntax;

import syntax.simple.*;

public class MainSyntax {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1 - simple
		//
		int work = 1;
		System.out.println("Hello Syntax ....");
		switch(work){
		case 0: break;
		case 1 : simpleGrammar(); break;
		}
	}
	public static void simpleGrammar(){
		System.out.println("SimpleGrammar ....");
		String[] st = new String[]{"c", "aca", "", "acb"}; 
		ParserG2 p2 = new ParserG2();
		for(int i=0; i<st.length;i++)
			System.out.println("... " + st[i] + " analys: " + p2.analys(st[i]));
	}
	
}
