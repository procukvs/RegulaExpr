package syntax;

import syntax.simple.*;
import syntax.lang.*;
import syntax.lang.SyntaxError;

public class MainSyntax {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1 - simple, 2 - language: Expr 
		//
		int work = 2;
		System.out.println("Hello Syntax ....");
		switch(work){
		case 0: break;
		case 1 : simpleGrammar(); break;
		case 2 : langExpr(); break;
		}
	}
	
	public static void langExpr(){
		System.out.println("Language: Expr ....");
		
		String[] st = new String[]{"0", "(1)", "a", "(((7+5)))", "6-6+7-9+(5)", "((9+1)*4+7)%5*6",
                "+-2","-2", "-6*5/0-2"}; 
		ParserExprAST p = new ParserExprAST();		
		for(int i=0; i<st.length;i++){
			//System.out.println("... " + st[i] + " analys: " + p.analys(st[i]));
			AST r = p.analys(st[i]);
			if (r!=null){
				System.out.println(r.toString());
				try{
					System.out.println("......Значення виразу = " + r.evalExpr());
				} catch(Error ex){System.out.println("----evalAST: " + ex.getMessage());}
			}
		}		
		//ExprParserFull p = new ExprParserFull();
		//String word = "-23 + 45    * 	( 17 - 5 ) ";
		//String file = "test.txt";
		//p.analys(word);
		//System.out.println("... " + word + " analys: " + p.analys(word));
		//System.out.println("..file " + file + " analys: " + p.analysFile(file));
		/*
		LexerExpr lex = new LexerExpr();
		lex.initString(word);
		//lex.initFile("test.txt");
		Token t = lex.nextToken();
        while ( t.type != LexerExpr.EOFT ) {
            System.out.println(t);
            t = lex.nextToken();
        }
        System.out.println(t); // EOF
		*/
	}
	
	public static void simpleGrammar(){
		System.out.println("SimpleGrammar ....");
		//String[] st = new String[]{"c", "aca", "", "acb"}; 
		//ParserG2 p = new ParserG2();
		//String[] st = new String[]{"0", "(1)", "a", "(((7+5)))", "6-6+7-9+(5)"}; 
		//ParserSExpr p = new ParserSExpr();
		String[] st = new String[]{"0", "(1)", "a", "(((7+5)))", "6-6+7-9+(5)", "((9+1)*4+7)%5*6",
				                  "+-2","+2", "-6*5/3-2"}; 
		ParserExprIter p = new ParserExprIter();
		
		//String[] st = new String[]{"(x|y)(1|2)", "x'*", "(ab|c)*", "(a?)a", "(ab)?d+", "(ab|c*"}; 
		//ParserRegExprIter p = new ParserRegExprIter();		
		for(int i=0; i<st.length;i++)
			System.out.println("... " + st[i] + " analys: " + p.analys(st[i]));
		
		//String[] st = new String[]{"ab", "aabbaba", "abba"}; 
		//ParserG1Tree p = new ParserG1Tree();
		/*
		String[] st = new String[]{"0", "(1)", "a", "(5-2)+1", "(((7+5)))", "6-6+7-9+(5)"};
		ParserSExprTree p = new ParserSExprTree();
		for(int i=0; i<st.length;i++){
			System.out.println("... " + st[i] + " analys:");
			SynTree r = p.analys(st[i]);
			if (r!=null)System.out.println(r.toString());
		}
		*/	
	}
	
}
