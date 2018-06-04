package syntax.descent;

public class Letter {
	// $ - end of string 
	String terminals;
	String input;
	int p;
	char c;
	
	public Letter(String term){
		terminals = term;
	}
	public void initial(String word){
		input=word; p=0; consume();
	}
	public char next() throws SyntaxError{
		char res=c;
		if(c!='$')
			if (terminals.contains(""+c)) consume();
			else throw new SyntaxError("Invalid character: " + c);
		return res;
	}
	void consume(){
		if (p<input.length()) c=input.charAt(p++); else c='$';
	}
}
