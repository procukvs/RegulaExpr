package syntax.descent;

public class ParserRegul {
	// регул€рний вираз, що задаЇ мову в алфав≥т≥ {a,b,...,x,y,z}
	// E -> T{'|' T}                   
	// T -> F{F}                    
	// F -> P {'*' | '+' | '?'}           
	// P -> '(' E ')' | 'a' | 'b' | ... | 'x' | 'y' | 'z'
	Letter input;
	char next;	
	public ParserRegul(){
		String sl = "()|*+?";
		for(char i='a'; i<='z';i++ ) sl+=i;
		// System.out.println("sl=" + sl); 
		input = new Letter(sl); //"abcdefghijklmnopqrstuvwxyz()|*+?");
	}
	
	public boolean analys(String word){
		input.initial(word);
		try{
			next = input.next();
			E();
			match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	void E() throws SyntaxError{
		//System.out.println("E:next=" + next); 
		T(); 
		while(next=='|'){ 
			next=input.next(); T();
		} 
	}	
	void T() throws SyntaxError{
		//System.out.println("T:next=" + next); 
		F();
		while(next=='(' || next>='a' && next<='z')
			F();
	}	
	void F() throws SyntaxError{
		//System.out.println("F:next=" + next); 
		P(); 
		while (next=='*' || next=='+' || next=='?')
			next=input.next();
	}	
	void P() throws SyntaxError{
		//System.out.println("P:next=" + next); 
		if (next=='(' ){
			next=input.next(); E(); match(')'); 
		} 
		else if(next>='a' && next<='z')	next=input.next();
	    else throw new SyntaxError("Expecting one from \"abc...xyz(\", found " + next);	
	}	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.next();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}	
	
}
