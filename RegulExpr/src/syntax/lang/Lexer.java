package syntax.lang;

public abstract class Lexer {
	  public static final char EOF = (char)-1; // represent end of file char
	  public static final int EOFT = 1;    // represent EOF token type
	  String input; // input string
	  int p;    // index into input of current character
	  char c;       // current character

	  public Lexer() {input=""; p=0; c = ' '; }

	  public void initString(String word){
		  input=word; p=0; c = ' '; consume();
	  }
	    
	  /** Move one character; detect "end of file" */
	  void consume(){
		  if (p<input.length()) c=input.charAt(p++); else c=EOF;
	  }
	  /** Ensure x is next character on the input stream */
	  public void match(char x)throws SyntaxError {
		  if ( c == x) consume();
		  else throw new SyntaxError("expecting "+x+"; found "+c);
	  }   
	    
	  public abstract Token nextToken()throws SyntaxError;
	  public abstract String getTokenName(int tokenType);
}
