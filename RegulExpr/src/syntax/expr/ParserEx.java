package syntax.expr;


public class ParserEx {
	// синтаксичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід: рядок (analys) або файл (analysFile)
	//  expr   ::= [AddOp] term { AddOp term}
	//  term   ::= factor { MulOp factor}
	//  factor ::= '(' expr ')' | Numb
	//  AddOp  ::= '+' | '-'
	//  MulOp  ::= '*' | '/' | '%'
	//  Numb   ::= digit{digit} --- digit=0..9
	LexerEx input; 
	Token next;   
    public ParserEx() { }
   
    public AST analysFile(String file){
  		input = new LexerEx(new SrcFile(file));
		return synAnalys();
     }
    
    public AST analysStr(String word){
 		input = new LexerEx(new SrcString(word));
		return synAnalys();
	}    
          
    AST synAnalys(){
    	AST t;
	 	try{
			next = input.nextToken();
			//System.out.println("analys: " + next.toString());
			t=expr();
			match(LexerEx.EOFT);
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
  		if(next.type==LexerEx.ADDOP) {
  			if (next.text.equals("-")) t= new AST(next); 
  			next=input.nextToken();
  		}
  		if (t==null) t=term(); else t.addSon(term());
  		while (next.type==LexerEx.ADDOP){
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
		while (next.type==LexerEx.MULOP){
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
		if(next.type == LexerEx.LPAREN){
			next=input.nextToken(); t=expr(); match(LexerEx.RPAREN); 
		} 
		else if (next.type == LexerEx.NUMB) {
			t = new AST(next); next=input.nextToken(); 
		}
		else 
			throw new SyntaxError("Expecting LPAREN or NUMB, found " + input.getTokenName(next.type));
		return t;
	}      
	void match(int x) throws SyntaxError {
		if ( next.type == x ) next=input.nextToken(); 
		else throw new SyntaxError("expecting "+input.getTokenName(x)+
	                             "; found "+ next);
	}
}
