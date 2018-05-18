package syntax.simple;

public class ParserRegExprIter {
	// регул€рний вираз, що задаЇ мову в алфав≥т≥ {a,b,c,d,x,y,1,2,'}
	// E -> T{'|' T}                   
	// T -> F{F}                    
	// F -> C {'*' | '+' | '?'}           
	// P -> '(' E ')' | 'a' | 'b' | 'c' | 'd' | 'x' | 'y' | '1' | '2' | '\''
	Lexer input;
	char next;	
	public ParserRegExprIter(){
		input = new Lexer("abcdxy12'|+*?()");
	}
	
	public boolean analys(String word){
		input.initial(word);
		try{
			next = input.next();
			E();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	void E() throws SyntaxError{
		T(); 
		while(next=='|'){ 
			next=input.next(); T();
		} 
	}	
	void T() throws SyntaxError{
		F();
		while(next=='(' || next=='a' || next=='b' || next=='c' || next=='d' || 
			  next=='x' || next=='y' || next=='1' || next=='2'|| next=='\'' )
			F();
	}	
	void F() throws SyntaxError{
		P(); 
		while (next=='*' || next=='+' || next=='?')
			next=input.next();
	}	
	void P() throws SyntaxError{
		switch (next){
		case '(': next=input.next(); E(); match(')');  break;
		case 'a': case 'b': case 'c': case 'd': case 'x':
		case 'y': case '1':	case '2': case '\'':	
			next=input.next(); break;
		default: throw new SyntaxError("Expecting one from \"abcdxy12\'(\", found " + input.getName(next));
		}
	}	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}	
	
}
