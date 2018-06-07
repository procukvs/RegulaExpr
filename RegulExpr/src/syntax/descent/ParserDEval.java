package syntax.descent;

import syntax.simple.SynTree;

public class ParserDEval {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// E -> ['+' | '-'] T {('-' | '+') T} 
	// T -> F {('*' | '/' | '%') F}        
	// F -> (E) | '0' | .. | '9'  
	// повертає Integer - значення виразу або null
	Letter input;
	char next;	
	public ParserDEval(){
		//input = new Letter("0123456789+-*/%()");
	}
	public Integer analys(String word){
		Integer t=null;
		//input.initial(word);
		input = new Letter(word); 
		try{
			next = input.nextChar();
			t=E();
			match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return null;
		}
		return t;
	}
	Integer E() throws SyntaxError{
		boolean neg = (next=='-');
		if(next=='+' || next=='-')next=input.nextChar(); 
		Integer p, r = T();
		if(neg) r = -r;
		while (next=='+' || next=='-'){
			neg = (next=='-');
			next=input.nextChar(); p=T();
			if (neg) r-=p; else r+=p;
		}
		return r;
	}	
	Integer T() throws SyntaxError{
		Integer p, r = F(); 
		while (next=='*' || next=='/' || next=='%'){
			char op = next;
			next=input.nextChar(); p=F();
			switch(op){
			case '/': r /= p; break;
			case '%': r %= p; break;
			default: r *=p;
			}
		}
		return r;
	}	
	Integer F() throws SyntaxError{
		Integer r=null;
		if (next=='(' ){
			next=input.nextChar(); r=E(); match(')'); 
		} 
		else if(next<='9' && next>='0')	{
			r = next-'0';
			next=input.nextChar();
		}
	    else throw new SyntaxError("Expecting one from \"0123456789(\", found " + next);
		return r;
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}
}
