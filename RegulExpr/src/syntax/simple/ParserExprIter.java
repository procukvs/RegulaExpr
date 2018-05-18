package syntax.simple;

public class ParserExprIter {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// E -> ['+' | '-'] T {('-' | '+') T} 
	// T -> F {('*' | '/' | '%') F}        
	// F -> (E) | '0' | .. | '9'   
	Lexer input;
	char next;	
	public ParserExprIter(){
		input = new Lexer("0123456789+-*/%()");
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
		if(next=='+' || next=='-')next=input.next(); 
		T();
		while (next=='+' || next=='-'){
			next=input.next(); T();
		}
	}	
	void T() throws SyntaxError{
		F(); 
		while (next=='*' || next=='/' || next=='%'){
			next=input.next(); F();
		}
	}	
	void F() throws SyntaxError{
		switch (next){
		case '(': next=input.next(); E(); match(')');  break;
		case '0': case '1': case '2': case '3': case '4':
		case '5': case '6':	case '7': case '8': case '9':	
			next=input.next(); break;
		default: throw new SyntaxError("Expecting one from \"0123456789(\", found " + input.getName(next));
		}
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}
}
