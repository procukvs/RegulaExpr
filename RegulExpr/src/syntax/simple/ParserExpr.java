package syntax.simple;

public class ParserExpr {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// E -> +TA | -TA | TA   A -> +TA | -TA | Eps    .... 1-6
	// T -> FB    B -> *FB | /FB | %FB | Eps         .... 7-11
	// F -> (E) | 0 | .. | 9                         .... 12-22
	Lexer input;
	char next;	
	public ParserExpr(){
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
		switch (next){
		case '+': next=input.next(); T(); A();  break;
		case '-': next=input.next(); T(); A();  break;
		default:  T(); A(); break;
		}
	}	
	void A() throws SyntaxError{
		switch (next){
		case '+': next=input.next(); T(); A();  break;
		case '-': next=input.next(); T(); A();  break;
		default: break;
		}
	}
	void T() throws SyntaxError{
		F(); B();
	}	
	void B() throws SyntaxError{
		switch (next){
		case '*': next=input.next(); F(); B();  break;
		case '/': next=input.next(); F(); B();  break;
		case '%': next=input.next(); F(); B();  break;		
		default: break;
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
