package syntax.simple;

public class Lexer {
	public static final char EOF = (char)-1;
	String terminals;
	String input;
	int p;
	char c;
	
	public Lexer(String term){
		terminals = term;
	}
	public void initial(String word){
		input=word; p=0; consume();
	}
	public char next() throws SyntaxError{
		char res=c;
		if(c!=EOF)
			if (terminals.contains(""+c)) consume();
			else throw new SyntaxError("Invalid character: " + c);
		return res;
	}
	
	public String getName(char c){
		return (c==EOF?"EOF":String.valueOf(c));
	}
	void consume(){
		if (p<input.length()) c=input.charAt(p++); else c=EOF;
	}

}
