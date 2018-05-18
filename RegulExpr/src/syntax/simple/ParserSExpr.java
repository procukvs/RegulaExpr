package syntax.simple;

public class ParserSExpr {
	Lexer input;
	char next;
	// S -> TA 
	// A -> +TA | -TA | Eps 
	// T -> (S) | 0 | ... | 9  
	public ParserSExpr(){
		input = new Lexer("0123456789+-()");
	}
	
	public boolean analys(String word){
		input.initial(word);
		try{
			next = input.next();
			S();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	void S() throws SyntaxError{
		T(); A();
	}
	void A() throws SyntaxError{
		switch (next){
		case '+': next=input.next(); T(); A();  break;
		case '-': next=input.next(); T(); A();  break;
		default: break;
		}
	}
	void T() throws SyntaxError{
		switch (next){
		case '(': next=input.next(); S(); match(')');  break;
		case '0': case '1': case '2': case '3': case '4':
		case '5': case '6':	case '7': case '8': case '9':	
			next=input.next(); break;
		default: throw new SyntaxError("Expecting one from \"0123456789()\", found " + input.getName(next));
		}
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}

}
