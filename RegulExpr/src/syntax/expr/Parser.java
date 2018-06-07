package syntax.expr;

public class Parser {
	// синтаксичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід: рядок (analys) або файл (analysFile)
	//  expr   ::= [AddOp] term { AddOp term}
	//  term   ::= factor { MulOp factor}
	//  factor ::= '(' expr ')' | Numb
	//  AddOp  ::= '+' | '-'
	//  MulOp  ::= '*' | '/' | '%'
	//  Numb   ::= digit{digit} --- digit=0..9
	Lexer input; 
	Token next;   
    public Parser() {}
  
   /*
    public boolean analysFile(String file){
    	if (input.initFile(file)) return synAnalys();
    	return false;
    }
    
    public boolean analys(String word){
		input.initString(word);
		return synAnalys();
	}    
    */      
    public boolean synAnalys(String src){
    	try{
    		input = new Lexer(new Letter(src));
			next = input.nextToken();
			//System.out.println("analys: " + next.toString());
			expr();
			match(Lexer.EOFT);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;	
    }
    
    void expr() throws SyntaxError{
    	//System.out.println("expr: " + next.toString());
		if(next.type==Lexer.ADDOP) next=input.nextToken(); 
		term();
		while (next.type==Lexer.ADDOP){
			next=input.nextToken();  term();

		}
	}	
	void term() throws SyntaxError{
		//System.out.println("term: " +next.toString());
		factor(); 
		while (next.type==Lexer.MULOP){
			next=input.nextToken();  factor();
			//System.out.println(next.toString());
		}
	}	
	void factor() throws SyntaxError{
		//System.out.println("factor: " + next.toString());
		if(next.type == Lexer.LPAREN){
			next=input.nextToken(); expr(); match(Lexer.RPAREN); 
		} 
		else if (next.type == Lexer.NUMB) next=input.nextToken(); 
		else 
			throw new SyntaxError("Expecting LPAREN or NUMB, found " + input.getTokenName(next.type));
	}   
	
	public void match(int x) throws SyntaxError {
		if ( next.type == x ) next=input.nextToken(); 
		else throw new SyntaxError("expecting "+input.getTokenName(x)+
	                             "; found "+ next);
	}
}
