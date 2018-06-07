package syntax.descent;

public class ParserDIter {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// E -> ['+' | '-'] T {('-' | '+') T} 
	// T -> F {('*' | '/' | '%') F}        
	// F -> (E) | '0' | .. | '9'   
	Letter input;
	char next;	
	public ParserDIter(){
		//input = new Letter("0123456789+-*/%()");
	}
	public boolean analys(String word){
		//input.initial(word);
		input = new Letter(word);
		try{
			next = input.nextChar();
			E();
			match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	void E() throws SyntaxError{
		if(next=='+' || next=='-')next=input.nextChar(); 
		T();
		while (next=='+' || next=='-'){
			next=input.nextChar(); T();
		}
	}	
	void T() throws SyntaxError{
		F(); 
		while (next=='*' || next=='/' || next=='%'){
			next=input.nextChar(); F();
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
