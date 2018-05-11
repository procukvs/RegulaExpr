package syntax.simple;

public class ParserG1 {
	Lexer input;
	char next;
	// S -> aSbA | b 
	// A -> baAS | a
	public ParserG1(){
		input = new Lexer("ab");
	}
	public boolean analys(String word){
		input.initial(word);
		try{
			next = input.next();
			S();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			//System.out.println("Error");
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	void S() throws SyntaxError{
		switch (next){
		case 'a': next=input.next(); S();match('b'); A();  break;
		default: match('b');
		}
	}

	void A() throws SyntaxError{
		switch (next){
		case 'b': next=input.next();  match('a'); A(); S(); break;
		default: match('a');
		}
	}	
	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}
}
