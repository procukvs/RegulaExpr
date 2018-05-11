package syntax.simple;

public class ParserG2 {
	Lexer input;
	char next;
	// S -> aSa | bSb  | c 
	public ParserG2(){
		input = new Lexer("abc");
	}
	
	public boolean analys(String word){
		input.initial(word);
		try{
			next = input.next();
			S();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	void S() throws SyntaxError{
		switch (next){
		case 'a': next=input.next(); S(); match('a');  break;
		case 'b': next=input.next(); S(); match('b');  break;
		default: match('c');
		}
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}
}
