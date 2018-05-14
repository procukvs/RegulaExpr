package syntax.simple;

public class ParserRegExpr {
	// регул€рний вираз, що задаЇ мову в алфав≥т≥ {a,b,c,d,x,y,1,2,'}
	// E -> TA   A -> '|'TA | Eps                   .... 1-3
	// T -> FB   B -> FB | Eps                      .... 4-6
	// F -> PC   C -> *C | +C | ?C | Eps            .... 7-11
	// P -> (E) | a | b | c | d | x | y | 1 | 2 | ' .... 12-21
	Lexer input;
	char next;	
	public ParserRegExpr(){
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
		T(); A(); 
	}	
	void A() throws SyntaxError{
		if(next=='|'){ next=input.next(); T(); A();	} // A-> |TA
		else ; // A-> Eps
	}
	void T() throws SyntaxError{
		F(); B();
	}	
	void B() throws SyntaxError{
		if(next!=Lexer.EOF && next!=')' && next!='+' && next!='*' && next!='?' && next!='|') // || next=='|')
			{F(); B();}
	}
	void F() throws SyntaxError{
		P(); C(); 
	}	
	void C() throws SyntaxError{
		switch (next){
		case '*': next=input.next(); C();  break;
		case '+': next=input.next(); C();  break;
		case '?': next=input.next(); C();  break;		
		default: break;
		}
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
