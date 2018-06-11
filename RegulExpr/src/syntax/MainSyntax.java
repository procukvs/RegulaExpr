package syntax;

import syntax.simple.*;
import syntax.lang.*;
import syntax.grammar.*;
import syntax.descent.*; 
import syntax.expr.*; 

import java.util.*;

public class MainSyntax {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 0 - grammar
		// 1 - simple, 2 - GrammarLL1
		// 3 - language: Expr
		// 4 - recursive-descent
		// 5 - syntax.expr
		int work = 5;
		System.out.println("Hello Syntax ....");
		switch(work){
		case 0: workGrammar(); break;
		case 1 : simpleGrammar(); break;
		case 2 : grammarLL1(); break;
		case 3 : langExpr(); break;
		case 4 : descent(); break;
		case 5 : syntaxExpr(); break;
		}
	}

	public static void syntaxExpr(){
		String src[] = {"  -9  ",  "  ( 56 -  2) ", " (6 ",  " 9+ (73 -**)", "\t-23 + 45    * \n	( 17 - 5 ) "};
		// 1 version Letter -> Lexer -> Parser only from STRING
		/*
		Parser p1 = new Parser();
		System.out.println("Syntax expr... ");
		for(int i = 0; i<src.length; i++){
			System.out.println(" expr = " + src[i] + " analys = " + p1.synAnalys(src[i]));
		}
		*/
		// 2 version  Letter=>LetterOld:
		//     Letter-interface + SrcFile + SrcString
		//     Parser --> analysFile(file) + analysStr(word)
		System.out.println("---------------------------------");
		System.out.println("Syntax expr...  any Source .. ");
		Parser pEx = new Parser();
		for(int i = 0; i<src.length; i++){
			System.out.println(" expr = " + src[i] + " analys = " + pEx.analysStr(src[i]));
		}
		System.out.println("---------------------------------");
		System.out.println(" Analys file test.txt = " + pEx.analysFile("test.txt"));
		
		ParserAST pAST = new ParserAST();
		System.out.println("Syntax expr...  AST .... ");
		for(int i = 0; i<src.length; i++){
			syntax.expr.AST tr = pAST.analysStr(src[i]);
			System.out.println(" expr = " + src[i] + " analys = " + tr);
			if (tr!=null) System.out.println("........ value expr = " + tr.evalExpr());
		}
		
		
	}
	
	public static String inString(Set<Character>cs){
		StringBuilder sb = new StringBuilder();
		for(char c:cs) sb.append(c);
		return sb.toString();
	}
	
	public static void grammarLL1(){
		char[] lS1 = {'S','S','S','T','T'};
		String[] rS1 ={"S+T","S-T", "T","(S)","d"};
		char[] lS2 = {'S', 'S', 'S'};
		String[] rS2 = {"aSa", "bSb", ""};
		char[] lS3 = {'S', 'S', 'S'};
		String[] rS3 = {"aSa", "bSb", "c"};	
		char[] lS4 = {'S', 'S', 'S', 'E','E','E','T','T','T','T','F','F'};
		String[] rS4 ={"+E", "-E", "E", "E+T","E-T", "T", "T*F", "T/F", "T%F", "F", "(S)","d"};
		String[] name = {"sExpr", "nPal", "lPal", "fullExpr"};
		GrammarLL1[] gram = {new GrammarLL1(lS1,rS1), new GrammarLL1(lS2,rS2),
					         new GrammarLL1(lS3,rS3), new GrammarLL1(lS4,rS4)};
		for( int i=0; i<gram.length; i++){
			System.out.println("---" + name[i] + "---" );
			System.out.println(gram[i].toString());	
			boolean lfrec = gram[i].leftRecursion();
			System.out.println("Left recurtion ? - " + lfrec);
			if(lfrec){
				gram[i].removeLeft();
				System.out.println("Without lr ==" + gram[i].toString());
			}
			boolean isll1 = gram[i].isLL1();
			System.out.println("isLL1 = " + isll1);
			
			if (!isll1){
				System.out.println("nxt=" + gram[i].getNxt().toString());	
				String testS = gram[i].getTerrors().toString();
				System.out.println("terrors = " + testS);
			} else{
				System.out.println("test=" + gram[i].getTest().toString());
			}
			
		}
		/*
		String[] wd = {"d", "d-d", ""};
		for(int j=0; j<wd.length; j++){
			ArrayList dv = gram[3].analys(wd[j]);
			System.out.println("word " + wd[j] + " is in language " + (dv !=null));
			if (dv !=null) System.out.println(" dv = " + Arrays.toString(dv.toArray()));
		}
		*/
	}
	
	public static void workGrammar(){
		char[] lS = {'S','S','S','T','T', 'T'};
		String[] rS ={"S+T","S-T", "T","(S)","d", ""};
		
		//char[] lS = {'S','S','S','T','T','T','T','F','F'};
		//String[] rS ={"S+T","S-T", "T", "T*F", "T/F", "T%F", "F", "(S)","d"};
	

		Grammar sExpr = new Grammar(lS,rS);
		System.out.println(sExpr.toString());
		Integer[][] td = { {0,2,4,4}, {0,2,4,4,1}, {3}, {0,9}, {0,1,3}, {0,2},{0,2,5,4}};
		for(int i=0; i<td.length; i++){
			ArrayList<Integer> dr = new ArrayList<>(Arrays.asList(td[i])); 
			System.out.println("derivation = " + dr.toString() + " is = " + sExpr.leftDerivation(dr)); 
			if (sExpr.leftDerivation(dr))
				System.out.println(sExpr.buildSynTree(dr));
			
		}
		//System.out.println("Left recurtion ? - " + sExpr.leftRecursion());
		//sExpr.removeLeft();
		//System.out.println(sExpr.toString());
		//Set<Character> terms = sExpr.getTerminals();
		/*
		boolean isll1 = sExpr.isLL1();
		System.out.println("isLL1 = " + isll1);
		System.out.println("test=" + sExpr.test.toString());
		if (!isll1){
			System.out.println("nxt=" + sExpr.nxt.toString());	
			String testS = sExpr.terrors.toString();
			System.out.println("terrors = " + testS);
		}
		*/
		/*
		System.out.println(inString(terms)+ '$' + inString(sExpr.getNonterminals()));
		Character c1 = 'c';
		Character b = 'N';
		System.out.println(""+c1+b);
		*/
		
		/*
		sExpr.buildFst();
		System.out.println("fst=" + sExpr.fst.toString());
		sExpr.buildNxt();
		System.out.println("nxt=" + sExpr.nxt.toString());	
		String[] wd= {"T", "A", "AT","+S"};
		for(int i=0; i<wd.length; i++)
			System.out.println("wd=\"" + wd[i] + "\" first=" + sExpr.first(wd[i]).toString());
		*/
		/*
		Set<Character> ns = new TreeSet<>();
		System.out.println("Set ns0 = " + ns.toString());
		ns.add('A');
		System.out.println("Set ns1 = " + ns.toString());
		ns.add('5');
		System.out.println("Set ns2 = " + ns.toString());
		ns.add('$');
		System.out.println("Set ns3 = " + ns.toString());
		ns.remove('$');
		System.out.println("Set ns2 = " + ns.toString());
		
		*/
	}
	
	public static void langExpr(){
		System.out.println("Language: Expr ....");
		
		String[] st = new String[]{"0", "(1)", "a", "(((7+5)))", "6-6+7-9+(5)", "((9+1)*4+7)%5*6",
                "+-2","-2", "-6*5/0-2"}; 
		ParserExprAST p = new ParserExprAST();		
		for(int i=0; i<st.length;i++){
			//System.out.println("... " + st[i] + " analys: " + p.analys(st[i]));
			syntax.lang.AST r = p.analys(st[i]);
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
	public static void descent(){
		System.out.println("Recursive descent ....");
		//String[] st = new String[]{"c", "abba", "abb", "abbbaab"}; 
		//ParserDG p = new ParserDG();
		String[] st = new String[]{"0", "-(+1)", "a", "(((7+5)))", "6-6+7-9+(5)", "((9+1)*4+7)%5*6",
                "+-2","+2", "-6*5/3-2"}; 
		//syntax.descent.ParserExpr p = new syntax.descent.ParserExpr();		
		ParserIter p = new ParserIter();
		//ParserDEval p = new ParserDEval();
		//String[] st = new String[]{"a", "(a)","(x|y)(a|b)", "xb*", "(ab|c)*", "(a?)a", "(ab)?d+", "(ab|c*", "(ab|c*)"}; 
		//ParserRegul p = new ParserRegul();
		for(int i=0; i<st.length;i++)
			System.out.println("... " + st[i] + " analys: " + p.analys(st[i]));
	}	
	
}
