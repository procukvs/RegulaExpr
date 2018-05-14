package syntax.simple;

public class ParserSExprTree {
	Lexer input;
	char next;
	// S -> TA 
	// A -> +TA | -TA | Eps 
	// T -> (S) | 0 | ... | 9  
	public ParserSExprTree(){
		input = new Lexer("0123456789+-()");
	}
	
	public SynTree analys(String word){
		SynTree t=null;
		input.initial(word);
		try{
			next = input.next();
			t=S();
			match(Lexer.EOF);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return null;
		}
		return t;
	}
	
	SynTree S() throws SyntaxError{
		SynTree t = new SynTree('S');
		t.addSon(T()); 
		t.addSon(A());
		return t;		
	}
	SynTree A() throws SyntaxError{
		SynTree t = new SynTree('A');
		switch (next){
		case '+': t.addSon(new SynTree('+')); next=input.next(); 
				  t.addSon(T()); 
				  t.addSon(A());  break;
		case '-': t.addSon(new SynTree('-')); next=input.next(); 
			      t.addSon(T()); 
			      t.addSon(A());  break;
		default: t.addSon(new SynTree()); break;
		}
		return t;		
	}
	SynTree T() throws SyntaxError{
		SynTree t = new SynTree('T');
		switch (next){
		case '(': t.addSon(new SynTree('(')); next=input.next(); 
				  t.addSon(S());
				  t.addSon(new SynTree(')')); match(')');  break;
		case '0': case '1': case '2': case '3': case '4':
		case '5': case '6':	case '7': case '8': case '9':	
			t.addSon(new SynTree(next)); next=input.next(); break;
		default: throw new SyntaxError("Expecting one from \"0123456789()\", found " + input.getName(next));
		}
		return t;		
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + input.getName(c) + 
				                    ", found " + input.getName(next));
	}

}
