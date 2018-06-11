package syntax.descent;

public class ParserG {
	Letter input;
	char next;
	// S -> aSbA | b 
	// A -> baAS | a
	public ParserG(){
		//input = new Letter("ab");
	}
	public boolean analys(String word){
		//input.initial(word);
		input = new Letter(word);
		try{
			next = input.nextChar();
			S(); match('$');
		} catch(SyntaxError ex){
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	void S() throws SyntaxError{
		if(next=='a'){
			next=input.nextChar(); S();match('b'); A();
		}
		else match('b');
	}
	void A() throws SyntaxError{
		if(next=='b'){
			next=input.nextChar();  match('a'); A(); S();
		}
		else match('a');
	}	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}
}
