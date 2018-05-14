package syntax.simple;

public class ParserG1Tree {
	Lexer input;
	char next;
	// S -> aSbA | b 
	// A -> baAS | a
	public ParserG1Tree(){
		input = new Lexer("ab");
	}
	
	public SynTree analys(String word){
		SynTree t=null;
		input.initial(word);
		try{
			next = input.next();
			t=S();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			//System.out.println("Error");
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return null;
		}
		return t;
	}
	
	SynTree S() throws SyntaxError{
		SynTree t = new SynTree('S');
		switch (next){
		case 'a': t.addSon(new SynTree('a')); next=input.next(); 
			      t.addSon(S());
			      t.addSon(new SynTree('b')); match('b');
			      t.addSon(A());  break;
		default: t.addSon(new SynTree('b')); match('b');
		}
		return t;
	}
	
	SynTree A() throws SyntaxError{
		SynTree t = new SynTree('A');
		switch (next){
		case 'b': t.addSon(new SynTree('b')); next=input.next();  
		          t.addSon(new SynTree('a')); match('a'); 
		          t.addSon(A()); 
		          t.addSon(S()); break;
		default: t.addSon(new SynTree('a')); match('a');
		}
		return t;
	}	
	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}
}
