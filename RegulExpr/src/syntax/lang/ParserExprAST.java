package syntax.lang;

import java.util.*;

public class ParserExprAST {
	// синтаксичний аналіз виразів з цілими числами і проміжками
	//    ............ будуємо AST
	//  AST :: Numb | ('+' | '-' | '*' | '/' | '%') AST AST 
	//  expr   ::= [AddOp] term { AddOp term}
	//  term   ::= factor { MulOp factor}
	//  factor ::= '(' expr ')' | Numb
	//  AddOp  ::= '+' | '-'
	//  MulOp  ::= '*' | '/' | '%'
	//  Numb   ::= digit{digit} --- digit=0..9
	LexerExpr input; 
	Token next;   
    public ParserExprAST() {
    	input = new LexerExpr();
    }
   
    public AST analys(String word){
    	AST t;
		input.initString(word);
	 	try{
			next = input.nextToken();
			//System.out.println("analys: " + next.toString());
			t=expr();
			match(LexerExpr.EOFT);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return null;
		}
		return t;	
	}    
     
    AST expr() throws SyntaxError{
		AST t,t1;
    	//System.out.println("expr: " + next.toString());
		t = null;
		if(next.type==LexerExpr.ADDOP) {
			if (next.text.equals("-")) t= new AST(next); 
			next=input.nextToken();
		}
		if (t==null) t=term(); else t.addSon(term());
		while (next.type==LexerExpr.ADDOP){
			t1 = new AST(next);	next=input.nextToken();
			t1.addSon(t); t1.addSon(term());
			t=t1;
		}
		return t;
	}	
	AST term() throws SyntaxError{
		AST t,t1;
		//System.out.println("term: " +next.toString());
		t = factor(); 
		while (next.type==LexerExpr.MULOP){
			t1 = new AST(next);	next=input.nextToken();
			t1.addSon(t); t1.addSon(factor()); 
			t=t1;
			//System.out.println(next.toString());
		}
		return t;
	}	
	AST factor() throws SyntaxError{
		AST t=null;
		//System.out.println("factor: " + next.toString());
		if(next.type == LexerExpr.LPAREN){
			next=input.nextToken(); t=expr(); match(LexerExpr.RPAREN); 
		} 
		else if (next.type == LexerExpr.NUMB) {
			t = new AST(next); next=input.nextToken(); 
		}
		else 
			throw new SyntaxError("Expecting LPAREN or NUMB, found " + input.getTokenName(next.type));
		return t;
	}   
	
	 public void match(int x) throws SyntaxError {
	        if ( next.type == x ) next=input.nextToken(); 
	        else throw new Error("expecting "+input.getTokenName(x)+
	                             "; found "+ next);
	    }
}
