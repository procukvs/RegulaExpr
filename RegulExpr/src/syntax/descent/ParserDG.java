package syntax.descent;

public class ParserDG {
	Letter input;
	char next;
	// S -> aSbA | b 
	// A -> baAS | a
	public ParserDG(){
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
		switch (next){
		case 'a': next=input.nextChar(); S();match('b'); A();  break;
		default: match('b');
		}
	}
	void A() throws SyntaxError{
		switch (next){
		case 'b': next=input.nextChar();  match('a'); A(); S(); break;
		default: match('a');
		}
	}	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}
}
