package syntax.descent;

public class ParserDExpr {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// E -> +TA | -TA | TA   A -> +TA | -TA | Eps    .... 1-6
	// T -> FB    B -> *FB | /FB | %FB | Eps         .... 7-11
	// F -> (E) | 0 | .. | 9                         .... 12-22
	Letter input;
	char next;	
	public ParserDExpr(){
		//input = new Letter("0123456789+-*/%()");
	}
	
	public boolean analys(String word){
		//input.initial(word);
		input = new Letter(word);
		try{
			next = input.nextChar();
			E(); match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}

	void E() throws SyntaxError{
		switch (next){
		case '+': next=input.nextChar(); T(); A();  break;
		case '-': next=input.nextChar(); T(); A();  break;
		default:  T(); A(); break;
		}
	}	
	void A() throws SyntaxError{
		switch (next){
		case '+': next=input.nextChar(); T(); A();  break;
		case '-': next=input.nextChar(); T(); A();  break;
		default: break;
		}		
	}
	void T() throws SyntaxError{
		F(); B();
	}	
	void B() throws SyntaxError{
		switch (next){
		case '*': next=input.nextChar(); F(); B();  break;
		case '/': next=input.nextChar(); F(); B();  break;
		case '%': next=input.nextChar(); F(); B();  break;		
		default: break;
		}
	}	
	void F() throws SyntaxError{
		if (next=='(' ){
			next=input.nextChar(); E(); match(')'); 
		} 
		else if(next<='9' && next>='0')	next=input.nextChar();
	    else throw new SyntaxError("Expecting one from \"0123456789(\", found " + next);
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}
}
